package uz.alphadroid.petchat.repo.imp

import uz.alphadroid.petchat.app.App
import uz.alphadroid.petchat.app.App.Companion.activity
import uz.alphadroid.petchat.data.model.UserData
import uz.alphadroid.petchat.repo.LoginRepository
import uz.alphadroid.petchat.utils.LocalData.googleSignInClient
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseDatabase
) :
    LoginRepository {
    override fun loginGoogleSignIn(account: GoogleSignInAccount): Flow<Result<Unit>> =
        callbackFlow<Result<Unit>> {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                task.addOnCompleteListener { data ->
                    if (task.isSuccessful) {
                        val ref: DatabaseReference = db.reference
                        val userData: UserData
                        val firebaseResult = data.result.user!!
                        userData = if (firebaseResult.phoneNumber != null) {
                            UserData(
                                firebaseResult.displayName,
                                firebaseResult.phoneNumber,
                                firebaseResult.photoUrl.toString()
                            )
                        } else {
                            UserData(
                                firebaseResult.displayName,
                                "No Phone",
                                firebaseResult.photoUrl.toString()
                            )

                        }
                        val uid = ref.child("users").push().key
                        uid?.let {
                            ref.child("users").child(uid).setValue(
                                userData
                            ).addOnSuccessListener {
                                trySend(Result.success(Unit))
                            }
                                .addOnFailureListener {
                                    trySend(Result.failure(task.exception!!))

                                }
                        }
                    }
                }
                task.addOnFailureListener {
                    trySend(Result.failure(it))
                }
            }



            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun loginPhone(data: UserData) = callbackFlow<Result<String>> {
        val verificationCallback =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

                override fun onVerificationFailed(e: FirebaseException) {
                    trySend(Result.failure(Exception(e.message)))
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    trySend(Result.success(verificationId))
                }
            }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(data.phone.toString())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(App.activity)
            .setCallbacks(verificationCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose()

    }.flowOn(Dispatchers.IO)

    override fun loginTwitter(): Flow<Result<Unit>> = callbackFlow<Result<Unit>> {
        val provider = OAuthProvider.newBuilder("twitter.com")
        val pendingResultTask = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    val ref: DatabaseReference = db.reference
                    val userData: UserData
                    val firebaseResult = it.user!!
                    userData = if (firebaseResult.phoneNumber != null) {
                        UserData(
                            firebaseResult.displayName,
                            firebaseResult.phoneNumber,
                            firebaseResult.photoUrl.toString()
                        )
                    } else {
                        UserData(
                            firebaseResult.displayName,
                            "No Phone",
                            firebaseResult.photoUrl.toString()
                        )

                    }
                    val uid = ref.child("users").push().key
                    uid?.let {
                        ref.child("users").child(uid).setValue(
                            userData
                        ).addOnSuccessListener {
                            trySend(Result.success(Unit))
                        }
                            .addOnFailureListener {
                                trySend(Result.failure(it))

                            }
                    }
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
        } else {
            firebaseAuth
                .startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener {
                    val ref: DatabaseReference = db.reference
                    val userData: UserData
                    val firebaseResult = it.user!!
                    userData = if (firebaseResult.phoneNumber != null) {
                        UserData(
                            firebaseResult.displayName,
                            firebaseResult.phoneNumber,
                            firebaseResult.photoUrl.toString()
                        )
                    } else {
                        UserData(
                            firebaseResult.displayName,
                            "No Phone",
                            firebaseResult.photoUrl.toString()
                        )

                    }
                    val uid = ref.child("users").push().key
                    uid?.let {
                        ref.child("users").child(uid).setValue(
                            userData
                        ).addOnSuccessListener {
                            trySend(Result.success(Unit))
                        }
                            .addOnFailureListener {
                                trySend(Result.failure(it))

                            }
                    }
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))

                }

        }

        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun loginFaceBook(token: AccessToken): Flow<Result<Unit>> =
        callbackFlow<Result<Unit>> {
            firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(token.token))
                .addOnSuccessListener {
                    val ref: DatabaseReference = db.reference
                    val userData: UserData
                    val firebaseResult = it.user!!
                    userData = if (firebaseResult.phoneNumber != null) {
                        UserData(
                            firebaseResult.displayName,
                            firebaseResult.phoneNumber,
                            firebaseResult.photoUrl.toString()
                        )
                    } else {
                        UserData(
                            firebaseResult.displayName,
                            "No Phone",
                            firebaseResult.photoUrl.toString()
                        )

                    }
                    val uid = ref.child("users").push().key
                    uid?.let {
                        ref.child("users").child(uid).setValue(
                            userData
                        ).addOnSuccessListener {
                            trySend(Result.success(Unit))
                        }
                            .addOnFailureListener { error ->
                                trySend(Result.failure(error))

                            }
                    }
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
            awaitClose()
        }.flowOn(Dispatchers.IO)


}