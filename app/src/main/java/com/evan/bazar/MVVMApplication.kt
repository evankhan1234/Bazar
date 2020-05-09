package com.evan.bazar

import android.app.Application
import com.evan.bazar.data.db.AppDatabase
import com.evan.bazar.data.network.MyApi
import com.evan.bazar.data.network.NetworkConnectionInterceptor
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
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { CategoryModelFactory(instance(),instance()) }
        bind() from provider { PurchaseModelFactory(instance(),instance()) }
        bind() from provider { SupplierModelFactory(instance(),instance()) }
        bind() from provider { CategoryDataSource(instance(),instance()) }
        bind() from provider { PurchaseDataSource(instance(),instance()) }
        bind() from provider { SupplierDataSource(instance(),instance()) }
        bind() from provider { CategorySourceFactory(instance()) }
        bind() from provider { PurchaseSourceFactory(instance()) }
        bind() from provider { SupplierSourceFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { CategoryViewModel(instance(),instance()) }
        bind() from provider { PurchaseViewModel(instance(),instance()) }
        bind() from provider { SupplierViewModel(instance(),instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider{ QuotesViewModelFactory(instance()) }


    }

}