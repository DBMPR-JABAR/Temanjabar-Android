package id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store

import id.go.jabarprov.dbmpr.core_main.store.Action

sealed class RegisterAction : Action {
    object GoToNextScreen : RegisterAction()
    object GoToPreviousScreen : RegisterAction()
    data class UpdateName(val name: String) : RegisterAction()
    data class UpdateEmail(val email: String) : RegisterAction()
    data class UpdatePassword(val password: String) : RegisterAction()
    data class UpdateRepeatPassword(val repeatPassword: String) : RegisterAction()
    data class UpdateAgreement(val isAgree: Boolean) : RegisterAction()
}