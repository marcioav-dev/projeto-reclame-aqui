package br.com.sabbetecnologia.reclameaqui.conexoes

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager

/**
 * Created by Marcio on 26/02/2018.
 */
class DbConn : AsyncTask<String, String, String>() {

    private lateinit var conn: Connection
    private var bancoHost: String = ""
    private var bancoNome: String = ""
    private var bancoUser: String = ""
    private var bancoSenha: String = ""

    @SuppressLint("Recycle")
    fun connGet(context: Context): Connection {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://$bancoHost/$bancoNome;user=$bancoUser;password=$bancoSenha;")

        } catch (e: Exception) {
            Log.e("CONN", "ERRO CALL", e)
        }

        return conn
    }

    override fun doInBackground(vararg strings: String): String? {
        return null
    }
}