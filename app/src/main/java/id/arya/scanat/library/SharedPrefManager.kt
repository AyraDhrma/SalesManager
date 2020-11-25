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
    private val PROV = "prov"
    private val KAB = "kab"
    private val TGLDAFTAR = "tgldaftar"
    private val FIREBASEID = "firebaseiid"

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
        edit?.putString(PROV, loginResponse.data.prov)
        edit?.putString(KAB, loginResponse.data.kabupaten)
        edit?.putString(TGLDAFTAR, loginResponse.data.tanggaldaftar)
        edit?.putString(MESSAGE, loginResponse.data.message)
        edit?.apply()
    }

    fun loadUserData(): LoginResponse.Data {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val loginResponse = LoginResponse.Data(
            sharedPreferences.getString(USERNAME, "").toString(),
            sharedPreferences.getString(PASSWORD, "").toString(),
            sharedPreferences.getString(NAMA, "").toString(),
            sharedPreferences.getString(IDROLE, "").toString(),
            sharedPreferences.getString(EMAIL, "").toString(),
            sharedPreferences.getString(PHONE, "").toString(),
            sharedPreferences.getString(PROV, "").toString(),
            sharedPreferences.getString(KAB, "").toString(),
            sharedPreferences.getString(TGLDAFTAR, "").toString(),
            ""
        )
        return loginResponse
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

    fun saveFirebaseId(id: String) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit?.putString(FIREBASEID, id)
        edit?.apply()
    }

    fun loadFirebaseId(): String {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(FIREBASEID, "").toString()
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