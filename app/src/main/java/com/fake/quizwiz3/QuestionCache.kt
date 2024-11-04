package com.fake.quizwiz3

class QuestionCache {
    companion object {
        var cachedQuestionsMC: List<MultipleChoiceQuestion> = emptyList()
        var cachedQuestionsTF: List<TrueOrFalseQuestion> = emptyList()
    }
}