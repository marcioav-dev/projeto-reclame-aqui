package br.com.sabbetecnologia.reclameaqui.administracao

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import br.com.sabbetecnologia.reclameaqui.MainActivity
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos.AdmEventosMainActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos.boletos.AdmBoletosActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.AdminMsgOpcoesActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.reclamacoesSugestoes.ReclamaSugestActivity
import com.google.firebase.auth.FirebaseAuth

class TelaInicioAdminActivity : AppCompatActivity() {

    private lateinit var btnMsg: Button
    private lateinit var btnEventos: Button
    private lateinit var btnReclamSuget: Button
    private lateinit var btnBoletos: Button

    private var alerta: AlertDialog? = null
    private lateinit var fbAuth: FirebaseAuth
    private var login = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicio_admin)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        btnMsg = findViewById(R.id.btnTIAAmensagens)
        btnEventos = findViewById(R.id.btnTIAAeventos)
        btnReclamSuget = findViewById(R.id.btnTIAAreclamacoes)
        btnBoletos = findViewById(R.id.btnTIAAboletos)

    }

    override fun onResume() {
        super.onResume()

        btnMsg.setOnClickListener{
            val i = Intent(this@TelaInicioAdminActivity, AdminMsgOpcoesActivity::class.java)
            i.putExtra("LOGIN", login)
            startActivity(i)
            finish()
        }

        btnEventos.setOnClickListener{
            val i = Intent(this@TelaInicioAdminActivity, AdmEventosMainActivity::class.java)
            i.putExtra("LOGIN", login)
            startActivity(i)
            finish()
        }

        btnReclamSuget.setOnClickListener {
            val i = Intent(this@TelaInicioAdminActivity, ReclamaSugestActivity::class.java)
            i.putExtra("LOGIN", login)
            startActivity(i)
            finish()
        }

        btnBoletos.setOnClickListener {
            val i = Intent(this@TelaInicioAdminActivity, AdmBoletosActivity::class.java)
            i.putExtra("LOGIN", login)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        alertaDeslogar()
    }

    private fun alertaDeslogar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ATENÇÃO")
        builder.setMessage("Ao retornar a tela inicial o usuário ira deslogar! \nDeseja continuar?")
        builder.setPositiveButton("Confirmar") { dialogInterface, i ->
            fbAuth = FirebaseAuth.getInstance()
            fbAuth.signOut()
            if (fbAuth.currentUser != null){
                Toast.makeText(applicationContext, "Erro ao tentar deslogar o usuário", Toast.LENGTH_SHORT).show()
            }else {
                val c = Intent(this@TelaInicioAdminActivity, MainActivity::class.java)
                startActivity(c)
                finish()
            }
        }
        builder.setNegativeButton("Cancelar") { dialogInterface, i -> alerta!!.dismiss() }
        alerta = builder.create()
        alerta!!.show()
    }
}
