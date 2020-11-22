package id.arya.scanat.library

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.arya.scanat.model.response.LoginResponse
import id.arya.scanat.model.response.SplashScreenResponse
import javax.inject.Inject

class SharedPrefManager
@Inject
constructor(@ApplicationContext val context: Context) {

    private val MESSAGE = "message"
    private val SHARED_PREF_NAME = "SharedPrefManager"
    private val USERNAME = "username"
    private val PASSWORD = "password"
    private val NAMA = "nama"
    private val FOTO = "foto"
    private val IDROLE = "id_role"
    private val EMAIL = "email"
    private val PHONE = "phone"
    private val VERSION = "version"
    private val INFO = "info"
    private val URL = "url"
    private val KEY = "key"
    private val IMG_BANNER = "img_banner"
    private val GREETINGS = "greetings"

    fun saveUserData(loginResponse: LoginResponse) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString(USERNAME, loginResponse.data.username)
        edit?.putString(PASSWORD, loginResponse.data.password)
        edit?.putString(NAMA, loginResponse.data.nama)
        edit?.putString(IDROLE, loginResponse.data.id_role)
        edit?.putString(EMAIL, loginResponse.data.email)
        edit?.putString(PHONE, loginResponse.data.phone)
        edit?.putString(MESSAGE, loginResponse.data.message)
        edit?.apply()
    }

    fun saveSplashData(splashScreenResponse: SplashScreenResponse) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString(VERSION, splashScreenResponse.version)
        edit?.putString(INFO, splashScreenResponse.info)
        edit?.putString(URL, splashScreenResponse.url)
        edit?.putString(KEY, splashScreenResponse.key)
        edit?.putString(IMG_BANNER, splashScreenResponse.img_banner)
        edit?.putString(GREETINGS, splashScreenResponse.greetings)
        edit?.apply()
    }

    fun loadUrl(): String {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(URL, "").toString()
    }

    fun loadApiKey(): String {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY, "").toString()
    }

    fun loadUsername(): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(NAMA, "")
    }

    fun loadRole(): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(IDROLE, "")
    }

    fun loadSalesCode(): String {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(PASSWORD, "").toString()
    }

    fun clearUserData() {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString(USERNAME, "")
        edit?.putString(PASSWORD, "")
        edit?.putString(NAMA, "")
        edit?.putString(FOTO, "")
        edit?.putString(IDROLE, "")
        edit?.putString(EMAIL, "")
        edit?.putString(PHONE, "")
        edit?.putString(MESSAGE, "")
        edit?.apply()
    }

}