package uz.alphadroid.petchat.viewmodels.imp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.alphadroid.petchat.data.model.UserData
import uz.alphadroid.petchat.usecases.imp.VerifyUseCaseImp
import uz.alphadroid.petchat.utils.hasConnection
import uz.alphadroid.petchat.viewmodels.VerifyScreenViewModel
import com.google.firebase.database.core.view.View
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VerifyScreenViewModelImp @Inject constructor(private val useCaseImp: VerifyUseCaseImp) :
    VerifyScreenViewModel, ViewModel() {
    override val successLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun checkCode(code: String, uid: String,data:UserData) {
        if (hasConnection()) {
            progressLiveData.value = true
            useCaseImp.checkVerifyCode(code, uid,data).onEach {
                it.onSuccess {
                    progressLiveData.value = false
                    successLiveData.value = Unit
                }

                it.onFailure {
                    progressLiveData.value = false
                    errorLiveData.value = it.message
                }

            }.launchIn(viewModelScope)
        } else {
            errorLiveData.value = "No Internet :("
        }
    }

}