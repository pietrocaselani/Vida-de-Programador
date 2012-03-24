package com.pc.programmerslife.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.pc.framework.image.ImageDownloader;
import com.pc.framework.image.ImageDownloader.ImageDownloaderListener;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.Manager;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.CommicPageAdapter;
import com.viewpagerindicator.CirclePageIndicator;

public class CommicActivity extends SherlockFragmentActivity implements ImageDownloaderListener {
	private Commic commic;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commic_activity);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			commic = (Commic) extras.getParcelable(Commic.EXTRA_COMMIC);
			if (commic != null) {
				this.progressBar = (ProgressBar) findViewById(R.id.commicActivity_progressBar);
				progressBar.setVisibility(ProgressBar.VISIBLE);
				
				ImageDownloader imageDownloader = new ImageDownloader(getApplicationContext(), this);
				imageDownloader.getAsyncImage(commic.getPath());
				
				ActionBar actionBar = getSupportActionBar();
				actionBar.setDisplayShowHomeEnabled(true);
				actionBar.setHomeButtonEnabled(true);
				actionBar.setTitle(commic.getTitle());
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (commic.getLink() != null) {
			getSupportMenuInflater().inflate(R.menu.commic_activity_menu, menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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
			boolean isFavorite = commic.isFavorite();
			commic.setFavorite(!isFavorite);
			
			String text = null;
			
			if (Manager.getInstance(getApplicationContext()).updateCommic(commic) == true)
				text = getString((commic.isFavorite() == true) ? R.string.save_favorite : R.string.unsave_favorite);
			else
				text = getString(R.string.error_favorite);
			
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onImageFinishDownload(Bitmap image, String link, ImageDownloader downloader) {
		Bitmap[] bitmaps = splitBitmap(image);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.commicActivity_viewPager);
		viewPager.setAdapter(new CommicPageAdapter(getSupportFragmentManager(), bitmaps));
		
		CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.commicActivity_circleIndicator);
		circleIndicator.setViewPager(viewPager);
		
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	public void onImageFailDownload(Exception e, String link, ImageDownloader downloader) {
		progressBar.setVisibility(ProgressBar.INVISIBLE);
		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
	
	private Bitmap[] splitBitmap(Bitmap bitmap) {
    	int originalW, originalH, w, h, rows, i, j, x, y, border;
    	
    	originalW = bitmap.getWidth();
    	originalH = bitmap.getHeight();
    	
    	border = (originalW * 10) / 510;
    	
    	w = (originalW / 2);
    	rows = Math.round((float) originalH / (float) w);
    	h = (originalH / rows); 
    	
    	Bitmap[] bitmaps = new Bitmap[2 * rows];
    	
    	for (i = 0; i < 2; i++) {
    		for (j = 0; j < rows; j++) {x = w * i + border;
    			x = (int) ((i == 0) ? w * i + border : w * i + Math.ceil(border / 2.0)); 
    			y = (int) ((j == 0) ? h * j + border : h  * j + Math.ceil(border / 2.0));
    			Bitmap smallBitmap = Bitmap.createBitmap(bitmap, x, y, w - (border + border / 2), h - (border + border / 2));
    			bitmaps[j * 2 + i] = smallBitmap;
    		}
    	}
    	
    	return bitmaps;
    }
}