package uz.ikhtidev.diskret.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.databinding.ThemeCardBinding

class ThemeAdapter(val items: ArrayList<Theme>) :
    RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {

    private var listener:ThemeClickListener?=null

    fun setListener(listener:ThemeClickListener){
        this.listener = listener
    }
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
        holder.itemView.setOnClickListener {
            listener?.onThemeClick(position)
        }
    }

    override fun getItemCount(): Int = items.size

    interface ThemeClickListener{
        fun onThemeClick(position:Int)
    }
}