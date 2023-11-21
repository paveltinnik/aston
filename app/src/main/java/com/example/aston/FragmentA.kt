package com.example.aston

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FragmentA : Fragment() {

    private lateinit var buttonToFragmentB: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToFragmentB = view.findViewById(R.id.button_to_fragment_b)

        buttonToFragmentB.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("FragmentA")
                .replace(R.id.fragment_container_view, FragmentB())
                .commit()
        }
    }
}