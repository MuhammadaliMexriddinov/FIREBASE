package uz.alphadroid.petchat.usecases

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun loginWithPhone(phone:String,name:String,) :Flow<Result<String>>
    fun googleSignIn(account: GoogleSignInAccount):Flow<Result<Unit>>
    fun loginTwitter():Flow<Result<Unit>>
    fun loginFaceBook(token: AccessToken):Flow<Result<Unit>>

}