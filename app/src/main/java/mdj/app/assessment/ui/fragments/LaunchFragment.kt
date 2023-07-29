package mdj.app.assessment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import mdj.app.assessment.R
import mdj.app.assessment.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment(), View.OnClickListener {

    // view binding
    private lateinit var binding: FragmentLaunchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLaunchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClickListener()
    }

    private fun registerClickListener() {
        binding.tvProblemSolving.setOnClickListener(this)
        binding.tvApiWork.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view) {

            binding.tvProblemSolving -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_launchFragment_to_problemSolvingFragment) // open problem solving fragment
            }

            binding.tvApiWork -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_launchFragment_to_petFragment) // open pet fragment
            }
        }
    }
}