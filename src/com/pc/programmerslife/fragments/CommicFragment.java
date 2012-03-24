package com.pc.programmerslife.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.pc.programmerslife.R;

public class CommicFragment extends SherlockFragment {
	private static final String BITMAP_KEY = "Bitmap";
	private static final String COMMIC_BITMAP_KEY = "CommicBitmap";
	
	private Bitmap commicBitmap;
	
	public static CommicFragment newInstance(Bitmap bitmap) {
		CommicFragment commicFragment = new CommicFragment();
		
		Bundle args = new Bundle();
		args.putParcelable(BITMAP_KEY, bitmap);
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
		
		if (savedInstanceState == null) {
			Bundle args = getArguments();
			if (args != null)
				this.commicBitmap = args.getParcelable(BITMAP_KEY);
		} else
			this.commicBitmap = savedInstanceState.getParcelable(COMMIC_BITMAP_KEY);
		
		ImageView imageView = (ImageView) getView().findViewById(R.id.commicFragment_imageView);
		imageView.setImageBitmap(commicBitmap);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable(COMMIC_BITMAP_KEY, commicBitmap);
	}
}