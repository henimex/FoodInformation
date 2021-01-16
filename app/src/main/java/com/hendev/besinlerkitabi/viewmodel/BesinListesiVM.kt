package com.hendev.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hendev.besinlerkitabi.model.Besin
import com.hendev.besinlerkitabi.servis.BesinAPIServis
import com.hendev.besinlerkitabi.servis.BesinDB
import com.hendev.besinlerkitabi.util.SpecialSharedPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiVM(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val besinApiServis = BesinAPIServis()
    private val disposable = CompositeDisposable()
    private val specialSharedPref=SpecialSharedPref(getApplication())
    private var guncellemeAraligi = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val kaydedilmeZamani = specialSharedPref.zamaniAl()

        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeAraligi){
            getDataOffline()
        }else{
            getDataOnline()
            //veri online alınacak
        }
    }

    fun refreshDataOnline(){
        getDataOnline()
    }

    private fun getDataOffline(){
        besinYukleniyor.value = true
        launch {
            val besinListesi = BesinDB(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri SQL / Roomdan Alındı",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataOnline() {
        besinYukleniyor.value = true
        disposable.add(besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                    override fun onSuccess(t: List<Besin>) {
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Besinler Internetten Alındı",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun besinleriGoster(besinlerListesi: List<Besin>) {
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinListesi: List<Besin>) {
        launch {
            val dao = BesinDB(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidList = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i < besinListesi.size) {
                besinListesi[i].uuid = uuidList[i].toInt()
                i += 1
            }
            besinleriGoster(besinListesi)
        }
        specialSharedPref.zamaniKaydet(System.nanoTime())
    }


    /*fun refreshFakeData() {
        val muz = Besin("Muz", "100", "10", "5", "1", "www.test.com")
        val cilek = Besin("Çilek", "90", "20", "5", "1", "www.test.com")
        val elma = Besin("Elma", "80", "3", "5", "1", "www.test.com")

        val besinlistesi = arrayListOf<Besin>(muz, cilek, elma)

        besinler.value = besinlistesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }*/

}