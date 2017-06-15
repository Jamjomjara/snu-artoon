package baryberri.a4_4_simplewebbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText URLEditText;
    Button goButton;
    Button backButton;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URLEditText = (EditText)findViewById(R.id.URLEditText);
        goButton = (Button)findViewById(R.id.goButton);
        backButton = (Button)findViewById(R.id.backButton);
        webView = (WebView)findViewById(R.id.webView);

        webView.setWebViewClient(new ThisWebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInputURL = URLEditText.getText().toString();
                if (!userInputURL.startsWith("http://")) {
                    userInputURL = "http://" + userInputURL;
                }
                URLEditText.setText(userInputURL);
                webView.loadUrl(userInputURL);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });
    }

    private class ThisWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
}
