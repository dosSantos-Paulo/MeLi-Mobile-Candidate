package com.dossantos.melimobilecandidate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dossantos.melimobilecandidate.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val analytics = Firebase.analytics
        BadCode().complexMethod("Blaclaclaclalcal")



        binding.sendClick.setOnClickListener {
            analytics.logEvent("click") {
                param(FirebaseAnalytics.Param.ITEM_ID, "buttom_home")
                param(FirebaseAnalytics.Param.ITEM_NAME, "new_button")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "buttom")
            }
        }

        binding.sendError.setOnClickListener {
            throw RuntimeException("Enviando erro para crashlytics")
        }
    }
}