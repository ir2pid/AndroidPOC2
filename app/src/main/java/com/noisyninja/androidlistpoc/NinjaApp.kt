package com.noisyninja.androidlistpoc

import android.app.Application
import com.noisyninja.androidlistpoc.layers.di.DaggerNinjaComponent
import com.noisyninja.androidlistpoc.layers.di.NinjaComponent
import com.noisyninja.androidlistpoc.layers.di.NinjaInjector
import com.noisyninja.androidlistpoc.layers.di.NinjaModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber


/**
 * Application subclass
 * Created by sudiptadutta on 27/04/18.
 */

class NinjaApp : Application() {

    var ninjaComponent: NinjaComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        ninjaComponent = DaggerNinjaComponent.builder()
                .ninjaModule(NinjaModule(this, LeakCanary.install(this)))
                .build()
        //ninjaComponent!!.inject(this)
        NinjaInjector.setApplication(this)
        NinjaInjector.getNinjaComponent(this).inject(this)
/*
        val intent = Intent(this, TimeoutService::class.java)
        startService(intent)
*/
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}