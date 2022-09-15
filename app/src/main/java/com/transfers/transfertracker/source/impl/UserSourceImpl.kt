package com.transfers.transfertracker.source.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.transfers.transfertracker.enums.Collections
import com.transfers.transfertracker.enums.Fields
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.util.errors.FBError
import com.transfers.transfertracker.util.errors.FBException
import com.transfers.transfertracker.util.result.BaseResult
import com.transfers.transfertracker.source.UserSource
import com.transfers.transfertracker.util.RemoteCacheConstants.KEY_FIELD_CURRENT_TEAM
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class UserSourceImpl @Inject constructor(): UserSource {

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private val currentTeamSubject: BehaviorSubject<Team> = BehaviorSubject.create()

    override fun isUserLoggedIn(): Single<BaseResult<Boolean>> =
        Single.create<BaseResult<Boolean>> { emitter ->
            FirebaseAuth.getInstance().currentUser?.let {
                emitter.onSuccess(BaseResult.Success(true))
            } ?: emitter.onError(FBException(FBError.ERROR_UNKNOWN))
        }.flatMap {
            getUser()
        }
        .map {
            BaseResult.Success(true)
        }

    override fun getUser(): Single<User> =
        Single.create { emitter ->
            user?.let { fbUser ->
                FirebaseFirestore.getInstance()
                    .collection(Collections.Users.name)
                    .document(fbUser.uid).get()
                    .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                    .addOnFailureListener { emitter.onError( it ) }
                    .addOnSuccessListener { document ->
                        document.toObject(User::class.java)?.let {
                            emitter.onSuccess(it)
                        } ?: emitter.onError(FBException(FBError.ERROR_CONVERSION))
                    }
            } ?: emitter.onError(Throwable())
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

    override fun updateUser(user: User): Single<User> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id).set(user)
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { emitter.onSuccess(user) }
        }

    override fun updateUserTeam(user: User): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id)
                .update(KEY_FIELD_CURRENT_TEAM, user.currentTeam)
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener {
                    emitter.onSuccess(BaseResult.Success(true))
                }
        }

    override fun getAllUsersTeam(uid: String): Single<List<Team>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(uid)
                .collection(Collections.Teams.name).get()
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { collection ->
                    collection.toObjects(Team::class.java).let {
                        emitter.onSuccess(it)
                    }
                }
        }

    override fun getCurrentTeam(user: User): Single<Team> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id)
                .collection(Collections.Teams.name)
                .document(user.currentTeam).get()
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { document ->
                    document.toObject(Team::class.java)?.let {
                        emitter.onSuccess(it)
                    }
                }
        }

    override fun saveSelectedTeam(user: User, team: Team): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id)
                .collection(Collections.Teams.name)
                .document(team.id.toString())
                .set(team)
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
        }

    override fun removeSelectedTeam(user: User, team: Team): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id)
                .collection(Collections.Teams.name)
                .document(team.id.toString())
                .delete()
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
        }
}