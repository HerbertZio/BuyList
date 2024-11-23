package com.zioinnovate.buylist

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zioinnovate.buylist.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListasSalvasActivity : AppCompatActivity() {

    private lateinit var listaView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas_salvas)

        listaView = findViewById(R.id.listaView)

        loadListas()

        listaView.setOnItemClickListener { _, _, position, _ ->
            val lista = listaView.adapter.getItem(position) as String
            val intent = Intent(this, DetalhesListaActivity::class.java)
            intent.putExtra("listaNome", lista)  // Passando o nome da lista corretamente
            startActivity(intent)
        }
    }

    private fun loadListas() {
        CoroutineScope(Dispatchers.IO).launch {
            val listas = AppDatabase.getInstance(applicationContext).listaDao().getAllListNames()
            runOnUiThread {
                if (listas.isNotEmpty()) {
                    // Usando o layout customizado para o item da lista
                    val adapter = object : ArrayAdapter<String>(this@ListasSalvasActivity, R.layout.item_lista, listas) {
                        override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                            val view = super.getView(position, convertView, parent)
                            // Aqui estamos pegando a TextView do layout customizado
                            val textView = view.findViewById<TextView>(R.id.itemTextView)
                            textView.setTextColor(resources.getColor(android.R.color.black))  // Definindo a cor preta
                            return view
                        }
                    }
                    listaView.adapter = adapter
                } else {
                    Toast.makeText(this@ListasSalvasActivity, "Nenhuma lista salva!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
