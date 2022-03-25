package com.example.beesapp.data

import com.example.beesapp.model.Brewery

class DataSource {

    fun loadBreweries(): List<Brewery> {
        return listOf(
            Brewery("Boteco do JO", "Bar", 2.9f, "www.bj.com.br", "Avenida nossa senhora, 223"),
            Brewery("Restaurante da Nena", "Restaurante", 3.4f, "www.rnn.com.br", "Alameda dos anjos, 8892"),
            Brewery("Adega diamante", "Adega", 3.0f, "www.adegadiamante.com.br", "Rua Engenheiro Roberto de Souza, 9923"),
            Brewery("Bar do Mineiro", "Bar", 1.7f, "www.bardomineiro.com.br", "Avenida Jose Cavalcanti, 3322"),
            Brewery("Alambique do Sul", "Alambique", 4.3f, "wwww.alabiquedosul.com.br", "Rua 15 de novembro, 333"),
            Brewery("Restaurante 10", "Restaurante", 5f, "www.res10.com.br", "Avenida 23 de maio, 2212")
        )
    }
}