package com.paypal.heresdk.sampleapp.ui;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paypal.heresdk.sampleapp.R;
import com.paypal.heresdk.sampleapp.login.LocalPreferences;
import com.paypal.heresdk.sampleapp.login.LoginActivity;
import com.paypal.paypalretailsdk.RetailSDK;

/**
 * Created by muozdemir on 1/17/18.
 */

public class WebViewActivity extends Activity
{
  private static final String LOG_TAG = WebViewActivity.class.getSimpleName();
  public static final String INTENT_URL_WEBVIEW = "URL_FOR_WEBVIEW";
  public static final String INTENT_URL_RESULT = "URL_RESULT";
  public static final String INTENT_ISLIVE_WEBVIEW = "ISLIVE_FOR_WEBVIEW";

  String mUrl;
  boolean mIsLive;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    Log.d(LOG_TAG, "onCreate");
    setContentView(R.layout.webview_activity);

    Intent intent = getIntent();

    if (intent.hasExtra(INTENT_URL_WEBVIEW))
    {
      mUrl = (String) intent.getSerializableExtra(INTENT_URL_WEBVIEW);
      Log.d(LOG_TAG, "onCreate url:" + mUrl);
      startWebView();
    }
  }

  private void startWebView()
  {
    Log.d(LOG_TAG, "startWebView url: " + mUrl);

    final WebView webView = (WebView) findViewById(R.id.id_webView);

    webView.getSettings().setJavaScriptEnabled(true);
    webView.requestFocus(View.FOCUS_DOWN);
    webView.setWebViewClient(new WebViewClient()
    {
      public boolean shouldOverrideUrlLoading(WebView view, String url)
      {
        Log.d(LOG_TAG, "shouldOverrideURLLoading: url: " + url);
        if (url != null && RetailSDK.getBraintreeManager().isBtReturnUrlValid(url)) {
          Log.d(LOG_TAG, "shouldOverrideURLLoading: GOOD url: " + url);
          Intent returnIntent = new Intent();
          returnIntent.putExtra(INTENT_URL_RESULT,url);
          setResult(Activity.RESULT_OK, returnIntent);
          onBackPressed();
          return true;
        }
        return false;
      }
    });

    webView.loadUrl(mUrl);
  }

}
