package com.behcetemreyildirim.coroutineexceptionhandler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //coroutines'te hata olursa otomatik çalışır ve çökmeyi engelleyerek hata mesajını yazar
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("exception: " + throwable.localizedMessage)
        }

       /* lifecycleScope.launch(handler) { //handler hata durumlarında otomatik çalışır ve scope'u durdurur
            launch {
                throw Exception("error") //throw -> uygulamayı çökertir
            }
            launch {
                delay(500)
                println("this is executed")
                //handler sadece hatanın bulunduğu launch'ı iptal etmez bütün coroutine'i iptal eder. bu kısımda çalışmaz
            }
        }*/

        lifecycleScope.launch(handler) {
            supervisorScope { //launch'ları gözlemler ve sadece hatanın bulunduğu launch'ı durdurur. diğer launch'lar çalışır
                launch {
                    throw Exception("error")
                }
                launch {
                    delay(500)
                    println("this is executed")

                }
            }
        }
    }
}