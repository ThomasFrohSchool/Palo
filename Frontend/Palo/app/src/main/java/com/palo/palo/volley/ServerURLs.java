package com.palo.palo.volley;

public class ServerURLs {
    //server address
    private static final String ROOT = "https://29f8a8f6-4b69-4628-be72-4b7c59cfe36b.mock.pstmn.io/";

//    private static final String ROOT = "http://coms-309-021.cs.iastate.edu:8080/";

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
}
