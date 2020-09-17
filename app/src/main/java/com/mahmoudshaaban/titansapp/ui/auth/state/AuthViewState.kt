package com.mahmoudshaaban.titansapp.ui.auth.state

import com.mahmoudshaaban.titansapp.models.AuthToken

data class AuthViewState(
    var registerationFields: RegisterationFields? = null ,
    var loginFields: LoginFields? = null ,
    var authToken: AuthToken? = null
)

data class RegisterationFields(
    var registeration_email: String? = null,
    var registeration_username: String? = null,
    var registeration_password: String? = null,
    var registeration_confirmPassword: String? = null
) {
    class RegisterationError {

        companion object {
           fun mustFillAllFields() : String {
               return "All fields are required"
           }
            fun passwordDoNotMatch() : String {
                return "Password must match"
            }
            fun none() : String {
                return "None"
            }

        }

    }
    fun isValidForRegisteration(): String{
        if (registeration_email.isNullOrEmpty()
            || registeration_username.isNullOrEmpty()
            || registeration_password.isNullOrEmpty()
            || registeration_confirmPassword.isNullOrEmpty()) {
            return RegisterationError.mustFillAllFields()

        }
        if (!registeration_password.equals(registeration_confirmPassword)){
            return RegisterationError.passwordDoNotMatch()
        }
        return RegisterationError.none()

    }



}
data class LoginFields(
    var login_email: String? = null,
    var login_password: String? = null
) {
    class LoginError {

        companion object {

            fun mustFillAllFields(): String {
                return "You can't login without an email and password."
            }

            fun none(): String {
                return "None"
            }

        }
    }

    fun isValidForLogin(): String {

        if (login_email.isNullOrEmpty()
            || login_password.isNullOrEmpty()
        ) {

            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }

    override fun toString(): String {
        return "LoginState(email=$login_email, password=$login_password)"
    }
}