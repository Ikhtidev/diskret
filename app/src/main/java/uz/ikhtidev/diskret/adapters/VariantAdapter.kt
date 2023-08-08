package uz.ikhtidev.diskret.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ikhtidev.diskret.database.entity.Variant
import uz.ikhtidev.diskret.databinding.ItemVariantBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.ALPHABET

class VariantAdapter(val items: List<Variant>) : RecyclerView.Adapter<VariantAdapter.ViewHolder>() {

    private var listener: AnswerClickListener? = null
    fun setListener(listener: AnswerClickListener?) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemVariantBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVariantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.webViewJ.loadDataWithBaseURL(
            "Https://ikhtidev.uz",
            items[position].title,
            "text/html",
            "windows-1252",
            null
        )
        holder.binding.tvJ.text = ALPHABET[position]
        holder.itemView.setOnClickListener {
            listener?.onClick(getCorrectAnswer(), items[position].isTrue)
        }
    }

    private fun getCorrectAnswer(): String {
        for (i in items.indices){
            if (items[i].isTrue) return ALPHABET[i]
        }
        return "0"
    }

    override fun getItemCount(): Int = items.size


    interface AnswerClickListener {
        fun onClick(correctAnswer:String, isCorrect: Boolean)
    }
}