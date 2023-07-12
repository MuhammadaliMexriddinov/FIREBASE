package uz.alphadroid.petchat.repo

import uz.alphadroid.petchat.data.model.UserData
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Flow

interface LoginRepository {
    fun loginGoogleSignIn(account: GoogleSignInAccount): kotlinx.coroutines.flow.Flow<Result<Unit>>

    fun loginPhone(data:UserData):kotlinx.coroutines.flow.Flow<Result<String>>
    fun loginTwitter():kotlinx.coroutines.flow.Flow<Result<Unit>>
    fun loginFaceBook(token: AccessToken):kotlinx.coroutines.flow.Flow<Result<Unit>>
}