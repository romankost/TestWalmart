package com.romakost.testwalmart.data.network.retrofit_adapters

import com.romakost.testwalmart.data.network.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResultCall<T : Any>(
    private val delegate: Call<T>
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val networkResponse = try {
                        val body = response.body()
                        if (response.isSuccessful && body != null) {
                            NetworkResult.Success(body)
                        } else {
                            NetworkResult.Error(
                                code = response.code(),
                                message = response.message()
                            )
                        }
                    } catch (e: HttpException) {
                        NetworkResult.Error(code = e.code(), message = e.message())
                    } catch (e: Throwable) {
                        NetworkResult.Exception(e)
                    }

                    callback.onResponse(this@ResultCall, Response.success(networkResponse))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(NetworkResult.Exception(t))
                    )
                }
            }
        )
    }

    override fun isExecuted() = delegate.isExecuted

    override fun execute(): Response<NetworkResult<T>> = Response.success(
        NetworkResult.Success(
            delegate.execute().body()!!
        )
    )

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun clone(): Call<NetworkResult<T>> = ResultCall(delegate.clone())

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
