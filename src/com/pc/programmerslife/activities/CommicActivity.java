package com.pc.programmerslife.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.R;

public class CommicActivity extends SherlockActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commic_activity);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Commic commic = (Commic) extras.getParcelable(Commic.EXTRA_COMMIC);
			if (commic != null) {
				ActionBar actionBar = getSupportActionBar();
				actionBar.setDisplayShowHomeEnabled(true);
				actionBar.setHomeButtonEnabled(true);
				actionBar.setTitle(commic.getTitle());
				
				WebView webView = (WebView) findViewById(R.id.commicActivity_webView);
				webView.loadUrl(commic.getPath());
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}