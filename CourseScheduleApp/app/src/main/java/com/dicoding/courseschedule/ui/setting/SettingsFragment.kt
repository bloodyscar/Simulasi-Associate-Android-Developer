package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val swDark: ListPreference? = findPreference(getString(R.string.pref_key_dark))
        swDark?.setOnPreferenceChangeListener { _, newValue ->
            val strVal = newValue.toString()
            if (strVal == getString(R.string.pref_dark_auto)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    updateTheme(
                        NightMode.AUTO.value
                    )
                } else {
                    updateTheme(NightMode.OFF.value)
                }
            } else if (strVal == getString(R.string.pref_dark_off)) {
                updateTheme(NightMode.OFF.value)
            } else {
                updateTheme(NightMode.ON.value)

            }

            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val swNotif: SwitchPreference? = findPreference(getString(R.string.pref_key_notify))
        swNotif?.setOnPreferenceChangeListener{ _, newValue ->
            val broadcast = DailyReminder()
            if (newValue == true){
                broadcast.setDailyReminder(requireContext())
                Toast.makeText(activity,"enabled",Toast.LENGTH_LONG).show()
            }else{
                broadcast.cancelAlarm(requireContext())
                Toast.makeText(activity,"disabled",Toast.LENGTH_LONG).show()
            }

            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}