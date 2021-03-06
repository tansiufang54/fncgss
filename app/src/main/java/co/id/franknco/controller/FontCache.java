package co.id.franknco.controller;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by GSS-NB-2017-0013 on 12/18/2017.
 */

public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface getTypeface(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}