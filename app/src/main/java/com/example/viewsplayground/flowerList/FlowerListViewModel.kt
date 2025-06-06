package com.example.viewsplayground.flowerList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewsplayground.data.FlowerDataSource

class FlowerListViewModel(val dataSource: FlowerDataSource) : ViewModel() {
    val flowersLiveData = dataSource.getFlowerList()
}

//用工厂的原因是ViewModel()不接收参数，要间接通过工厂传参，比如这里传的ctx
class FlowerListViewModelFactory(private val ctx: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FlowerListViewModel::class.java)) {
            return FlowerListViewModel(dataSource = FlowerDataSource.getDataSource(ctx.resources)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}