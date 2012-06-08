package com.pc.programmerslife.fragments.dialogs;

import com.pc.programmerslife.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppDialogFragment extends DialogFragment {
	public static final String APP_DIALOG_FRAGMENT_TAG = "AppDialogFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_app, container);
		
		getDialog().setTitle(R.string.app);
		
		return view;
	}
}