package uz.ikhtidev.diskret.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.databinding.ThemeCardBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_NUMBER
import uz.ikhtidev.diskret.ui.ThemeDetailActivity

class ThemeAdapter(val items: ArrayList<Theme>) :
    RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ThemeCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ThemeCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.themeName.text = items[position].name
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            FILE_NUMBER = position
            context.startActivity(Intent(context, ThemeDetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int = items.size
}