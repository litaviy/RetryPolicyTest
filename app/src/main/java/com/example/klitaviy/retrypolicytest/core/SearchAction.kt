package com.example.klitaviy.retrypolicytest.core

import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

/**
 * Created by klitaviy on 9/6/18-5:03 PM.
 */
object SearchAction {

    fun provide(): Completable = setup()

    private fun setup(): Completable = connectErrorHandler(
            Completable.fromAction {
                if (RandomProvider.random.nextBoolean()) {
                    throw if (RandomProvider.random.nextBoolean()) {
                        Logger.log("SearchAction. Throwing NetworkException.")
                        NetworkException()
                    } else {
                        Logger.log("SearchAction. Throwing BadCredentialsException.")
                        BadCredentialsException()
                    }
                } else {
                    Logger.log("SearchAction. Setup Success.")
                }
            }
    )

    private fun connectErrorHandler(target: Completable): Completable =
            target.retryWhen {
                it.flatMap { error ->
                    if (error is NetworkException) {
                        Logger.log("SearchAction. Retry Call With Delay.")
                        Flowable.just("").delay(1000, TimeUnit.MILLISECONDS)
                    } else {
                        Logger.log("SearchAction. Forward Error.")
                        Flowable.error(error)
                    }
                }
            }
}

class NetworkException() : Exception("NetworkException Message")
class BadCredentialsException() : Exception("BadCredentialsException Message")