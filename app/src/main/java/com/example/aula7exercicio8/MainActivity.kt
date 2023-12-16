package com.example.aula7exercicio8

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var btnCapturar: Button
    private lateinit var imgFoto: ImageView
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCapturar = findViewById(R.id.btnCapturar)
        imgFoto = findViewById(R.id.imgFoto)
        linearLayout = findViewById(R.id.linearLayout)

        btnCapturar.setOnClickListener {
            capturePhoto()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val date = getCurrentDateTime()
            addPhotoToGallery(imageBitmap, date)
        }
    }

    private fun capturePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            takePictureLauncher.launch(takePictureIntent)
        } else {
            // Caso não haja aplicativo de câmera disponível
            // Trate isso de acordo com a lógica do seu aplicativo
            // Por exemplo, exiba uma mensagem para o usuário
        }
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun addPhotoToGallery(photo: Bitmap, dateTime: String) {
        val imageView = ImageView(this)
        imageView.setImageBitmap(photo)

        // Adiciona a foto e a data/hora em um TextView ao LinearLayout
        val textView = TextView(this)
        textView.text = "Data/Hora: $dateTime"
        linearLayout.addView(textView)
        linearLayout.addView(imageView)
    }
}
