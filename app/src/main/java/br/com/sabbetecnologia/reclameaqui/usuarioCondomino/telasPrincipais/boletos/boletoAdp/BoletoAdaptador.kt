package br.com.sabbetecnologia.reclameaqui.usuarioCondomino.telasPrincipais.boletos.boletoAdp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.modelos.BoletosDAO

class BoletoAdaptador(val array: ArrayList<BoletosDAO>, val login: String) : RecyclerView.Adapter<CustomViewHolderTEA>() {

    override fun getItemCount(): Int {
        return array.size
        //return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderTEA {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.recycler_boletos, parent, false)
        return CustomViewHolderTEA(cellRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolderTEA, position: Int) {
        val txtData = holder.view.findViewById<TextView>(R.id.txtRecBoletoData)
        val txtValor = holder.view.findViewById<TextView>(R.id.txtRecBoletoValor)
        val txtMultas = holder.view.findViewById<TextView>(R.id.txtRecBoletoMultas)
        val txtTotal = holder.view.findViewById<TextView>(R.id.txtRecBoletoTotal)

        txtData.text = array[position].dataBoleto
        txtValor.text = array[position].valorBoleto
        txtMultas.text = array[position].multaBoleto
        txtTotal.text = array[position].totalBoleto
    }

}

class CustomViewHolderTEA(val view: View) : RecyclerView.ViewHolder(view)