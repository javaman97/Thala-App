package com.dhoni.thalaapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.dhoni.thalaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener {
            val input = binding.textInputText.text?.toString()?.trim()

            if (input.isNullOrBlank()) {
                Toast.makeText(this, "Input Something for THALA", Toast.LENGTH_SHORT).show()
            } else {
                if (input.isDigitsOnly()) {
                    val sum = sumOfDigits(input.toInt())
                    if (sum == 7) {
                        binding.txtMsg.text = "$input ${getString(R.string.thala_for_a_reason)}"
                        binding.txtMsg.visibility = View.VISIBLE
                        binding.thalaVideoView.visibility = View.VISIBLE
                        binding.btnShare.visibility = View.VISIBLE
                        binding.imgTry.visibility = View.INVISIBLE
                        showVideo()
                    }
                }
                else if( input.length == 7){
                    binding.txtMsg.text = "$input ${getString(R.string.thala_for_a_reason)}"
                    binding.txtMsg.visibility = View.VISIBLE
                    binding.thalaVideoView.visibility = View.VISIBLE
                    binding.btnShare.visibility = View.VISIBLE
                    binding.imgTry.visibility = View.INVISIBLE
                    showVideo()
                }
                else {
                    binding.thalaVideoView.visibility = View.GONE
                    binding.btnShare.visibility = View.GONE
                    binding.imgTry.visibility = View.VISIBLE
                    binding.txtMsg.text = "TRY AGAIN"
                    binding.txtMsg.visibility = View.VISIBLE
                }
            }
        }

        binding.btnShare.setOnClickListener {
            shareUrl()
        }
    }

   private fun sumOfDigits(number:Int):Int {
       var sum = 0
       var n = number
       while (n != 0) {
           sum += n % 10
           n /= 10
       }
       return sum
   }
    private fun showVideo() {
        // Set the video path (replace "your_video_file" with the actual video file name)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.thala_song
        val uri = Uri.parse(videoPath)

        // Set up MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.thalaVideoView)
        binding.thalaVideoView.setMediaController(mediaController)

        // Set the video URI
        binding.thalaVideoView.setVideoURI(uri)

        // Start the video
        binding.thalaVideoView.start()

    }

    private fun shareUrl() {
        val encryptedUrl = encryptUrl("https://github.com/javaman97")
        val shareText = "Check out this encrypted URL: $encryptedUrl"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun encryptUrl(url: String): String {
        return android.util.Base64.encodeToString(url.toByteArray(), android.util.Base64.DEFAULT)
    }
}