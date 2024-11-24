package com.demo.moviedemo.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


/**
 * A base class for data repositories that
 * handles API requests and provides a common error handling mechanism.
 */
open class DataRepository {

    companion object {
        const val TAG = "DataRepository"
    }

    /**
     * A generic function to handle API calls and wrap the result in an [ApiResult] object.
     *
     * This function executes the provided API call within a coroutine scope, handling potential exceptions and
     * returning a success or error result accordingly.
     *
     * @param T The type of the data expected from the API call.
     * @param dispatcher The coroutine dispatcher to use for executing the API call. Defaults to [Dispatchers.IO].
     * @param execute A suspending lambda function that performs the actual API call and returns a [Response] object.
     *
     * @return An [ApiResult] object representing the outcome of the API call.
     *         - [ApiResult.Success] if the API call was successful and a valid response body was received.
     *         - [ApiResult.Error] if the API call failed due to an exception or an unsuccessful response code.
     */
    open suspend fun <T: Any> handleApi(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        execute: suspend () -> Response<T>
    ): ApiResult<T> {
        val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
            throwable.printStackTrace()
        }

        return withContext(dispatcher + coroutineExceptionHandler) {
            try {
                val response = execute()
                val body = response.body()
                if (response.isSuccessful) {
                    val result = body?: "" as T
                    ApiResult.Success(result)
                } else {
                    val code = response.code()
                    val message = response.errorBody()?.string() + response.code()
                    if (code == 418) Timber.e("I'M A TEAPOT: %s", response)
                    ApiResult.Error(message)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                ApiResult.Error(e.message.toString())
            } catch (e: IOException){
                e.printStackTrace()
                ApiResult.Error(e.message.toString())
            } catch (e: Throwable){
                e.printStackTrace()
                ApiResult.Error(e.message.toString())
            }
        }
    }
}