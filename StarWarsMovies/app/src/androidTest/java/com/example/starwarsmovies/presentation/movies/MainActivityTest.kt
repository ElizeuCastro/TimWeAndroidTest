package com.example.starwarsmovies.presentation.movies

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.starwarsmovies.R
import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.data.di.DataModule
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.useCase.GetMovieUseCase
import com.example.starwarsmovies.presentation.di.PresentationModule
import com.example.starwarsmovies.presentation.utils.RecyclerViewMatchers.atPosition
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class MainActivityTest {

    private val movies = listOf(
        Movie(
            cover = "https://www.coverwhiz.com/uploads/movies/star-wars-episode-iv-a-new-hope.jpg",
            director = "George Lucas",
            episodeId = "IV",
            releaseDate = "25-05-1977",
            title = "A New Hope"
        ),
        Movie(
            cover = "https://www.coverwhiz.com/uploads/movies/star-wars-episode-v-the-empire-strikes-back.jpg",
            title = "The Empire Strikes Back",
            director = "Irvin Kershner",
            episodeId = "V",
            releaseDate = "17-05-1980"
        )
    )

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @MockK(relaxed = true)
    private lateinit var getMovieUseCase: GetMovieUseCase

    @Before
    fun before() {
        MockKAnnotations.init(this)
        stopKoin()
        startKoin {
            modules(
                listOf(
                    DataModule.modules,
                    module {
                        factory { getMovieUseCase }
                    },
                    PresentationModule.modules
                )
            )
        }

//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        stopKoin()
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun shouldShowCorrectScreenTitle() {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        val expectedTitle = context.getString(R.string.movies_title_screen)

        onView(withText(expectedTitle)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun shouldShowCorrectMovieAtTheFirstPosition() = runBlocking {

        coEvery { getMovieUseCase() } returns Resource.Success(data = movies)

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(0, R.id.iv_cover, withContentDescription("Imagem: A New Hope")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(0, R.id.tv_title, withText("Star Wars - Episódio IV: A New Hope")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(0, R.id.tv_director_label, withText("Diretor:")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(0, R.id.tv_director, withText("George Lucas")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(0, R.id.tv_release_date_label, withText("Data de lançamento:")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(0, R.id.tv_release_date, withText("25-05-1977")))
        )

        activityScenario.close()
    }

    @Test
    fun shouldShowCorrectMovieAtTheSecondPosition() = runBlocking {

        coEvery { getMovieUseCase() } returns Resource.Success(data = movies)

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(1, R.id.iv_cover, withContentDescription("Imagem: The Empire Strikes Back")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(1, R.id.tv_title, withText("Star Wars - Episódio V: The Empire Strikes Back")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(1, R.id.tv_director_label, withText("Diretor:")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(1, R.id.tv_director, withText("Irvin Kershner")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(1, R.id.tv_release_date_label, withText("Data de lançamento:")))
        )
        onView(withId(R.id.rv_movies)).check(
            matches(atPosition(1, R.id.tv_release_date, withText("17-05-1980")))
        )

        activityScenario.close()
    }

    @Test
    fun shouldShowErrorMessage() {

        coEvery { getMovieUseCase() } returns Resource.Error(message = "Any Error")

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.tv_error)).check(matches(withText("Any Error")))

        activityScenario.close()
    }

}