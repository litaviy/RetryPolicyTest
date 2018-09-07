package com.example.klitaviy.retrypolicytest.core

import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by klitaviy on 9/6/18-5:03 PM.
 */
object InitializationAction {

    fun provide(): Completable = setup()

    private fun setup(): Completable = connectErrorHandler(
            Completable.fromAction {
                if (RandomProvider.random.nextBoolean()) {
                    Logger.log("InitializationAction. Throwing exception.")
                    throw ExceptionProvider.getException("InitializationActionException")
                } else{
                    Logger.log("InitializationAction. Setup Success.")
                }
            }
    )

    private fun connectErrorHandler(target: Completable): Completable =
            target.retryWhen {
                it.flatMap { error ->
//                    if (RandomProvider.random.nextBoolean()) {
                        Logger.log("InitializationAction. Retry Call.")
                        Flowable.just("")
//                    } else {
//                        Logger.log("InitializationAction. Forward Error.")
//                        Flowable.error(error)
//                    }
                }
            }
}