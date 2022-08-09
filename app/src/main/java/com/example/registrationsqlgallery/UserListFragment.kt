package com.example.registrationsqlgallery

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.registrationsqlgallery.Adapters.RvAdapter
import com.example.registrationsqlgallery.CLASS.RegistrationClass
import com.example.registrationsqlgallery.DB.MyDbHelper
import com.example.registrationsqlgallery.databinding.FragmentUserListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class UserListFragment : Fragment() {

    lateinit var binding: FragmentUserListBinding

    lateinit var myDbHelper: MyDbHelper

    lateinit var rvAdapter: RvAdapter

    lateinit var list: ArrayList<RegistrationClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserListBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDbHelper = MyDbHelper(requireContext())

        list = myDbHelper.getAllRegistration()



        for (i in 0 until myDbHelper.getAllRegistration().size) {

            Toast.makeText(
                requireContext(),
                "MB -> ${myDbHelper.getAllRegistration()[i].nameAndSurname}",
                Toast.LENGTH_SHORT
            ).show()

        }

        Toast.makeText(requireContext(), "${list.size}", Toast.LENGTH_SHORT).show()

        rvAdapter =
            RvAdapter(myDbHelper.getAllRegistration(), object : RvAdapter.ItemClickListener {
                override fun itemClick(registrationClass: RegistrationClass, position: Int) {

                    val bottomSheetDialog = BottomSheetDialog(requireContext())
                    bottomSheetDialog.setContentView(
                        layoutInflater.inflate(
                            R.layout.dialog_item,
                            null,
                            false
                        )
                    )
                    if (registrationClass.imageUri != null) {

                        val bitmap =
                            BitmapFactory.decodeByteArray(
                                registrationClass.imageUri,
                                0,
                                registrationClass.imageUri?.size!!
                            )
                        bottomSheetDialog.findViewById<ImageView>(R.id.image_dialog)!!
                            .setImageBitmap(bitmap)

                    }

                    bottomSheetDialog.findViewById<TextView>(R.id.txt_name_dialog)!!.text =
                        registrationClass.nameAndSurname
                    bottomSheetDialog.findViewById<TextView>(R.id.txt_number_dialog)!!.text =
                        "${registrationClass.country}, ${registrationClass.address}"
                    bottomSheetDialog.findViewById<LinearLayout>(R.id.llR)!!
                        .setBackgroundColor(Color.TRANSPARENT)


                    bottomSheetDialog.findViewById<ImageView>(R.id.call_dialog)!!.setOnClickListener {

                        Dexter.withContext(requireContext())
                            .withPermissions(
                                Manifest.permission.CALL_PHONE
                            ).withListener(object : MultiplePermissionsListener {
                                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                                    Toast.makeText(
                                        requireContext(),
                                        "Rasmlarga kirishga ruxsat berildi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(Intent.ACTION_CALL)
                                    intent.data = Uri.parse("tel:${registrationClass.telephoneNumber}")
                                    startActivity(intent)


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

                    bottomSheetDialog.findViewById<ImageView>(R.id.sms_dialog)!!.setOnClickListener {

                        val it = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${registrationClass.telephoneNumber}"))
                        it.putExtra("sms_body", "")
                        startActivity(it)

                    }

                    bottomSheetDialog.show()

                }
            })

        binding.rv.adapter = rvAdapter

    }


}