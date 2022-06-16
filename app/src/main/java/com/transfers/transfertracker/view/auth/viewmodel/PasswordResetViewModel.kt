package com.transfers.transfertracker.view.auth.viewmodel

class PasswordResetViewModel(application: Application, private val userAuthRepository: UserAuthRepository): BaseAuthViewModel(application = application) {

    val emailField = MutableLiveData<String>()

    private val _sendPasswordResetEmailResult = MutableLiveData<BaseAuthResult>()
    val sendPasswordResetEmailResult: LiveData<BaseAuthResult> = _sendPasswordResetEmailResult

    val showProgressBar = MutableLiveData(View.GONE)

    val emailButtonEnabled: LiveData<Boolean> = emailField.map {
        validateSignInData()
    }

    fun sendResetPasswordEmail(){
        subscribeOnLifecycle(userAuthRepository.sendPasswordResetEmail(emailField.value!!)
            .doOnSubscribe{
                showProgressBar.value = View.VISIBLE
            }
            .subscribe(
                {
                    if(it is BaseResult.Success){
//                        if(it.data){
//                            _sendPasswordResetEmailResult.value = AuthResultSuccess(success = ResetSuccessUserView(R.string.error_check_email_title, R.string.error_check_email_message))
//                        }
                    }
                    showProgressBar.value = View.GONE
                },
                {
                    _sendPasswordResetEmailResult.value = handleAuthError(R.string.error_could_not_send_password_reset_email, BaseResult.Error(it))
                    showProgressBar.value = View.GONE
                }
            )
        )
    }

    private fun validateSignInData(): Boolean = isEmailValid(emailField.value)

    private fun isEmailValid(email: String?): Boolean {
        return if(!email.isNullOrBlank()){
            if (email.contains("@")) {
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }else{
                false
            }
        }
        else{
            false
        }
    }
}