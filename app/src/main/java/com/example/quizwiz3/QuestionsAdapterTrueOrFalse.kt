package com.example.quizwiz3
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionsAdapterTrueOrFalse(
    private val questions: List<TrueOrFalseQuestion>,
    private val onItemClicked: (TrueOrFalseQuestion) -> Unit
) : RecyclerView.Adapter<QuestionsAdapterTrueOrFalse.TrueFalseViewHolder>() {

    // ViewHolder to hold the views for each item
    class TrueFalseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.questionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrueFalseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_true_false, parent, false)
        return TrueFalseViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrueFalseViewHolder, position: Int) {
        val currentQuestion = questions[position]
        holder.questionText.text = currentQuestion.questionText

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClicked(currentQuestion)
        }
    }

    override fun getItemCount(): Int = questions.size
}
