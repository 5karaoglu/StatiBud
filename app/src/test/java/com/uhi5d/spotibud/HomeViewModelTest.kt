package com.uhi5d.spotibud

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.uhi5d.spotibud.domain.model.MyArtists
import com.uhi5d.spotibud.domain.model.mytracks.MyTracks
import com.uhi5d.spotibud.domain.model.recommendations.Recommendations
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.presentation.ui.home.HomeViewModel
import com.uhi5d.spotibud.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var useCase: UseCase

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var recommendationsObserver: Observer<DataState<Recommendations>>

    @InternalCoroutinesApi
    @Test
    fun getRecommendationsSuccessfully() {
        testCoroutineRule.runBlockingTest {
            doReturn(MyArtists("", emptyList(),null,
                "",null,"",null))
                .`when`(useCase).getMyTopArtists("","")

            doReturn(
                MyTracks("", emptyList(),null,
                "",null,"",null)
            )
                .`when`(useCase).getMyTopTracks("","")

            val viewModel = HomeViewModel(useCase,sharedPreferences)
            viewModel.recommendations.observeForever(recommendationsObserver)
            verify(useCase).getMyTopArtists("","")
            verify(useCase).getMyTopTracks("","")
            verify(recommendationsObserver).onChanged(DataState.Success(
                Recommendations(emptyList(),
                emptyList())
            ))
            viewModel.recommendations.removeObserver(recommendationsObserver)

        }
    }
}