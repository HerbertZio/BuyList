package com.zioinnovate.buylist

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.zioinnovate.buylist.database.AppDatabase
import com.zioinnovate.buylist.database.Lista
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalhesListaActivity : AppCompatActivity() {

    private lateinit var nomeLista: TextView
    private lateinit var itensListaContainer: LinearLayout
    private lateinit var botaoEditar: Button
    private lateinit var botaoSalvar: Button
    private lateinit var botaoExcluir: Button
    private lateinit var botaoAdicionarItem: Button

    private var isEditing = false
    private var listaNome: String? = null
    private var listaId: Int? = null  // ID da lista, que deve ser mantido para atualizações

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_lista)

        // Inicializa os componentes da UI
        nomeLista = findViewById(R.id.nomeLista)
        itensListaContainer = findViewById(R.id.itensListaContainer)
        botaoEditar = findViewById(R.id.botao_editar)
        botaoSalvar = findViewById(R.id.botao_editar_lista)
        botaoExcluir = findViewById(R.id.botao_excluir)
        botaoAdicionarItem = findViewById(R.id.botao_adicionar_item)

        listaNome = intent.getStringExtra("listaNome")

        if (listaNome != null) {
            // Carrega os detalhes da lista usando o nome
            loadLista(listaNome!!)
        } else {
            Toast.makeText(this, "Erro ao carregar a lista", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Ação para editar a lista
        botaoEditar.setOnClickListener {
            toggleEditMode(true)
        }

        // Ação para salvar as alterações na lista
        botaoSalvar.setOnClickListener {
            if (listaNome != null) {
                salvarLista()
            }
        }

        // Ação para excluir a lista
        botaoExcluir.setOnClickListener {
            if (listaNome != null) {
                excluirLista(listaNome!!)
            }
        }

        // Ação para adicionar um novo item à lista
        botaoAdicionarItem.setOnClickListener {
            addNewItemField()
        }
    }

    // Função para carregar os itens da lista do banco de dados
    private fun loadLista(nome: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val lista = AppDatabase.getInstance(applicationContext).listaDao().getByName(nome)
            runOnUiThread {
                if (lista != null) {
                    listaId = lista.id  // Salva o ID da lista para atualizações futuras
                    nomeLista.text = lista.nome
                    nomeLista.setTextColor(resources.getColor(android.R.color.black)) // Título em preto

                    // Limpa a lista exibida
                    itensListaContainer.removeAllViews()

                    val itens = lista.itens.split(", ")
                    for (item in itens) {
                        addLinhaToList(item)
                    }
                } else {
                    Toast.makeText(this@DetalhesListaActivity, "Lista não encontrada.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    // Função para adicionar os itens da lista no container (exibição)
    private fun addLinhaToList(item: String?) {
        val itemLinearLayout = LinearLayout(this)
        itemLinearLayout.orientation = LinearLayout.HORIZONTAL
        val itemEditText = EditText(this)
        itemEditText.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        itemEditText.setText(item)
        itemEditText.setTextColor(resources.getColor(android.R.color.black)) // Texto do item em preto
        itemEditText.isEnabled = false  // Impede edição do texto diretamente
        itemLinearLayout.addView(itemEditText)

        // Criando o checkbox ao lado do item
        val checkBox = CheckBox(this)
        itemLinearLayout.addView(checkBox)

        itensListaContainer.addView(itemLinearLayout)
    }

    // Função para adicionar um novo campo de item (linha)
    private fun addNewItemField() {
        val itemLinearLayout = LinearLayout(this)
        itemLinearLayout.orientation = LinearLayout.HORIZONTAL
        val itemEditText = EditText(this)
        itemEditText.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        itemEditText.setTextColor(resources.getColor(android.R.color.black)) // Texto do item em preto
        itemLinearLayout.addView(itemEditText)

        // Criando o checkbox ao lado do item
        val checkBox = CheckBox(this)
        itemLinearLayout.addView(checkBox)

        itensListaContainer.addView(itemLinearLayout)
    }

    // Função para salvar as alterações na lista
    private fun salvarLista() {
        val nome = listaNome!!
        val itens = mutableListOf<String>()

        for (i in 0 until itensListaContainer.childCount) {
            val itemView = itensListaContainer.getChildAt(i) as LinearLayout
            val itemEditText = itemView.getChildAt(0) as EditText
            val item = itemEditText.text.toString()
            if (item.isNotEmpty()) {
                itens.add(item)
            }
        }

        if (nome.isNotEmpty() && itens.isNotEmpty()) {
            val lista = Lista(id = listaId ?: 0, nome = nome, itens = itens.joinToString(", "))

            CoroutineScope(Dispatchers.IO).launch {
                // Atualiza a lista no banco de dados
                AppDatabase.getInstance(applicationContext).listaDao().update(lista)

                // Volta para a UI principal para mostrar as mudanças
                runOnUiThread {
                    Toast.makeText(this@DetalhesListaActivity, "Lista atualizada com sucesso!", Toast.LENGTH_SHORT).show()

                    // Desativa o modo de edição e recarrega a lista
                    toggleEditMode(false)
                    loadLista(nome)
                }
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    // Função para excluir a lista
    private fun excluirLista(nomeLista: String) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(applicationContext).listaDao().deleteByName(nomeLista)  // Exclui a lista pelo nome
            runOnUiThread {
                Toast.makeText(this@DetalhesListaActivity, "Lista excluída com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // Função para alternar entre o modo de edição e o modo de visualização
    private fun toggleEditMode(editing: Boolean) {
        isEditing = editing
        botaoSalvar.visibility = if (editing) View.VISIBLE else View.GONE
        botaoEditar.visibility = if (editing) View.GONE else View.VISIBLE
        botaoAdicionarItem.visibility = if (editing) View.VISIBLE else View.GONE

        // Permite ou não edição dos itens
        for (i in 0 until itensListaContainer.childCount) {
            val itemView = itensListaContainer.getChildAt(i) as LinearLayout
            val itemEditText = itemView.getChildAt(0) as EditText
            itemEditText.isEnabled = editing
            // Quando estiver em modo de edição, mude a cor do texto para branco
            if (editing) {
                itemEditText.setTextColor(resources.getColor(android.R.color.white))
            } else {
                itemEditText.setTextColor(resources.getColor(android.R.color.black)) // Volta a cor preta quando não estiver editando
            }
        }
    }
}
