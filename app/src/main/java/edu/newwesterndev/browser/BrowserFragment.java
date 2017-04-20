package edu.newwesterndev.browser;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BrowserFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Position";
    private static final String ARG_PARAM2 = "urlKey";

    private int position;
    private String url;


    public BrowserFragment() {
        // Required empty public constructor
    }

    public static BrowserFragment newInstance(int position, String url) {
        BrowserFragment fragment = new BrowserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putString(ARG_PARAM2, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
            url = getArguments().getString(ARG_PARAM2);
        } else {
            Log.d("Arguments", "getArguments doesn't have any args");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webViewFragment);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Toast.makeText(getContext(), request.getUrl().toString(), Toast.LENGTH_SHORT).show();
                view.loadUrl(request.getUrl().toString());
                url = request.getUrl().toString();
                return true;
            }
        });

        if(this.url != null) {
            webView.loadUrl(this.url);
        } else {
            webView.loadUrl("https://developer.android.com/");
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("url", url);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
