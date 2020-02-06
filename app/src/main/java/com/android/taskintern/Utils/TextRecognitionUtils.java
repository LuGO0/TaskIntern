package com.android.taskintern.Utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;

public class TextRecognitionUtils {

    public static String processTextRecognitionResults(FirebaseVisionText texts){

        //..
        String mString="";

        List<FirebaseVisionText.TextBlock> blocks=texts.getTextBlocks();

        if(blocks.size()==0){
            //no text was found
            Log.i("alternate run","no text was found");
        }

        for(int j=0;j<blocks.size();j++) {
            List<FirebaseVisionText.Line> lines=blocks.get(j).getLines();
            for(int i=0;i<lines.size();i++){
                List<FirebaseVisionText.Element> elements=lines.get(i).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    mString +=elements.get(k).getText()+" ";
                }
            }
        }

        return mString;
    }


    public static void runTextRecognition(Bitmap mSelectedImage, final TextView mTextView){


        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionTextRecognizer detector= FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        //disable the process button
        detector.processImage(image)
            .addOnSuccessListener(
                new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        //enable the process button
                        String res=processTextRecognitionResults(firebaseVisionText);
                        mTextView.setText(res);
                    }
                }
            );
    }
}
