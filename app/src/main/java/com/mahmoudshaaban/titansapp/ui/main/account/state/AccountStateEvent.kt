package com.mahmoudshaaban.titansapp.ui.main.account.state

sealed class AccountStateEvent {
   class GetAccountPropertiesStateEvent : AccountStateEvent()

    data class UpdateAccountPropertiesEvent(
        val email : String ,
        val username  : String
    ) : AccountStateEvent()

    data class ChangePasswordEvent(
        val currentPassword : String ,
        val newPassword : String ,
        val confirmPassword : String
    ) : AccountStateEvent()

    class None : AccountStateEvent()
}