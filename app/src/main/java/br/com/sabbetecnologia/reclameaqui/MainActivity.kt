
package br.com.sabbetecnologia.reclameaqui

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.sabbetecnologia.reclameaqui.administracao.TelaInicioAdminActivity
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.TelaInicioActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

@Suppress("RedundantSamConstructor")
/**
 * Created by Marcio on 26/02/2018.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var btnEnviar: Button
    private lateinit var btnLimpar: Button
    private lateinit var txtCadastrar: TextView
    private lateinit var edtUser: EditText
    private lateinit var edtSenha: EditText

    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        btnEnviar = findViewById(R.id.btnMAenviar)
        btnLimpar = findViewById(R.id.btnMAcancelar)
        txtCadastrar = findViewById(R.id.txtMACadastrar)
        edtUser = findViewById(R.id.edtMAlogin)
        edtSenha = findViewById(R.id.edtMAsenha)
    }

    override fun onResume() {
        super.onResume()

        btnLimpar.setOnClickListener {
            edtUser.setText("")
            edtSenha.setText("")
        }

        btnEnviar.setOnClickListener {
            if (edtUser.text.toString().isEmpty() || edtSenha.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            }else if(edtUser.text.toString() == "admin@gmail.com"){
                val cn = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val nf = cn.activeNetworkInfo
                if (nf != null && nf.isConnected) {
                    fbAuth = FirebaseAuth.getInstance()
                    fbAuth.signInWithEmailAndPassword(edtUser.text.toString(), edtSenha.text.toString()).addOnCompleteListener(this@MainActivity, OnCompleteListener {
                        if (it.isSuccessful) {
                            if (fbAuth.currentUser != null) {
                                Log.i("USER", fbAuth.currentUser.toString())
                                val i = Intent(this@MainActivity, TelaInicioAdminActivity::class.java)
                                i.putExtra("LOGIN", edtUser.text.toString())
                                startActivity(i)
                                finish()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Usuario ou Senha inválidos", Toast.LENGTH_SHORT).show()
                        }
                    })
                }else{
                    Toast.makeText(applicationContext, "Dispositivo não conectado a Internet!", Toast.LENGTH_SHORT).show()
                }
            }else{
                val cn = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val nf = cn.activeNetworkInfo
                if (nf != null && nf.isConnected) {
                    fbAuth = FirebaseAuth.getInstance()
                    fbAuth.signInWithEmailAndPassword(edtUser.text.toString(), edtSenha.text.toString()).addOnCompleteListener(this@MainActivity, OnCompleteListener<AuthResult> {
                        if (it.isSuccessful) {
                            if (fbAuth.currentUser != null) {
                                Log.i("USER", fbAuth.currentUser.toString())
                                val i = Intent(this@MainActivity, TelaInicioActivity::class.java)
                                i.putExtra("LOGIN", edtUser.text.toString())
                                startActivity(i)
                                finish()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Usuario ou Senha inválidos", Toast.LENGTH_SHORT).show()
                        }
                    })
                }else{
                    Toast.makeText(applicationContext, "Dispositivo não conectado a Internet!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        txtCadastrar.setOnClickListener {
            val i = Intent(this@MainActivity, CadastroUserActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

