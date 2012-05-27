package com.pc.programmerslife.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.R;
import com.pc.programmerslife.fragments.CommicFragment;

public class CommicActivity extends SherlockFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commic_activity);
		
		CommicFragment commicFragment = (CommicFragment) getSupportFragmentManager().findFragmentById(R.id.commicActivity_CommicFragment);
		
		Commic commic = getIntent().getParcelableExtra(Commic.EXTRA_COMMIC);
		
		commicFragment.setCommic(commic);
	}
}