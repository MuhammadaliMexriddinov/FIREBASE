package uz.alphadroid.petchat.usecases

import uz.alphadroid.petchat.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface VerifyUseCase {
    fun checkVerifyCode(code:String,uid:String,data: UserData):Flow<Result<Unit>>
}