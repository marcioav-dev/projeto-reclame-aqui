package br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import br.com.sabbetecnologia.reclameaqui.MainActivity
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.boletos.BoletosMainActivity
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.eventos.EventosMainActivity
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.mensagens.MensagensMainActivity
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.reclamacoes.ReclamacoesMainActivity
import com.google.firebase.auth.FirebaseAuth

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class TelaInicioActivity : AppCompatActivity() {

    private var login = ""

    private var alerta: AlertDialog? = null
    private lateinit var fbAuth: FirebaseAuth

    private lateinit var btnMsgs: Button
    private lateinit var btnBoletos: Button
    private lateinit var btnEventos: Button
    private lateinit var btnReclamacoes: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicio)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        btnMsgs = findViewById(R.id.btnTIAAmensagens)
        btnBoletos = findViewById(R.id.btnTIAAboletos)
        btnEventos = findViewById(R.id.btnTIAAeventos)
        btnReclamacoes = findViewById(R.id.btnTIAAreclamacoes)
    }

    override fun onResume(){
        super.onResume()

        btnMsgs.setOnClickListener {
            val i = Intent(this@TelaInicioActivity, MensagensMainActivity::class.java)
            i.putExtra("LOGIN",login)
            startActivity(i)
            finish()
        }

        btnBoletos.setOnClickListener {
            val i = Intent(this@TelaInicioActivity, BoletosMainActivity::class.java)
            i.putExtra("LOGIN",login)
            startActivity(i)
            finish()
        }

        btnEventos.setOnClickListener {
            val i = Intent(this@TelaInicioActivity, EventosMainActivity::class.java)
            i.putExtra("LOGIN",login)
            startActivity(i)
            finish()
        }

        btnReclamacoes.setOnClickListener {
            val i = Intent(this@TelaInicioActivity, ReclamacoesMainActivity::class.java)
            i.putExtra("LOGIN",login)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
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
                val c = Intent(this@TelaInicioActivity, MainActivity::class.java)
                startActivity(c)
                finish()
            }
        }
        builder.setNegativeButton("Cancelar") { dialogInterface, i -> alerta!!.dismiss() }
        alerta = builder.create()
        alerta!!.show()
    }
}
