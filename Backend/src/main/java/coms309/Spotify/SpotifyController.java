package coms309.Spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import top.jfunc.json.impl.JSONObject;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SpotifyController {

    private String success = "\"message\":\"success\"";
    private String failure = "\"message\":\"failure\"";

    private String getToken() {
        try {
            String url = "https://accounts.spotify.com/api/token";
            URL obj = new URL(url);

            Map<String, String> body = new LinkedHashMap<>();
            body.put("client_id", "b82ef89345fa42a7893a0f199d64439f");
            body.put("client_secret", "92ca45fcd0ee42239ca5a259c88baf11");
            body.put("grant_type", "client_credentials");

            StringBuilder postData = new StringBuilder();

            for (Map.Entry bod : body.entrySet()) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(String.valueOf(bod.getKey()), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(bod.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes);

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            System.out.println(response.toString());

            JSONObject myResponse = new JSONObject(response.toString());
            System.out.println(myResponse.getString("access_token"));

            return myResponse.getString("access_token");

        } catch (Exception e) {
            return e.toString();
        }
    }

    @GetMapping(path = "/token")
    public String token() {
        return getToken();
    }

    @GetMapping(path = "/search")
    public String search(@RequestParam("q") String q) {
        String authToken = getToken();

        String url = "https://api.spotify.com/v1/search";
        

        return q;
    }
}