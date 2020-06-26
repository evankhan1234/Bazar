package com.evan.bazar

import android.app.Application
import com.evan.bazar.data.db.AppDatabase
import com.evan.bazar.data.network.MyApi
import com.evan.bazar.data.network.NetworkConnectionInterceptor
import com.evan.bazar.data.network.PushApi
import com.evan.bazar.data.preferences.PreferenceProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.data.repositories.QuotesRepository
import com.evan.bazar.data.repositories.UserRepository
import com.evan.bazar.ui.auth.AuthViewModelFactory
import com.evan.bazar.ui.home.HomeViewModelFactory
import com.evan.bazar.ui.home.category.CategoryDataSource
import com.evan.bazar.ui.home.category.CategoryModelFactory
import com.evan.bazar.ui.home.category.CategorySourceFactory
import com.evan.bazar.ui.home.category.CategoryViewModel
import com.evan.bazar.ui.home.chat.ChatListDataSource
import com.evan.bazar.ui.home.chat.ChatListModelFactory
import com.evan.bazar.ui.home.chat.ChatListSourceFactory
import com.evan.bazar.ui.home.chat.ChatListViewModel
import com.evan.bazar.ui.home.delivery.DeliveryDataSource
import com.evan.bazar.ui.home.delivery.DeliveryModelFactory
import com.evan.bazar.ui.home.delivery.DeliverySourceFactory
import com.evan.bazar.ui.home.delivery.DeliveryViewModel
import com.evan.bazar.ui.home.newsfeed.ownpost.OwnPostDataSource
import com.evan.bazar.ui.home.newsfeed.ownpost.OwnPostModelFactory
import com.evan.bazar.ui.home.newsfeed.ownpost.OwnPostSourceFactory
import com.evan.bazar.ui.home.newsfeed.ownpost.OwnPostViewModel
import com.evan.bazar.ui.home.newsfeed.publicpost.PublicPostDataSource
import com.evan.bazar.ui.home.newsfeed.publicpost.PublicPostModelFactory
import com.evan.bazar.ui.home.newsfeed.publicpost.PublicPostSourceFactory
import com.evan.bazar.ui.home.newsfeed.publicpost.PublicPostViewModel
import com.evan.bazar.ui.home.product.ProductDataSource
import com.evan.bazar.ui.home.product.ProductModelFactory
import com.evan.bazar.ui.home.product.ProductSourceFactory
import com.evan.bazar.ui.home.product.ProductViewModel
import com.evan.bazar.ui.home.profile.ProfileViewModelFactory
import com.evan.bazar.ui.home.purchase.PurchaseDataSource
import com.evan.bazar.ui.home.purchase.PurchaseModelFactory
import com.evan.bazar.ui.home.purchase.PurchaseSourceFactory
import com.evan.bazar.ui.home.purchase.PurchaseViewModel
import com.evan.bazar.ui.home.quotes.QuotesViewModelFactory
import com.evan.bazar.ui.home.supplier.SupplierDataSource
import com.evan.bazar.ui.home.supplier.SupplierModelFactory
import com.evan.bazar.ui.home.supplier.SupplierSourceFactory
import com.evan.bazar.ui.home.supplier.SupplierViewModel
import com.evan.dokan.ui.home.notice.NoticeDataSource
import com.evan.dokan.ui.home.notice.NoticeModelFactory
import com.evan.dokan.ui.home.notice.NoticeSourceFactory
import com.evan.dokan.ui.home.notice.NoticeViewModel
import com.google.firebase.database.FirebaseDatabase

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { PushApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance(), instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { CategoryModelFactory(instance(),instance()) }
        bind() from provider { ChatListModelFactory(instance(),instance()) }
        bind() from provider { OwnPostModelFactory(instance(),instance()) }
        bind() from provider { PublicPostModelFactory(instance(),instance()) }
        bind() from provider { NoticeModelFactory(instance(),instance()) }
        bind() from provider { PurchaseModelFactory(instance(),instance()) }
        bind() from provider { SupplierModelFactory(instance(),instance()) }
        bind() from provider { DeliveryModelFactory(instance(),instance()) }
        bind() from provider { ProductModelFactory(instance(),instance()) }
        bind() from provider { CategoryDataSource(instance(),instance()) }
        bind() from provider { ChatListDataSource(instance(),instance()) }
        bind() from provider { NoticeDataSource(instance(),instance()) }
        bind() from provider { OwnPostDataSource(instance(),instance()) }
        bind() from provider { PublicPostDataSource(instance(),instance()) }
        bind() from provider { PurchaseDataSource(instance(),instance()) }
        bind() from provider { SupplierDataSource(instance(),instance()) }
        bind() from provider { DeliveryDataSource(instance(),instance()) }
        bind() from provider { ProductDataSource(instance(),instance()) }
        bind() from provider { CategorySourceFactory(instance()) }
        bind() from provider { ChatListSourceFactory(instance()) }
        bind() from provider { OwnPostSourceFactory(instance()) }
        bind() from provider { PublicPostSourceFactory(instance()) }
        bind() from provider { NoticeSourceFactory(instance()) }
        bind() from provider { PurchaseSourceFactory(instance()) }
        bind() from provider { SupplierSourceFactory(instance()) }
        bind() from provider { DeliverySourceFactory(instance()) }
        bind() from provider { ProductSourceFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { CategoryViewModel(instance(),instance()) }
        bind() from provider { ChatListViewModel(instance(),instance()) }
        bind() from provider { OwnPostViewModel(instance(),instance()) }
        bind() from provider { PublicPostViewModel(instance(),instance()) }
        bind() from provider { PurchaseViewModel(instance(),instance()) }
        bind() from provider { SupplierViewModel(instance(),instance()) }
        bind() from provider { DeliveryViewModel(instance(),instance()) }
        bind() from provider { ProductViewModel(instance(),instance()) }
        bind() from provider { NoticeViewModel(instance(),instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider{ QuotesViewModelFactory(instance()) }


    }
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}