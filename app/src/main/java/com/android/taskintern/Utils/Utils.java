package com.android.taskintern.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Utils {

    public static void setupHashMap(HashMap<String,String> key) {
        key.put("CC","Coordinating Juntion");
        key.put("CD","Cardinal number");
        key.put("DT","Determiner");
        key.put("EX","Existential there");
        key.put("FW","Foreign Word");
        key.put("IN","Preposition");
        key.put("JJ","Adjective");
        key.put("JJR","Adjective comparative");
        key.put("JJS","Adjective superlative");
        key.put("NN","Noun");
        key.put("NNS","Noun plural");
        key.put("NNP","Proper Noun");
        key.put("NNPS","Proper Noun Plural");
        key.put("PDT","Predeterminer");
        key.put("NP","Noun Phrase");
        key.put("PP","Prepositional Phrase");
        key.put("VP","Verb Phrase");
        key.put("RB","Adverb");
        key.put("UH","Interjection");
        key.put("VB","Verb");
        key.put("WP","Pronoun");
    }

    /*
     * This function takes in image assets in the folder and uses them
     * uses assetManager for that purpose and handles exceptions as well
     */
    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
