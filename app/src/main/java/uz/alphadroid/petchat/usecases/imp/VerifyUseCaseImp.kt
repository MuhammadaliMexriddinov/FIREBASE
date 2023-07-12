package uz.alphadroid.petchat.usecases.imp

import uz.alphadroid.petchat.data.model.UserData
import uz.alphadroid.petchat.repo.VerifyRepository
import uz.alphadroid.petchat.usecases.VerifyUseCase
import java.util.concurrent.Flow
import javax.inject.Inject

class VerifyUseCaseImp @Inject constructor(private val repository: VerifyRepository) :
    VerifyUseCase {
    override fun checkVerifyCode(
        code: String,
        uid: String,
        data :UserData
    ): kotlinx.coroutines.flow.Flow<Result<Unit>> {
        return repository.verifyCode(verificationId = uid, code = code,data)
    }
}