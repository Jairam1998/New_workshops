package com.example.official;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

public class PostRequest extends AsyncTask <Properties,Integer,String> {

    private ProgressBar progressBar;
    private PostResponseHandler handler;
    private String service;

    public PostRequest (PostResponseHandler handler, ProgressBar progressBar, String service) {

        this.service = service;
        this.progressBar = progressBar;
        this.handler = handler;
    }

    @Override
    protected void onPostExecute(String response) {

        super.onPostExecute(response);
        progressBar.setVisibility(View.INVISIBLE);
        handler.handlePostResponse(response);
    }

    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }


    protected void onProgressUpdate(Integer...progress) {
        progressBar.setProgress(progress[0]);
    }


    protected String doInBackground(Properties...args) {

        String requestUrl = Constants.SERVICE_BASE + service;
        return performPostCall(requestUrl,args[0]);
    }

    private String  performPostCall(String requestURL,
                                    Properties postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            Log.e("CarteBlanche","Exception",e);
        }

        return response;
    }

    private String getPostDataString(Properties params) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Properties.Entry entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey().toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }

        return result.toString();
    }

}