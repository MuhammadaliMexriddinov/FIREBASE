package uz.alphadroid.petchat.viewmodels.imp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.alphadroid.petchat.usecases.LoginUseCase
import uz.alphadroid.petchat.utils.hasConnection
import uz.alphadroid.petchat.viewmodels.LoginScreenViewModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModelImp @Inject constructor(private val useCase: LoginUseCase) :
    LoginScreenViewModel, ViewModel() {
    override val accountLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val successLiveData: MutableLiveData<String> = MutableLiveData()
    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override fun googleSign(googleSignIn: GoogleSignInAccount) {
        if (hasConnection()) {
            useCase.googleSignIn(googleSignIn).onEach {
                it.onSuccess { success ->
                    accountLiveData.value = success
                }
                it.onFailure { error ->
                    println("XATO  ::::::::::::${error.message.toString()}")
                    errorLiveData.value = error.message
                }
            }.launchIn(viewModelScope)
        }

    }

    override fun loginTwitter() {
        if (hasConnection()) {
            progressLiveData.value = true
            useCase.loginTwitter().onEach {
                it.onSuccess {
                    accountLiveData.value = it

                }
                it.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            errorLiveData.value = "No Internet :("
        }
    }

    override fun loginFaceBook(token: AccessToken) {
        if (hasConnection()) {
            progressLiveData.value = true
            useCase.loginFaceBook(token).onEach {
                it.onSuccess {
                    progressLiveData.value = false
                    accountLiveData.value = it
                }
                it.onFailure {
                    progressLiveData.value = false
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            errorLiveData.value = "No Connection :("
        }
    }

    override fun loginWithPhoneName(phone: String, name: String) {
        if (hasConnection()) {
            progressLiveData.value = true
            useCase.loginWithPhone(phone, name).onEach {
                it.onSuccess {
                    progressLiveData.value = false
                    successLiveData.value = it
                }
                it.onFailure {
                    progressLiveData.value = false
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            errorLiveData.value = "No Connection :("
        }
    }
}