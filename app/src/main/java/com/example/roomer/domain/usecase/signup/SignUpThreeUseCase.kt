package com.example.roomer.domain.usecase.signup

import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpThreeUseCase(
    private val repository: AuthRepositoryInterface
) {

    operator fun invoke(
        token: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
    ): Flow<Resource<String>> = flow {

        try {
            emit(Resource.Loading())
            val process = repository.putSignUpDataThree(
                token,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits
            )

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success())
                }
            } else {
                val errMsg = process.errorBody()!!.string()
                emit(Resource.Error.GeneralError(message = errMsg))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
