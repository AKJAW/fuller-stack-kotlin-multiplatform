package com.akjaw.fullerstack.screens.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akjaw.fullerstack.android.R
import org.kodein.di.DI
import org.kodein.di.DIAware

//TODO base activity?
class SplashActivity : AppCompatActivity(), DIAware {
    override val di: DI by DI.lazy {
        TODO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Splash)
        super.onCreate(savedInstanceState)
    }
}
