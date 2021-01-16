package com.hendev.besinlerkitabi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hendev.besinlerkitabi.R
import com.hendev.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.hendev.besinlerkitabi.util.gorselIndir
import com.hendev.besinlerkitabi.util.loadPH
import com.hendev.besinlerkitabi.viewmodel.BesinDetayVM
import kotlinx.android.synthetic.main.besin_rv_row.*
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {

    private var  besinId = 0
    private lateinit var viewModel : BesinDetayVM
    private lateinit var dataBinding : FragmentBesinDetayiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        //return inflater.inflate(R.layout.fragment_besin_detayi, container, false) //Data Binding yaptıktan sonra iptal edildi
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayVM::class.java)
        viewModel.roomVerisiniAl(besinId)

        observLiveData()
    }

    fun observLiveData() {
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {

                dataBinding.secilenBesin = it

                //Data Binding İle iptal edilen kodlar
                /*txtDetayIsim.text = it.besinIsim
                txtDetayKalori.text = it.besinKalori
                txtDetayKarbonhidrat.text = it.besinKarbonhidrat
                txtDetayProtein.text = it.besinProtein
                txtDetayYag.text = it.besinYag
                context?.let {
                    imgBesinDetayImage.gorselIndir(besin.besinGorsel, loadPH(it))
                }*/
            }
        })
    }

}