package uz.alphadroid.petchat.repo.imp

import uz.alphadroid.petchat.app.App.Companion.activity
import uz.alphadroid.petchat.data.model.UserData
import uz.alphadroid.petchat.repo.VerifyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class VerifyRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseDatabase
) :
    VerifyRepository {
    override fun verifyCode(verificationId: String, code: String, data: UserData) =
        callbackFlow<Result<Unit>> {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
                activity
            ) { task ->
                if (task.isSuccessful) {
                    val ref: DatabaseReference = db.reference
                    val uid = ref.child("users").push().key
                    uid?.let {
                        ref.child("users").child(uid).setValue(data).addOnSuccessListener {
                            trySend(Result.success(Unit))
                        }
                            .addOnFailureListener {
                                trySend(Result.failure(task.exception!!))

                            }
                    }

                } else {
                    trySend(Result.failure(task.exception!!))
                }
            }
            awaitClose()
        }
}