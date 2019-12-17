package com.example.official;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

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

}
