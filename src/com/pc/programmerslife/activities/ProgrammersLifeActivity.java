package com.pc.programmerslife.activities;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.pc.programmerslife.R;
import com.pc.programmerslife.fragments.ProgrammersLifeFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class ProgrammersLifeActivity extends SherlockFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programmers_life_activity);
		
		if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fragmentTransaction.add(R.id.programmersLife_mainLayout, new ProgrammersLifeFragment());
			fragmentTransaction.commit();
		}
	}
}