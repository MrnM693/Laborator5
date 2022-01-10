package cc.cunbm.android.asyncimagedownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> url = new ArrayList<>();
    ImageView img;
    Button downloadBtn;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url.add("https://i.pinimg.com/564x/2e/bf/8c/2ebf8c78bd65cf3c1b7e5ff0ef8ff3ac.jpg");
        url.add("https://i.pinimg.com/564x/04/b5/59/04b5590f089b72e055a315b845da72a2.jpg");
        url.add("https://i.pinimg.com/564x/85/0b/e8/850be82c910633dc8c75cce74ffdd372.jpg");

        img = (ImageView) findViewById(R.id.imageView1);
        downloadBtn = (Button) findViewById(R.id.button);
        //ONCLICK
        downloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Downloader().execute(url);


            }
        });

    }

    private class Downloader extends AsyncTask<ArrayList<String>, Bitmap, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //CREATE PD,SET PROPERTIES
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Image Downloader");
            pd.setMessage("Downloading...");
            pd.setIndeterminate(false);
            //pd.show();
        }

        @Override
        protected Bitmap doInBackground(ArrayList<String>... url) {

            ArrayList<String> myurl = url[0];
            Bitmap bm = null;
            InputStream is = null;

            for (int i = 0; i < myurl.size(); i++) {
                try {
                    is = new URL(myurl.get(i)).openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Thread.sleep(2000);
                    publishProgress(bm);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... imagine) {
            super.onProgressUpdate(imagine);
            img.setImageBitmap(imagine[0]);
            pd.hide();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            pd.dismiss();
        }
    }
}