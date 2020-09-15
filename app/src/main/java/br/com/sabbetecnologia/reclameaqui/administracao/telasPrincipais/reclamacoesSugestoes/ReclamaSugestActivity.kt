package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.reclamacoesSugestoes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.TelaInicioAdminActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.reclamacoesSugestoes.recSugAdp.RecSugAdaptador
import br.com.sabbetecnologia.reclameaqui.modelos.ReclamSugestDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReclamaSugestActivity : AppCompatActivity() {

    private var login = ""
    private lateinit var recMensagens: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclama_sugest)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        recMensagens = findViewById(R.id.recRSA)

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        val array = ArrayList<ReclamSugestDAO>()

        recMensagens.layoutManager = LinearLayoutManager(this)
        recMensagens.adapter = RecSugAdaptador(array, login)

        val fireBaseDb = FirebaseDatabase.getInstance().reference.child("ReclamacoesSugestoes")
        fireBaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                array.clear()
                for (dados in dataSnapshot.children) {
                    val msg = dados.getValue(ReclamSugestDAO::class.java)
                    array.add(msg!!)
                    recMensagens.adapter = RecSugAdaptador(array, login)
                }
                recMensagens.scrollToPosition(array.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onBackPressed() {
        val i = Intent(this@ReclamaSugestActivity, TelaInicioAdminActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }
}
