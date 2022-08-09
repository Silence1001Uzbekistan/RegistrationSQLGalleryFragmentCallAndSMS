package com.example.registrationsqlgallery.Adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationsqlgallery.CLASS.RegistrationClass
import com.example.registrationsqlgallery.databinding.ItemRvBinding

class RvAdapter(var list: ArrayList<RegistrationClass>,var itemClickListener: ItemClickListener):RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){

        fun onBind(registrationClass: RegistrationClass,position: Int){

            itemRvBinding.txtNameRvItem.text = registrationClass.nameAndSurname
            itemRvBinding.txtNumberRvItem.text = registrationClass.telephoneNumber

            itemRvBinding.root.setOnClickListener {

                itemClickListener.itemClick(registrationClass, position)

            }

            if (registrationClass.imageUri != null){

                val bitmap =
                    BitmapFactory.decodeByteArray(registrationClass.imageUri, 0, registrationClass.imageUri?.size!!)
                itemRvBinding.imageRvItem.setImageBitmap(bitmap)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {

        holder.onBind(list[position],position)

    }

    override fun getItemCount(): Int {

        return list.size

    }


    interface ItemClickListener{
        fun itemClick(registrationClass: RegistrationClass,position: Int)
    }

}