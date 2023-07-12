package uz.alphadroid.petchat.usecases.imp

import uz.alphadroid.petchat.data.model.UserData
import uz.alphadroid.petchat.repo.LoginRepository
import uz.alphadroid.petchat.usecases.LoginUseCase
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCaseImp @Inject constructor(private val repo: LoginRepository) : LoginUseCase {
    override fun loginWithPhone(
        phone: String,
        name: String
    ): kotlinx.coroutines.flow.Flow<Result<String>> {
        return repo.loginPhone(
            UserData(
                name,
                phone,
                photoUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
            )
        )
    }

    override fun googleSignIn(account: GoogleSignInAccount): Flow<Result<Unit>> {
        return repo.loginGoogleSignIn(account)
    }

    override fun loginTwitter(): Flow<Result<Unit>> {
        return  repo.loginTwitter()
    }

    override fun loginFaceBook(token: AccessToken): Flow<Result<Unit>> {
        return  repo.loginFaceBook(token)
    }
}