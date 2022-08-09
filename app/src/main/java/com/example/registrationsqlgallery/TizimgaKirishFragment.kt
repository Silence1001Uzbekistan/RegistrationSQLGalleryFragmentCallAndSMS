package com.example.registrationsqlgallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.registrationsqlgallery.CLASS.RegistrationClass
import com.example.registrationsqlgallery.DB.MyDbHelper
import com.example.registrationsqlgallery.databinding.FragmentTizimgaKirishBinding

class TizimgaKirishFragment : Fragment() {

    lateinit var binding: FragmentTizimgaKirishBinding
    lateinit var myDbHelper: MyDbHelper

    lateinit var list:ArrayList<RegistrationClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTizimgaKirishBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDbHelper = MyDbHelper(requireContext())

        list = myDbHelper.getAllRegistration()

        binding.txtRegistration.setOnClickListener {

            findNavController().navigate(R.id.registrationFragment)

        }

        binding.btnSign.setOnClickListener {


            for (i in 0 until list.size){

                if (binding.edtTelNumber.text.toString() == list[i].nameAndSurname && binding.edtPassword.text.toString() == list[i].telephoneNumber){

                    findNavController().navigate(R.id.userListFragment)
                    Toast.makeText(requireContext(), "Found it", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(requireContext(), "Enter correctly", Toast.LENGTH_SHORT).show()
                }

            }


        }

    }


}