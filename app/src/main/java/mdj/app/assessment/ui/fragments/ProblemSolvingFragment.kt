package mdj.app.assessment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mdj.app.assessment.R
import mdj.app.assessment.databinding.FragmentPetBinding
import mdj.app.assessment.databinding.FragmentProblemSolvingBinding

class ProblemSolvingFragment : Fragment() {

    // view binding
    private lateinit var binding: FragmentProblemSolvingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProblemSolvingBinding.inflate(inflater, container, false)

        return binding.root
    }

}