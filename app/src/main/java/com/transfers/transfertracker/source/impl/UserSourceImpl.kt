package com.transfers.transfertracker.source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.transfers.transfertracker.enums.Collections
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.errors.FBError
import com.transfers.transfertracker.model.errors.FBException
import com.transfers.transfertracker.model.result.BaseResult
import com.transfers.transfertracker.source.UserSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserSourceImpl @Inject constructor(): UserSource {

    override fun getUser(id: String): Single<User> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(id).get()
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnFailureListener { emitter.onError( it ) }
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    if(user != null){
                        emitter.onSuccess(user)
                    }
                }
        }

    override fun createUser(user: User): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id).set(user)
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
        }

    override fun updateUser(user: User): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id).set(user)
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
        }
}