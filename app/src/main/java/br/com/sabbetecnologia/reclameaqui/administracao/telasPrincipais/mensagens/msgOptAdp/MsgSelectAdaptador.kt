package br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.msgOptAdp

import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.sabbetecnologia.reclameaqui.R
import br.com.sabbetecnologia.reclameaqui.administracao.telasPrincipais.mensagens.AdminMsgActivity
import br.com.sabbetecnologia.reclameaqui.cadastro.CadastroUsuarioDAO

class MsgOptAdp(val array: ArrayList<CadastroUsuarioDAO>, val login: String) : RecyclerView.Adapter<CustomViewHolderTEA>() {

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderTEA {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.user_cad_admin, parent, false)
        return CustomViewHolderTEA(cellRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolderTEA, position: Int) {
        val txtNome = holder.view.findViewById<TextView>(R.id.txtADPuserNome)
        val txtEmail = holder.view.findViewById<TextView>(R.id.txtADPuserEmail)
        val cl = holder.view.findViewById<ConstraintLayout>(R.id.clADM)

        txtNome.text = array[position].nomeCompleto
        txtEmail.text = array[position].emailUsuario

        if (array[position].emailUsuario.equals("admin@gmail.com")){
            txtNome.visibility = View.GONE
            txtEmail.visibility = View.GONE
            cl.visibility = View.GONE
        }

        cl.setOnClickListener {
            val i = Intent(holder.view.context, AdminMsgActivity::class.java)
            i.putExtra("LOGIN", login)
            i.putExtra("USER", array[position].emailUsuario)
            i.putExtra("NOME", array[position].nomeCompleto)
            holder.view.context.startActivity(i)
        }

    }

}

class CustomViewHolderTEA(val view: View) : RecyclerView.ViewHolder(view)