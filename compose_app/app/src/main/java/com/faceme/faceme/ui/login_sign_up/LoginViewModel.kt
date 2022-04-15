package com.faceme.faceme.ui.login_sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobile.client.UserState
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.AccountOwner
import kotlinx.coroutines.flow.*

sealed interface LoginRegisterUiState {
    val userName: String
    val password: String
    val userState: UserState

    data class IsLogin(
        override var userName: String,
        override var password: String,
        override var userState: UserState
    ) : LoginRegisterUiState

    data class IsRegister(
        override var userName: String,
        override var password: String,
        override var userState: UserState,
        var firstName: String,
        var lastName: String,
        var displayName: String,
        var verificationCode: String,
        var showVerification: Boolean
    ) : LoginRegisterUiState
}

private data class LoginViewModelState(
    var userName: String,
    var password: String,
    var userState: UserState
) {
    fun toUiState(): LoginRegisterUiState =
        LoginRegisterUiState.IsLogin(
            userName,
            password,
            userState
        )
}

private data class RegisterViewModelState(
    var userName: String,
    var password: String,
    var userState: UserState,
    var firstName: String,
    var lastName: String,
    var displayName: String,
    var verificationCode: String,
    var showVerification: Boolean
) {
    fun toUiState(): LoginRegisterUiState =
        LoginRegisterUiState.IsRegister(
            userName,
            password,
            userState,
            firstName,
            lastName,
            displayName,
            verificationCode,
            showVerification
        )
}

class LoginViewModel : ViewModel() {
    private val viewModelState = MutableStateFlow(
        LoginViewModelState(
            "",
            "",
            UserState.SIGNED_OUT
        )
    )

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {

    }

    fun onUserNameChanged(newUserName: String) {
        viewModelState.update {
            it.copy(userName = newUserName)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        viewModelState.update {
            it.copy(password = newPassword)
        }
    }

    fun updateUserState(newUserState: UserState) {
        viewModelState.update {
            it.copy(userState = newUserState)
        }
    }

    fun loginUser() {

    }
}

class RegisterViewModel : ViewModel() {
    private val viewModelState = MutableStateFlow(
        RegisterViewModelState(
            "",
            "",
            UserState.GUEST,
            "",
            "",
            "",
            "",
            false
        )
    )

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {

    }

    fun onUserNameChanged(newUserName: String) {
        viewModelState.update {
            it.copy(userName = newUserName)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        viewModelState.update {
            it.copy(password = newPassword)
        }
    }

    fun updateUserState(newUserState: UserState) {
        viewModelState.update {
            it.copy(userState = newUserState)
        }
    }

    fun onFirstNameChanged(newFirstName: String) {
        viewModelState.update {
            it.copy(firstName = newFirstName)
        }
    }

    fun onLastNameChanged(newLastName: String) {
        viewModelState.update {
            it.copy(lastName = newLastName)
        }
    }

    fun onDisplayNameChanged(newDisplayName: String) {
        viewModelState.update {
            it.copy(displayName = newDisplayName)
        }
    }

    private fun showVerification() {
        viewModelState.update {
            it.copy(showVerification = true)
        }
    }

    fun onVerificationCodeChanged(newVerificationCode: String) {
        viewModelState.update {
            it.copy(verificationCode = newVerificationCode)
        }
    }

    fun registerUser() {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), uiState.value.userName)
            .build()
        Amplify.Auth.signUp( uiState.value.userName, uiState.value.password, options,
            { Log.i("FaceMe Auth", "User SignedUp") },
            { Log.i("FaceMe Auth", "User Failed to Signup") }
        )
        showVerification()
    }

    fun verify() {
        val stateSnap = uiState.value as LoginRegisterUiState.IsRegister
        Amplify.Auth.confirmSignUp(
            uiState.value.userName, stateSnap.verificationCode,
            { Log.i("FaceMe Authenticator", "User Verified") },
            { Log.i("FaceMe Authenticator", "User Failed to Verify") }
        )

        val user = AccountOwner.builder()
            .email(stateSnap.userName)
            .verifiedImage("")
            .firstName(stateSnap.firstName)
            .lastName(stateSnap.lastName)
            .displayName(stateSnap.displayName)
            .build()

        // Attach it to the API
        Amplify.API.mutate(
            ModelMutation.create(user),
            { Log.i("FaceMeVerifier", "Mutation Success, User Created") },
            { Log.i("FaceMeVerifier", "Mutation Failed, User Not Created")}
        )
    }
}