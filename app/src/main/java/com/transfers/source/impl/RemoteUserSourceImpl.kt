package com.transfers.source.impl

import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.transfers.source.UserSource
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.errors.AuthError
import com.transfers.transfertracker.model.errors.AuthException
import com.transfers.transfertracker.model.errors.ErrorTransformer
import com.transfers.transfertracker.model.result.BaseResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteUserSourceImpl @Inject constructor(private val errorTransformer: ErrorTransformer) : UserSource {

    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String): Single<User> =
        Single.create {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    if (user is FirebaseUser) {
                        it.onSuccess(User(user.uid))
                    } else {
                        it.onError(AuthException(AuthError.ERROR_UNKNOWN))
                    }
                }
                .addOnFailureListener { exception ->

                    it.onError(errorTransformer.convertThrowableForAuthentication(exception))
                }
        }

    fun signIn(email: String, password: String): Single<User> =
        Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    if(user is FirebaseUser){
                        emitter.onSuccess(User(user.uid))
                    }else{
                        emitter.onError(AuthException(AuthError.ERROR_UNKNOWN))
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(errorTransformer.convertThrowableForAuthentication(exception))
                }
        }

    fun sendPasswordResetEmail(@NonNull email: String ): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    emitter.onSuccess(BaseResult.Success(true))
                }
                .addOnFailureListener {
                    emitter.onError(errorTransformer.convertThrowableForAuthentication(it))
                }
        }

    fun logout() {
        firebaseAuth.signOut()
    }

}