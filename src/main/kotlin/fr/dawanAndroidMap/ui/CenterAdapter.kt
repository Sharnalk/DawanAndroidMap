package fr.dawanAndroidMap.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.dawanAndroidMap.R
import fr.dawanAndroidMap.data.Center

/**
 * Adapter pour afficher la liste des centres dans une RecyclerView.
 * à l'init défini une callback implémenté dans MainActivity
 */
class CentersAdapter(private val onItemClick: ((Center) -> Unit)? = null) : ListAdapter<Center, CentersAdapter.VH>(DIFF)
{

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Center>() {
            override fun areItemsTheSame(a: Center, b: Center) = a.id == b.id
            override fun areContentsTheSame(a: Center, b: Center) = a == b
        }
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.centerName)
        val addr: TextView = v.findViewById(R.id.centerAddress)
    }
    // Inflate le layout item_center
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_center, parent, false))
    // Bind les données dans la list avec la callback on Click
    override fun onBindViewHolder(holder: VH, pos: Int) {
        val c = getItem(pos)
        holder.name.text = c.name
        holder.addr.text = c.address
        holder.itemView.setOnClickListener { onItemClick?.invoke(c) }
    }
}