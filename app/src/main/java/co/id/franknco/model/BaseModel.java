package co.id.franknco.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseModel {
    public String getString(JSONObject json, String key) {
        try {
            return json.has(key) && !json.getString(key).equals("null") ? json.getString(key) : "";
        }catch (JSONException ignore) {
            return "";
        }
    }

    public int getInt(JSONObject json, String key) {
        try {
            return json.has(key) ? json.getInt(key): 0;
        }catch (JSONException ignore) {
            return 0;
        }
    }

    public double getDouble(JSONObject json, String key){
        try {
            return json.has(key) ? json.getDouble(key) : 0;
        }catch (JSONException ignore){
            return 0;
        }
    }

    public long getLong(JSONObject json, String key){
        try {
            return json.has(key) ? json.getLong(key) : 0;
        }catch (JSONException ignore){
            return 0;
        }
    }
    public boolean getBoolean(JSONObject json, String key, boolean def){
        try {
            return json.has(key) ? json.getBoolean(key) : def;
        }catch (JSONException ignore){
            return def;
        }
    }

    public JSONObject getJSON(JSONObject json, String key){
        try {
            return json.has(key) ? json.getJSONObject(key) : new JSONObject();
        }catch (JSONException ignore){
            return new JSONObject();
        }
    }
    public JSONObject getJSON(JSONArray array, int position){
        try {
            return array.getJSONObject(position);
        }catch (JSONException ignore){
            return new JSONObject();
        }
    }
    public Object get(JSONObject json, String key){
        try {
            return json.has(key) ? json.get(key) : new Object();
        }catch (JSONException ignore){
            return new Object();
        }
    }
    public JSONArray getJSONArray(JSONObject json, String key){
        try {
            return json.has(key) ? json.getJSONArray(key) : new JSONArray();
        }catch (JSONException ignore){
            return new JSONArray();
        }
    }

}
