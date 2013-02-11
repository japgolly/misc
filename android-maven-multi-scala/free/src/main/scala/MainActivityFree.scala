package com.mycompanyname.bananas.free

import android.util.Log;
import com.google.ads.AdView
import com.mycompanyname.bananas.MainActivity

class MainActivityFree extends MainActivity {

  lazy val adView = getResources.findViewById(R.id.adView).asInstanceOf[AdView]

  override def onDestroy {
    try {
      adView.destroy
    } catch {
      case e: Throwable => Log.d("Ads", "onDestroy() failed.", e);
    }
    super.onDestroy
  }
}
