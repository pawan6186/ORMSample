package com.assignment.pawan.soreboard.util;


public class URLHelper {


    public static String API_ENDPOINT = "http://hackerearth.0x10.info/api/gyanmatrix";
    public static String RECORDS = "?type=json&query=list_player";
    public static final String none = "";

    public static String getAPIEndpointURL(String requstAPI) {
        return String.format("%s%s", API_ENDPOINT, requstAPI);
    }


}
