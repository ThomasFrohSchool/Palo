package com.palo.palo.volley;

/**
 * ServerURLs contains paths to server. This contains the server's root address and all the paths to the routes to the server.
 */
public class ServerURLs {
    //server address
//    private static final String ROOT = "https://29f8a8f6-4b69-4628-be72-4b7c59cfe36b.mock.pstmn.io/";

    private static final String ROOT = "http://coms-309-021.cs.iastate.edu:8080/";

    /* login uses post (see example request/response)
       REQUEST: { "user": { "email": "test@test.com",  "password": "testPassword" } }
       RESPONSE: { "error": "false", "message": "Login Successful", "user": { "username": "TESTuser", "email": "asdf@test.com", "password": "zxcvb", "id": 4 } }
    */
    public static final String LOGIN = ROOT + "login";

    /* register uses post (see example request/response)
       REQUEST: { "user": { "username": "TESTuser", "email": "test@test.com",  "password": "testPassword" } }
       RESPONSE: { "error": "false", "message": "Login Successful", "user": { "username": "TESTuser", "email": "asdf@test.com", "id": 4 } }
    */
    public static final String REGISTER = ROOT + "register";
    
    public static final String SEARCH = ROOT + "search?q=";
    public static final String CREATE_POST = ROOT + "createPost/";
    public static final String FEED = ROOT + "feed/";
    public static final String USER_BY_ID = ROOT + "user/";

    public static String ATTACHMENT(int type){
        switch (type){
            case 0: return ROOT + "getAlbum?id=";
            case 1: return ROOT + "getArtist?id=";
            case 2: return ROOT + "getTrack?id=";
            default: return "";
        }
    }

    public static final String GET_COMMENTS = ROOT + "posts/getcomments/";
}
