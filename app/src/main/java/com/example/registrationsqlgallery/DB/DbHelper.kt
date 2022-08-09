package com.example.registrationsqlgallery.DB

import com.example.registrationsqlgallery.CLASS.RegistrationClass

interface DbHelper {

    fun insertRegistration(registrationClass: RegistrationClass)

    fun getAllRegistration():ArrayList<RegistrationClass>

    fun getRegistrationById(id:Int):RegistrationClass


}