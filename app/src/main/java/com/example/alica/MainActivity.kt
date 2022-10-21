package com.example.alica

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    // в строке ниже мы создаем переменные
    // для textview и imageview
    lateinit var outputTV: TextView
    lateinit var micIV: ImageView

    // в строке ниже мы создаем постоянное значение
    private val REQUEST_CODE_SPEECH_INPUT = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // инициализация переменных представления списка с их идентификаторами
        outputTV = findViewById(R.id.idTVOutput)
        micIV = findViewById(R.id.idIVMic)

        // on below line we are adding on click
        // listener for mic image view.
        //в строке ниже мы добавляем
        // прослушиватель кликов для просмотра изображения с микрофона.
        micIV.setOnClickListener {
            // в строке ниже мы вызываем намерение распознавателя речи
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // в строке ниже мы передаем языковую модель
            // и моделируем свободную форму в нашем намерении
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // в строке ниже мы передаем наш
            // язык как язык по умолчанию
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // в нижней строке мы указываем подсказку
            // сообщение, как говорить с текстом в нижней строке
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // в строке ниже мы указываем блок try catch.
            // в этом блоке мы вызываем стартовую активность
            // для метода результата и передачи нашего кода результата.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // в нижней строке мы показываем сообщение об ошибке в Toast
                Toast
                    .makeText(
                        this@MainActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

    // в строке ниже мы вызываем метод результата действия
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // в этом методе мы проверяем запрос
        // код с нашим кодом результата
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // в строке ниже мы проверяем, в порядке ли код результата
            if (resultCode == RESULT_OK && data != null) {

                // в этом случае мы извлекаем
                // данные из нашего списка массивов
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // в нижней строке мы устанавливаем данные
                // в наше выходное текстовое представление
                outputTV.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }
}