package br.com.sabbetecnologia.reclameaqui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

/**
 * Created by Marcio on 26/02/2018.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val timerThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(5000)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("ERRO", "SPLASH", e)

                } finally {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
        timerThread.start()
    }

    override fun onPause() {
        // TODO Auto-generated method stub
        super.onPause()
        finish()
    }
}
