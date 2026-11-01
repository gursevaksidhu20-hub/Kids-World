package com.example.util

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

class SpeechHelper(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    var isInitialized = mutableStateOf(false)
        private set
    var isSpeaking = mutableStateOf(false)
        private set

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("SpeechHelper", "Failed to instantiate TTS", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Try default locale
                tts?.language = Locale.getDefault()
            }
            // Child-friendly clear pitch and pacing
            tts?.setPitch(1.05f)
            tts?.setSpeechRate(0.85f)
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    isSpeaking.value = true
                }
                override fun onDone(utteranceId: String?) {
                    isSpeaking.value = false
                }
                override fun onError(utteranceId: String?) {
                    isSpeaking.value = false
                }
            })
            isInitialized.value = true
        } else {
            isInitialized.value = false
        }
    }

    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
        if (!isInitialized.value || tts == null) {
            return
        }
        try {
            stop()
            val utteranceId = "utterance_${System.currentTimeMillis()}"
            val params = Bundle()
            tts?.speak(text, queueMode, params, utteranceId)
            playGentleHaptic()
        } catch (e: Exception) {
            Log.e("SpeechHelper", "TTS speak failed", e)
        }
    }

    fun stop() {
        try {
            tts?.stop()
            isSpeaking.value = false
        } catch (e: Exception) {
            // ignore
        }
    }

    fun playGentleHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(30)
            }
        } catch (e: Exception) {
            // vibrator not available or permission denied
        }
    }

    fun playSuccessHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 40, 60, 60)
                val amplitudes = intArrayOf(0, 180, 0, 255)
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(100)
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
        } catch (e: Exception) {
            // ignore
        }
    }
}
