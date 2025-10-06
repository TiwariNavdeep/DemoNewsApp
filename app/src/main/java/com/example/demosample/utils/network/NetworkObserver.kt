package com.example.demosample.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkObserver(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected.postValue(true)
        }

        override fun onLost(network: Network) {
            _isConnected.postValue(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startNetworkCallback() {
        // 1️⃣ Check current network status immediately
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        val connected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        _isConnected.postValue(connected)

        val request = android.net.NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    fun stopNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}