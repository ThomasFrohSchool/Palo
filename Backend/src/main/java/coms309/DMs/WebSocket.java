package coms309.DMs;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@Controller 
@ServerEndpoint(value = "/chat/{from}/{to}")  
public class WebSocket {


	private static MessageTable msgTable; 


	@Autowired
	public void setMessageRepository(MessageTable table) {
		msgTable = table;  
	}


	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(WebSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("from") String fromUser,@PathParam("to") String touser) 
      throws IOException {

		logger.info("Entered into Open");


		sessionUsernameMap.put(session, fromUser+":"+touser);
		usernameSessionMap.put(fromUser+":"+touser, session);

		sendMessageToPArticularUser(fromUser, touser, getChatHistory(fromUser,touser));
		
	}


	@OnMessage
	public void onMessage(Session session, String message) throws IOException {


		logger.info("Entered into Message: Got Message:" + message);
		String fromUser = sessionUsernameMap.get(session).split(":")[0];


		
		String toUser = sessionUsernameMap.get(session).split(":")[1];

		sendMessageToPArticularUser(toUser, fromUser, fromUser + ": " + message);
		sendMessageToPArticularUser(fromUser, toUser, fromUser + ": " + message);

		msgTable.save(new Message(fromUser, toUser, message));
	}


	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
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




        dm.sort((e1, e2) ->
            (e1.getSent().toString()).compareTo((e2.getSent().toString())));
			

		StringBuilder sb = new StringBuilder();
		if(dm != null && dm.size() != 0) {
			for (Message d : dm) {
				sb.append(d.getfromUser() + ": " + d.getContent() + "\n");
			}
		}

		


		return sb.toString();
	}

} 
