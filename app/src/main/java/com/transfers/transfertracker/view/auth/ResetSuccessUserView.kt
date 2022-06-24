package com.transfers.transfertracker.view.auth

import androidx.annotation.StringRes
import com.transfers.transfertracker.view.auth.BaseSuccessUserView

data class ResetSuccessUserView(@StringRes val title: Int, val message: Int) : BaseSuccessUserView()