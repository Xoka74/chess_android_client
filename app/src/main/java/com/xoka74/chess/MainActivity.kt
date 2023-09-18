package com.xoka74.chess

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xoka74.chess.databinding.ActivityMainBinding
import com.xoka74.chess.ui.chess.game.GameFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_game,R.id.navigation_login,
                R.id.navigation_dashboard, R.id.navigation_play, R.id.navigation_profile,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                runOnUiThread {
                    binding.progressBar.isVisible = uiState.isLoading
                    binding.contentContainer.isGone = uiState.isLoading

                    if (uiState.game != null) {
                        binding.navView.isGone = true
                        navController.navigate(
                            R.id.navigation_game,
                            bundleOf(GameFragment.gameIdTag to uiState.game.id)
                        )
                    }
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_login || destination.id == R.id.navigation_game) {
                binding.navView.isGone = true
            } else {
                binding.navView.isVisible = true
            }
        }
        onInternetConnectionChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }



    private fun onInternetConnectionChanged() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
//        binding.connectionLost.isVisible = connectivityManager.activeNetwork == null

        var isConnectionAvailable = false

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
//                    binding.connectionLost.isVisible = false
//                    binding.connectionAvailable.isVisible = isConnectionAvailable
//                    isConnectionAvailable = true
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        binding.connectionAvailable.isVisible = false
//                    }, 4000)
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
//                    binding.connectionAvailable.isVisible = false
//                    binding.connectionLost.isVisible = true
                }
            }
        })
    }
}