package com.example.godstyle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.godstyle.R
import com.example.godstyle.model.Cita

class CitaAdapter(
    private val onDeleteClick: (Cita) -> Unit,
    private val onEditClick: (Cita) -> Unit
) : ListAdapter<Cita, CitaAdapter.CitaViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCliente: TextView = itemView.findViewById(R.id.tvCliente)
        private val tvServicio: TextView = itemView.findViewById(R.id.tvServicio)
        private val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(cita: Cita) {
            tvCliente.text = cita.cliente
            tvServicio.text = cita.servicio
            tvFechaHora.text = "${cita.fecha} ${cita.hora}"

            btnEditar.setOnClickListener { onEditClick(cita) }
            btnEliminar.setOnClickListener { onDeleteClick(cita) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Cita>() {
        override fun areItemsTheSame(old: Cita, new: Cita) =
            old.id == new.id
        override fun areContentsTheSame(old: Cita, new: Cita) =
            old == new
    }
}
