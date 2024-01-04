package com.thoughtctlphotoapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtctlphotoapp.model.GallaryResponse
import com.thoughtctlphotoapp.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel(){

    var galleryObserver= MutableLiveData<GallaryResponse?>()

    fun getGalleryList(query:String){
        viewModelScope.launch {
            repository.getPhotoList(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : Observer<GallaryResponse> {
                    override fun onSubscribe(d: Disposable?) {
                        Log.e("GalleryViewModel", "Subscribe")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("GalleryViewModel", "OnError ${e?.message}")
                    }

                    override fun onComplete() {
                        Log.e("GalleryViewModel", "Completed")
                    }

                    override fun onNext(value: GallaryResponse?) {
                        Log.e("GalleryViewModel", "OnNext")
                        galleryObserver.value = value
                    }

                })
        }
    }
}