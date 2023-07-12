package uz.alphadroid.petchat.viewmodels

import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface LoginScreenViewModel {
    val successLiveData:MutableLiveData<String>
    val errorLiveData:MutableLiveData<String>
    val progressLiveData:MutableLiveData<Boolean>
    val accountLiveData:MutableLiveData<Unit>
    fun loginWithPhoneName(phone:String,name:String)
    fun googleSign(googleSignIn: GoogleSignInAccount)
    fun loginTwitter()
    fun loginFaceBook(token: AccessToken)

}