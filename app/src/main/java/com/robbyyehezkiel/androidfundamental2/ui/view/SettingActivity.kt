package com.robbyyehezkiel.androidfundamental2.ui.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.robbyyehezkiel.androidfundamental2.R
import com.robbyyehezkiel.androidfundamental2.databinding.ActivitySettingBinding
import com.robbyyehezkiel.androidfundamental2.utils.dataStore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding!!

    private val themeKey = stringPreferencesKey("theme")
    private val themeFlow = dataStore.data.map { preferences ->
        preferences[themeKey] ?: "light"
    }

    private val debouncePeriod = 300L // milliseconds
    private var themeChangeJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        setupThemeSwitch()
        setupLanguageButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.setting)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupThemeSwitch() {
        lifecycleScope.launch {
            val themeMode = themeFlow.first()
            binding.switchButton.isChecked = themeMode == "dark"
        }

        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            themeChangeJob?.cancel() // cancel any ongoing theme change job
            themeChangeJob = lifecycleScope.launch {
                delay(debouncePeriod)
                saveThemeMode(if (isChecked) "dark" else "light")
            }
        }
    }

    private fun setupLanguageButton() {
        binding.language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private suspend fun saveThemeMode(themeMode: String) {
        dataStore.edit { preferences ->
            preferences[themeKey] = themeMode
        }
        applyTheme(themeMode)
    }

    private fun applyTheme(themeMode: String) {
        val nightMode = when (themeMode) {
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
