package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.CrimeDatabase
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME ="crime-database"
class CrimeRepository private constructor(context:Context) {

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDAO = database.crimeDAO()
    fun getCrimes(): LiveData<List<Crime>> = crimeDAO.getCrimes()
    fun getCrime(id: UUID) : LiveData<Crime?> = crimeDAO.getCrime(id)
    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun initialize (context: Context){
            if (INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE?:
            throw IllegalStateException("CrimeRepository must be initialized")

        }
    }
}