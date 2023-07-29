package mdj.app.assessment.ui.fragments

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mdj.app.assessment.R
import mdj.app.assessment.databinding.FragmentProblemSolvingBinding
import mdj.app.assessment.utils.Helper.Companion.showToast

class ProblemSolvingFragment : Fragment(), View.OnClickListener {

    // view binding
    private lateinit var binding: FragmentProblemSolvingBinding

    // tag
    private val TAG = ProblemSolvingFragment::javaClass.name


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProblemSolvingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClickListener()
    }

    private fun registerClickListener() {
        binding.tvSubmit.setOnClickListener(this)
    }

    private fun isValid(): Boolean {

        if (isEmpty(binding.edtSomething.text.trim())) {
            context?.let { showToast(it, it.getString(R.string.error_please_enter_something)) }
            return false
        }

        return true
    }

    private fun getUniqueWordCount(words: String) {

        // make list of string
        val wordList = listOf(*words.trim().split(" ").toTypedArray())

        // mapping
        val output = wordList.groupBy { it }
            .map { "'${it.key}': ${it.value.size}" }
            .toString()

        // set text in text view
        binding.tvResult.text = output

        // print output
        Log.d(TAG, output)

    }

    override fun onClick(view: View?) {

        when (view) {

            binding.tvSubmit -> {

               if (isValid()) {
                   getUniqueWordCount(binding.edtSomething.text.toString().lowercase())
               }
            }
        }
    }
}