package com.dbtechprojects.pokemonApp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.dbtechprojects.pokemonApp.R

// Dialog fragment for filter option in list fragment

class FilterDialog(var fragmentlistener: TypePicker) : DialogFragment() {

    var listener: TypePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true) // used to retain fragment configuration on rotation
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        listener = fragmentlistener as TypePicker
        val fragement_container = container
        val rootview = layoutInflater.inflate(R.layout.dialog_typefilter, fragement_container, false)
        val fireImg = rootview.findViewById<ImageView>(R.id.dialog_fire_img)
        val waterImg = rootview.findViewById<ImageView>(R.id.dialog_water_img)
        val grassImg = rootview.findViewById<ImageView>(R.id.dialog_grass_img)
        val cancelBtn = rootview.findViewById<Button>(R.id.dialog_cancel_button)


        fireImg.setOnClickListener {
            // pass type back to list fragment
            listener?.typeToSearch("fire")
            this.dismiss()
        }

        waterImg.setOnClickListener {
            // pass type back to list fragment
            listener?.typeToSearch("water")
            this.dismiss()
        }

        grassImg.setOnClickListener {
            // pass type back to list fragment
            listener?.typeToSearch("grass")
            this.dismiss()
        }

        cancelBtn.setOnClickListener {
            //close helpDialog
            this.dismiss()
        }

        return rootview
    }

    //interface implemented in list fragment

    interface TypePicker {
        fun typeToSearch(type: String)
    }

    // handle rotation
    override fun onDestroyView() {
        if (dialog != null && retainInstance) dialog!!.setOnDismissListener(null)
        super.onDestroyView()
    }
}