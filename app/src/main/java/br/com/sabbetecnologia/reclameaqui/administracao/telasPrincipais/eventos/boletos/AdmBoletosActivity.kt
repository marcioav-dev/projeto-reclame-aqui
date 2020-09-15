package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos.boletos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.TelaInicioAdminActivity
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.msgOptAdp.MsgOptAdp
import br.com.sabbetecnologia.reclameaqui.cadastro.CadastroUsuarioDAO
import br.com.sabbetecnologia.reclameaqui.modelos.BoletosDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest

class AdmBoletosActivity : AppCompatActivity() {

    private lateinit var spnUsuario: Spinner
    private lateinit var edtValor: EditText
    private lateinit var edtMulta: EditText
    private lateinit var edtDia: EditText
    private lateinit var edtMes: EditText
    private lateinit var edtAno: EditText
    private lateinit var btnLimpar: Button
    private lateinit var btnEnviar: Button

    private var usuarioSelecionado = ""

    private var login = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_boletos)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        spnUsuario = findViewById(R.id.spnABA)
        edtValor = findViewById(R.id.edtABAvalorFatura)
        edtMulta = findViewById(R.id.edtABAmultas)
        edtDia = findViewById(R.id.edtABAdia)
        edtMes = findViewById(R.id.edtABAmes)
        edtAno = findViewById(R.id.edtABAano)
        btnLimpar = findViewById(R.id.btnABAlimparCampos)
        btnEnviar = findViewById(R.id.btnABAenviar)

        val bundle = intent.extras
        login = bundle.getString("LOGIN")


        val usuariosEmail = ArrayList<CadastroUsuarioDAO>()
        val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Usuarios")

        fireBaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                usuariosEmail.clear()
                for (dados in dataSnapshot.children) {
                    val msg = dados.getValue(CadastroUsuarioDAO::class.java)
                    usuariosEmail.add(msg!!)
                }

                val email = ArrayList<String>()

                for (i in 0 until usuariosEmail.size){
                    val emailGet = usuariosEmail[i].emailUsuario.toString()
                    if (emailGet.equals("admin@gmail.com")){

                    }else {
                        email.add(emailGet)
                    }
                }

                val diaAdapter = ArrayAdapter(applicationContext,
                        R.layout.spinner_texto, email)
                diaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnUsuario.adapter = diaAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onBackPressed() {
        val i = Intent(this@AdmBoletosActivity, TelaInicioAdminActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }

    override fun onResume() {
        super.onResume()

        btnLimpar.setOnClickListener {
            edtValor.setText("")
            edtMulta.setText("")
            edtDia.setText("")
            edtMes.setText("")
            edtAno.setText("")
        }

        btnEnviar.setOnClickListener {
            usuarioSelecionado = spnUsuario.selectedItem.toString()
            val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Boletos").child(md5Gen(usuarioSelecionado))
            if (edtValor.text.isEmpty() || edtMulta.text.isEmpty() || edtDia.text.isEmpty() || edtMes.text.isEmpty() || edtAno.text.isEmpty()){
                Toast.makeText(applicationContext, "TODOS OS CAMPOS DEVEM SER PREENCHIDOS CORRETAMENTE", Toast.LENGTH_LONG).show()
            }else{
                val dao = BoletosDAO()
                dao.usuarioBoleto = usuarioSelecionado
                dao.valorBoleto = edtValor.text.toString()
                dao.multaBoleto = edtMulta.text.toString()
                val total = edtValor.text.toString().toFloat() + edtMulta.text.toString().toFloat()
                dao.totalBoleto = total.toString()
                dao.dataBoleto = edtDia.text.toString() + "/" + edtMes.text.toString() + "/" + edtAno.text.toString()

                try {
                    fireBaseDb.push().setValue(dao)

                    Toast.makeText(applicationContext, "BOLETO ENVIADO COM SUCESSO", Toast.LENGTH_LONG).show()
                    edtValor.setText("")
                    edtMulta.setText("")
                    edtDia.setText("")
                    edtMes.setText("")
                    edtAno.setText("")
                }catch (e: Exception){
                    Log.e("BOLETO", "GRAVA", e)
                }
            }

        }
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
