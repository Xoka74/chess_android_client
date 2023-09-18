package com.xoka74.chess.ui.profile.history_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.xoka74.chess.domain.models.Game

class GameHistoryDiffUtils(
    private val oldList: List<Game>,
    private val newList: List<Game>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}