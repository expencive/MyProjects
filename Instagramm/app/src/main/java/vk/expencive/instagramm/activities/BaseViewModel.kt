package vk.expencive.instagramm.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BaseViewModel: ViewModel() {
    val cityLD = MutableLiveData<String>()
}