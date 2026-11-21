package com.fixtrack.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fixtrack.mobile.ui.ParteAdapter
import com.fixtrack.mobile.ui.ParteViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ParteViewModel
    private lateinit var adapter: ParteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar RecyclerView
        val recycler = findViewById<RecyclerView>(R.id.recyclerPartes)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ParteAdapter(emptyList())
        recycler.adapter = adapter

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this)[ParteViewModel::class.java]

        // Observar los cambios en los datos
        viewModel.partes.observe(this) { listaPartes ->
            adapter.actualizarLista(listaPartes)
        }

        // Cargar los partes del técnico
        viewModel.cargarPartesTecnico(1L)
    }
}