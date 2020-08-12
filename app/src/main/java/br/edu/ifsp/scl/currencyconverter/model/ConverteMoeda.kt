package br.edu.ifsp.scl.currencyconverter.model

data class ConverteMoeda(
    var quantidade: Double,
    var codigoMoedaBase: String,
    var nomeMoedaBase: String,
    var taxa: TaxaMoeda,
    var status: String,
    var data: String
)