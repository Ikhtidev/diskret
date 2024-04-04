package uz.ikhtidev.diskret.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.ikhtidev.diskret.R
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.databinding.ItemResultBinding

class ResultAdapter(val items: List<Theme>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultPercent = (items[position].testResult * 100) / items[position].testsCount
        holder.binding.apply {
            tvTheme.text = items[position].name
            "${items[position].testResult}/${items[position].testsCount}".also {
                tvResult.text = it
            }
            dropCircle.backgroundTintList =
                if (resultPercent > 50)
                    ContextCompat.getColorStateList(
                        holder.itemView.context,
                        R.color.purple_700
                    )
                else
                    ContextCompat.getColorStateList(
                        holder.itemView.context,
                        R.color.red
                    )
        }

    }

    override fun getItemCount(): Int = items.size

}

