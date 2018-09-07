package com.example.klitaviy.retrypolicytest.core.actions

import com.example.klitaviy.retrypolicytest.DeviceNotFoundException
import com.example.klitaviy.retrypolicytest.core.Logger
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by klitaviy on 9/6/18-5:03 PM.
 */
object SearchAction {

    fun provide(): Single<String> = setup()

    private fun setup(): Single<String> = connectErrorHandler(
            // Search
            // Throw DeviceNotFoundException if device is null
            Single.just("Some Device")
    )

    private fun connectErrorHandler(target: Single<String>): Single<String> =
            target.retryWhen {
                // TODO - Check retries count
                it.flatMap { error ->
                    if (error is DeviceNotFoundException) {
                        Logger.log("SearchAction - DeviceNotException. Retry Call With Delay.")
                        Flowable.just("").delay(1000, TimeUnit.MILLISECONDS)
                    } else {
                        Logger.log("SearchAction - Untracked Error - $error. Forward Error.")
                        Flowable.error(error)
                    }
                }
            }
}
