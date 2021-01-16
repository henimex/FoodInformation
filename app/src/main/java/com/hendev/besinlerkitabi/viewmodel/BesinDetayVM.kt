package com.hendev.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hendev.besinlerkitabi.model.Besin
import com.hendev.besinlerkitabi.servis.BesinDB
import kotlinx.coroutines.launch

class BesinDetayVM(application: Application) : BaseViewModel(application) {
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid:Int){
       launch {
           val dao = BesinDB(getApplication()).besinDao()
           val besin = dao.getBesin(uuid)
           besinLiveData.value = besin
       }


    /* val muz = Besin("Muz", "100", "10", "5", "1", "www.test.com")
        besinLiveData.value = muz*/
    }
}