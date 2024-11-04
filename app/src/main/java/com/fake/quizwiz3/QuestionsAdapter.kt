package com.fake.quizwiz3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionsAdapter(
    private val questions: List<MultipleChoiceQuestion>,
    private val onItemClicked: (MultipleChoiceQuestion) -> Unit
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.tvQuestion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_questions_adapter, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.questionTextView.text = question.questionText

        // Handle click events
        holder.itemView.setOnClickListener {
            onItemClicked(question) // Pass the entire question object, including options
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}

