package com.mahmoudshaaban.titansapp.ui

interface DataStateChangeListener {

    fun onDataStateChange(dataState: DataState<*>?)


    fun expandAppBar()
}