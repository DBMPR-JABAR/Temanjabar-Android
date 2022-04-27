package id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store

import id.go.jabarprov.dbmpr.core_main.store.State

data class RegisterState(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val repeatPassword: String? = null,
    val isAgree: Boolean = false,
    val screenState: RegisterScreenState = RegisterScreenState.REGISTER_NAME,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null
) : State

enum class RegisterScreenState {
    REGISTER_NAME,
    REGISTER_EMAIL,
    REGISTER_PASSWORD,
    REGISTER_AGREEMENT
}