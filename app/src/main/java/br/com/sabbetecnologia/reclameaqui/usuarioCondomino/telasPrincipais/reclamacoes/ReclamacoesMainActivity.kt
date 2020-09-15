package br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.reclamacoes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.modelos.ReclamSugestDAO
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.TelaInicioActivity
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest



class ReclamacoesMainActivity : AppCompatActivity() {

    private var login = ""
    private lateinit var btnLimpar: Button
    private lateinit var btnEnviar: Button
    private lateinit var edtReclamacao: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamacoes_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        btnLimpar = findViewById(R.id.btnAMMUlimpar)
        btnEnviar = findViewById(R.id.btnAMMUenviar)
        edtReclamacao = findViewById(R.id.edtAMMUreclamacao)

        val bundle = intent.extras
        login = bundle.getString("LOGIN")
    }

    override fun onBackPressed() {
        val i = Intent(this@ReclamacoesMainActivity, TelaInicioActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()

        btnLimpar.setOnClickListener {
            edtReclamacao.setText("")
        }

        btnEnviar.setOnClickListener {
            when {
                edtReclamacao.text.toString().isEmpty() -> Toast.makeText(applicationContext, "O campo reclamação está em branco!!! Favor digitar uma mensagem antes de enviar", Toast.LENGTH_SHORT).show()
                edtReclamacao.text.length < 50 -> Toast.makeText(applicationContext, "A Mensagem é muito curta para ser enviada!", Toast.LENGTH_SHORT).show()
                else -> {
                    val destin = "admin@gmail.com"
                    val destinatario64 = md5Gen(destin)
                    val login64 = md5Gen(login)
                    val reclamaDAO = ReclamSugestDAO()

                    val today = Time(Time.getCurrentTimezone())
                    today.setToNow()

                    val dia = today.monthDay
                    val mes = today.month
                    val ano = today.year

                    reclamaDAO.mensagem = edtReclamacao.text.toString()
                    reclamaDAO.remetente = login
                    reclamaDAO.dataMsg = dia.toString() + "/" + String.format("%02d", mes) + "/" + ano.toString()
                    reclamaDAO.horaMsg = today.format("%k:%M:%S")
                    try {
                        val fireBaseSend = FirebaseDatabase.getInstance().reference.child("ReclamacoesSugestoes")
                        fireBaseSend.push().setValue(reclamaDAO)

                        edtReclamacao.setText("")
                        Toast.makeText(applicationContext, "Reclamação / Sugestão enviada com sucesso!", Toast.LENGTH_SHORT).show()
                    }catch (e: Exception){
                        Toast.makeText(applicationContext, "Erro ao tentar enviar a Reclamação / Sugestão", Toast.LENGTH_SHORT).show()
                        Log.e("ERRO", "ENVIAR", e)
                    }
                }
            }
        }
    }

    private fun md5Gen(texto: String): String{
        //TODO **************************************************************MD5****************************************************************************
        val md = MessageDigest.getInstance("MD5")
        md.update(texto.toByteArray())
        val byteData = md.digest()
        val sb = StringBuffer()
        for (i in byteData.indices) {
            sb.append(String.format("%02x", byteData[i]))
        }
        //TODO *********************************************************************************************************************************************
        return sb.toString()
    }
}
