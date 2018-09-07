package com.example.klitaviy.retrypolicytest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.klitaviy.retrypolicytest.core.Logger
import com.example.klitaviy.retrypolicytest.core.actions.InitializationAction
import com.example.klitaviy.retrypolicytest.core.actions.SearchAction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            InitializationAction.provide()
                    .andThen(Completable.defer { SearchAction.provide() })
                    .retryWhen {
                        // TODO - Check retries count
                        it.flatMap { error ->
                            if (error is NonFatalException) {
                                Logger.log("MainActivity - Run Retry - ${error.message}")
                                Flowable.just("")
                            } else {
                                Logger.log("MainActivity - Forward Error - ${error.message}")
                                Flowable.error(error)
                            }
                        }
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Logger.log("MainActivity - Done.")
                    }, {
                        Logger.log("MainActivity - Error - $it")
                        it.printStackTrace()
                    })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
