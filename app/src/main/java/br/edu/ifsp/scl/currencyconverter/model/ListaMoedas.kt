package br.edu.ifsp.scl.currencyconverter.model

// Mudei seu dataclass porque o moedas não é uma lista na verdade.
data class ListaMoedas(
    var moedas: HashMap<String, String>,
    var status: String?
)