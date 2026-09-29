package com.saathi.app.data.repository

/**
 * Abstraction over "whatever generates Saathi AI's replies".
 * For the MVP, [MockAiEngine] returns canned, keyword-matched responses.
 *
 * TO ADD A REAL LLM LATER:
 * 1. Create e.g. `class GeminiAiEngine(...) : AiEngine` or `OpenAiEngine`
 *    that calls the real API and returns the model's text reply.
 * 2. Swap the default engine passed into ChatRepository below (or inject
 *    it via the ViewModel factory) -- no other code needs to change,
 *    since ChatViewModel only depends on ChatRepository.getAiResponse().
 */
interface AiEngine {
    suspend fun getResponse(userMessage: String): String
}

class MockAiEngine : AiEngine {

    private val keywordResponses = listOf(
        listOf("tired", "exhausted", "sleep", "no sleep") to
            "It is completely normal to feel tired after childbirth. Consider resting and asking a family member for support.",
        listOf("sad", "cry", "crying", "down", "low") to
            "It's okay to feel sad sometimes. Your feelings are valid. Talking to someone you trust or a doctor can really help.",
        listOf("stressed", "overwhelmed", "anxious", "anxiety") to
            "Feeling overwhelmed is common for new mothers. Try taking a few deep breaths, and remember it's okay to ask for help.",
        listOf("happy", "good", "great", "better") to
            "That's wonderful to hear! Celebrate the small wins on your journey.",
        listOf("pain", "hurt", "ache", "sore") to
            "If you're experiencing physical pain, please consult your doctor. In the meantime, gentle rest can help.",
        listOf("baby", "feeding", "breastfeed", "milk") to
            "Every baby and mother's journey is different. Be patient with yourself, and reach out to a lactation consultant if needed."
    )

    private val fallbackResponses = listOf(
        "Thank you for sharing that with me. Remember, you are doing an amazing job.",
        "I'm here for you. Would you like a tip to help you feel better right now?",
        "Your well-being matters. Consider taking a short break and drinking some water."
    )

    override suspend fun getResponse(userMessage: String): String {
        val lowered = userMessage.lowercase()
        for ((keywords, response) in keywordResponses) {
            if (keywords.any { lowered.contains(it) }) return response
        }
        return fallbackResponses.random()
    }
}

class ChatRepository(
    private val aiEngine: AiEngine = MockAiEngine()
) {
    suspend fun getAiResponse(userMessage: String): String {
        return aiEngine.getResponse(userMessage)
    }
}
