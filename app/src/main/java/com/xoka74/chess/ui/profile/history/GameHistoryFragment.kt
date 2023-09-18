package com.xoka74.chess.ui.profile.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xoka74.chess.databinding.FragmentGameHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameHistoryFragment : Fragment() {

    private val viewModel: GameHistoryViewModel by viewModels()
    private val binding by lazy { FragmentGameHistoryBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }
}