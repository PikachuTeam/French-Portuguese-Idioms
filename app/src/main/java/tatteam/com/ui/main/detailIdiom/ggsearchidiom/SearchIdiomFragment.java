package tatteam.com.ui.main.detailIdiom.ggsearchidiom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tatteam.com.R;
import tatteam.com.app.BaseFragment;


/**
 * Created by hoaba on 9/14/2015.
 */
public class SearchIdiomFragment extends BaseFragment {
    private String phrase;
    private WebView wvSearchIdiom;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        phrase = bundle.getString("phrase");
        setUpToolbarFake();

        View rootView = inflater.inflate(R.layout.fragment_google_search_idiom, container, false);
        wvSearchIdiom = (WebView) rootView.findViewById(R.id.webview);
        setUpWebView();
        return rootView;
    }

    public String getUrl() {
        String url = "https://www.google.com/search?q=";
        if (phrase != null) {
            String[] part = phrase.split(" ");
            for (int i = 0; i < part.length; i++) {
                url = url + "+" + part[i];
            }
        }
        return url;
    }

    public void setUpWebView() {
        MyWebViewClient myBrowser = new MyWebViewClient();
        wvSearchIdiom.setWebViewClient(myBrowser);
        wvSearchIdiom.getSettings().setJavaScriptEnabled(true);
        wvSearchIdiom.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvSearchIdiom.loadUrl(getUrl());
    }

    public void setUpToolbarFake() {
        getBaseActivity().getActionsMenu().setVisibility(View.GONE);
        getBaseActivity().getContenToolBar().setVisibility(View.GONE);
        getBaseActivity().getToolBarFake().setVisibility(View.VISIBLE);
        getBaseActivity().getBtnToolBarBackFake().setVisibility(View.VISIBLE);
        getBaseActivity().getTvToolBarTitleFake().setVisibility(View.VISIBLE);

        getBaseActivity().getTvToolBarTitleFake().setText(phrase);

        getBaseActivity().getToolBarBackFake().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpToolBar();
                getFragmentManager().popBackStack();
            }
        });
    }

    public void setUpToolBar() {
        getBaseActivity().getActionsMenu().setVisibility(View.VISIBLE);
        getBaseActivity().getContenToolBar().setVisibility(View.VISIBLE);

        getBaseActivity().getToolBarFake().setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (wvSearchIdiom.canGoBack()) {
            wvSearchIdiom.goBack();
        } else {
            setUpToolBar();
            super.onBackPressed();
        }

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
