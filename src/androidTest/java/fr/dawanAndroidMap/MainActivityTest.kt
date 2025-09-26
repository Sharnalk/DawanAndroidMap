package fr.dawanAndroidMap

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.dawanAndroidMap.data.Center
import fr.dawanAndroidMap.ui.CentersAdapter
import fr.dawanAndroidMap.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests instrumentés Espresso pour MainActivity.
 * On évite le réseau en pré-remplissant Room avant le lancement.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun seedDb() {
        DbTestUtils.seedCenters(
            Center(id = 1, name = "Dawan Paris", address = "21 rue de Paris", latitude = 48.8566, longitude = 2.3522),
            Center(id = 2, name = "Dawan Lyon", address = "10 rue de Lyon", latitude = 45.7640, longitude = 4.8357),
            Center(id = 3, name = "Dawan Bordeaux", address = "250 av. Emile Counord", latitude = 44.8649, longitude = -0.5754)
        )
    }

    @After
    fun cleanDb() {
        DbTestUtils.clearCenters()
    }

    @Test
    fun launch_showsRecycler_andItemFromDb() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // La RecyclerView est visible
            onView(withId(R.id.centersRecyclerView)).check(matches(isDisplayed()))

            // On retrouve un item précis
            onView(withId(R.id.centersRecyclerView)).perform(
                RecyclerViewActions.scrollTo<CentersAdapter.VH>(
                    hasDescendant(withText("Dawan Lyon"))
                )
            )
            onView(withText("Dawan Lyon")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun click_firstItem_doesNotCrash_andListStillVisible() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // Clic sur le premier item de la liste
            onView(withId(R.id.centersRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<CentersAdapter.VH>(0, click())
            )

            // L'écran est toujours vivant : la liste est affichée
            onView(withId(R.id.centersRecyclerView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun mapContainer_isDisplayed() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // Le conteneur de la Google Map est bien dans le layout
            onView(withId(R.id.mapContainer)).check(matches(isDisplayed()))
        }
    }
}
