package com.saptarshidas.technohrms.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.auth.AuthRequest
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentLoginBinding
import com.saptarshidas.technohrms.ui.dashboard.HomeActivity
import com.saptarshidas.technohrms.ui.user_role.UserHomeActivity
import com.saptarshidas.technohrms.utils.enable
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.startNewActivity
import com.saptarshidas.technohrms.utils.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>() {

    private val TAG = "LoginFragment"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vModel.authLiveData.observe(viewLifecycleOwner, {
            
            viewBinding.progressBar.visible(false)
            when(it){

                is Resource.Success->lifecycleScope.launch {
                    Log.d(TAG, "onActivityCreated: ${it.data.jwt} ${it.data.username}")

                    preferencesHelper.setAccessToken(it.data.jwt)
                    preferencesHelper.setUsername(it.data.username)

                    if(it.data.role.get(0).authority.equals("ROLE_USER")){
                        requireActivity().startNewActivity(UserHomeActivity::class.java)
                    }else {
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }

                is Resource.Error-> {
                    handleApiError(it) { login() }
                }
            }
        })
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        viewBinding.progressBar.visible(false)
        viewBinding.signInBtn.enable(false)

        viewBinding.edTextPassword.addTextChangedListener{
            val email = viewBinding.edTextEmail.text.toString().trim()
            viewBinding.signInBtn.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }
    }

    override fun initActionView() {
        viewBinding.signInBtn.setOnClickListener{
            Log.d(TAG, "initActionView: ")
            login() // initiate=ing login

        }
    }

    private fun login(){
        viewBinding.progressBar.visible(true)
        val user = viewBinding.edTextEmail.text.toString().trim()
        val pass = viewBinding.edTextPassword.text.toString().trim()
        val authReq =  AuthRequest(user, pass)

        vModel.login(authReq)
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

}