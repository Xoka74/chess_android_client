package com.xoka74.chess.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xoka74.chess.R
import com.xoka74.chess.databinding.FragmentLoginBinding
import com.xoka74.chess.runOnUiThread
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding.username.addTextChangedListener {
            viewModel.enterUsername(it.toString())
        }

        binding.password.addTextChangedListener {
            viewModel.enterPassword(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                runOnUiThread {
                    binding.container.isVisible = !it.isLogging
                    binding.progressBar.isVisible = it.isLogging

                    binding.username.setText(it.username, TextView.BufferType.EDITABLE)
                    binding.password.setText(it.password, TextView.BufferType.EDITABLE)

                    if (it.isLoggingSuccessful) {
                        findNavController().navigate(R.id.navigation_play)
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.login()
        }

        return binding.root
    }
}