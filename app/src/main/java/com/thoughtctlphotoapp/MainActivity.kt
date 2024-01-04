package com.thoughtctlphotoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.thoughtctlphotoapp.adapter.PhotoAdapter
import com.thoughtctlphotoapp.databinding.ActivityMainBinding
import com.thoughtctlphotoapp.model.GallaryResponse
import com.thoughtctlphotoapp.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.viewModels
import com.thoughtctlphotoapp.utils.Constants.LISTTYPE

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var layoutManagerObj: LayoutManager
lateinit var gallaryResponse:GallaryResponse
    private val galleryViewModel: GalleryViewModel by viewModels()

    @Inject
    lateinit var photoAdapter: PhotoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutManagerObj = LinearLayoutManager(this, VERTICAL, false)

        binding.listType.apply {
            setOnClickListener {
                try {
                    if (text.toString().equals(context.getString(R.string.list))) {
                        text = context.getString(R.string.grid)
                        layoutManagerObj = LinearLayoutManager(this@MainActivity, VERTICAL, false)
                        setupRecyclerView(gallaryResponse, "List")
                    } else {
                        text = context.getString(R.string.list)
                        layoutManagerObj = GridLayoutManager(this@MainActivity, 2)
                        setupRecyclerView(gallaryResponse, "Grid")
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

        binding.apply {
            ivSearch.setOnClickListener {
                if (edtQuery.text.toString().isNotEmpty()) {
                    pBar.visibility= VISIBLE
                    binding.listType.text = getString(R.string.grid)
                    layoutManagerObj = LinearLayoutManager(this@MainActivity, VERTICAL, false)
                    galleryViewModel.getGalleryList(edtQuery.text.toString())
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter search text",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        galleryViewModel.galleryObserver.observe(this@MainActivity) {
            Log.e("Mainactivity", it.toString())
            binding.pBar.visibility= GONE
            if (it != null) {
                gallaryResponse=it
            }

            setupRecyclerView(it,"List")
        }
    }

    fun setupRecyclerView(gallaryResponse: GallaryResponse?, listType: String) {

        gallaryResponse?.let {
            if (it.success) {
                if (it.data.isNotEmpty()) {
                    binding.tvEmptyMessage.visibility = GONE
                    LISTTYPE=listType
                    photoAdapter.differ.submitList(it.data)

                    binding.rvImageList.apply {
                        layoutManager = layoutManagerObj
                        adapter = photoAdapter
                    }
                } else {
                    binding.tvEmptyMessage.visibility = VISIBLE
                }
            } else {
                binding.tvEmptyMessage.visibility = VISIBLE
            }
        }

    }
}