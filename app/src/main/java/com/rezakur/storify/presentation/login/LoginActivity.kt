package com.rezakur.storify.presentation.login

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rezakur.storify.databinding.ActivityLoginBinding
import com.rezakur.storify.presentation.login.viewmodels.LoginStatus
import com.rezakur.storify.presentation.login.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        observeState()
    }

    private fun initView() {
        binding.apply {
            btnLogin.isEnabled = false
            btnLogin.setOnClickListener {
                loginViewModel.login()
            }
            etEmail.doOnTextChanged { text, _, _, _ ->
                loginViewModel.onEmailChanged(text.toString())
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                loginViewModel.onPasswordChanged(text.toString())
            }

        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    loginViewModel.loginState.collect { state ->
                        binding.btnLogin.isEnabled =
                            state.email.isNotEmpty() && state.password.isNotEmpty()
                        binding.llLoading.visibility =
                            if (state.loginStatus == LoginStatus.LOADING) VISIBLE else GONE

                        if (state.loginStatus == LoginStatus.SUCCESS) {
                            Toast.makeText(this@LoginActivity, "Sukses", Toast.LENGTH_LONG).show()
                        } else if (state.loginStatus == LoginStatus.FAILED) {
                            Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}