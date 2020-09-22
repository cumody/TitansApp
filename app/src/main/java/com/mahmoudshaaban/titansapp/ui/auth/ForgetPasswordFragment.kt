package com.mahmoudshaaban.titansapp.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.DataState
import com.mahmoudshaaban.titansapp.ui.DataStateChangeListener
import com.mahmoudshaaban.titansapp.ui.Response
import com.mahmoudshaaban.titansapp.ui.ResponseType
import com.mahmoudshaaban.titansapp.ui.auth.ForgetPasswordFragment.WebAppInterface.*
import com.mahmoudshaaban.titansapp.util.Constants
import kotlinx.android.synthetic.main.fragment_forget_password.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ClassCastException


class ForgetPasswordFragment : BaseAuthFragment() {

    lateinit var webView: WebView
    lateinit var stateChangeListener: DataStateChangeListener

    val webInteractiononCallback : OnWebInterActionCallback = object : OnWebInterActionCallback{
        override fun onSuccess(email: String) {
            Log.d(TAG, "onSuccess: a reset link will be send to that email")
            onPasswordResetLinkSent()
        }

        override fun onError(errormessage: String) {

            Log.d(TAG, "onError: $errormessage")

            val dataState = DataState.error<Any>(
                response = Response(
                    errormessage , ResponseType.Dialog()
                )
            )
            stateChangeListener.onDataStateChange(
                dataState = dataState

            )
        }

        override fun onLoading(isLoading: Boolean) {
            Log.d(TAG, "onLoading: ...")
            GlobalScope.launch(Main) {
                stateChangeListener.onDataStateChange(
                    DataState.loading(isLoading = isLoading , cachedData = null)
                )
            }
        }

    }

    private fun onPasswordResetLinkSent() {
        GlobalScope.launch(Main) {
            parent_view.removeView(webView)
            webView.destroy() 

            val animation = TranslateAnimation(
                password_reset_done_container.width.toFloat(),
                0f ,
                0f ,
                0f
            )
            animation.duration = 500
            password_reset_done_container.startAnimation(animation)
            password_reset_done_container.visibility = View.VISIBLE
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webview)

        Log.d(TAG, "ForgotPasswordFragment: ${viewModel.hashCode()}")

        loadPasswordResetWebView()


        return_to_launcher_fragment.setOnClickListener{
            findNavController().popBackStack()
        }


    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadPasswordResetWebView() {
        stateChangeListener.onDataStateChange(
            DataState.loading(true , null)
        )
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                stateChangeListener.onDataStateChange(
                    DataState.loading(isLoading = false , cachedData = null)
                )
            }
        }
        webView.loadUrl(Constants.PASSWORD_RESET_URL)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(webInteractiononCallback) , "AndroidTextListener")



    }

    // handle appinterface with web
    class WebAppInterface constructor(
        private val callback : OnWebInterActionCallback) {

        private val TAG = "Appdebug"

        @JavascriptInterface
        fun onSuccess(email : String){
            callback.onSuccess(email)
        }
        @JavascriptInterface
        fun onError(errorMessage : String){
            callback.onError(errorMessage)
        }
        @JavascriptInterface
        fun onLoading(isLoading : Boolean   ){
            callback.onLoading(isLoading)
        }

        interface OnWebInterActionCallback{


            fun onSuccess(email : String)

            fun onError(errormessage : String)

            fun onLoading (isLoading : Boolean)

        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            stateChangeListener = context as DataStateChangeListener

        } catch (e: ClassCastException) {
            Log.d(TAG, "$context must implement DataStateChangeListener")

        }
    }
}