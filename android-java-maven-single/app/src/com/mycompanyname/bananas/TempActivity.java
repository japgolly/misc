package com.mycompanyname.bananas;

import android.app.Activity;
import android.widget.TextView;

import com.google.java.contract.Requires;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.temp)
public class TempActivity extends Activity {

	@ViewById(R.id.txt) TextView textView;

	@Click(R.id.btn)
	@Requires("textView != null")
	public void onButton() {
		textView.setText("great");
	}
}
