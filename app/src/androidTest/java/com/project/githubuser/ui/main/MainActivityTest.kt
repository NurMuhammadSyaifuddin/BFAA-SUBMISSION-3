package com.project.githubuser.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.project.githubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import com.project.githubuser.adapter.UsersAdapter

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun getSearchUsers(){
        onView(withId(R.id.edt_search_main)).perform(typeText("NurMuhammadSyaifuddin"), closeSoftKeyboard())
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.ViewHolder>(0, click())
        )
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
    }


}