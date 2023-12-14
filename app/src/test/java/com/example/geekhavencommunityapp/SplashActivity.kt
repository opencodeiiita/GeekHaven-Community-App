import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      
        setContentView(R.layout.splash_screen)

 
        val splashTimeOut = 3000L // 3 seconds
        val mainIntent = Intent(this, NextActivity::class.java)

       
        val splashTimer = object : Thread() {
            override fun run() {
                try {
                    sleep(splashTimeOut)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(mainIntent)
                    finish() 
                }
            }
        }
        splashTimer.start()
    }
}
