package com.thoughtctlphotoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.thoughtctlphotoapp.R
import com.thoughtctlphotoapp.databinding.ItemPhotoBinding
import com.thoughtctlphotoapp.model.Data
import com.thoughtctlphotoapp.utils.Constants
import com.thoughtctlphotoapp.utils.DateUtils.getPostDate
import javax.inject.Inject

class PhotoAdapter @Inject constructor() : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    lateinit var itemPhotoBinding: ItemPhotoBinding
    lateinit var context: Context

    inner class PhotoViewHolder() : RecyclerView.ViewHolder(itemPhotoBinding.root) {

        fun set(item: Data, position: Int) {
            itemPhotoBinding.apply {
                item.apply {

                        if (Constants.LISTTYPE.equals("Grid")) {
                            cardGrid.visibility= View.VISIBLE
                            cardList.visibility= View.GONE
                        }else{
                            cardList.visibility= View.VISIBLE
                            cardGrid.visibility= View.GONE
                        }

                    //grid
                    tvPhotoName.text=title
                    tvPhotoCount.text= images_count.toString()
                    tvPostDate.text= getPostDate(datetime.toLong())
                    if (images?.isNotEmpty() == true) {
                        ivPhoto.load(images[0].link) {
                            crossfade(true)
                            placeholder(R.drawable.baseline_image_24)
                            error(R.drawable.baseline_image_24)
                            transformations(RoundedCornersTransformation(15.0f))
                        }
                    }else{
                        ivPhoto.load(R.drawable.baseline_image_24) {
                            crossfade(true)
                            placeholder(R.drawable.baseline_image_24)
                            transformations(RoundedCornersTransformation(15.0f))
                        }
                    }


                    //list
                    tvPhotoNameList.text=title
                    tvPhotoCountList.text= images_count.toString()
                    tvPostDateList.text= getPostDate(datetime.toLong())
                    if (images?.isNotEmpty() == true) {
                        ivPhotoList.load(images[0].link) {
                            crossfade(true)
                            placeholder(R.drawable.baseline_image_24)
                            error(R.drawable.baseline_image_24)
                            transformations(RoundedCornersTransformation(15.0f))
                        }
                    }else{
                        ivPhotoList.load(R.drawable.baseline_image_24) {
                            crossfade(true)
                            placeholder(R.drawable.baseline_image_24)
                            transformations(RoundedCornersTransformation(15.0f))
                        }
                    }

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        itemPhotoBinding = ItemPhotoBinding.inflate(inflater, parent, false)
        context = parent.context
        return PhotoViewHolder()

    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.set(differ.currentList.get(position),position)
        holder.setIsRecyclable(false)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}