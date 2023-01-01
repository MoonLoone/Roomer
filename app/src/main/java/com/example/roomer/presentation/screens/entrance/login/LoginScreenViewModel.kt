package com.example.roomer.presentation.screens.entrance.login

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.api.RoomerApiObj
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.usecase.LoginUseCase
import com.example.roomer.utils.Resource
import com.example.roomer.state.LoginScreenState
import com.example.roomer.utils.SpManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val roomerRepository = RoomerRepository(RoomerApiObj.api)
    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state
    val loginUseCase = LoginUseCase(roomerRepository)

    init {
        val email = SpManager().getSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.EMAIL,
            FIELD_DEFAULT_VALUE
        )
        val password = SpManager().getSharedPreference(
            getApplication<Application>().applicationContext,
            SpManager.Sp.PASSWORD,
            FIELD_DEFAULT_VALUE
        )
        if (email != FIELD_DEFAULT_VALUE && password != FIELD_DEFAULT_VALUE) {
            getUserLogin(email!!, password!!)
        }
    }

    fun getUserLogin(email: String, password: String) {

        if (email.trim().isEmpty() || password.trim().isEmpty()
        ) {
            _state.value =
                LoginScreenState(error = EMPTY_FIELD_ERR_MSG, isLoading = false)
            return
        }

        viewModelScope.launch {
            loginUseCase(email, password, REQUEST_DELAY).collect { result ->
                when (result) {

                    is Resource.Loading -> {
                        _state.value = LoginScreenState(
                            isLoading = true,
                            internetProblem = false
                        )
                    }
                    is Resource.Success -> {
                        _state.value = LoginScreenState(
                            isLoading = false,
                            internetProblem = false,
                            success = true,
                        )
                        SpManager().setSharedPreference(
                            getApplication<Application>().applicationContext,
                            key = SpManager.Sp.TOKEN,
                            value = result.data
                        )
                    }
                    is Resource.Internet -> {
                        _state.value = LoginScreenState(
                            internetProblem = true,
                            isLoading = false,
                            error = result.message!!
                        )
                    }
                    is Resource.Error -> {
                        _state.value = LoginScreenState(
                            error = result.message!!,
                            isLoading = false,
                            internetProblem = false
                        )
                    }
                    else -> {
                        _state.value = LoginScreenState(
                            error = result.message!!,
                            isLoading = false,
                            internetProblem = false
                        )
                    }
                }
            }
            }
    }

    fun clearViewModel() {
        _state.value = LoginScreenState(
            error = "",
            isLoading = false,
            internetProblem = false,
            success = false
        )
    }

    companion object {
        const val FIELD_DEFAULT_VALUE = ""
        const val EMPTY_FIELD_ERR_MSG = "Field(s) can't be empty!"
        const val REQUEST_DELAY: Long = 2000

    }
}