// ui/dashboard/DashboardAdapter.kt
package com.example.architectureexplorer.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.architectureexplorer.data.model.Entity
import com.example.architectureexplorer.databinding.ItemEntityBinding

class DashboardAdapter(
    private val onClick: (Entity) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.EntityViewHolder>() {

    private val entityList = mutableListOf<Entity>()

    fun submitList(list: List<Entity>) {
        entityList.clear()
        entityList.addAll(list)
        notifyDataSetChanged()
    }

    inner class EntityViewHolder(private val binding: ItemEntityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            binding.tvName.text = entity.name
            binding.tvArchitect.text = "Architect: ${entity.architect}"
            binding.tvLocation.text = "Location: ${entity.location}"
            binding.root.setOnClickListener { onClick(entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(entityList[position])
    }

    override fun getItemCount(): Int = entityList.size
}
