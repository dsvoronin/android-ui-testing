package com.avito.android.test.page_object

import android.support.test.espresso.action.SwipeDirections
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.view.View
import com.avito.android.test.InteractionContext
import com.avito.android.test.SimpleInteractionContext
import com.avito.android.test.action.Actions
import com.avito.android.test.action.ActionsDriver
import com.avito.android.test.action.ActionsImpl
import com.avito.android.test.checks.Checks
import com.avito.android.test.checks.ChecksDriver
import com.avito.android.test.checks.ChecksImpl
import com.avito.android.test.espresso.EspressoActions
import com.avito.android.test.matcher.IsRefreshingMatcher
import org.hamcrest.Matcher
import org.hamcrest.core.Is.`is`

class SwipeRefreshElement(interactionContext: InteractionContext) : PageObjectElement(interactionContext) {

    constructor(matcher: Matcher<View>) : this(SimpleInteractionContext(matcher))

    override val checks: SwipeRefreshChecks = SwipeRefreshChecksImpl(interactionContext)
    override val actions: SwipeRefreshActions = SwipeRefreshActionsImpl(interactionContext)
}

interface SwipeRefreshActions : Actions {

    fun pullToRefresh()
}

class SwipeRefreshActionsImpl(private val driver: ActionsDriver) : SwipeRefreshActions,
        Actions by ActionsImpl(driver) {

    override fun pullToRefresh() {
        driver.perform(EspressoActions.swipe(SwipeDirections.TOP_TO_BOTTOM))
    }
}

interface SwipeRefreshChecks : Checks {

    fun isRefreshing()
    fun isNotRefreshing()
}

class SwipeRefreshChecksImpl(private val driver: ChecksDriver) : SwipeRefreshChecks,
        Checks by ChecksImpl(driver) {

    override fun isRefreshing() {
        driver.check(matches(IsRefreshingMatcher(`is`(true))))
    }

    override fun isNotRefreshing() {
        driver.check(matches(IsRefreshingMatcher(`is`(false))))
    }
}