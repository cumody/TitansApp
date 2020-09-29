package com.mahmoudshaaban.titansapp.ui.main.account

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.models.AccountProperties
import com.mahmoudshaaban.titansapp.ui.main.account.state.AccountStateEvent
import kotlinx.android.synthetic.main.fragment_update_account.*

class UpdateAccountFragment : BaseAccountFragment(){




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        subscribeObservers()
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            stateChangeListener.onDataStateChange(dataState)
            Log.d(TAG, "UpdateAccountFragment, DataState: ${dataState} ")
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewstate ->
            if (viewstate != null){
                viewstate.accountProperties?.let {
                    Log.d(TAG, "UpdateAccountFragment, ViewState ${it} ")
                    setAccountProperties(it)
                }
            }

        })
    }

    private fun setAccountProperties(accountProperties: AccountProperties){
        input_email.setText(accountProperties.email)
        input_username.setText(accountProperties.username)
    }

    // for updating changes to the server
    private fun saveChanges(){
        viewModel.setStateEvent(
            AccountStateEvent.UpdateAccountPropertiesEvent(
                input_email.text.toString(),
                input_username.text.toString()
            )
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.save -> {
                saveChanges()
                return true
            }

        }

    }
}