package br.com.sabbetecnologia.reclameaqui.bancoInterno

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by slipk on 09/03/2018.
 */
class BancoInterno(context: Context) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO){

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {

        onCreate(db)
    }

    companion object {

        private const val NOME_BANCO = "____________.db"

        private const val TABELA_NOME = ""

        private const val VERSAO = 1
    }
}