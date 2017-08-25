package com.android.training.unionsep;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewLocalWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_local_web_view);

        WebView localWebView = (WebView) findViewById(R.id.localWebView);
        localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        localWebView.setWebChromeClient(new WebChromeClient());
        localWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                String savedFilePath = saveJPEG(url);
                addGallery(savedFilePath);

                Toast.makeText(ViewLocalWebViewActivity.this, "保存しました", Toast.LENGTH_LONG).show();

                return true;
            }
        });

        localWebView.loadUrl("file:///android_asset/index.html");
    }

    private String saveJPEG(final String base64Character) {
        System.out.println("##### debug base64 character : " + base64Character);

        if (Build.VERSION.SDK_INT >= 23) {
            int selfPermission = ContextCompat.checkSelfPermission(ViewLocalWebViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        ViewLocalWebViewActivity.this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        1
                );
            }
        }

//        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/android-training/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.out.println("##### error save directory");
                return null;
            }
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        File jpegPath = new File(dir.getAbsolutePath() + "/" + "kbz_" + sdf.format(date) + ".jpg");
        System.out.println("##### jpeg path : " + jpegPath);

        String data = base64Character.replaceFirst("data:image/jpeg;base64,", "");
        byte[] bytes = Base64.decode(data, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(jpegPath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException ioe) {

        } finally {
            try {
                fos.close();
            } catch (IOException e) {

            }
        }

        return jpegPath.getAbsolutePath();
//        }
    }

    private void addGallery(String absolutePath) {
        File file = new File(absolutePath);

        ContentValues values = new ContentValues();
        ContentResolver resolver = getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.SIZE, file.length());
        values.put(MediaStore.Images.Media.TITLE, file.getName());
        values.put(MediaStore.Images.Media.DATA, file.getPath());

        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
