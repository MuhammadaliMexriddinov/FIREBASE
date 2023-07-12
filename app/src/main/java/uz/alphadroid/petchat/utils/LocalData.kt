package uz.alphadroid.petchat.utils

import android.annotation.SuppressLint
import com.google.android.gms.auth.api.signin.GoogleSignInClient

object LocalData {
      @SuppressLint("StaticFieldLeak")
      var googleSignInClient: GoogleSignInClient? =null
      const val REQUEST_CODE =0

}