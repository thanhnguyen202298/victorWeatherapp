package com.victorthanh.utilslib.presentation.widget;

import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class TranslateAnim extends TranslateAnimation {

    private long mElapsedAtPause;
    private boolean mPaused;

    public TranslateAnim(float fromXDelta, float toXDelta, float fromYDelta,
                         float toYDelta) {
        super(fromXDelta, toXDelta, fromYDelta, toYDelta);
        mElapsedAtPause = 0;
        mPaused = false;
    }

    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        if (getStartTime() >= 0) {
            if (mPaused && mElapsedAtPause == 0) {
                mElapsedAtPause = currentTime - getStartTime();
            }
            if (mPaused)
                setStartTime(currentTime - mElapsedAtPause);
        }
        return super.getTransformation(currentTime, outTransformation);
    }

    public void pause() {
        mElapsedAtPause = 0;
        mPaused = true;
    }

    public void resume() {
        mPaused = false;
    }
}
