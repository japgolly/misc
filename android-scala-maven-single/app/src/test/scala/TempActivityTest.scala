package com.mycompanyname.bananas

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.Button
import com.xtremelabs.robolectric.RobolectricTestRunner

@RunWith(classOf[RobolectricTestRunner])
class TempActivityTest {

  var activity: TempActivity = null
  def button = activity.findViewById(R.id.btn).asInstanceOf[Button]

  @Before def setup {
    activity = new TempActivity
    activity.onCreate(null)
  }

  @Test def test {
    assertThat(activity.textView.getText.toString, not(equalTo("great")))
    button.performClick
    assertThat(activity.textView.getText.toString, equalTo("great"))
  }
}

