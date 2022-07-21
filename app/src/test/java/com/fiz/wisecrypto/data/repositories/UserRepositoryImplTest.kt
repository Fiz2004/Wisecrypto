package com.fiz.wisecrypto.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fiz.wisecrypto.data.data_source.UserLocalDataSourceImpl
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.toActiveEntity
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.User
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.annotation.Config

private const val ANY_S = ""
private const val ANY_D = 0.0
private const val EPSILON = 0.000001

@Config(sdk = [30])
@RunWith(AndroidJUnit4::class)
class UserRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userLocalDataSource: UserLocalDataSourceImpl
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var user: User

    init {
        AndroidThreeTen.init(ApplicationProvider.getApplicationContext())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        userLocalDataSource = mock()
        userRepository = UserRepositoryImpl(userLocalDataSource, testCoroutineDispatcher)

        user = User.create(
            fullName = ANY_S, numberPhone = ANY_S, userName = ANY_S, email = ANY_S, balance = 30.0,
            actives = listOf(
                Active.create(
                    idCoin = "test",
                    count = 1000.0,
                    priceForBuy = ANY_D
                )
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenSellActive_shouldInvokeSaveInLocalDataSource() {
        runTest(testCoroutineDispatcher) {
            testCoroutineScope.launch {
                /* Given */

                /* When */
                userRepository.sellActive(
                    user = user,
                    idCoin = ANY_S,
                    userCoinForSell = ANY_D,
                    priceCurrency = ANY_D
                )
                advanceUntilIdle()
                /* Then */
                verify(userLocalDataSource, times(1)).saveActivesAndBalance(
                    email = any(),
                    actives = any(),
                    balance = any(),
                    transactionEntity = any()
                )

            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenSellActive_shouldChangeActiveCount() {
        runTest(testCoroutineDispatcher) {
            launch {
                /* Given */

                /* When */
                userRepository.sellActive(
                    user = user,
                    idCoin = "test",
                    userCoinForSell = 500.0,
                    priceCurrency = ANY_D
                )
                advanceUntilIdle()
                /* Then */
                val actualActives = listOf(
                    Active.create(
                        idCoin = "test",
                        count = 500.0,
                        priceForBuy = ANY_D
                    )
                ).map { it.toActiveEntity(user.email) }
                argumentCaptor<List<ActiveEntity>>().apply {

                    verify(userLocalDataSource).saveActivesAndBalance(
                        email = any(),
                        actives = capture(),
                        balance = any(),
                        transactionEntity = any()
                    )

                    val expectedActive = firstValue.first()

                    assertEquals(actualActives.first().count, expectedActive.count)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenSellActive_shouldChangeBalanceUser() {
        runTest(testCoroutineDispatcher) {
            launch {
                /* Given */

                /* When */
                userRepository.sellActive(
                    user = user,
                    idCoin = "test",
                    userCoinForSell = 500.0,
                    priceCurrency = 50.0
                )
                advanceUntilIdle()
                /* Then */
                val actualBalance = 8000.0

                argumentCaptor<Double>().apply {
                    verify(userLocalDataSource, times(1)).saveActivesAndBalance(
                        email = any(),
                        actives = any(),
                        balance = capture(),
                        transactionEntity = any()
                    )

                    val expectedBalance = firstValue

                    assertEquals(actualBalance, expectedBalance, EPSILON)
                }

            }
        }
    }

    @Test
    fun buyActive() {

    }

    @Test
    fun addBalance() {

    }

    @Test
    fun cashBalance() {

    }
}