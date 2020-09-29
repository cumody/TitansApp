package com.mahmoudshaaban.titansapp.ui.main.account


import androidx.lifecycle.LiveData
import com.mahmoudshaaban.titansapp.models.AccountProperties
import com.mahmoudshaaban.titansapp.repository.main.AccountRepository
import com.mahmoudshaaban.titansapp.session.SessionManager
import com.mahmoudshaaban.titansapp.ui.BaseViewModel
import com.mahmoudshaaban.titansapp.ui.DataState
import com.mahmoudshaaban.titansapp.ui.main.account.state.AccountStateEvent
import com.mahmoudshaaban.titansapp.ui.main.account.state.AccountStateEvent.*
import com.mahmoudshaaban.titansapp.ui.main.account.state.AccountViewState
import com.mahmoudshaaban.titansapp.util.AbsentLiveData
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepository
)
    : BaseViewModel<AccountStateEvent, AccountViewState>()
{
    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        when(stateEvent){

            is GetAccountPropertiesStateEvent ->{
                return sessionManager.cachedToken.value?.let {authtoken ->
                    accountRepository.getAccountProperties(authtoken)

                }?: AbsentLiveData.create()
            }
            is UpdateAccountPropertiesEvent ->{
                return sessionManager.cachedToken.value?.let {authtoken ->
                    authtoken.account_pk?.let { pk ->
                        accountRepository.saveAccountProperties(
                            authtoken,
                            AccountProperties(
                                pk ,
                                stateEvent.email,
                                stateEvent.username
                            )
                        )
                    }

                }?: AbsentLiveData.create()
            }
            is ChangePasswordEvent ->{
                return AbsentLiveData.create()
            }
            is None ->{
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties){
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties){
            return
        }
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun logout(){
        sessionManager.logout()
    }

}

