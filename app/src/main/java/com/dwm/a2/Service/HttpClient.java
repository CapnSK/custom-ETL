package com.dwm.a2.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import com.dwm.a2.Enums.HttpMethodType;



//https://www.baeldung.com/java-http-request - how to fetch response from remote server

public class HttpClient {
    private static HttpClient client;
    private static LogService logger = LogService.getLogger(HttpClient.class);

    private HttpClient(){
    }

    public static HttpClient getInstance(){
        if(client == null){
            client = new HttpClient();
        }
        return client;
    }

    public String invokeUsingGet(String url, Map<String, String> params){
        if(params != null && !params.isEmpty()){
            url = appendParamsToUrl(url, params);
        }
        return invoke(url, HttpMethodType.GET);
    }

    private String invoke(String urlStr, HttpMethodType method){
        String response = null;
        if(urlStr != null && method != null){
            switch(method){
                case GET:
                    try {
                        URL url = new URL(urlStr);
                        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("GET");
                        try(BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                            StringBuilder content = new StringBuilder();
                            String inputLine = "";
                            while((inputLine = input.readLine()) != null){
                                content.append(inputLine);
                            }
                            response = content.toString();
                        }
                        connection.disconnect();
                    } catch (IOException e) {
                        logger.log(Arrays.toString(e.getStackTrace()));
                    }
                    break;
                case POST:
                    break;
                default:
                    break;
            }
        }
        return response;
    }

    private String appendParamsToUrl(String url, Map<String, String> params){
        StringBuilder effectiveUrl = new StringBuilder(url);
        params.entrySet().stream().forEach((entry) ->{
            String key = entry.getKey();
            String value = entry.getValue();

            if(!effectiveUrl.toString().contains("?")){
                effectiveUrl.append("?");
            } else if(effectiveUrl.toString().endsWith("?")){
                // Do Nothing already ? added
            } else if(effectiveUrl.toString().contains("?")){
                // Url has more than 0 parameters
                effectiveUrl.append("&");
            }

            effectiveUrl.append(key);
            effectiveUrl.append("=");
            effectiveUrl.append(value);
        });
        return effectiveUrl.toString();
    }
}
