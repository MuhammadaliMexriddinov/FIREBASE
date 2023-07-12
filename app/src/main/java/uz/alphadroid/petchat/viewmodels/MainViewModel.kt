package uz.alphadroid.petchat.viewmodels

import androidx.lifecycle.LiveData

interface MainViewModel {
    val loginScreenLiveData: LiveData<Unit>
    val homeScreenLiveData: LiveData<Unit>

}