package com.mycompanyname.bananas;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import com.jayway.android.robotium.solo.Solo;
import lombok.val;

public class TempActivityIT extends ActivityInstrumentationTestCase2<TempActivity_> {

	private Solo solo;

	public TempActivityIT() {
		super("com.mycompanyname.bananas", TempActivity_.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	public void test() throws Exception {
		val button = (Button) solo.getCurrentActivity().findViewById(R.id.btn);
		solo.clickOnView(button);
	}
}

