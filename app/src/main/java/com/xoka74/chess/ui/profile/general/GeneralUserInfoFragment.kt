package com.xoka74.chess.ui.profile.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xoka74.chess.databinding.FragmentGeneralUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralUserInfoFragment : Fragment() {


    private val viewModel: GeneralUserInfoViewModel by viewModels()
    private val binding by lazy { FragmentGeneralUserInfoBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }
}