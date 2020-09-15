package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.TelaInicioAdminActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.msgOptAdp.MsgOptAdp
import br.com.sabbetecnologia.reclameaqui.cadastro.CadastroUsuarioDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminMsgOpcoesActivity : AppCompatActivity() {

    private lateinit var recAdminMsg: RecyclerView
    private var login = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_msg_opcoes)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        recAdminMsg = findViewById(R.id.recAMOA)

        val array = ArrayList<CadastroUsuarioDAO>()
        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        recAdminMsg.layoutManager = LinearLayoutManager(this)
        recAdminMsg.adapter = MsgOptAdp(array, login)

        val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Usuarios")
        fireBaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                array.clear()
                for (dados in dataSnapshot.children) {
                    val msg = dados.getValue(CadastroUsuarioDAO::class.java)
                    array.add(msg!!)
                    recAdminMsg.adapter = MsgOptAdp(array, login)
                }
                recAdminMsg.scrollToPosition(array.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onBackPressed() {
        val i = Intent(this@AdminMsgOpcoesActivity, TelaInicioAdminActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }
}
