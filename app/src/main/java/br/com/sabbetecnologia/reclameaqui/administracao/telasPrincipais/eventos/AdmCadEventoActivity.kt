package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.modelos.EventoDAO
import com.google.firebase.database.FirebaseDatabase

class AdmCadEventoActivity : AppCompatActivity() {

    private var login = ""

    private lateinit var edtNome: EditText
    private lateinit var edtDia: EditText
    private lateinit var edtMes: EditText
    private lateinit var edtAno: EditText
    private lateinit var edtDetalhes: EditText

    private lateinit var btnLimpar: Button
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_cad_evento)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        edtNome = findViewById(R.id.edtACEAnome)
        edtDia = findViewById(R.id.edtACEAdia)
        edtMes = findViewById(R.id.edtACEAmes)
        edtAno = findViewById(R.id.edtACEAano)
        edtDetalhes = findViewById(R.id.edtACEAdetalhes)
        btnLimpar = findViewById(R.id.btnACEAlimpar)
        btnSalvar = findViewById(R.id.btnACEAsalvar)
    }

    override fun onResume() {
        super.onResume()

        btnLimpar.setOnClickListener {
            edtNome.setText("")
            edtDia.setText("")
            edtMes.setText("")
            edtAno.setText("")
            edtDetalhes.setText("")
        }

        btnSalvar.setOnClickListener {
            if (edtNome.text.isEmpty() || edtAno.text.isEmpty() || edtMes.text.isEmpty() || edtDia.text.isEmpty() || edtDetalhes.text.isEmpty()){
                Toast.makeText(applicationContext, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            }else{
                val nome = edtNome.text.toString()
                val ano = edtAno.text.toString()
                val mes = edtMes.text.toString()
                val dia = edtDia.text.toString()
                val detalhes = edtDetalhes.text.toString()

                val diaValida = dia.toInt()
                val mesValida = mes.toInt()
                val anoValida = ano.toInt()

                if (anoValida < 2018){
                    Toast.makeText(applicationContext, "Os eventos devem ser cadastrados apartir do ANO atual", Toast.LENGTH_SHORT).show()
                }else{
                    if (mesValida < 1 || mesValida > 12){
                        Toast.makeText(applicationContext, "O MÊS digitado não é válido, tente novamente", Toast.LENGTH_SHORT).show()
                    }else{
                        if (mesValida == 2){
                            if (diaValida < 1 || diaValida > 28){
                                Toast.makeText(applicationContext, "O DIA digitado para o MÊS selecionado não é válido, tente novamente", Toast.LENGTH_SHORT).show()
                            }else{
                                val retorno = gravaEvento(nome, ano, mes, dia, detalhes)
                                if (retorno.equals("sucesso")){
                                    finalizaEvento()
                                }else{
                                    Toast.makeText(applicationContext, "Erro ao salvar informações", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else{
                            if (diaValida < 1 || diaValida > 31){
                                Toast.makeText(applicationContext, "O DIA digitado não é válido, tente novamente", Toast.LENGTH_SHORT).show()
                            }else{
                                val retorno = gravaEvento(nome, ano, mes, dia, detalhes)
                                if (retorno.equals("sucesso")){
                                    finalizaEvento()
                                }else{
                                    Toast.makeText(applicationContext, "Erro ao salvar informações", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val i = Intent(this@AdmCadEventoActivity, AdmEventosMainActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }

    fun gravaEvento(nome: String, ano: String, mes: String, dia: String, detalhes: String): String{
        var retorno = ""
        try {
            val evento = EventoDAO()
            evento.nomeEvento = nome
            evento.anoEvento = ano
            evento.mesEvento = mes
            evento.diaEvento = dia
            evento.detalhesEvento = detalhes
            val fireBaseSend = FirebaseDatabase.getInstance().reference.child("Eventos")
            fireBaseSend.child(ano).child(mes).child(dia).push().setValue(evento)
            retorno = "sucesso"
        }catch (e: Exception){
            retorno = "erro"
            Log.e("EVENTO", "GRAVAR", e)
        }
        return retorno
    }

    fun finalizaEvento(){
        val i = Intent(this@AdmCadEventoActivity, AdmEventosMainActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }
}
