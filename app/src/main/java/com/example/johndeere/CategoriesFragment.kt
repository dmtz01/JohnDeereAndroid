package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCategory = adapterCategories(requireActivity(), categoriesList) {
            val bundle = Bundle()
            bundle.putParcelable("category", it)
            Navigation.findNavController(view).navigate(R.id.action_categories_frag_to_words_frag, bundle)
        }

        binding.rvCategories.adapter = adapterCategory
        binding.rvCategories.layoutManager = GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)


        /*
        val c1 = view.findViewById<Button>(R.id.categories1_button)
        val c2 = view.findViewById<Button>(R.id.categories2_button)
        val c3 = view.findViewById<Button>(R.id.categories3_button)
        val c4 = view.findViewById<Button>(R.id.categories4_button)

        c1.setOnClickListener{
            view.findNavController().navigate(R.id.action_categories_frag_to_words_frag)
        }

        c2.setOnClickListener{
            view.findNavController().navigate(R.id.action_categories_frag_to_words_frag)
        }

        c3.setOnClickListener{
            view.findNavController().navigate(R.id.action_categories_frag_to_words_frag)
        }

        c4.setOnClickListener{
            view.findNavController().navigate(R.id.action_categories_frag_to_words_frag)
        }

         */
    }
}