package id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store

import android.util.Patterns
import id.go.jabarprov.dbmpr.core_main.store.Store
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterStore @Inject constructor() : Store<RegisterAction, RegisterState>(RegisterState()) {
    override fun reduce(action: RegisterAction) {
        coroutineScope.launch {
            when (action) {
                RegisterAction.GoToNextScreen -> goToNextScreen()
                RegisterAction.GoToPreviousScreen -> goToPreviousScreen()
                is RegisterAction.UpdateAgreement -> updateAgreement(action.isAgree)
                is RegisterAction.UpdateEmail -> updateEmail(action.email)
                is RegisterAction.UpdateName -> updateName(action.name)
                is RegisterAction.UpdatePassword -> updatePassword(action.password)
                is RegisterAction.UpdateRepeatPassword -> updateRepeatPassword(action.repeatPassword)
            }
        }
    }

    private fun goToNextScreen() {
        when (state.value.screenState) {
            RegisterScreenState.REGISTER_NAME -> {
                state.value = state.value.copy(screenState = RegisterScreenState.REGISTER_EMAIL)
            }
            RegisterScreenState.REGISTER_EMAIL -> {
                state.value = state.value.copy(screenState = RegisterScreenState.REGISTER_PASSWORD)
            }
            RegisterScreenState.REGISTER_PASSWORD -> {
                state.value = state.value.copy(screenState = RegisterScreenState.REGISTER_AGREEMENT)
            }
        }
    }

    private fun goToPreviousScreen() {
        when (state.value.screenState) {
            RegisterScreenState.REGISTER_AGREEMENT -> {
                state.value = state.value.copy(screenState = RegisterScreenState.REGISTER_PASSWORD)
            }
            RegisterScreenState.REGISTER_PASSWORD -> {
                state.value = state.value.copy(screenState = RegisterScreenState.REGISTER_EMAIL)
            }
            RegisterScreenState.REGISTER_EMAIL -> {
                state.value = state.value.copy(screenState = RegisterScreenState.REGISTER_NAME)
            }
        }
    }

    private fun updateAgreement(isAgree: Boolean) {
        state.value = state.value.copy(isAgree = isAgree)
    }

    private fun updateName(name: String?) {
        state.value = state.value.copy(name = name)
        if (name.isNullOrBlank()) {
            state.value = state.value.copy(nameError = "Nama tidak boleh kosong")
        } else {
            state.value = state.value.copy(nameError = null)
        }
    }

    private fun updateEmail(email: String?) {
        state.value = state.value.copy(email = email)
        if (!email.isNullOrBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state.value = state.value.copy(emailError = "Email tidak valid")
        } else {
            state.value = state.value.copy(emailError = null)
        }
    }

    private fun updatePassword(password: String?) {
        state.value = state.value.copy(password = password)
        when {
            !password.isNullOrBlank() && password.length < 6 -> {
                state.value = state.value.copy(passwordError = "Password kurang dari 6 karakter")
            }
            else -> {
                state.value = state.value.copy(passwordError = null)
            }
        }
    }

    private fun updateRepeatPassword(repeatPassword: String?) {
        state.value = state.value.copy(repeatPassword = repeatPassword)
        if (repeatPassword != state.value.password) {
            state.value = state.value.copy(repeatPasswordError = "Password tidak sama")
        } else {
            state.value = state.value.copy(repeatPasswordError = null)
        }
    }
}