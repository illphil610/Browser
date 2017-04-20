package edu.newwesterndev.browser;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String HOME_PAGE = "https://developer.android.com/index.html";

    Toolbar myToolbar;
    EditText editText;
    Button goButton;
    ViewPager viewPager;
    Uri data;
    String currentUrl;
    int currentFrag, maxFrag;
    WebAdapter webAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.showOverflowMenu();

        data = getIntent().getData();

        currentFrag = 1;
        maxFrag = 1;

        if(data != null) {
            currentUrl = data.toString();
        } else {
            currentUrl = HOME_PAGE;
        }

        goButton = (Button) findViewById(R.id.urlButton);
        editText = (EditText) findViewById(R.id.urlEditText);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        webAdapter = new WebAdapter(getSupportFragmentManager());
        viewPager.setAdapter(webAdapter);
        webAdapter.addUrl(currentUrl);

        viewPager.setCurrentItem(currentFrag);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUrl = editText.getText().toString();
                WebView webView = (WebView) findViewById(R.id.webViewFragment);
                webView.loadUrl(currentUrl);

                webAdapter.updateUrl(currentUrl, viewPager.getCurrentItem());
                webAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                if (currentFrag > 0) {
                    currentFrag--;
                    viewPager.setCurrentItem(currentFrag);
                    editText.setText(webAdapter.getUrl(currentFrag));
                }
                return true;
            case R.id.action_forward:
                if (currentFrag < maxFrag - 1) {
                    currentFrag++;
                    viewPager.setCurrentItem(currentFrag);
                    editText.setText(webAdapter.getUrl(currentFrag));
                }
                return true;
            case R.id.action_new:
                maxFrag++;
                currentFrag = maxFrag;
                webAdapter.addUrl(editText.getText().toString());
                webAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(currentFrag);

                // Move back a page because adapter always add two fragments
                currentFrag--;
                viewPager.setCurrentItem(currentFrag);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class WebAdapter extends FragmentStatePagerAdapter {

        ArrayList urls = new ArrayList<>();

        public WebAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addUrl(String str) {
            urls.add(str);
        }

        public void updateUrl(String str, int position) {
            urls.set(position, str);
        }

        public String getUrl(int position) {
            return urls.get(position).toString();
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public int getCount() {
            return maxFrag;
        }

        @Override
        public Fragment getItem(int position) {
            return BrowserFragment.newInstance(position, urls.get(position).toString());
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}