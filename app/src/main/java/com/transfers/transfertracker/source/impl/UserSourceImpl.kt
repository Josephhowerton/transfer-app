package com.transfers.transfertracker.source.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.transfers.transfertracker.enums.Collections
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.util.errors.FBError
import com.transfers.transfertracker.util.errors.FBException
import com.transfers.transfertracker.util.result.BaseResult
import com.transfers.transfertracker.source.UserSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserSourceImpl @Inject constructor(): UserSource {

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

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

    override fun updateUser(user: User): Single<BaseResult<Boolean>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance()
                .collection(Collections.Users.name)
                .document(user.id).set(user)
                .addOnFailureListener { emitter.onError( it ) }
                .addOnCanceledListener { emitter.onError( FBException(FBError.ERROR_CLIENT_CANCELED) ) }
                .addOnSuccessListener { emitter.onSuccess(BaseResult.Success(true)) }
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

    override fun getSelectedTeam(user: User): Single<Team> =
        Single.create { emitter -> }

    override fun updateSelectedTeam(user: User) = TODO()

    override fun getUnselectedTeams(user: User): Single<List<Team>> =
        Single.create { emitter -> }

}