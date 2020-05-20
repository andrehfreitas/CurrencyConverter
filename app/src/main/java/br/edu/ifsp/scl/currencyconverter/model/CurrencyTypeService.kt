package br.edu.ifsp.scl.currencyconverter.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.scl.currencyconverter.R
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

class CurrencyTypeService(val context: Context) {
    /* Fila de requisições Volley */
    private val filaRequisicoesVolley = Volley.newRequestQueue(context)
    private val gson = Gson()


    /* Acessa o WebService e retorna um LiveData que será preenchido */
    fun getCurrency(): MutableLiveData<CurrencyList> {

        /* Montando URL de consulta ao WebService */
        val url = "${CurrencyTypeApi.URL_BASE}${CurrencyTypeApi.END_POINT}"

        /* Montando requisição */
        val currencyListLd = MutableLiveData<CurrencyList>()
        val requisicao = buildRequest(url, currencyListLd)

        /* Adiciona a requisição na fila de requisições Volley */
        filaRequisicoesVolley.add(requisicao)

        return currencyListLd
    }

    private fun buildRequest(url: String, currencyListLd: MutableLiveData<CurrencyList>): JsonObjectRequest {
        return object: JsonObjectRequest(
            Method.GET,
            url,
            null,
            { currencyList: JSONObject? ->
                currencyList?.let {
                    currencyListLd.value = gson.fromJson(currencyList.toString(), CurrencyList::class.java)
                }
            },
            { error: VolleyError? -> Log.e("CurrencyConverter", "${error?.message}")}
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    Pair(
                        CurrencyTypeApi.X_RAPIDAPI_KEY_FIELD,
                        CurrencyTypeApi.X_RAPIDAPI_KEY_VALUE
                    ),
                    Pair(
                        CurrencyTypeApi.X_RAPIDAPI_HOST_FIELD,
                        CurrencyTypeApi.X_RAPIDAPI_HOST_VALUE
                    )
                )
            }
        }
    }
}