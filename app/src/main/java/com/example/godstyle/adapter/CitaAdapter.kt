package com.example.godstyle.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.godstyle.databinding.ItemCitaBinding
import com.example.godstyle.model.Cita

class CitaAdapter(
    private val onEditClick: (Cita) -> Unit,
    private val onDeleteClick: (Cita) -> Unit
) : ListAdapter<Cita, CitaAdapter.CitaViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = ItemCitaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CitaViewHolder(private val binding: ItemCitaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cita: Cita) {
            binding.tvCliente.text = cita.cliente
            binding.tvServicio.text = cita.servicio
            binding.tvFecha.text    = "${cita.fecha} ${cita.hora}"

            binding.btnEditar.setOnClickListener {
                onEditClick(cita)
            }
            binding.btnBorrar.setOnClickListener {
                onDeleteClick(cita)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Cita>() {
            override fun areItemsTheSame(old: Cita, new: Cita) = old.id == new.id
            override fun areContentsTheSame(old: Cita, new: Cita) = old == new
        }
    }
}
