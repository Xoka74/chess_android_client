package com.xoka74.chess.ui.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xoka74.chess.R
import com.xoka74.chess.databinding.FragmentPlayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayFragment : Fragment() {

    private val binding by lazy { FragmentPlayBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.playPublic.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_play_to_navigation_matchmaking)
        }

        return binding.root
    }
}