package com.example.official;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;

public class Utils {


    public static Properties getJSONObject(JSONObject jsonObject) throws Exception {

        Iterator iterator = jsonObject.keys();
        Properties result = new Properties();

        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            result.put(key,jsonObject.getString(key));
        }

        return result;
    }

    public static List<Properties> getJSONObjects(String jsonString) throws Exception {

        List<Properties> jsonObjects = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObjects.add(getJSONObject(jsonObject));
        }

        return jsonObjects;
    }

    public static String getDataJsonString(Context context, String jsonString) throws Exception {

        Log.d(Constants.LOGTAG,"RESPONSEJSON:" + jsonString);

        Properties props = getJSONObject(new JSONObject(jsonString));

        String message = (String)props.get(Constants.RESPONSE_MESSAGE_NAME);
        String status = (String)props.get(Constants.RESPONSE_STATUS_NAME);
        String body = "";

        if (Constants.RESPONSE_SUCCESS_VALUE.equals(status)) {

            body = (String)props.get(Constants.RESPONSE_DATA_NAME);
            Log.d(Constants.LOGTAG,"BODY:" + body);

        } else {

            Log.d(Constants.LOGTAG,"HELLO:"+message);
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }

        return body;
    }

}
