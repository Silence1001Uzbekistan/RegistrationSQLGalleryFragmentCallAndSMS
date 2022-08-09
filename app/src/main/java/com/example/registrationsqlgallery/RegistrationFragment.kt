package com.example.registrationsqlgallery

import android.Manifest
import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.registrationsqlgallery.CLASS.RegistrationClass
import com.example.registrationsqlgallery.DB.MyDbHelper
import com.example.registrationsqlgallery.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding
    lateinit var myDbHelper: MyDbHelper

    lateinit var absolutePath: ByteArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAction()

        myDbHelper = MyDbHelper(requireContext())

        absolutePath = ByteArray(0)

        binding.cardRegistration.setOnClickListener {

            if (binding.edtName.text.isNotEmpty() && binding.edtTelNumberRg.text.isNotEmpty() && binding.edtCountry.selectedItem.toString()
                    .isNotEmpty() && binding.edtAddress.text.isNotEmpty()
            ) {

                myDbHelper.insertRegistration(
                    RegistrationClass(
                        binding.edtName.text.toString(),
                        binding.edtTelNumberRg.text.toString(),
                        binding.edtCountry.selectedItem.toString(),
                        binding.edtAddress.text.toString(),
                        absolutePath
                    )
                )



                Snackbar.make(it, "Save", Snackbar.LENGTH_LONG).show()
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()


            } else {

                Snackbar.make(it, "Enter correctly", Snackbar.LENGTH_LONG).show()

            }

        }

        binding.imageAdd.setOnClickListener {

            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Rasmni kameradan yoki gallereyadan yuklashingiz mumkin:")
            builder.setCancelable(false)
            builder.setPositiveButton(
                "Camera "
            ) { dialog, id ->
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 0)
                dialog.cancel()
            }

            builder.setNegativeButton(
                "Gallery "
            ) { dialog, id ->
                startActivityForResult(
                    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    },
                    1
                )
                dialog.cancel()
            }

            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.holo_blue_dark))
                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.holo_blue_dark))
            }
            alert.show()


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.circleImageview.setImageBitmap(bitmap)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            absolutePath = byteArray
        } else if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data?.data ?: return
            binding.circleImageview.setImageURI(uri)
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val format = SimpleDateFormat("yyMMdd_hhmmss").format(Date())
            val file = File(activity?.filesDir, "${format}_image.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            val fileInputStream = FileInputStream(file)
            val readBytes = fileInputStream.readBytes()
            absolutePath = readBytes
//            Toast.makeText(context, "$absolutePath", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showAction() {

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                    Toast.makeText(
                        requireContext(),
                        "Rasmlarga kirishga ruxsat berildi",
                        Toast.LENGTH_SHORT
                    ).show()


                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {

                    Toast.makeText(
                        requireContext(),
                        "Rasmlarga kirishga ruxsat bermadinggiz ",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()

                }
            }).check()


    }
}