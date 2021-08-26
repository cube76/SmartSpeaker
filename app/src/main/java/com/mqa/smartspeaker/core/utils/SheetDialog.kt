package com.mqa.smartspeaker.core.utils

import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup

object SheetDialog {
    fun toggle(show: Boolean, view: View, parent: View) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = 600
        transition.addTarget(view)
        TransitionManager.beginDelayedTransition(parent as ViewGroup?, transition)
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}