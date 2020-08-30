package com.decimalab.easytask.viewmodel.splash

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.decimalab.easytask.BuildConfig
import com.decimalab.easytask.model.local.AppPreferences
import com.decimalab.easytask.model.remote.Networking
import com.decimalab.easytask.model.repository.ValidateTokenRepository
import retrofit2.HttpException

/**
 * Created by Shakil Ahmed Shaj on 15,April,2020.
 * shakilahmedshaj@gmail.com
 */
class SplashViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "SplashViewModel"
    }

    private val networkService = Networking.create(BuildConfig.BASE_URL)
    private val validateTokenRepository = ValidateTokenRepository(networkService)
    private var  sharedPreferences = application.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private var appPreferences: AppPreferences
    var token = MutableLiveData<String>()

    init {
        appPreferences = AppPreferences(sharedPreferences)
        token.value = appPreferences.getAccessToken()
    }


    fun validateToken() = liveData {

        try {
            val data = validateTokenRepository.validateToken(token.value.toString())
            emit(data)

        } catch (httpException: HttpException) {
            Log.e(TAG, httpException.toString())


        } catch (exception: Exception) {
            Log.e(TAG, exception.toString())


        }

    }

}