package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.User

class UserListViewModel : ViewModel() {
    val userLiveData = MutableLiveData<ArrayList<User>>()
    val toastLiveData = MutableLiveData<String>()
    val transitionLiveData = MutableLiveData<String>()

    fun addUser() {
        var list = userLiveData.value
        if(list == null) list = ArrayList()
        val count = list.size.plus(1)
        list.add(User("Player $count", "$count"))
        userLiveData.postValue(list)
    }

    fun next() {
        var list = userLiveData.value
        if(list == null) list = ArrayList()
        if(list.size < 3)
            toastLiveData.postValue("3人以上必要です")
        else
            transitionLiveData.postValue("s")
    }
}