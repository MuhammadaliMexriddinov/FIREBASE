package uz.alphadroid.petchat.viewmodels.imp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.alphadroid.petchat.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModelImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @ApplicationContext applicationContext: Context
) : ViewModel(), MainViewModel {
    override val loginScreenLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val homeScreenLiveData: MutableLiveData<Unit> = MutableLiveData()

    init {
        if (firebaseAuth.currentUser == null)
            loginScreenLiveData.value = Unit
        else    homeScreenLiveData.value = Unit

    }

}