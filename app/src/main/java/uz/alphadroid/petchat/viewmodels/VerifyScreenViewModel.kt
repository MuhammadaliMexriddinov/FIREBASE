package uz.alphadroid.petchat.viewmodels

import androidx.lifecycle.MutableLiveData
import uz.alphadroid.petchat.data.model.UserData

interface VerifyScreenViewModel {
    val successLiveData :MutableLiveData<Unit>
    val errorLiveData :MutableLiveData<String>
    val progressLiveData:MutableLiveData<Boolean>
    fun checkCode(code:String,uid:String,data:UserData)
}