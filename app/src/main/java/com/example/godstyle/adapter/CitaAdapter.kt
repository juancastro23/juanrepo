package com.example.godstyle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.godstyle.R
import com.example.godstyle.model.Cita

class CitaAdapter(
    private val onDeleteClick: (Cita) -> Unit,
    private val onEditClick: (Cita) -> Unit
) : ListAdapter<Cita, CitaAdapter.CitaViewHolder>(CITA_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = getItem(position)
        holder.bind(cita)
    }

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtCliente = itemView.findViewById<TextView>(R.id.clienteText)
        private val txtServicio = itemView.findViewById<TextView>(R.id.servicioText)
        private val txtFechaHora = itemView.findViewById<TextView>(R.id.fechaText)
        private val botonEliminar = itemView.findViewById<Button>(R.id.botonEliminar)

        fun bind(cita: Cita) {
            txtCliente.text = cita.cliente
            txtServicio.text = cita.servicio
            txtFechaHora.text = "${cita.fecha} - ${cita.hora}"

            itemView.setOnClickListener { onEditClick(cita) }
            botonEliminar.setOnClickListener { onDeleteClick(cita) }
        }
    }

    companion object {
        private val CITA_COMPARATOR = object : DiffUtil.ItemCallback<Cita>() {
            override fun areItemsTheSame(oldItem: Cita, newItem: Cita): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Cita, newItem: Cita): Boolean = oldItem == newItem
        }
    }
}
