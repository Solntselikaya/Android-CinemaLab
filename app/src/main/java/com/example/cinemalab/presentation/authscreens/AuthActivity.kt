<<<<<<<< Updated upstream:app/src/main/java/com/example/cinemalab/MainActivity.kt
package com.example.cinemalab
========
package com.example.cinemalab.presentation.authscreens
>>>>>>>> Stashed changes:app/src/main/java/com/example/cinemalab/presentation/authscreens/AuthActivity.kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

<<<<<<<< Updated upstream:app/src/main/java/com/example/cinemalab/MainActivity.kt
class MainActivity : AppCompatActivity() {
========
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
>>>>>>>> Stashed changes:app/src/main/java/com/example/cinemalab/presentation/authscreens/AuthActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}