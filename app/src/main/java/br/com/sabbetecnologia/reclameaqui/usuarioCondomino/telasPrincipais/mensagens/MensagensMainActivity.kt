package br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.mensagens

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.TelaInicioActivity
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.mensagens.adaptador.MsgAdp
import br.com.sabbetecnologia.reclameaqui.modelos.MsgDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest



class MensagensMainActivity : AppCompatActivity() {

    private var login = ""

    private lateinit var recMsgs: RecyclerView
    private lateinit var edtMsg: EditText
    private lateinit var btnEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensagens_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        recMsgs = findViewById(R.id.recAdmMsgs)
        edtMsg = findViewById(R.id.edtAMAmsg)
        btnEnviar = findViewById(R.id.btnAMAenviar)

        val array = ArrayList<MsgDAO>()

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        val destin = "admin@gmail.com"
        val destinatario64 = md5Gen(destin)
        val login64 = md5Gen(login)
        val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Mensagens").child(login64).child(destinatario64)

        recMsgs.layoutManager = LinearLayoutManager(this)
        recMsgs.adapter = MsgAdp(array, login)

        fireBaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                array.clear()
                for (dados in dataSnapshot.children) {
                    val msg = dados.getValue(MsgDAO::class.java)
                    array.add(msg!!)
                    recMsgs.adapter = MsgAdp(array, login)
                }
                recMsgs.scrollToPosition(array.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onResume() {
        super.onResume()

        btnEnviar.setOnClickListener {
            if (!edtMsg.text.toString().isEmpty()){
                val msgDAO = MsgDAO()
                msgDAO.destinatario = "admin@gmail.com"
                val destinatario = msgDAO.destinatario.toString()
                val destinatario64 = md5Gen(destinatario)
                val login64 = md5Gen(login)
                msgDAO.mensagem = edtMsg.text.toString()

                try {
                            val fireBaseSend = FirebaseDatabase.getInstance().reference.child("Mensagens")
                    fireBaseSend.child(login64).child(destinatario64).push().setValue(msgDAO)
                    fireBaseSend.child(destinatario64).child(login64).push().setValue(msgDAO)

                    edtMsg.setText("")
                }catch (e: Exception){
                    Log.e("ERRO", "ENVIAR", e)
                }
            }
        }
    }

    override fun onBackPressed() {
        val i = Intent(this@MensagensMainActivity, TelaInicioActivity::class.java)
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
