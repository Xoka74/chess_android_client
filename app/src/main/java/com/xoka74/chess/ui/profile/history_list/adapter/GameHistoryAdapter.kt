package com.xoka74.chess.ui.profile.history_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xoka74.chess.databinding.ItemGameBinding
import com.xoka74.chess.domain.models.Game

class GameHistoryAdapter(
    private val games: MutableList<Game> = mutableListOf(),
    private val onItemClick: ((Game) -> Unit),
) : RecyclerView.Adapter<GameHistoryAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(inflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
    }

    fun insertItems(items: List<Game>){
        games.addAll(items)
        this.notifyItemRangeInserted(games.size, items.size)
    }

    fun updateList(newGames: List<Game>) {
        val diffUtil = GameHistoryDiffUtils(games, newGames)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        games.addAll(newGames)

        diffResults.dispatchUpdatesTo(this)
    }

    inner class GameViewHolder(
        private val binding: ItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.board.text = game.id.toString()
        }
    }
}