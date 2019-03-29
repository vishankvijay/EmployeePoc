package com.example.employeepoc.repo.showlistrepo.database

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoomExecutor
private constructor(val diskIO: Executor) {

    companion object {
        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: RoomExecutor? = null

        val instance: RoomExecutor?
            get() {
                if (sInstance == null) {
                    synchronized(LOCK) {
                        sInstance =
                            RoomExecutor(
                                Executors.newSingleThreadExecutor()
                            )
                    }
                }
                return sInstance
            }
    }
}
