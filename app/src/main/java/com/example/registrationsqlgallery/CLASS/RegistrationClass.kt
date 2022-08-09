package com.example.registrationsqlgallery.CLASS

class RegistrationClass {

    var id: Int? = null
    var nameAndSurname: String? = null
    var telephoneNumber: String? = null
    var country: String? = null
    var address: String? = null
    var imageUri: ByteArray? = null


    constructor()
    constructor(
        id: Int?,
        nameAndSurname: String?,
        telephoneNumber: String?,
        country: String?,
        address: String?,
        imageUri: ByteArray?
    ) {
        this.id = id
        this.nameAndSurname = nameAndSurname
        this.telephoneNumber = telephoneNumber
        this.country = country
        this.address = address
        this.imageUri = imageUri
    }

    constructor(
        nameAndSurname: String?,
        telephoneNumber: String?,
        country: String?,
        address: String?,
        imageUri: ByteArray?
    ) {
        this.nameAndSurname = nameAndSurname
        this.telephoneNumber = telephoneNumber
        this.country = country
        this.address = address
        this.imageUri = imageUri
    }


}