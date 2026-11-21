package com.fixtrack.mobile.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fixtrack.mobile.R
import com.fixtrack.mobile.local.ParteEntity

class ParteAdapter(private var partes: List<ParteEntity>) :
    RecyclerView.Adapter<ParteAdapter.ParteViewHolder>() {

    class ParteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCliente: TextView = view.findViewById(R.id.tvCliente)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parte, parent, false)
        return ParteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParteViewHolder, position: Int) {
        val parte = partes[position]
        holder.tvCliente.text = parte.clienteNombre
        holder.tvDescripcion.text = parte.descripcionAveria
        holder.tvEstado.text = parte.estado
        holder.tvFecha.text = parte.fecha
    }

    override fun getItemCount(): Int = partes.size

    fun actualizarLista(nuevaLista: List<ParteEntity>) {
        partes = nuevaLista
        notifyDataSetChanged()
    }
}