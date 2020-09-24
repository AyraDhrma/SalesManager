package id.arya.scanat.library

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.arya.scanat.model.response.LoginResponse
import javax.inject.Inject

class SharedPrefManager
@Inject
constructor(@ApplicationContext val context: Context) {

    private val SHARED_PREF_NAME = "SharedPrefManager"
    private val USERNAME = "username"
    private val PASSWORD = "password"
    private val NAMA = "nama"
    private val FOTO = "foto"
    private val IDROLE = "id_role"
    private val EMAIL = "email"
    private val PHONE = "phone"
    private val MESSAGE = "message"

    fun saveUserData(loginResponse: LoginResponse) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString(USERNAME, loginResponse.data.username)
        edit?.putString(PASSWORD, loginResponse.data.password)
        edit?.putString(NAMA, loginResponse.data.nama)
        edit?.putString(FOTO, loginResponse.data.foto)
        edit?.putString(IDROLE, loginResponse.data.id_role)
        edit?.putString(EMAIL, loginResponse.data.email)
        edit?.putString(PHONE, loginResponse.data.phone)
        edit?.putString(MESSAGE, loginResponse.data.message)
        edit?.apply()
    }

    fun loadUsername(): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(USERNAME, "")
    }

}