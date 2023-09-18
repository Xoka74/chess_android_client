package com.xoka74.chess.ui.chess.game

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.xoka74.chess.R
import com.xoka74.chess.databinding.FragmentGameBinding
import com.xoka74.chess.domain.models.GameStatus
import com.xoka74.chess.runOnUiThread
import com.xoka74.chess.ui.chess.game.GameFinishedDialogFragment.Companion.gameStatusTag
import com.xoka74.chess.ui.chess.game.GameFinishedDialogFragment.Companion.isWhiteWinnerTag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment() {

    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val viewModel: GameViewModel by viewModels()
    private val moveSound by lazy { MediaPlayer.create(requireContext(), R.raw.move_self) }


    companion object {
        const val gameIdTag = "gameIdTag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val gameId = requireArguments().getInt(gameIdTag)

        viewModel.connectToGame(gameId)

        binding.chessBoard.onPieceMoved = { fromCell, toCell, pieceView ->
            val move = getMove(fromCell.getRowCol()) + getMove(toCell.getRowCol())
            viewModel.sendMove(move)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                runOnUiThread {
//                    it ?: return@runOnUiThread
//                    binding.chessBoard.setFen(it.game.board)
//                    it.isMemberWhite ?: return@runOnUiThread
//                    binding.chessBoard.setIsWhiteOriented(it.isMemberWhite)
//                    moveSound.start()
//                    if (it.gameStatus > GameStatus.CHECK) {
//                        val dialog = GameFinishedDialogFragment()
//
//                        dialog.arguments = bundleOf(
//                            isWhiteWinnerTag to it.isWhiteWinner,
//                            gameStatusTag to it.gameStatus.value
//                        )
//                        dialog.isCancelable = false
//                        dialog.show(parentFragmentManager, "dialog")
//                    }
                }
            }
        }

        return binding.root
    }

    private fun getMove(rowCol: Pair<Int, Int>): String {
        return (rowCol.second + 97).toChar() + (rowCol.first + 1).toString()
    }
}