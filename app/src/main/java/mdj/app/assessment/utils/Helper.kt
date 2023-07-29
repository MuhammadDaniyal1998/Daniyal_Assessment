package mdj.app.assessment.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.github.ybq.android.spinkit.SpinKitView
import mdj.app.assessment.AppClass.Companion.application
import mdj.app.assessment.R
import mdj.app.assessment.databinding.DialogFullMediaBinding

class Helper {

    companion object {

        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun internetCheck(): Boolean {

            val cmg =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                cmg.getNetworkCapabilities(cmg.activeNetwork)?.let { networkCapabilities ->
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
            } else {
                return cmg.activeNetworkInfo?.isConnectedOrConnecting == true
            }

            // show toast when internet off
            showToast(application, application.getString(R.string.error_internet_not_available))

            return false
        }

        fun emptyView(
            frameLayout: View,
            fr_visibility: Boolean,
            textView: TextView,
            emptyText: String
        ) {

            // set view visibility
            frameLayout.visibility = if (fr_visibility) VISIBLE else GONE

            // set text in text view
            textView.text = emptyText
        }

        fun progressDialog(activity: Activity): Dialog? {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.loader_layout)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }

        fun loadImageFromUrl(
            context: Context,
            url: String,
            imageView: ImageView,
            loader: SpinKitView
        ) {

            Glide.with(context).asDrawable()
                .error(R.drawable.error_bg)
                .placeholder(R.drawable.error_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(url)
                .into(object : CustomTarget<Drawable?>() {

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
                    ) {

                        // set image in image view
                        imageView.setImageDrawable(resource)

                        // hide loader
                        loader.visibility = GONE
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)

                        // show loader
                        loader.visibility = VISIBLE
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)

                        // hide loader
                        loader.visibility = GONE
                    }
                })
        }

        fun showFullMediaDialog(
            context: Context,
            url: String
        ) {

            val dialog = Dialog(context)
            val binding: DialogFullMediaBinding =
                DialogFullMediaBinding.inflate(LayoutInflater.from(context))
            dialog?.setContentView(binding.root)
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // load image
            loadImageFromUrl(
                context,
                url,
                binding.ivFull,
                binding.loaderLayout.spinKit
            )

            // click listener
            binding.ivBack.setOnClickListener {
                dialog.dismiss() // dialog dismiss
            }

            dialog.show()
        }

    }

}