package com.transfers.transfertracker.util.result

import com.transfers.transfertracker.view.auth.BaseSuccessUserView

data class AuthResultSuccess(val success: BaseSuccessUserView) : BaseAuthResult()