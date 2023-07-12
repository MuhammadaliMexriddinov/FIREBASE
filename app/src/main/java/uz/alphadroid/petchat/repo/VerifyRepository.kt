package uz.alphadroid.petchat.repo

import uz.alphadroid.petchat.data.model.UserData
import java.util.concurrent.Flow

interface VerifyRepository  {
    fun verifyCode(verificationId:String,code:String, data:UserData):kotlinx.coroutines.flow.Flow<Result<Unit>>
}