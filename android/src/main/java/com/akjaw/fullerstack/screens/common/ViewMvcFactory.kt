package com.akjaw.fullerstack.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akjaw.fullerstack.screens.common.mainactivity.MainViewMvc
import helpers.date.PatternProvider

class ViewMvcFactory(private val layoutInflater: LayoutInflater, private val patternProvider: PatternProvider) {

    fun getMainViewMvc(parent: ViewGroup?) = MainViewMvc(layoutInflater, parent)
}
