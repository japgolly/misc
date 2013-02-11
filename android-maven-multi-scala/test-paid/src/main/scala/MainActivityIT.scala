package com.mycompanyname.bananas.paid

import android.test.ActivityInstrumentationTestCase2
import android.view.View
import com.mycompanyname.bananas.MainActivity
import com.mycompanyname.bananas.calc.Measure
import com.jayway.android.robotium.solo.Solo

class MainActivityIT extends ActivityInstrumentationTestCase2[MainActivity]("com.mycompanyname.bananas.paid", classOf[MainActivity]) {

  lazy private val solo = new Solo(getInstrumentation(), getActivity())

  override protected def tearDown() {
    solo.finishOpenedActivities
    super.tearDown
  }

  @inline def find[T <: View](resId: Int): T = solo.getCurrentActivity.findViewById(resId).asInstanceOf[T]
  // lazy private val bananas = find[Button](R.id.bananas)

  def testStuff {
    // solo.clickOnView(bananas);
  }
}
