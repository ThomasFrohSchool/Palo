package coms309.Spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import top.jfunc.json.impl.JSONArray;
import top.jfunc.json.impl.JSONObject;

import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "Spotify Controller", description = "REST API's relating to connecting with the Spotify API")
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

    @ApiOperation(value = "Get a Spotify OAuth Token")
    @GetMapping(path = "/token")
    public String token() {
        return getToken();
    }

    @ApiOperation(value = "Search for an Album, Artist, or Track")
    @GetMapping(path = "/search")
    public String search(@ApiParam(value = "String that will be searched", required = true) @RequestParam("q") String q) {
        String authToken = getToken();

        StringBuilder query = new StringBuilder("https://api.spotify.com/v1/search?q=");
        Scanner scan = new Scanner(q);
        while(scan.hasNext()){
            query.append(scan.next());
            if(scan.hasNext()){
                query.append("%20");
            }
        }

        query.append("&type=artist%2Calbum%2Ctrack&market=US&limit=10");

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

           //StringBuilder albumBuilder = new StringBuilder("Albums: ");
            //StringBuilder artistBuilder = new StringBuilder("Artists: ");
            //StringBuilder trackBuilder = new StringBuilder("Tracks: ");


            String id;

            JSONArray albumArray = new JSONArray();
            JSONArray artistArray = new JSONArray();
            JSONArray trackArray = new JSONArray();

            for(int i = 0; i<4; i++){
                for(int k = 0; k<11; k++){
                    if(i ==0){ //albums
                        if(k == 0){
                            albumItems = new JSONArray(albums.getString("items"));
                        }

                        if(albumItems.size() > k){
                            albumItem = new JSONObject(albumItems.get(k).toString());

                            id = albumItem.getString("id");
    

                            albumArray.put(album(id));

                            
                            //albumBuilder.append("#" + k + " :  Name: \"" + name + "\"  ID: \"" + id+ "\"  ");
                        }


                    }else if(i == 1){//artists
                        
                        if(k == 0){
                            artistItems = new JSONArray(artists.getString("items"));
                        }
                        if(artistItems.size() > k){
                            artistItem = new JSONObject(artistItems.get(k).toString());


                            id = artistItem.getString("id");

                            artistArray.put(artist(id));

                            //artistBuilder.append("#" + k + " :  Name: \"" + name + "\"  ID: \"" + id+ "\"  ");
                        }


                    }else if(i == 2){//tracks
                        if(k == 0){
                            trackItems = new JSONArray(tracks.getString("items"));
                        }
                        if(trackItems.size() > k){
                            trackItem = new JSONObject(trackItems.get(k).toString());

                            
                            id = trackItem.getString("id");

                            
                            trackArray.put(track(id));
                            //trackBuilder.append("#" + k + " :  Name: \"" + name + "\"  ID: \"" + id+ "\"  ");
    
                        }

                    }
                }
            }


            

            //StringBuilder bob = new StringBuilder("{")

            JSONObject myObj = new JSONObject();
            myObj.put("albums", albumArray);
            myObj.put("artists", artistArray);
            myObj.put("tracks", trackArray);
            //myObj.put("status", "Down so bad im depressed");


            
            


            return "{\"albums\":" +  albumArray.toString() + ", \"artists\":" +  artistArray.toString() + ", \"tracks\":" +  trackArray.toString() + "}";




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Didnt make it thru";
    }




    @ApiOperation(value = "Get info about an Album by ID")
    @GetMapping(path = "/getAlbum")
    private String getAlbum(@ApiParam(value = "ID that will be used to find the Album", required = true) @RequestParam("id") String id){
        return album(id).toString();
    }

    


    @ApiOperation(value = "Get info about a Track by ID")
    @GetMapping(path = "/getTrack")
    private String getTrack(@ApiParam(value = "ID that will be used to find the Track", required = true) @RequestParam("id") String id){
        return track(id).toString();
    }

    
    @ApiOperation(value = "Get info about a Artist by ID")
    @GetMapping(path = "/getArtist")
    private String getArtist(@ApiParam(value = "ID that will be used to find the Artist", required = true) @RequestParam("id") String id){
        return artist(id).toString();
    }


    private JSONObject album(String id){

        StringBuilder query = new StringBuilder("https://api.spotify.com/v1/albums/");

        
        query.append(id);
        query.append("?market=US");
        String url = query.toString();
        
        JSONObject myResponse = new JSONObject(getByURL(url));

        JSONArray images = new JSONArray(myResponse.getString("images"));
        JSONObject image = new JSONObject(images.getString(1));      //get the middle image, 300x300
        String imageURL = image.getString("url");

        JSONArray artists = new JSONArray(myResponse.getString("artists"));
        
        if(!(artists.size() > 0)){ //TODO Check for multiple artists
            return null;
            
            
        }//else{
            //multiple artists TODO
       // }

       JSONObject artist = new JSONObject(artists.getString(0));
       String artistName = artist.getString("name");
       String albumName = myResponse.getString("name");

       JSONObject linkObj = new JSONObject(myResponse.getString("external_urls"));
       String link = linkObj.getString("spotify");

       JSONObject myObj = new JSONObject();
       myObj.put("name", albumName);
       myObj.put("artist", artistName);
       myObj.put("imageUrl", imageURL);
       myObj.put("link", link);
       myObj.put("id", id);
        


        return myObj;
    }

    private JSONObject artist(String id){
        

        StringBuilder query = new StringBuilder("https://api.spotify.com/v1/artists/");

        
        query.append(id);
        String url = query.toString();
        
        JSONObject myResponse = new JSONObject(getByURL(url));

        JSONArray images = new JSONArray(myResponse.getString("images"));

        JSONObject myObj = new JSONObject();
        if(images.size() > 0){
            JSONObject image = new JSONObject(images.getString(1));      //get the middle image, 300x300
            String imageURL = image.getString("url");
            myObj.put("imageUrl", imageURL);
        }else{
            myObj.put("imageUrl", null);
        }

       String artistName = myResponse.getString("name");

       JSONObject linkObj = new JSONObject(myResponse.getString("external_urls"));
       String link = linkObj.getString("spotify");

       myObj.put("artist", artistName);
       myObj.put("link", link);
       myObj.put("id", id);
        


        return myObj;
    }

    private JSONObject track(String id){
        

        StringBuilder query = new StringBuilder("https://api.spotify.com/v1/tracks/");

        
        query.append(id);
        query.append("?market=US");
        String url = query.toString();
        
        JSONObject myResponse = new JSONObject(getByURL(url));
        String playback = myResponse.getString("preview_url");
        JSONObject album = new JSONObject(myResponse.getString("album"));
        JSONArray images = new JSONArray(album.getString("images"));
        JSONObject image = new JSONObject(images.getString(1));      //get the middle image, 300x300

        
        String imageURL = image.getString("url");

        JSONArray artists = new JSONArray(myResponse.getString("artists"));
        
        if(!(artists.size() > 0)){ //TODO Check for multiple artists
            return null;
            
            
        }//else{
            //multiple artists TODO
       // }

       JSONObject artist = new JSONObject(artists.getString(0));
       String artistName = artist.getString("name");
       String albumName = myResponse.getString("name");

       JSONObject linkObj = new JSONObject(myResponse.getString("external_urls"));
       String link = linkObj.getString("spotify");
       
       JSONObject myObj = new JSONObject();
    

       myObj.put("name", albumName);
       myObj.put("playbackLink", playback);
       myObj.put("artist", artistName);
       myObj.put("imageUrl", imageURL);
       myObj.put("link", link);
       myObj.put("id", id);
        


        return myObj;
    }


    private String getByURL(String url){
        String authToken = getToken();
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