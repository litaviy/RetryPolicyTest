package com.example.klitaviy.retrypolicytest.core.actions

import com.example.klitaviy.retrypolicytest.NetworkException
import com.example.klitaviy.retrypolicytest.core.Logger
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

/**
 * Created by klitaviy on 9/6/18-5:03 PM.
 */
object LoginAction {

    fun provide(): Completable = setup()

    private fun setup(): Completable = connectErrorHandler(
            // Login
            // Throw LoginFailedException on LoginFailed RC
            // Throw BadCredentialsException on MissingAPI / Username / Password RC
            Completable.complete()
    )

    private fun connectErrorHandler(target: Completable): Completable =
            target.retryWhen {
                // TODO - Check retries count
                it.flatMap { error ->
                    when (error) {
                        is NetworkException -> {
                            Logger.log("LoginAction - NetworkException. Retry Call With Delay.")
                            Flowable.just("").delay(1000, TimeUnit.MILLISECONDS)
                        }
                        else -> {
                            Logger.log("LoginAction - Untracked Error - $error. Forward Error.")
                            Flowable.error(error)
                        }
                    }
                }
            }
}
