package com.pc.programmerslife.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.pc.programmerslife.R;

public class BitmapFragment extends SherlockFragment {
	private static final String BITMAP_KEY = "BitmapKey";
	
	private Bitmap bitmap;
	
	public static BitmapFragment newInstance(Bitmap bitmap) {
		BitmapFragment bitmapFragment = new BitmapFragment();
		
		Bundle args = new Bundle();
		args.putParcelable(BITMAP_KEY, bitmap);
		bitmapFragment.setArguments(args);
		
		return bitmapFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bitmap_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		bitmap = (Bitmap) ((savedInstanceState == null) ? getArguments().getParcelable(BITMAP_KEY) :
			savedInstanceState.getParcelable(BITMAP_KEY));
		
		((ImageView) getView().findViewById(R.id.bitmapFragment_imageView)).setImageBitmap(bitmap);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable(BITMAP_KEY, bitmap);
	}
}