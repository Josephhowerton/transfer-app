package com.transfers.transfertracker.repo

import androidx.annotation.NonNull
import com.transfers.transfertracker.model.result.BaseResult
import io.reactivex.rxjava3.core.Single

interface AuthRepository {
    fun signOut()
    fun signUp(@NonNull displayName: String, @NonNull email: String, @NonNull password: String): Single<BaseResult<Boolean>>
    fun signIn(@NonNull email: String, @NonNull password: String): Single<BaseResult<Boolean>>
    fun sendPasswordResetEmail(@NonNull email: String ): Single<BaseResult<Boolean>>
}
