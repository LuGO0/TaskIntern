package com.android.taskintern.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.taskintern.R;
import com.android.taskintern.Utils.TextRecognitionUtils;
import com.android.taskintern.Utils.Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class MainActivity extends AppCompatActivity {

    HashMap<String,String> mKey= new HashMap<>();
    Bitmap mSelectedImage;
    TextView mTextView, mTaggedTextView;
    ImageView mImageView;
    Button mTagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTagButton=(Button)findViewById(R.id.tag);
        mTextView=(TextView)findViewById(R.id.interpreted_string);
        mTaggedTextView =(TextView)findViewById(R.id.tagged_string);
        mImageView=(ImageView)findViewById(R.id.image_to_be_processed);

        //setting up the key for POS tagging
        Utils.setupHashMap(mKey);

        //getting the hardCoded Image setup
        mSelectedImage = Utils.getBitmapFromAsset(this, "aid.jpg");
        mImageView.setImageBitmap(mSelectedImage);

        //running the text recognition
        TextRecognitionUtils.runTextRecognition(mSelectedImage,mTextView);
    }


    public void onClickButton(View view) {

        //disabling the button
        mTagButton.setEnabled(false);

        //Loading sentence detector model
        InputStream modelStream = null;
        POSModel model = null;

        POSTaggerME tagger = null;

        //getting the extracted text from the google Vision APIs
        String sentences = mTextView.getText().toString();

        //Setting up system properties which otherwise resulted in improper decoding of the binary file
        // of the model while apps execution....source stackOverflow
        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
        try {
            AssetFileDescriptor fileDescriptor = getAssets().openFd("en-pos-maxent.bin");
            FileInputStream inputStream = fileDescriptor.createInputStream();
            POSModel posModel = new POSModel(inputStream);
            tagger = new POSTaggerME(posModel);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String mAns="";

        try {
            if (tagger != null) {

                String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(sentences);
                String[] tags = tagger.tag(whitespaceTokenizerLine);

                for (int i = 0; i < whitespaceTokenizerLine.length; i++) {
                    String word = whitespaceTokenizerLine[i].trim();
                    String tag = tags[i].trim();
                    mAns += ( mKey.get(tag)+ " : " + word+"  ");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        mTaggedTextView.setText(mAns);
        mTagButton.setEnabled(true);
    }
}
