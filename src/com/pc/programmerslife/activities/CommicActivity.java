package com.pc.programmerslife.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.R;

public class CommicActivity extends SherlockActivity {
	private Commic commic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commic_activity);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			commic = (Commic) extras.getParcelable(Commic.EXTRA_COMMIC);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.commic_activity_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else if (item.getItemId() == R.id.commicActivityMenu_share) {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, (commic.getLink() != null) ? commic.getLink() : commic.getPath());
			shareIntent.setType("text/plain");
			startActivity(Intent.createChooser(shareIntent, getString(R.string.share_using)));
			return true;
		} else if (item.getItemId() == R.id.commicActivityMenu_favorite) {
			
		}
		return super.onOptionsItemSelected(item);
	}
}