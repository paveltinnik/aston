package com.example.aston

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FragmentB : Fragment() {

    private lateinit var buttonToFragmentC: Button
    private lateinit var buttonBack: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToFragmentC = view.findViewById(R.id.button_to_fragment_c)
        buttonBack = view.findViewById(R.id.button_back)

        buttonToFragmentC.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("FragmentB")
                .replace(R.id.fragment_container_view, FragmentC())
                .commit()
        }

        buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}