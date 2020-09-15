package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.TelaInicioAdminActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.AdminMsgOpcoesActivity

class AdmEventosMainActivity : AppCompatActivity() {

    private var login = ""

    private lateinit var btnCadastrar: Button
    private lateinit var btnExibir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_eventos_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        btnCadastrar = findViewById(R.id.btnAEMAcadastrar)
        btnExibir = findViewById(R.id.btnAEMAvisualizar)
    }

    override fun onResume() {
        super.onResume()

        btnCadastrar.setOnClickListener {
            val i = Intent(this@AdmEventosMainActivity, AdmCadEventoActivity::class.java)
            i.putExtra("LOGIN", login)
            startActivity(i)
            finish()
        }

        btnExibir.setOnClickListener {
            val i = Intent(this@AdmEventosMainActivity, AdmExibirEventosActivity::class.java)
            i.putExtra("LOGIN", login)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        val i = Intent(this@AdmEventosMainActivity, TelaInicioAdminActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }
}
