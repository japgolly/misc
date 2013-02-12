package com.mycompanyname.bananas

import android.test.ActivityInstrumentationTestCase2
import android.view.View
import android.widget.Button
import com.jayway.android.robotium.solo.Solo

class TempActivityIT extends ActivityInstrumentationTestCase2[TempActivity]("com.mycompanyname.bananas", classOf[TempActivity]) {

  lazy private val solo = new Solo(getInstrumentation(), getActivity())

  override protected def tearDown() {
    solo.finishOpenedActivities
    super.tearDown
  }

  @inline def find[T <: View](resId: Int): T = solo.getCurrentActivity.findViewById(resId).asInstanceOf[T]
  lazy private val button = find[Button](R.id.btn)

  def testStuff {
    solo.clickOnView(button);
  }
}
