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
import java.util.Scanner;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aj.org.objectweb.asm.Type;
import top.jfunc.json.impl.JSONArray;
import top.jfunc.json.impl.JSONObject;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SpotifyController {


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


            JSONObject myResponse = new JSONObject(response.toString());

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

        StringBuilder query = new StringBuilder("https://api.spotify.com/v1/search?q=");
        Scanner scan = new Scanner(q);
        while(scan.hasNext()){
            query.append(scan.next());
            if(scan.hasNext()){
                query.append("%20");
            }
        }

        query.append("&type=artist%2Calbum%2Ctrack&market=US&limit=3");

        String url = query.toString();
        URL obj;

        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestProperty("Authorization", "Bearer " + authToken);
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestMethod("GET");   

            int responseCode = con.getResponseCode();

            if(responseCode != 200){
                return String.valueOf(responseCode);
            }
            

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));           //reading input
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject myResponse = new JSONObject(response.toString());

            JSONObject albums = new JSONObject(myResponse.getString("albums"));
            JSONObject artists = new JSONObject(myResponse.getString("artists"));
            JSONObject tracks = new JSONObject(myResponse.getString("tracks"));

            JSONArray albumItems = null;   //array of items
            JSONArray artistItems = null;
            JSONArray trackItems = null;

            JSONObject albumItem; //individual item
            JSONObject artistItem;
            JSONObject trackItem;

            StringBuilder albumBuilder = new StringBuilder("Albums: ");
            StringBuilder artistBuilder = new StringBuilder("Artists: ");
            StringBuilder trackBuilder = new StringBuilder("Tracks: ");


            String name;
            String id;

            for(int i = 0; i<4; i++){
                for(int k = 0; k<4; k++){
                    if(i ==0){ //albums
                        if(k == 0){
                            albumItems = new JSONArray(albums.getString("items"));
                        }

                        if(albumItems.size() > k){
                            albumItem = new JSONObject(albumItems.get(k).toString());

                            name = albumItem.getString("name");
                            id = albumItem.getString("id");
    
                            albumBuilder.append("#" + k + " :  Name: \"" + name + "\"  ID: \"" + id+ "\"  ");
                        }


                    }else if(i == 1){//artists
                        
                        if(k == 0){
                            artistItems = new JSONArray(artists.getString("items"));
                        }
                        if(artistItems.size() > k){
                            artistItem = new JSONObject(artistItems.get(k).toString());

                            name = artistItem.getString("name");
                            id = artistItem.getString("id");
                            artistBuilder.append("#" + k + " :  Name: \"" + name + "\"  ID: \"" + id+ "\"  ");
                        }


                    }else if(i == 2){//tracks
                        if(k == 0){
                            trackItems = new JSONArray(tracks.getString("items"));
                        }
                        if(trackItems.size() > k){
                            trackItem = new JSONObject(trackItems.get(k).toString());

                            name = trackItem.getString("name");
                            id = trackItem.getString("id");
                            trackBuilder.append("#" + k + " :  Name: \"" + name + "\"  ID: \"" + id+ "\"  ");
    
                        }

                    }
                }
            }

            /*
            //JSONObject 
            JSONObject albumItem;
            JSONObject artistItem;
            JSONObject trackItem;
            String type;
            String id;

            StringBuilder toRet = new StringBuilder("");

            */
            /*
            for(int i = 0; i<4;i++){
                albumItem = new JSONObject(albumItems.get(i).toString());
                artistItem = new JSONObject(artistItems.get(i).toString());
                trackItem = new JSONObject(trackItems.get(i).toString());

                type = albumItem.getString("type");
                id = albumItem.getString("id");

                toRet.append("Type: " + type + " ID: " + id + "\n");

                type = artistItem.getString("type");
                id = artistItem.getString("id");

                toRet.append("Type: " + type + " ID: " + id + "\n");

                type = trackItem.getString("type");
                id = trackItem.getString("id");

                toRet.append("Type: " + type + " ID: " + id + "\n");
            }
            */


            //String fxnResponse = getAlbum(id);


            return (albumBuilder.toString() + "\n" + artistBuilder.toString()+ "\n" + trackBuilder.toString());




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "didnt make it thru";
    }



    private String getAlbum(String id){
        String authToken = getToken();

        StringBuilder query = new StringBuilder("https://api.spotify.com/v1/albums/");

        
        query.append(id);

        String url = query.toString();
        URL obj;

        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestProperty("Authorization", "Bearer " + authToken);
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestMethod("GET");   

            int responseCode = con.getResponseCode();

            if(responseCode != 200){
                return String.valueOf(responseCode);
            }
            

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));           //reading input
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject myResponse = new JSONObject(response.toString());
            return myResponse.toString();




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "didnt make it thru";
    }
    
}