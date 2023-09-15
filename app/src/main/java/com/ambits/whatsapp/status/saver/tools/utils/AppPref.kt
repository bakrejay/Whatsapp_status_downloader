package com.ambits.whatsapp.status.saver.tools.utils

import android.content.Context
import com.ambits.whatsapp.status.saver.tools.data.enums.AppThemeType
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.KEY_PATH_WHATSAPP
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.KEY_PATH_WHATSAPP_BUSINESS
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.KEY_PERMISSION_WHATSAPP
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.KEY_PERMISSION_WHATSAPP_BUSINESS

class AppPref(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)

    companion object{
        const val PREF_NAME = "WhappStausChatAppPref"
        const val PREF_MODE = Context.MODE_PRIVATE

        const val KEY_IS_ON_BOARDING_SHOWED = "keyIsOnBoardingShowed"
        const val KEY_CURRENT_APP_THEME = "keyCurrentAppTheme"
        const val KEY_Is_ENABLED_DYNAMIC_THEME = "keyIsEnabledDynamicTheme"
    }


    fun isShowOnBoarding(): Boolean =
        sharedPreferences.getBoolean(KEY_IS_ON_BOARDING_SHOWED, false)

    fun setOnBoardingShowed() {
        sharedPreferences.edit().putBoolean(KEY_IS_ON_BOARDING_SHOWED, true).apply()
    }

    fun setWhatsappPath(path : String){
        sharedPreferences.edit().putString(KEY_PATH_WHATSAPP,path).apply()
    }

    fun getWhatsappPath() : String =  sharedPreferences.getString(KEY_PATH_WHATSAPP,"")!!

    fun setWhatsappBusinessPath(path : String){
        sharedPreferences.edit().putString(KEY_PATH_WHATSAPP_BUSINESS,path).apply()
    }

    fun getWhatsappBusinessPath() : String =  sharedPreferences.getString(KEY_PATH_WHATSAPP_BUSINESS,"")!!

    fun isWhatsappPermissionGiven() : Boolean = sharedPreferences.getBoolean(KEY_PERMISSION_WHATSAPP,false)

    fun setWhatsappPermissionGiven() {
        sharedPreferences.edit().putBoolean(KEY_PERMISSION_WHATSAPP, true).apply()
    }

    fun isWhatsappBusinessPermissionGiven() : Boolean = sharedPreferences.getBoolean(KEY_PERMISSION_WHATSAPP_BUSINESS,false)

    fun setWhatsappPermissionBusinessGiven() {
        sharedPreferences.edit().putBoolean(KEY_PERMISSION_WHATSAPP_BUSINESS, true).apply()
    }

    fun getCurrentAppTheme() =
        sharedPreferences.getString(KEY_CURRENT_APP_THEME, AppThemeType.System.themeType)

    fun isEnabledDynamicTheme() =
        sharedPreferences.getBoolean(KEY_Is_ENABLED_DYNAMIC_THEME, false)

    fun putCurrentTheme(themeType: String) {
        sharedPreferences.edit().putString(KEY_CURRENT_APP_THEME, themeType).apply()
    }

    fun setIsEnabledDynamicTheme(isDynamicColorEnable: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_Is_ENABLED_DYNAMIC_THEME, isDynamicColorEnable).apply()
    }

}