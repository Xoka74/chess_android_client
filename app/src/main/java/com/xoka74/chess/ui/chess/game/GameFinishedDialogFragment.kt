package com.xoka74.chess.ui.chess.game

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.xoka74.chess.R
import com.xoka74.chess.domain.models.GameStatus

class GameFinishedDialogFragment : DialogFragment() {

    companion object {
        const val isWhiteWinnerTag = "isWhiteWinnerTag"
        const val gameStatusTag = "gameStatusTag"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val isWhiteWinner = requireArguments().getBoolean(isWhiteWinnerTag)
        val winner = if (isWhiteWinner) "White" else "Black"
        val message = when (GameStatus.getByValue(requireArguments().getInt(gameStatusTag))) {
            GameStatus.CHECKMATE -> {
                "$winner wins. Checkmate!"
            }

            GameStatus.STALEMATE -> {
                "Nobody wins. Stalemate!"
            }

            GameStatus.SURRENDER -> {
                "$winner wins. Other side is surrendered"
            }

            else -> "Game has not been finished yet"
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                .setPositiveButton(R.string.main_menu) { dialog, id ->
                    findNavController().navigate(R.id.navigation_play)
                }
                .setNegativeButton(R.string.show_move_history, { dialog, id ->

                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}