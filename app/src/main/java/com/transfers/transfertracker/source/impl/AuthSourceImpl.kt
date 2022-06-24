package com.transfers.transfertracker.source.impl

import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.transfers.transfertracker.source.UserSource
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.errors.AuthError
import com.transfers.transfertracker.model.errors.AuthException
import com.transfers.transfertracker.model.errors.ErrorTransformer
import com.transfers.transfertracker.model.result.BaseResult
import com.transfers.transfertracker.source.AuthSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthSourceImpl @Inject constructor(private val errorTransformer: ErrorTransformer) :
    AuthSource {

    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun completeSignUp(user: FirebaseUser): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            firebaseAuth.updateCurrentUser(user)
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
                .addOnFailureListener { exception -> emitter.onError(errorTransformer.convertThrowableForAuthentication(exception)) }
        }

    override fun signUp(displayName: String, email: String, password: String): Single<FirebaseUser> =
        Single.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let {
                        emitter.onSuccess(it)
                    } ?: emitter.onError(AuthException(AuthError.ERROR_UNKNOWN))
                }.addOnFailureListener { emitter.onError(errorTransformer.convertThrowableForAuthentication(it)) }
        }

    override fun signIn(email: String, password: String): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let {
                        emitter.onSuccess(BaseResult.Success(true))
                    } ?: emitter.onError(AuthException(AuthError.ERROR_UNKNOWN))
                }.addOnFailureListener { emitter.onError(errorTransformer.convertThrowableForAuthentication(it)) }
        }

    override fun sendPasswordResetEmail(@NonNull email: String ): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
                .addOnFailureListener { emitter.onError(errorTransformer.convertThrowableForAuthentication(it)) }
        }

    override fun signOut(): Single<Boolean> =
        Single.create { emitter ->
            firebaseAuth.signOut()
            emitter.onSuccess(firebaseAuth.currentUser == null)
        }

}