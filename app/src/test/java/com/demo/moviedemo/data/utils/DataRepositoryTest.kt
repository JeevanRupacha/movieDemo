package com.demo.moviedemo.data.utils

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DataRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var dataRepository: DataRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        dataRepository = DataRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `handleApi should return success result for successful response`() = runTest {
        // Arrange
        val mockResponse: Response<String> = mockk()
        coEvery { mockResponse.isSuccessful } returns true
        coEvery { mockResponse.body() } returns "Success"

        // Act
        val result = dataRepository.handleApi(
            dispatcher = testDispatcher,
            execute = { mockResponse }
        )

        // Assert
        assert(result is ApiResult.Success)
        assertEquals("Success", (result as ApiResult.Success).value)
    }

    @Test
    fun `handleApi should return error result for unsuccessful response`() = runTest {
        val mockResponse: Response<String> = mockk()
        val mockErrorBody: ResponseBody = mockk()
        coEvery { mockResponse.isSuccessful } returns false
        coEvery { mockResponse.code() } returns 400
        coEvery { mockResponse.errorBody() } returns mockErrorBody
        coEvery { mockErrorBody.string() } returns "Bad Request"

        val result = dataRepository.handleApi(
            dispatcher = testDispatcher,
            execute = { mockResponse }
        )

        assert(result is ApiResult.Error)
    }

    @Test
    fun `handleApi should handle IOException`() = runTest {
        val exception = java.io.IOException("Network error")
        val execute: suspend () -> Response<String> = { throw exception }

        val result = dataRepository.handleApi(
            dispatcher = testDispatcher,
            execute = execute
        )

        assert(result is ApiResult.Error)
        assertEquals("Network error", (result as ApiResult.Error).errorMsg)
    }

    @Test
    fun `handleApi should handle unknown exceptions`() = runTest {
        // Arrange
        val exception = RuntimeException("Unexpected error")
        val execute: suspend () -> Response<String> = { throw exception }

        // Act
        val result = dataRepository.handleApi(
            dispatcher = testDispatcher,
            execute = execute
        )

        // Assert
        assert(result is ApiResult.Error)
        assertEquals("Unexpected error", (result as ApiResult.Error).errorMsg)
    }
}