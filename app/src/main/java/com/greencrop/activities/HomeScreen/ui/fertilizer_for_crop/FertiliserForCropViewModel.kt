package com.greencrop.activities.HomeScreen.ui.fertilizer_for_crop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FertiliserForCropViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"

    }
    val text: LiveData<String> = _text


}