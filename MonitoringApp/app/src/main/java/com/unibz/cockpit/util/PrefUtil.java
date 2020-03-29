package com.unibz.cockpit.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.unibz.cockpit.model.User;

import java.lang.reflect.Type;

public class PrefUtil {

    private static SharedPreferences sharedpreferences;;
    private static SharedPreferences.Editor editor;
    private static Gson gson;
    private static final String PREF_NAME = "APP_PREF";

    //KEYS
    public static final String PREF_USER = "USER";




    private static void createEditor(Context context){
        if(sharedpreferences == null){
            sharedpreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            gson = new Gson();
        }
    }

    public static void savePreference(Context context, String key, Object value){
        createEditor(context);
        if(value instanceof Integer){
            editor.putInt(key, ((Integer) value).intValue());
        }
        if(value instanceof String){
            editor.putString(key, value.toString());
        }
        if(value instanceof Float){
            editor.putFloat(key, ((Float) value).floatValue());
        }
        if(value instanceof Boolean){
            editor.putBoolean(key, ((Boolean) value).booleanValue());
        }
        if(value instanceof Long){
            editor.putLong(key, ((Long) value).longValue());
        }
        editor.commit();
    }

    public static void savePreferenceObject(Context context, String key, Object value){
        createEditor(context);
        editor.putString(key, gson.toJson(value));
        editor.commit();
    }

    public static void removePreference(Context context,String key){
        createEditor(context);
        editor.remove(key);
        editor.commit();
    }

    public static String getStringPreference(Context context, String key){
        createEditor(context);
        return sharedpreferences.getString(key,"");
    }

    public static Float getFloatPreference(Context context, String key){
        createEditor(context);
        return sharedpreferences.getFloat(key,0);
    }

    public static Integer getIntegerPreference(Context context, String key){
        createEditor(context);
        return sharedpreferences.getInt(key,0);
    }

    public static Boolean getBooleanPreference(Context context, String key){
        createEditor(context);
        return sharedpreferences.getBoolean(key,false);
    }

    public static Long getLongPreference(Context context, String key){
        createEditor(context);
        return sharedpreferences.getLong(key,0);
    }

    public static User getUserPreference(Context context, String key){
        createEditor(context);
        String json = sharedpreferences.getString(key, "");
        return gson.fromJson(json, User.class);
        //return json;
    }

    public static boolean isKeyExist(Context context, String key){
        createEditor(context);
        return sharedpreferences.contains(key);
    }

    public static void clearPreferences(Context context){
        createEditor(context);
        editor.clear();
        editor.commit();
    }

}
