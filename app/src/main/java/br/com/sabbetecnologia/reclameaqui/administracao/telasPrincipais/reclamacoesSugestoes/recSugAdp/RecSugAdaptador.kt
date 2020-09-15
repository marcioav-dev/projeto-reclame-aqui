package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.reclamacoesSugestoes.recSugAdp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.modelos.ReclamSugestDAO

class RecSugAdaptador(val array: ArrayList<ReclamSugestDAO>, val login: String) : RecyclerView.Adapter<CustomViewHolderTEA>() {

    override fun getItemCount(): Int {
        return array.size
        //return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderTEA {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.recycler_reclama_sugere, parent, false)
        return CustomViewHolderTEA(cellRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolderTEA, position: Int) {
        val txtRemetente = holder.view.findViewById<TextView>(R.id.txtRRSremetente)
        val txtData = holder.view.findViewById<TextView>(R.id.txtRRSdata)
        val txtHora = holder.view.findViewById<TextView>(R.id.txtRRShora)
        val txtMensagem = holder.view.findViewById<TextView>(R.id.txtRRSmensagem)

        txtRemetente.text = array[position].remetente
        txtData.text = array[position].dataMsg
        txtHora.text = array[position].horaMsg
        txtMensagem.text = array[position].mensagem
    }

}

class CustomViewHolderTEA(val view: View) : RecyclerView.ViewHolder(view)