package com.akjaw.fullerstack.screens.common.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseViewMvc

class MainViewMvc(layoutInflater: LayoutInflater, parent: ViewGroup?) : BaseViewMvc() {
    override val rootView: View = layoutInflater.inflate(R.layout.activity_main, parent, false)
}
