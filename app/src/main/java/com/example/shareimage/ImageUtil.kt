package com.example.shareimage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class ImageUtil {
    companion object {

        fun joinBitmaps(view1:View,view2:View):Bitmap{

            val a = view1.width
            val b = view1.height
            val c = view2.width
            val d = view2.height

            val b1 = getBitmapFromView(view1)
            val b2 = getBitmapFromView(view2)
            val b3 = Bitmap.createBitmap(a, b + d, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(b3)
            canvas.drawBitmap(b2!!, Matrix(), null)
            canvas.drawBitmap(b1!!, 0f, view2.height.toFloat(), null)
            return b3
        }


        fun getBitmapFromView(view: View): Bitmap? {
            val returnedBitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable = view.background
            if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
            view.draw(canvas)
            return returnedBitmap
        }

        fun saveBitmap(context: Context, bmp: Bitmap): File? {
            var outStream: OutputStream? = null
            val file: File = getFileShared(context)!!
            try {
                outStream = FileOutputStream(file)
                bmp.setHasAlpha(true)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                outStream.flush()
                outStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return file
        }
        private fun getFileShared(context: Context?): File? {
            val timeStamp =
                "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return ImageUtil.getFileShared(context!!, timeStamp)
        }

        private fun getFileShared(
            context: Context,
            url: String
        ): File? {
            // For a more secure solution, use EncryptedFile from the Security library
            // instead of File.
            var file: File? = null
            try {
                val fileName = Uri.parse(url).lastPathSegment
                file = File.createTempFile(fileName, ".jpeg", context.cacheDir)
            } catch (e: IOException) {
                // Error while creating file
            }
            return file
        }


    }


}