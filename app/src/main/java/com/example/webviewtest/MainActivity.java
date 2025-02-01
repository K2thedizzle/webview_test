package com.example.webviewtest;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private WebView mywebview;
    private final Handler handler = new Handler();
    private final int refreshInterval = 5000; // Refresh every 1 seconds

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            mywebview.reload();
            handler.postDelayed(this, refreshInterval);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mywebview = findViewById(R.id.mywebview);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.loadUrl("https://www.mhofuapps.store/mlb-witt");

        // Start auto-refresh
        handler.postDelayed(refreshRunnable, refreshInterval);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(refreshRunnable); // Stop refreshing when activity is destroyed
    }
}
