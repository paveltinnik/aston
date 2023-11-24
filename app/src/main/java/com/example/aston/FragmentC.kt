package com.example.aston

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager

private const val ARG_TEXT = "text"

class FragmentC : Fragment() {
    private var textFromFragmentB: String? = null

    private lateinit var tvFromFragmentB: TextView
    private lateinit var buttonToFragmentD: Button
    private lateinit var buttonToFragmentA: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textFromFragmentB = it.getString(ARG_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvFromFragmentB = view.findViewById(R.id.tv_from_fragment_b)
        buttonToFragmentD = view.findViewById(R.id.button_to_fragment_d)
        buttonToFragmentA = view.findViewById(R.id.button_to_fragment_a)

        tvFromFragmentB.text = textFromFragmentB

        buttonToFragmentD.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FragmentD(), FragmentD.TAG)
                .addToBackStack(BACK_STACK_NAME)
                .commit()
        }

        buttonToFragmentA.setOnClickListener {
            parentFragmentManager.popBackStack(FragmentA.BACK_STACK_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    companion object {

        const val BACK_STACK_NAME = "From C To D"
        const val TAG = "FragmentC"

        @JvmStatic
        fun newInstance(text: String) =
            FragmentC().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, text)
                }
            }
    }
}