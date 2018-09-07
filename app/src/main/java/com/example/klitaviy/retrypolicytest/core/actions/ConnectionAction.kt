package com.example.klitaviy.retrypolicytest.core.actions

import io.reactivex.Completable

/**
 * Created by klitaviy on 9/6/18-5:03 PM.
 */
object ConnectionAction {

    fun provide(): Completable = setup()

    private fun setup(): Completable = connectErrorHandler(
            // Connect Device
            Completable.complete()
    )

    private fun connectErrorHandler(target: Completable): Completable =
            target.retryWhen { it }
}