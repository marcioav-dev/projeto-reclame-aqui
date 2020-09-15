package br.com.sabbetecnologia.reclameaqui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.sabbetecnologia.reclameaqui.cadastro.CadastroUsuarioDAO
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Suppress("ReplaceCallWithBinaryOperator", "RedundantSamConstructor")
class CadastroUserActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var edtSenhaAuth: EditText
    private lateinit var edtNome: EditText
    private lateinit var btnLimpar: Button
    private lateinit var btnEnviar: Button

    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_user)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        edtEmail = findViewById(R.id.edtCAemail)
        edtSenha = findViewById(R.id.edtCAsenha)
        edtSenhaAuth = findViewById(R.id.edtCAsenhaAuth)
        edtNome = findViewById(R.id.edtCAnome)
        btnLimpar = findViewById(R.id.btnCAlimpar)
        btnEnviar = findViewById(R.id.btnCAenviar)

        fbAuth = FirebaseAuth.getInstance()
    }

    override fun onBackPressed() {
        val i = Intent(this@CadastroUserActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onResume() {
        super.onResume()

        btnLimpar.setOnClickListener {
            edtEmail.setText("")
            edtSenha.setText("")
            edtSenhaAuth.setText("")
        }

        btnEnviar.setOnClickListener {
            if (edtEmail.text.toString().equals("") || edtSenha.text.toString().equals("") || edtSenhaAuth.text.toString().equals("")) {
                Toast.makeText(applicationContext, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            }else{
                if (edtSenha.text.toString() != edtSenhaAuth.text.toString()){
                    Toast.makeText(applicationContext, "As senhas devem ser iguais. Tente Novamente", Toast.LENGTH_SHORT).show()
                }else{
                    fbAuth.createUserWithEmailAndPassword(edtEmail.text.toString(), edtSenha.text.toString()).addOnCompleteListener(this@CadastroUserActivity, OnCompleteListener<AuthResult>{
                        if (it.isComplete){
                            val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Usuarios")

                            val email = edtEmail.text.toString()
                            val nome = edtNome.text.toString()
                            val cadUser = CadastroUsuarioDAO()

                            cadUser.nomeCompleto = nome
                            cadUser.emailUsuario = email

                            fireBaseDb.push().setValue(cadUser)
                            Toast.makeText(applicationContext, "Usuário cadastrado com Sucesso", Toast.LENGTH_SHORT).show()
                            val i = Intent(this@CadastroUserActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }else{
                            Toast.makeText(applicationContext, "Erro ao cadastrar usuário, tente novamente.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}
