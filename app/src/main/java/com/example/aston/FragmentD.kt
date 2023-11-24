package com.example.aston

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager

class FragmentD : Fragment() {

    private lateinit var buttonToFragmentB: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToFragmentB = view.findViewById(R.id.button_to_fragment_b)

        buttonToFragmentB.setOnClickListener {
            parentFragmentManager.popBackStack(FragmentB.BACK_STACK_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    companion object {

        const val TAG = "FragmentD"
    }
}