package com.akjaw.framework.composition

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun AppCompatActivity.lifecycleModule(): DI.Module = DI.Module("lifecycleModule") {
    bind<FragmentActivity>() with singleton { this@lifecycleModule }
    bind<Context>("Context") with singleton { this@lifecycleModule }
}
