package com.example.expensetrackerai.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class VoiceRecognizer(private val context: Context) {

    // States to tell UI what is happening
    sealed class VoiceState {
        object Idle : VoiceState()
        object Listening : VoiceState()
        data class Result(val text: String) : VoiceState()
        data class Error(val message: String) : VoiceState()
    }

    fun startListening(): Flow<VoiceState> = callbackFlow {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur-PK")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ur-PK")
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "ur-PK") // English (ya hi-IN for Hindi)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                trySend(VoiceState.Listening)
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                val message = when (error) {
                    SpeechRecognizer.ERROR_NO_MATCH -> "No match found"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permission denied"
                    else -> "Error occurred: $error"
                }
                trySend(VoiceState.Error(message))
                close() // Stop flow on error
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    trySend(VoiceState.Result(matches[0]))
                }
                close() // Stop flow after result
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer.setRecognitionListener(listener)
        speechRecognizer.startListening(intent)

        awaitClose {
            speechRecognizer.destroy()
        }
    }
}