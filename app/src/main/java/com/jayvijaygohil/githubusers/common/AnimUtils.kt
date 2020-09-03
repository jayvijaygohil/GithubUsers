package com.jayvijaygohil.githubusers.common

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun View.slideInUp(animatorSet: AnimatorSet, duration: Long) {
    val distance = bottom.div(2).toFloat()
    val object1: ObjectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    val object2: ObjectAnimator = ObjectAnimator.ofFloat(this, "translationY", distance, 0f)

    animatorSet.playTogether(object1, object2)
    animatorSet.duration = duration
    animatorSet.interpolator = AccelerateDecelerateInterpolator()
    animatorSet.start()
}