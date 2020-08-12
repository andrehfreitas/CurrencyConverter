package br.edu.ifsp.scl.currencyconverter.model

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class ListaMoedasTypeAdapter: TypeAdapter<ListaMoedas>() {
    override fun write(out: JsonWriter?, value: ListaMoedas?) {
        // Não implementei porque você só vai usar fromJson, logo, não é necessário.
    }

    // Quando chamar o fromJson, a função read deste TypeAdapter vai ser chamada para devolver
    // um objeto de ListaMoedas. Nesta solução vamos ter que desserializar o JSON na mão e para
    // isso vamos percorrer o JSON e criar um HashMap onde as chaves são a sigla das moedas e
    // os values são as descrições das moedas.
    override fun read(reader: JsonReader?): ListaMoedas {
        var statusMoeda = ""
        val moedasHashMap = HashMap<String, String>()
        reader?.let {
            reader.beginObject()

            while (reader.hasNext()) {
                var jsonToken = reader.peek()

                val nomeCampo = if (jsonToken == JsonToken.NAME) reader.nextName() else ""

                // Pega o valor do campo status no objeto JSON
                if (nomeCampo == "status") {
                    jsonToken = reader.peek()
                    statusMoeda = reader.nextString()
                }

                // Currencies não é um vetor, mas um objeto com muitos pares atributo-valor.
                // Veja o exemplo de uma das linhas  "BRL": "Brazilian Real"
                // Vamos pegar todos eles e separar num HashMap.
                if (nomeCampo == "currencies") {
                    reader.beginObject()

                    while (reader.hasNext()) {
                        var siglaDescricaoJsonToken = reader.peek()

                        // Aqui pegamos a parte do atributo. Exemplo: "BRL"
                        val siglaMoeda = if (siglaDescricaoJsonToken == JsonToken.NAME) reader.nextName() else ""

                        siglaDescricaoJsonToken = reader.peek()
                        // Aqui pegamos a descrição. Por exemplo "Brazilian Real"
                        // No seu aplicativo você não usa a descrição e poderíamos usar um ArrayList
                        // ao invés de um HashMap, mas assim a gente entende melhor o processo de desserialização
                        val descricaoMoeda = reader.nextString()

                        moedasHashMap[siglaMoeda] = descricaoMoeda
                    }

                    reader.endObject()
                }
            }
            reader.endObject()
        }

        // Depois de pegar o status e todos os pares que estavam no currencies, retornamos o
        // objeto de ListaMoedas
        return ListaMoedas(status = statusMoeda, moedas = moedasHashMap)
    }
}