package com.zioinnovate.buylist

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zioinnovate.buylist.database.AppDatabase
import com.zioinnovate.buylist.database.Lista
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CriarListaActivity : AppCompatActivity() {
    private lateinit var nomeLista: EditText
    private lateinit var itensListaContainer: LinearLayout
    private val numeroLinhas = 20  // Definindo o número fixo de linhas para a lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_lista)

        nomeLista = findViewById(R.id.nomeLista)
        itensListaContainer = findViewById(R.id.itensListaContainer)

        // Criar as linhas automaticamente
        for (i in 1..numeroLinhas) {
            addLinhaToList(i)
        }

        // Botão para salvar a lista
        findViewById<View>(R.id.botaoSalvar).setOnClickListener {
            salvarLista()
        }
    }

    private fun addLinhaToList(numero: Int) {
        // Cria um EditText para a linha numerada
        val itemEditText = EditText(this)
        itemEditText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        itemEditText.hint = "Item $numero"  // Exibe o número da linha como hint

        // Adiciona a linha no container
        itensListaContainer.addView(itemEditText)
    }

    private fun salvarLista() {
        val nome = nomeLista.text.toString()
        val itens = mutableListOf<String>()

        // Coleta todos os itens inseridos
        for (i in 0 until itensListaContainer.childCount) {
            val itemView = itensListaContainer.getChildAt(i) as EditText
            val item = itemView.text.toString()
            if (item.isNotEmpty()) {
                itens.add(item)
            }
        }

        // Verifica se o título e os itens foram preenchidos
        if (nome.isNotEmpty() && itens.isNotEmpty()) {
            val lista = Lista(nome = nome, itens = itens.joinToString(", "))
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getInstance(applicationContext).listaDao().insert(lista)
                runOnUiThread {
                    Toast.makeText(this@CriarListaActivity, "Lista salva com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }
}
