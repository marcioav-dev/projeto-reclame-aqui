package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.eventos.eventosAdp

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.modelos.EventoDAO

class AdmEventosExibirAdp(val array: ArrayList<EventoDAO>) : RecyclerView.Adapter<CustomViewHolderTEA>() {

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderTEA {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.recycler_eventos_msg, parent, false)
        return CustomViewHolderTEA(cellRow)
    }

    @Suppress("UNUSED_VARIABLE")
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolderTEA, position: Int) {
        val txtNome = holder.view.findViewById<TextView>(R.id.txtREMAnome)
        val txtData = holder.view.findViewById<TextView>(R.id.txtREMAdata)
        val txtDetalhes = holder.view.findViewById<TextView>(R.id.txtREMAdetalhes)

        txtNome.text = array[position].nomeEvento
        txtData.text = array[position].diaEvento + "/" + array[position].mesEvento + "/" + array[position].anoEvento
        txtDetalhes.text = array[position].detalhesEvento
    }
}

class CustomViewHolderTEA(val view: View) : RecyclerView.ViewHolder(view)