package br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.boletos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.reclamacoesSugestoes.recSugAdp.RecSugAdaptador
import br.com.sabbetecnologia.reclameaqui.modelos.BoletosDAO
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.TelaInicioActivity
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.boletos.boletoAdp.BoletoAdaptador
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest

class BoletosMainActivity : AppCompatActivity() {

    private var login = ""
    private lateinit var rec: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boletos_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        rec = findViewById(R.id.recBMA)

        val array = ArrayList<BoletosDAO>()

        rec.layoutManager = LinearLayoutManager(this)
        rec.adapter = BoletoAdaptador(array, login)

        val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Boletos").child(md5Gen(login))
        fireBaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                array.clear()
                for (dados in dataSnapshot.children) {
                    val msg = dados.getValue(BoletosDAO::class.java)
                    array.add(msg!!)
                    rec.adapter = BoletoAdaptador(array, login)
                }
                rec.scrollToPosition(array.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onBackPressed() {
        val i = Intent(this@BoletosMainActivity, TelaInicioActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }

    fun md5Gen(texto: String): String{
        //TODO **************************************************************MD5****************************************************************************
        val toMd5 = texto
        val md = MessageDigest.getInstance("MD5")
        md.update(toMd5.toByteArray())
        val byteData = md.digest()
        val sb = StringBuffer()
        for (i in byteData.indices) {
            sb.append(String.format("%02x", byteData[i]))
        }
        val md5gen = sb.toString()
        //TODO *********************************************************************************************************************************************
        return md5gen
    }
}
