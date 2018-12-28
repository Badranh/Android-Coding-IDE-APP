package net.cryptobrewery.mediumarticleproject.CustomViews;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class CachingFactory {
    private static Map<String,Typeface> FontMap = new HashMap<>();

    public static Typeface  getFont(String name,AssetManager assetManager){
        if(FontMap.containsKey(name)){
            return FontMap.get(name);
        }else{
            Typeface typeface =  Typeface.createFromAsset(assetManager, name);
            if(typeface != null)
                 FontMap.put(name,typeface);
            else
                throw new RuntimeException("Font Not Found");
            return typeface;
        }
    }

}
