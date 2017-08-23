package com.android.training.unionsep;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewLocalWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_local_web_view);

        WebView localWebView = (WebView) findViewById(R.id.localWebView);
        localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.setWebChromeClient(new WebChromeClient());

        localWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                InputStream is;

                try {
                    URL u = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) u.openConnection();
                    con.setRequestMethod("GET");
                    con.setDoOutput(true);
                    con.connect();
                    is = con.getInputStream();

                    String path = Environment.getExternalStorageDirectory() + "/apdroid/";
                    String fileName = url.substring(url.lastIndexOf("/") + 1);
                    File dir = new File(path);
                    dir.mkdirs();
                    File outputFile = new File(dir, fileName);
                    FileOutputStream fos = new FileOutputStream(outputFile);

                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    is.close();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String name = Environment.getExternalStorageDirectory() + "/apdroid/" + url.substring(url.lastIndexOf('/', + 1));
                    intent.setDataAndType(Uri.fromFile(new File(name)), "application/vnd.android.package-archive");
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }





//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setType(mimetype);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);

            }
        });
        localWebView.loadUrl("file:///android_asset/index.html");
    }

}
