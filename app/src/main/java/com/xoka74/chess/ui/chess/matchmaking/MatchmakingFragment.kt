package com.xoka74.chess.ui.chess.matchmaking

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xoka74.chess.R
import com.xoka74.chess.databinding.FragmentMatchmakingBinding
import com.xoka74.chess.runOnUiThread
import com.xoka74.chess.ui.chess.game.GameFragment.Companion.gameIdTag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchmakingFragment : Fragment() {

    private val binding by lazy { FragmentMatchmakingBinding.inflate(layoutInflater) }
    private val viewModel: MatchmakingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                runOnUiThread {
                    uiState.matchId ?: return@runOnUiThread
                    val mp = MediaPlayer.create(requireContext(), R.raw.notify)
                    mp.start()
                    mp.setOnCompletionListener {
                        findNavController().navigate(
                            R.id.navigation_game,
                            bundleOf(gameIdTag to uiState.matchId)
                        )
                    }
                }
            }
        }
        return binding.root
    }
}