package com.pc.programmerslife.fragments;

import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.pc.framework.image.ImageDownloader;
import com.pc.framework.image.ImageDownloader.ImageDownloaderListener;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.CommicManager;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.BitmapPageAdapter;
import com.viewpagerindicator.CirclePageIndicator;

public class CommicFragment extends SherlockFragment implements ImageDownloaderListener {
	private Commic commic;
	private ImageDownloader imageDownloader;
	
	public static CommicFragment newInstance(Commic commic) {
		CommicFragment commicFragment = new CommicFragment();
		
		Bundle args = new Bundle();
		args.putParcelable(Commic.EXTRA_COMMIC, commic);
		commicFragment.setArguments(args);
		
		return commicFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.commic_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		this.commic = (Commic) ((savedInstanceState == null) ? getArguments().getParcelable(Commic.EXTRA_COMMIC) :
			savedInstanceState.getParcelable(Commic.EXTRA_COMMIC));
		
		ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(commic.getTitle());
		
		setHasOptionsMenu(true);
		
		((ProgressBar) getView().findViewById(R.id.commicFragment_progressBar)).setVisibility(View.VISIBLE);
		
		imageDownloader = new ImageDownloader(getSherlockActivity(), this);
		imageDownloader.getAsyncImage(commic.getPath());
	}
	
	@Override
	public void onDestroy() {
		imageDownloader.cancel(true);
		
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		if (getTargetFragment() instanceof CommicsFragment)
			((CommicsFragment) getTargetFragment()).configureActionBar();
		else if (getTargetFragment() instanceof FavoritesFragment)
			((FavoritesFragment) getTargetFragment()).configureActionBar();
		
		super.onDestroyView();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable(Commic.EXTRA_COMMIC, commic);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (commic.getLink() != null)
			inflater.inflate(R.menu.commic_activity_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			getSherlockActivity().getSupportFragmentManager().popBackStack();
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
			
			if (CommicManager.getInstance().updateCommicFavorite(commic) == true)
				text = getString((commic.isFavorite() == true) ? R.string.save_favorite : R.string.unsave_favorite);
			else
				text = getString(R.string.error_favorite);
			
			Toast.makeText(getSherlockActivity(), text, Toast.LENGTH_SHORT).show();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onImageFinishDownload(Bitmap image, String link, ImageDownloader downloader) {
		Bitmap[] bitmaps = splitBitmap(image);
		
		ViewPager bitmapsPager = (ViewPager) getView().findViewById(R.id.commicFragment_viewPager);
		bitmapsPager.setAdapter(new BitmapPageAdapter(getSherlockActivity().getSupportFragmentManager(), bitmaps));
		
		CirclePageIndicator circleIndicator = (CirclePageIndicator) getView().findViewById(R.id.commicFragment_circleIndicator);
		circleIndicator.setViewPager(bitmapsPager);
		
		((ProgressBar) getView().findViewById(R.id.commicFragment_progressBar)).setVisibility(View.INVISIBLE);
	}

	@Override
	public void onImageFailDownload(Exception e, String link, ImageDownloader downloader) {
		((ProgressBar) getView().findViewById(R.id.commicFragment_progressBar)).setVisibility(View.INVISIBLE);
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