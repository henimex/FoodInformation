package com.hendev.besinlerkitabi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendev.besinlerkitabi.R
import com.hendev.besinlerkitabi.adapters.BesinRecycleAdapter
import com.hendev.besinlerkitabi.viewmodel.BesinListesiVM
import kotlinx.android.synthetic.main.fragment_besin_listesi.*

class BesinListesiFragment : Fragment() {

    private lateinit var viewModel : BesinListesiVM
    private val recyclerBesinAd = BesinRecycleAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =ViewModelProviders.of(this).get(BesinListesiVM::class.java)
        viewModel.refreshData()
        besinListRV.layoutManager = LinearLayoutManager(context)
        besinListRV.adapter = recyclerBesinAd

        swipeRefreshLayout.setOnRefreshListener {
            progresBarList.visibility = View.VISIBLE
            txtListHata.visibility = View.GONE
            besinListRV.visibility = View.GONE
            viewModel.refreshDataOnline()
            swipeRefreshLayout.isRefreshing = false
        }

        observLiveData()
    }

    fun observLiveData() {
        viewModel.besinler.observe(viewLifecycleOwner, Observer { t ->
            t?.let {
                besinListRV.visibility = View.VISIBLE
                recyclerBesinAd.besinListesiniGuncelle(t)
            }
        })

        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if (it) {
                    txtListHata.visibility = View.VISIBLE
                    besinListRV.visibility = View.GONE
                } else {
                    txtListHata.visibility = View.GONE
                }
            }
        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleme ->
            yukleme?.let {
                if (it){
                    besinListRV.visibility = View.GONE
                    txtListHata.visibility = View.GONE
                    progresBarList.visibility = View.VISIBLE
                }else{
                    progresBarList.visibility = View.GONE
                }
            }
        })
    }
}