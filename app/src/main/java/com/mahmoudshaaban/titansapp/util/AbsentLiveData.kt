package com.mahmoudshaaban.titansapp.util

import androidx.lifecycle.LiveData

class AbsentLiveData<T : Any?> private constructor() : LiveData<T>()

{
init {
    postValue(value)
}
 companion object {
     fun <T> create() : LiveData<T> {
         return AbsentLiveData()
     }
 }
}