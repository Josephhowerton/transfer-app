package com.transfers.transfertracker.model.result

import com.transfers.transfertracker.view.auth.BaseSuccessUserView

data class AuthResultSuccess(val success: BaseSuccessUserView) : BaseAuthResult()