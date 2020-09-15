package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.msgAdp

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.modelos.MsgDAO
import java.security.MessageDigest

@Suppress("UnnecessaryVariable")
class MsgAdmAdp(val array: ArrayList<MsgDAO>, val login: String, private val user: String, private val nome: String) : RecyclerView.Adapter<CustomViewHolderTEA>() {

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderTEA {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.recycler_msgs, parent, false)
        return CustomViewHolderTEA(cellRow)
    }

    @Suppress("UNUSED_VARIABLE")
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolderTEA, position: Int) {
        val txtRemetente = holder.view.findViewById<TextView>(R.id.txtADPMSGremetente)
        val txtMsg = holder.view.findViewById<TextView>(R.id.txtADPMSGmsg)

        val destin = user
        val destinatario64 = md5Gen(destin)
        val login64 = md5Gen(login)

        if (array[position].destinatario == destin){
            txtRemetente.text = "Admin"
            txtMsg.text = array[position].mensagem
        }else{
            txtRemetente.text = nome
            txtMsg.setTextColor(Color.BLACK)
            txtMsg.setBackgroundResource(R.drawable.edit_txt_msg)
            txtMsg.text = array[position].mensagem
        }
    }

    private fun md5Gen(texto: String): String{
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

class CustomViewHolderTEA(val view: View) : RecyclerView.ViewHolder(view)