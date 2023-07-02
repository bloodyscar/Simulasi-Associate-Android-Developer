import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun testAddTaskButton_opensAddTaskActivity() {
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.nameCourse)).check(matches(isDisplayed()))
    }


}
