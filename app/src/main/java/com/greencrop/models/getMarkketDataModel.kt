package com.greencrop.models

import com.google.gson.annotations.SerializedName


class getMarkketDataModel(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("fertilizer_name")
    val fertilizer_name: String? = null,
    @SerializedName("fertilizer_cost")
    val fertilizer_cost: String? = null,
    @SerializedName("quantity_per_hactor")
    val quantity_per_hactor: String? = null,
)
