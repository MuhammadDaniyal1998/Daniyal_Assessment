package mdj.app.assessment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mdj.app.assessment.databinding.ItemPetBinding
import mdj.app.assessment.models.PetModel
import mdj.app.assessment.utils.Helper.Companion.loadImageFromUrl

class PetAdapter(
    private val context: Context,
    private val itemClickListener: ItemClickListener,
    private var petList: MutableList<PetModel>,
) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPetBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(petList[position]){

                // set text in text views
                binding.tvTags.text = makeHashTag(tags)
                binding.tvLikesCount.text = "$likes ${if (likes > 1) "Likes" else "Like"}"
                binding.tvUserName.text = user

                // load image in image view from url
                context?.let {
                    loadImageFromUrl(
                        it,
                        largeImageURL,
                        binding.ivLarge,
                        binding.loaderLayout.spinKit
                    )
                }

                // click listener
                binding.root.setOnClickListener {
                    itemClickListener.onItemClick(position, petList[position])
                }
            }
        }
    }

    private fun makeHashTag(tags: String): String? {

        var hashTags = ""
        val tagsSplit = tags.split(", ").toTypedArray()

        for (tag in tagsSplit) {
            hashTags = "$hashTags #${tag}"
        }

        return hashTags
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // interface
    interface ItemClickListener {
        fun onItemClick(position:Int, model: PetModel)
    }
}