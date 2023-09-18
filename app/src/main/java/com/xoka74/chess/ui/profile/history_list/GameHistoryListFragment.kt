package com.xoka74.chess.ui.profile.history_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xoka74.chess.R
import com.xoka74.chess.databinding.FragmentGameHistoryListBinding
import com.xoka74.chess.runOnUiThread
import com.xoka74.chess.ui.profile.history_list.adapter.GameHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameHistoryListFragment : Fragment() {

    private val viewModel: GameHistoryListViewModel by viewModels()
    private val binding by lazy { FragmentGameHistoryListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val adapter = GameHistoryAdapter {}
        val layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        var loading = true;
        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount;
                    val totalItemCount = layoutManager.itemCount;
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading && !viewModel.uiState.value.isLoaded) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            viewModel.fetchHistory()
                            loading = true;
                        }
                    }
                }
            }
        }

        binding.recyclerView.addOnScrollListener(onScrollListener)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                uiState.paginatedList ?: return@collect
                runOnUiThread {
                    binding.count.text = getString(R.string.total_games, uiState.paginatedList.count);
                    binding.recyclerView.post { adapter.insertItems(uiState.paginatedList.results) }

                    if (uiState.isLoaded) {
                        binding.recyclerView.removeOnScrollListener(onScrollListener)
                    }
                }
            }
        }

        return binding.root
    }
}