package br.edu.ifsp.scl.currencyconverter.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.scl.currencyconverter.model.ListaMoedas
import br.edu.ifsp.scl.currencyconverter.model.CurrencyTypeService

class CurrencyTypeViewModel(context: Context): ViewModel() {
    private val model = CurrencyTypeService(context)

    fun buscaMoedas(): MutableLiveData<ListaMoedas> {
        return model.getCurrency()
    }
}