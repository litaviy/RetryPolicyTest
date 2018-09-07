package com.example.klitaviy.retrypolicytest.core.actions

import com.example.klitaviy.retrypolicytest.DeviceSetupException
import com.example.klitaviy.retrypolicytest.core.Logger
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

/**
 * Created by klitaviy on 9/6/18-5:03 PM.
 */
object SetupAction {

    fun provide(): Completable = setup()

    private fun setup(): Completable = connectErrorHandler(
            // Register
            // Throw DeviceSetupException if it is required
            Completable.complete()
    )

    private fun connectErrorHandler(target: Completable): Completable =
            target.retryWhen {
                // TODO - Check retries count
                it.flatMap { error ->
                    if (error is DeviceSetupException) {
                        Logger.log("SetupAction - DeviceSetupException. Retry Call With Delay.")
                        Flowable.just("").delay(1000, TimeUnit.MILLISECONDS)
                    } else {
                        Logger.log("SetupAction - Untracked Error - $error. Forward Error.")
                        Flowable.error(error)
                    }
                }
            }
}
