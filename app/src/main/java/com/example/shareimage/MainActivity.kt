package com.example.shareimage

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.example.shareimage.databinding.ActivityMainBinding
import com.example.shareimage.databinding.HeaderLayoutBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var header :View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)
        header= HeaderLayoutBinding.inflate(layoutInflater).root

        binding.btn.setOnClickListener {


            val widthSpec = MeasureSpec.makeMeasureSpec(binding.constraintLayout.width, MeasureSpec.EXACTLY)
            val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            header.measure(widthSpec, heightSpec)
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());


            val file: File = ImageUtil.saveBitmap(this,ImageUtil.joinBitmaps( binding.constraintLayout,header ))!!


            val uri =
                FileProvider.getUriForFile(this, "com.example.shareimage", file)
            // create new Intent
            // create new Intent
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.type = "image/*"
            startActivity(Intent.createChooser(shareIntent, "Compartir constancia"))
        }


    }
}