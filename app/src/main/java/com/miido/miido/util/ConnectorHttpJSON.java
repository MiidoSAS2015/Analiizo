package com.miido.miido.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Alvaro on 03/03/2015.
 */
public class ConnectorHttpJSON {

    private String url;


    public ConnectorHttpJSON(String url){
        this.url = url;
    }

    public /*JSONObject*/ String execute() throws IOException, JSONException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(this.url);
        HttpResponse response = client.execute(post);
        String feed = inputStreamToString(response.getEntity().getContent());
        String jsonWithWrongEnd = feed.split("fields_structure\\(")[1];
        return jsonWithWrongEnd;
        //JSONObject object = new JSONObject(jsonWithWrongEnd.substring(0, jsonWithWrongEnd.length() - 1));
        //return object;
        //return null;
    }

    private String inputStreamToString(InputStream is) throws UnsupportedEncodingException {
        String line = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                "utf-8"), 8);
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        } catch (Exception ex) {
        }
        return sb.toString();
    }

}
