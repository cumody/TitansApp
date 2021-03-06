package com.mahmoudshaaban.titansapp.ui.main.account

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.models.AccountProperties
import com.mahmoudshaaban.titansapp.session.SessionManager
import com.mahmoudshaaban.titansapp.ui.main.account.state.AccountStateEvent
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

class AccountFragment : BaseAccountFragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        change_password.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }
        logout_button.setOnClickListener {
           viewModel.logout()

        }
        subscribeObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_view_menu, menu)


    }

    private fun setAccountDataFields(accountProperties: AccountProperties){
        email?.setText(accountProperties.email)
        username?.setText(accountProperties.username)

    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner , Observer { dataState ->
            stateChangeListener.onDataStateChange(dataState)
            dataState?.let {
                it.data?.let { data ->
                    data.data?.let {  event ->
                        event.getContentIfNotHandled()?.let {ViewState ->
                            ViewState.accountProperties?.let {accountProperties ->
                                Log.d(TAG, "AccountFragment, DataState: ${accountProperties} ")
                                viewModel.setAccountPropertiesData(accountProperties)
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner , Observer { viewState ->
            viewState?.let {
                it.accountProperties?.let {
                    Log.d(TAG, "AccountFragment: ViewState ${it} ")
                    setAccountDataFields(it)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStateEvent(
            AccountStateEvent.GetAccountPropertiesStateEvent()
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                findNavController().navigate(R.id.action_accountFragment_to_updateAccountFragment)
                return true

            }

        }
        return super.onOptionsItemSelected(item)

    }
}