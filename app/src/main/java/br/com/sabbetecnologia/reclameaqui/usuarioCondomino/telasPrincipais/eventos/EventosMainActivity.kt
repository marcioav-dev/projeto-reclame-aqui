package br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.eventos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos.eventosAdp.AdmEventosExibirAdp
import br.com.sabbetecnologia.reclameaqui.modelos.EventoDAO
import br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.TelaInicioActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class EventosMainActivity : AppCompatActivity() {

    private var login = ""

    private lateinit var spnDia: Spinner
    private lateinit var spnMes: Spinner
    private lateinit var spnAno: Spinner
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val bundle = intent.extras
        login = bundle.getString("LOGIN")

        spnDia = findViewById(R.id.spnEMAdia)
        spnMes = findViewById(R.id.spnEMAmes)
        spnAno = findViewById(R.id.spnEMAano)
        recycler = findViewById(R.id.recEMA)

        recycler.layoutManager = LinearLayoutManager(this)

        val diaList = ArrayList<String>()
        for(i in 1..31){
            diaList.add(String.format("%02d", i))
        }

        val mesList = ArrayList<String>()
        for(i in 1..12){
            mesList.add(String.format("%02d", i))
        }

        val anoList = ArrayList<String>()
        for(i in 2018..2030){
            anoList.add(i.toString())
        }

        val diaAdapter = ArrayAdapter(this,
                R.layout.spinner_texto, diaList)
        diaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnDia.adapter = diaAdapter

        val mesAdapter = ArrayAdapter(this,
                R.layout.spinner_texto, mesList)
        mesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnMes.adapter = mesAdapter

        val anoAdapter = ArrayAdapter(this,
                R.layout.spinner_texto, anoList)
        anoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnAno.adapter = anoAdapter

        val array = ArrayList<EventoDAO>()

        spnDia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val dia = spnDia.getItemAtPosition(position).toString()
                val mes = spnMes.selectedItem.toString()
                val ano = spnAno.selectedItem.toString()
                val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Eventos").child(ano).child(mes).child(dia)

                fireBaseDb.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        array.clear()
                        for (dados in snapshot.children) {
                            val msg = (dados.getValue(EventoDAO::class.java))
                            array.add(msg!!)
                        }
                        recycler.adapter = AdmEventosExibirAdp(array)
                        var log = ""
                        for (i in 0..(array.size - 1)){
                            log += "${array[i].anoEvento}\n${array[i].mesEvento}\n${array[i].diaEvento}\n${array[i].nomeEvento}\n${array[i].detalhesEvento}\n\n"
                        }
                        Log.e("ARRAY", log)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }

        spnMes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val dia = spnDia.selectedItem.toString()
                val mes = spnMes.getItemAtPosition(position).toString()
                val ano = spnAno.selectedItem.toString()
                val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Eventos").child(ano).child(mes).child(dia)

                fireBaseDb.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        array.clear()
                        for (dados in snapshot.children) {
                            val msg = (dados.getValue(EventoDAO::class.java))
                            array.add(msg!!)
                        }
                        recycler.adapter = AdmEventosExibirAdp(array)
                        var log = ""
                        for (i in 0..(array.size - 1)){
                            log += "${array[i].anoEvento}\n${array[i].mesEvento}\n${array[i].diaEvento}\n${array[i].nomeEvento}\n${array[i].detalhesEvento}\n\n"
                        }
                        Log.e("ARRAY", log)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }

        spnAno.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val dia = spnDia.selectedItem.toString()
                val mes = spnMes.selectedItem.toString()
                val ano = spnAno.getItemAtPosition(position).toString()
                val fireBaseDb = FirebaseDatabase.getInstance().reference.child("Eventos").child(ano).child(mes).child(dia)

                fireBaseDb.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        array.clear()
                        for (dados in snapshot.children) {
                            val msg = (dados.getValue(EventoDAO::class.java))
                            array.add(msg!!)
                        }
                        recycler.adapter = AdmEventosExibirAdp(array)
                        var log = ""
                        for (i in 0..(array.size - 1)){
                            log += "${array[i].anoEvento}\n${array[i].mesEvento}\n${array[i].diaEvento}\n${array[i].nomeEvento}\n${array[i].detalhesEvento}\n\n"
                        }
                        Log.e("ARRAY", log)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
    }

    override fun onBackPressed() {
        val i = Intent(this@EventosMainActivity, TelaInicioActivity::class.java)
        i.putExtra("LOGIN", login)
        startActivity(i)
        finish()
    }
}
