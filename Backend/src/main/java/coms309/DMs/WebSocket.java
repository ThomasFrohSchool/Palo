package coms309.DMs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{from}/{to}")  // this is Websocket url
public class WebSocket {

  // cannot autowire static directly (instead we do it by the below
  // method
	private static MessageTable msgTable; 

	/*
   * Grabs the MessageRepository singleton from the Spring Application
   * Context.  This works because of the @Controller annotation on this
   * class and because the variable is declared as static.
   * There are other ways to set this. However, this approach is
   * easiest.
	 */
	@Autowired
	public void setMessageRepository(MessageTable table) {
		msgTable = table;  // we are setting the static variable
	}

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(WebSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("from") String fromUser,@PathParam("to") String touser) 
      throws IOException {

		logger.info("Entered into Open");

    // store connecting user information
		sessionUsernameMap.put(session, fromUser+":"+touser);
		//sessionUsernameMap.put(session, touser+":"+fromUser);
		usernameSessionMap.put(fromUser+":"+touser, session);
		//usernameSessionMap.put(touser+":"+fromUser, session);

		//Send chat history to the newly connected user
		sendMessageToPArticularUser(fromUser, touser, getChatHistory(fromUser,touser));
		
	}


	@OnMessage
	public void onMessage(Session session, String message) throws IOException {

		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		String fromUser = sessionUsernameMap.get(session).split(":")[0];

    // Direct message to a user using the format "@username <message>"
		
		String toUser = sessionUsernameMap.get(session).split(":")[1];

      // send the message to the sender and receiver
		sendMessageToPArticularUser(toUser, fromUser, fromUser + ": " + message);
		sendMessageToPArticularUser(fromUser, toUser, fromUser + ": " + message);

		

		// Saving chat history to repository
		msgTable.save(new Message(fromUser, toUser, message));
	}


	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

    // remove the user connection information
		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}


	private void sendMessageToPArticularUser(String fromuser, String toUser, String message) {
		try {
			usernameSessionMap.get(fromuser+":"+toUser).getBasicRemote().sendText(message);
		} 
    catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
		catch(NullPointerException e){
		}
	}

  // Gets the Chat history from the repository
	private String getChatHistory(String fromName, String toName) {
		List<Message> messages = msgTable.findByfromUser(fromName);
		List<Message> dm = new ArrayList<Message>();
		for(int i=0;i<messages.size();i++){
			if(messages.get(i).gettoUser().equals(toName)){
				dm.add(messages.get(i));
			}
		}
		List<Message> toMessages = msgTable.findBytoUser(fromName);
		for(int i=0;i<toMessages.size();i++){
			if(toMessages.get(i).getfromUser().equals(toName)){
				dm.add(toMessages.get(i));
			}
		}
		//List<Message> messages = msgTable.findAll();
    // convert the list to a string
		StringBuilder sb = new StringBuilder();
		if(dm != null && dm.size() != 0) {
			for (Message d : dm) {
				sb.append(d.getfromUser() + ": " + d.getContent() + "\n");
			}
		}
		return sb.toString();
	}

} 
