package com.abercrombie.codetest.fragments;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.abercrombie.codetest.R;

public class WebViewFragment extends BaseFragment {
  private static String URL = "URL";
  @BindView(R.id.web_view) WebView webView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;

  public static WebViewFragment newInstance(String url) {
    WebViewFragment fragment = new WebViewFragment();
    Bundle bundle = new Bundle();
    bundle.putString(URL, url.replaceAll("\\\\","") );
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_webview;
  }

  @Override public void initView() {
    progressBar.setVisibility(View.VISIBLE);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient() {
      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (isAdded())
        progressBar.setVisibility(View.GONE);
      }
    });
    webView.loadUrl(getArguments().getString(URL));
  }

  public ProgressBar getProgressBar() {
    return progressBar;
  }
}
