package com.hendev.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hendev.besinlerkitabi.R

/*
fun String.benimEklentim(parametre: String){
    println(parametre)
}*/

fun ImageView.gorselIndir(url: String?, ph: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(ph).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun loadPH(contex : Context) :CircularProgressDrawable {
    return CircularProgressDrawable(contex).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}


@BindingAdapter("android:downloadImage")
fun downloadImage(view : ImageView, url: String?){
    view.gorselIndir(url, loadPH(view.context))
}