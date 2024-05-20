package dev.maxsiomin.authlib.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UsersDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UsersDatabase
    private val dao get() = database.dao

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UsersDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDownDatabase() {
        database.close()
    }

    @Test
    fun testInsertionAndGettingById() = runTest {
        val entity = UserEntity(
            username = "testUsername",
            passwordHash = "hash",
            avatarUrl = "",
            fullName = "Name Surname"
        )
        dao.insertNewUser(entity)
        assertThat(dao.getUserByName(entity.username)).isEqualTo(entity)
    }

}