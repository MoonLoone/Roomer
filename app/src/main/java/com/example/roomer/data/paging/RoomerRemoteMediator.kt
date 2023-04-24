package com.example.roomer.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.domain.model.entities.BaseEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RoomerRemoteMediator<T: BaseEntity>(
    private val database: RoomerDatabase,
    private val useCaseFunction: suspend (Int) -> List<T>,
    private val saveToDb: suspend (Any) -> Unit,
    private val deleteFromDb: suspend () -> Unit,
): RemoteMediator<Int, T>() {

    private var refreshFlag = false

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        return try{
            val loadKey = when(loadType){
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }
            val response = useCaseFunction(loadKey?:0)
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    deleteFromDb()
                }
                saveToDb(response)
            }
            MediatorResult.Success(endOfPaginationReached = loadKey == null)
        }
        catch (e: IOException){
            MediatorResult.Error(e)
        }
        catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        //return if (System.currentTimeMillis() <= cacheTimeout){
        return if (true){
            InitializeAction.SKIP_INITIAL_REFRESH
        }
        else{
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    fun reloadLastPage(){

    }

}