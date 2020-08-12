package br.edu.ifsp.scl.currencyconverter.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.edu.ifsp.scl.currencyconverter.R
import br.edu.ifsp.scl.currencyconverter.viewmodel.CurrencyTypeViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModelType: CurrencyTypeViewModel
    private lateinit var moedaAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.currency_converter_sdm)

        viewModelType = CurrencyTypeViewModel(this)

        currencyAdapterInit()

        btnCoverter.setOnClickListener {
            
        }


    }

    private fun currencyAdapterInit() {
        /* Preenchido por Web Service */
        moedaAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        spnMoedaOrigem.adapter = moedaAdapter
        spnMoedaDestino.adapter = moedaAdapter
        viewModelType.buscaMoedas().observe(
            this,
            Observer { listaMoedas ->
                if (listaMoedas.moedas.isNotEmpty()) {
                    // Uma vez que temos o HashMap de siglas e descrições das moedas, pegamos somente
                    // as siglas para popular o spinner
                    listaMoedas.moedas.keys.sorted().forEach { siglaMoeda ->
                        moedaAdapter.add(siglaMoeda)
                    }
                }
            }
        )
    }
}
