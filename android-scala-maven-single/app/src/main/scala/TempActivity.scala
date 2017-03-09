package com.mycompanyname.bananas

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class TempActivity extends Activity {

  lazy val textView = findViewById(R.id.txt).asInstanceOf[TextView]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.temp)
  }

  def onButton(v: View) {
    textView.setText("great")
  }
}
