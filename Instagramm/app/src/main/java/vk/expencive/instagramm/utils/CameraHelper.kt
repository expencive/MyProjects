package vk.expencive.instagramm.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraHelper(private val activity: Activity){
    var imageUri: Uri? = null
    private val simpleDateFormat = SimpleDateFormat(
        "yyyyMMdd_HHmmss",
        Locale.US
    ).format(Date())
    val TAKE_REQUEST_CODE = 1


    fun takeCameraPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) != null) {
            val imageFile = createImageFile()
            imageUri = FileProvider.getUriForFile(
                activity,
                "vk.expencive.instagramm.fileprovider",
                imageFile
            );
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            activity.startActivityForResult(intent, TAKE_REQUEST_CODE)
        }
    }

    private fun createImageFile(): File {

        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${simpleDateFormat.format((Date()))}_",
            ".jpg",
            storageDir
        )
    }

}