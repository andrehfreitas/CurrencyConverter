package br.edu.ifsp.scl.currencyconverter.model

import com.google.gson.annotations.SerializedName

data class CurrencyListItem (
    @SerializedName("currencies")
    val moeda: String
    )