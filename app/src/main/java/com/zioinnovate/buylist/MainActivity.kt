package com.zioinnovate.buylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declaração das variáveis para os botões
    private lateinit var botaoNova: Button
    private lateinit var botaoListas: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando os botões com findViewById
        botaoNova = findViewById(R.id.botao_nova)
        botaoListas = findViewById(R.id.botao_listas)

        // Configuração do clique para criar uma nova lista
        botaoNova.setOnClickListener {
            val intent = Intent(this, CriarListaActivity::class.java)
            startActivity(intent)

            // Animação de transição para a próxima activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Configuração do clique para acessar as listas salvas
        botaoListas.setOnClickListener {
            val intent = Intent(this, ListasSalvasActivity::class.java)
            startActivity(intent)

            // Animação de transição para a próxima activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}