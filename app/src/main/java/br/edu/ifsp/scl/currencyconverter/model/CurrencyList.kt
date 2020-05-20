package br.edu.ifsp.scl.currencyconverter.model


data class CurrencyList(
    val list: List<CurrencyListItem>,
    val status: String
)