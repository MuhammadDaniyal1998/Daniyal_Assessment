package mdj.app.assessment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import mdj.app.assessment.R
import mdj.app.assessment.adapters.PetAdapter
import mdj.app.assessment.databinding.FragmentPetBinding
import mdj.app.assessment.models.PetModel
import mdj.app.assessment.networking.HttpResponse.Companion.getResponse
import mdj.app.assessment.networking.NetworkResult
import mdj.app.assessment.ui.activities.MainActivity
import mdj.app.assessment.utils.Constants.TAG_PET
import mdj.app.assessment.utils.Constants.VALUE_API_KEY
import mdj.app.assessment.utils.Constants.VALUE_API_TYPE
import mdj.app.assessment.utils.Constants.VALUE_IMAGE_TYPE
import mdj.app.assessment.utils.Constants.VALUE_PRETTY
import mdj.app.assessment.utils.Helper.Companion.emptyView
import mdj.app.assessment.utils.Helper.Companion.internetCheck
import mdj.app.assessment.utils.Helper.Companion.showFullMediaDialog
import mdj.app.assessment.utils.Helper.Companion.showToast
import mdj.app.assessment.viewmodels.PetViewModel

@AndroidEntryPoint
class PetFragment : Fragment(), PetAdapter.ItemClickListener {

    // view binding
    private lateinit var binding: FragmentPetBinding

    // view model
    private val petViewModel by viewModels<PetViewModel>()

    // list
    private var petList: MutableList<PetModel> = mutableListOf()

    // adapter
    private lateinit var adapter: PetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetBinding.inflate(inflater, container, false)

        if (internetCheck()) {
            petViewModel.getPets(
                VALUE_API_KEY, VALUE_API_TYPE, VALUE_IMAGE_TYPE, VALUE_PRETTY,
                TAG_PET
            ) // hit pet call
        } else {
            context?.let { it1 ->
                emptyView(
                    binding.frLayout.root,
                    true,
                    binding.frLayout.tvMsg,
                    it1.getString(R.string.error_no_pet_found)
                )
            } // setup empty view

        }

        return binding.root
    }

    private fun setDataInRecyclerview() {

        // create layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        // pass it to rvLists layoutManager
        binding.rvPet.layoutManager = layoutManager

        // initialize the adapter,
        // and pass the required argument
        adapter = context?.let { PetAdapter(it, this, petList) }!!

        // attach adapter to the recycler view
        binding.rvPet.adapter = adapter

    }

    override fun onItemClick(position: Int, model: PetModel) {
        context?.let {
            showFullMediaDialog(
                it, model.largeImageURL
            )
        } // show full image
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataInRecyclerview()
        bindObservers()
    }

    private fun bindObservers() {

        petViewModel.liveData.observe(viewLifecycleOwner, Observer { it2 ->

            // hide loader
            (activity as MainActivity?)!!.dialog.dismiss()

            it2.getContentIfNotHandled()?.let {

                when (it) {

                    is NetworkResult.Success -> {

                        when (it.tag) {

                            TAG_PET -> {

                                // clear list before store data
                                petList.clear()

                                // storing data in list
                                getResponse(it)?.let { it1 -> petList.addAll(it1.hits) }

                                // notify adapter
                                adapter.notifyDataSetChanged()

                                // setup empty view
                                context?.let { it1 ->
                                    emptyView(
                                        binding.frLayout.root,
                                        petList.size == 0,
                                        binding.frLayout.tvMsg,
                                        it1.getString(R.string.error_no_pet_found)
                                    )
                                }
                            }
                        }
                    }

                    is NetworkResult.Error -> {

                        context?.let { mContext ->
                            showToast(
                                mContext, it.message.toString()
                            )
                        } // show toast

                        when (it.tag) {

                            TAG_PET -> {
                                context?.let { it1 ->
                                    emptyView(
                                        binding.frLayout.root,
                                        true,
                                        binding.frLayout.tvMsg,
                                        it1.getString(R.string.error_no_pet_found)
                                    )
                                } // setup empty view
                            }
                        }
                    }

                    is NetworkResult.Loading -> {
                        (activity as MainActivity?)!!.dialog.show() // show loader
                    }
                }
            }
        })
    }
}