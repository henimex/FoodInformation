package com.hendev.besinlerkitabi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hendev.besinlerkitabi.R
import com.hendev.besinlerkitabi.databinding.BesinRvRowBinding
import com.hendev.besinlerkitabi.model.Besin
import com.hendev.besinlerkitabi.util.gorselIndir
import com.hendev.besinlerkitabi.util.loadPH
import com.hendev.besinlerkitabi.view.BesinListesiFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.besin_rv_row.view.*

class BesinRecycleAdapter (val besinListesi:ArrayList<Besin>) : RecyclerView.Adapter<BesinRecycleAdapter.BesinViewHolder>(),BesinClick {

    class BesinViewHolder(var x: BesinRvRowBinding) : RecyclerView.ViewHolder(x.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val infilater = LayoutInflater.from(parent.context)
        //val design = infilater.inflate(R.layout.besin_rv_row, parent, false) // dataBinding yaptıktan sonra degistirildi.
        val design = DataBindingUtil.inflate<BesinRvRowBinding>(infilater,R.layout.besin_rv_row,parent,false)
        return BesinViewHolder(design)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.x.besin = besinListesi[position]
        holder.x.listener = this

        //Data Binding ayarlaması yaptıktan sonra asagıdaki kodlar degistirildi.
        /*holder.itemView.txtBesinIsmi.text = besinListesi.get(position).besinIsim
        holder.itemView.txtBesinKalori.text = besinListesi.get(position).besinKalori
        //Picasso Kulalnımı
        //Picasso.get().load(besinListesi.get(position).besinGorsel).into(holder.itemView.imageView)
        //Glide Kullanımı
        holder.itemView.imageView.gorselIndir(besinListesi.get(position).besinGorsel, loadPH(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi:List<Besin>) {
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besinUuid.text.toString().toInt()
        val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }

    fun hocaninBesinTiklandi(view: View) {
        val uuid = view.besinUuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }
}