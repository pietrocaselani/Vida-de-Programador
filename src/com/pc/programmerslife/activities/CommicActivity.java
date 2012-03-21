package com.pc.programmerslife.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockActivity;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.R;

public class CommicActivity extends SherlockActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commic_activity);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Object o = extras.getParcelable(Commic.EXTRA_COMMIC);
			Commic commic = (Commic) o;
			if (commic != null) {
				WebView webView = (WebView) findViewById(R.id.commicActivity_webView);
				webView.loadUrl(commic.getPath());
			}
		}
	}
}