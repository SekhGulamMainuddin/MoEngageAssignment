package com.sekhgmainuddin.assignmentmoengage.common

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sekhgmainuddin.assignmentmoengage.R
import java.text.SimpleDateFormat

@BindingAdapter(
    "app:imageUrl",
    "app:progressBar"
)
fun setImageUrl(
    view: ImageView,
    imageUrl: String?,
    progressBar: ProgressBar,
) {
    Glide.with(view.context).load(imageUrl).placeholder(R.drawable.placeholder_image)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.isVisible = false
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.isVisible = false
                return false
            }
        }).into(view)
}

@BindingAdapter("app:date")
fun setDate(view: TextView, date: String) {
    view.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date)?.let {
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(
            it
        )
    }
}

@BindingAdapter("app:customRes", "app:customColor")
fun setCustomRes(view: ImageButton, customRes: Int, customColor: Int) {
    view.setImageDrawable(AppCompatResources.getDrawable(view.context, customRes))
    view.imageTintList = AppCompatResources.getColorStateList(view.context, customColor)
}

@BindingAdapter("app:drawableImage")
fun setDrawableImage(view: ImageView, drawableImage: Int) {
    Glide.with(view.context).load(AppCompatResources.getDrawable(view.context, drawableImage))
        .into(view)
}