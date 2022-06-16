package com.transfers.transfertracker.view.auth.viewmodel

class LoginViewModel(application: Application, private val userAuthRepository: UserAuthRepository) : BaseAuthViewModel(application) {

    val emailField = MutableLiveData<String>()
    val passwordField = MutableLiveData<String>()

    val signInButtonEnabled: LiveData<Boolean> = passwordField.map {
        validateSignInData()
    }

    private val _loginResult = MutableLiveData<BaseAuthResult>()
    val loginResult: LiveData<BaseAuthResult> = _loginResult

    private val _navigateToCreateAccount = MutableLiveData<Boolean>()
    val navigateToCreateAccount: LiveData<Boolean> = _navigateToCreateAccount

    private val _navigateToReset = MutableLiveData<Boolean>()
    val navigateToReset: LiveData<Boolean> = _navigateToReset

    val showProgressBar = MutableLiveData(View.GONE)

    fun signIn() {
        subscribeOnLifecycle(
            userAuthRepository.signIn(emailField.value!!, passwordField.value!!)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ showProgressBar.value = View.VISIBLE}
                .doOnError{
                    userAuthRepository.logout()
                }
                .subscribe(
                    {
                        showProgressBar.value = View.GONE
                        if(it is BaseResult.Success){
                            _loginResult.value = AuthResultSuccess(success = AuthSuccessUserView(displayName = ""))
                        }
                        else if(it is BaseResult.Error){
                            _loginResult.value = handleAuthError(R.string.error_sign_in_failed, it)
                        }
                        else{
                            _loginResult.value = AuthResultError(R.string.error_sign_in_failed, R.string.error_default)
                        }
                    },
                    {
                        showProgressBar.value = View.GONE
                        _loginResult.value = handleAuthError(R.string.error_sign_in_failed, BaseResult.Error(it))
                    }
                )
        )
    }

    // navigate to create account fragment
    fun signUp() {
        _navigateToCreateAccount.value = true
        _navigateToCreateAccount.value = false
    }

    fun reset() {
        _navigateToReset.value = true
        _navigateToReset.value = false
    }

    private fun validateSignInData(): Boolean
        = !(!isEmailValid(emailField.value) || !isPasswordValid(passwordField.value))

    // A placeholder username validation check
    private fun isEmailValid(username: String?): Boolean {
        return if(!username.isNullOrBlank()){
            if (username.contains("@")) {
                Patterns.EMAIL_ADDRESS.matcher(username).matches()
            } else {
                username.isNotBlank()
            }
        }
        else{
            false
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return if(!password.isNullOrBlank()) {
            password.length > 5
        }else{
            false
        }
    }
}