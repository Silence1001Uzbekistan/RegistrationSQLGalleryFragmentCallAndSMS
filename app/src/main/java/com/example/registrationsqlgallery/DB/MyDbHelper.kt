package com.example.registrationsqlgallery.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.registrationsqlgallery.CLASS.RegistrationClass
import com.example.registrationsqlgallery.CONSTANT.Constant

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION), DbHelper {
    override fun onCreate(p0: SQLiteDatabase?) {

        val query =
            "create table ${Constant.TABLE_NAME} (${Constant.ID} integer not null primary key autoincrement unique,${Constant.NAME} text not null,${Constant.NUMBER} text not null,${Constant.COUNTRY} text not null,${Constant.ADDRESS} text not null,${Constant.IMAGE_URI} blob not null)"

        p0?.execSQL(query)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {


    }

    override fun insertRegistration(registrationClass: RegistrationClass) {

        val database = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put("name", registrationClass.nameAndSurname)
        contentValues.put("number", registrationClass.telephoneNumber)
        contentValues.put("country", registrationClass.country)
        contentValues.put("address", registrationClass.address)
        contentValues.put("image", registrationClass.imageUri)

        database.insert(Constant.TABLE_NAME, null, contentValues)
        database.close()


    }

    override fun getAllRegistration(): ArrayList<RegistrationClass> {

        var list = ArrayList<RegistrationClass>()

        val query = "select * from ${Constant.TABLE_NAME}"

        val database = this.readableDatabase

        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {

            do {


                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)
                val country = cursor.getString(3)
                val address = cursor.getString(4)
                val image = cursor.getBlob(5)

                val registrationClass = RegistrationClass(id, name, number, country, address, image)
                list.add(registrationClass)


            } while (cursor.moveToNext())

        }

        return list

    }

    override fun getRegistrationById(id: Int): RegistrationClass {

        val database = this.readableDatabase

        val cursor = database.query(
            Constant.TABLE_NAME,
            arrayOf(
                Constant.ID,
                Constant.NAME,
                Constant.NUMBER,
                Constant.COUNTRY,
                Constant.ADDRESS,
                Constant.IMAGE_URI
            ),
            "${Constant.ID} = ?",
            arrayOf(id.toString()), null, null, null
        )

        cursor?.moveToFirst()

        return RegistrationClass(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getBlob(5)
        )

    }
}