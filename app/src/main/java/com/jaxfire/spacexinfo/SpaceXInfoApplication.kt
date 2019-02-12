package com.jaxfire.spacexinfo

import android.app.Application
import com.jaxfire.spacexinfo.data.db.SpaceXInfoDatabase
import com.jaxfire.spacexinfo.data.db.SpaceXInfoDatabase_Impl
import com.jaxfire.spacexinfo.data.network.*
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepository
import com.jaxfire.spacexinfo.data.repository.SpaceXInfoRepositoryImpl
import com.jaxfire.spacexinfo.ui.rocket_info.detail.RocketDetailViewModelFactory
import com.jaxfire.spacexinfo.ui.rocket_info.list.RocketListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*


class SpaceXInfoApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        // Will provide instances of Context, services and anything related to Android
        import(androidXModule(this@SpaceXInfoApplication))

        bind() from singleton { SpaceXInfoDatabase(instance()) }
        bind() from singleton { instance<SpaceXInfoDatabase>().rocketDao() }
        bind() from singleton { instance<SpaceXInfoDatabase>().launchesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { SpaceXApiService(instance()) }
        bind<SpaceXInfoNetworkDataSource>() with singleton { SpaceXInfoNetworkDataSourceImpl(instance()) }
        bind<SpaceXInfoRepository>() with singleton { SpaceXInfoRepositoryImpl(instance(), instance(), instance()) }
        bind() from provider { RocketListViewModelFactory(instance()) }
        bind() from factory { detailRocketId: String -> RocketDetailViewModelFactory(instance(), detailRocketId) }
    }
}