package com.greencrop.activities.HomeScreen.ui.season_crop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeasonalCropViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"

    }
    val text: LiveData<String> = _text


}