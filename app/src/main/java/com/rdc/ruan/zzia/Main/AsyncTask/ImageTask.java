package com.rdc.ruan.zzia.Main.AsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ruan on 2015/3/11.
 */
public class ImageTask  extends AsyncTask {
    ImageView imageView;
    String url;
    ProgressBar progressBar;
    public ImageTask(String url,ImageView imageView,ProgressBar progressBar) {
        super();
        this.url=url;
        this.imageView=imageView;
        this.progressBar=progressBar;
    }

    @Override
    protected Object doInBackground(Object[] params){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        char[] cookie=url.toCharArray();
        String str="";
        Log.i("Log", url + "====");
        for (int i=0;i<50;i++){
            str+=cookie[i];
        }
        str=str+"CheckCode.aspx";

        try {
            myFileUrl = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        /**
         * Bitmap缩放2倍
         */
        Bitmap temp =(Bitmap)o;
        Matrix matrix = new Matrix();
        matrix.postScale(2,2);
        Bitmap bitmap = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(),
                matrix, true);
        imageView.setImageBitmap(bitmap);
        imageView.setAlpha(100f);
        //imageView.setImageBitmap((Bitmap)o);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
