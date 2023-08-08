package uz.ikhtidev.diskret.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ikhtidev.diskret.database.entity.QuestionWithVariant
import uz.ikhtidev.diskret.databinding.ItemViewpageLayoutBinding
import uz.ikhtidev.diskret.ui.QuizActivity

class QuizAdapter(val context:QuizActivity, private val questions:List<QuestionWithVariant>) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>(){

    private var listener: QuizClickListener? = null
    fun setListener(listener: QuizClickListener?) {
        this.listener = listener
    }

    inner class QuizViewHolder(val binding:ItemViewpageLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            ItemViewpageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.binding.wvSavol.loadDataWithBaseURL("https://ikhtidev.uz", questions[position].question.title, "text/html", "windows-1252", null)
        val variantAdapter = VariantAdapter(questions[position].variants)
        holder.binding.rvJavob.adapter = variantAdapter

        variantAdapter.setListener(object : VariantAdapter.AnswerClickListener {
            override fun onClick(correctAnswer:String, isCorrect: Boolean) {
                listener?.onClick(correctAnswer, isCorrect)
                context.setOnVariantClickListener(correctAnswer, isCorrect)
            }
        })
    }

    interface QuizClickListener {
        fun onClick(correctAnswer:String, isCorrect: Boolean)
    }
}