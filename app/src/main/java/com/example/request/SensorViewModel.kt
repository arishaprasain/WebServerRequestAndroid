package com.example.request



import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.await

class SensorViewModel : ViewModel() {
    var bpm = mutableStateOf(0)
        private set
    var spo2 = mutableStateOf(0)
        private set

    init {
        fetchSensorDataPeriodically()
    }

    private fun fetchSensorDataPeriodically() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                try {3
                    val response = RetrofitClient.sensorApi.getSensorData().await()
                    // Log the raw JSON response (using toString for simplicity)
                    Log.d("SensorViewModel", "JSON Response: $response")
                    bpm.value = response.BPM
                    spo2.value = response.SpO2

                } catch (e: Exception) {
                    // Log any errors encountered
                    Log.e("SensorViewModel", "Error fetching sensor data", e)
                    e.printStackTrace()
                }
                delay(1000) // Wait 1 second before next request
            }
        }
    }
}


