package br.edu.ifsp.scl.currencyconverter.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import org.json.JSONObject

class CurrencyTypeService(val context: Context) {
    /* Fila de requisições Volley */
    private val filaRequisicoesVolley = Volley.newRequestQueue(context)

    // Aqui estamos registrando a classe de desserialização personalizada que criamos
    // Poderíamos usar um CustomDesserializer, mas isso é outro assunto.
    private val gson = GsonBuilder().registerTypeAdapter(ListaMoedas::class.java, ListaMoedasTypeAdapter().nullSafe()).create()

    /* Acessa o WebService e retorna um LiveData que será preenchido */
    fun getCurrency(): MutableLiveData<ListaMoedas> {

        /* Montando URL de consulta ao WebService */
        val url = "${CurrencyTypeApi.URL_BASE}${CurrencyTypeApi.END_POINT}"

        /* Montando requisição */
        val currencyListLd = MutableLiveData<ListaMoedas>()
        val requisicao = buildRequest(url, currencyListLd)

        /* Adiciona a requisição na fila de requisições Volley */
        filaRequisicoesVolley.add(requisicao)

        return currencyListLd
    }

    private fun buildRequest(url: String, listaMoedasLd: MutableLiveData<ListaMoedas>): JsonObjectRequest {
        return object: JsonObjectRequest(
            Method.GET,
            url,
            null,
            { currencyJsonResponse: JSONObject? ->
                currencyJsonResponse?.let {
                    // Aqui a função read do ListaMoedasTypeAdapter é chamado para converter o JSON num ListaMoedas
                    listaMoedasLd.value = gson.fromJson(currencyJsonResponse.toString(), ListaMoedas::class.java)
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