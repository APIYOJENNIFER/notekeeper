package com.example.androidassociatedeveloper;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    public static final String ORIGINAL_COURSE_ID = "com.example.androidassociatedeveloper.ORIGINAL_COURSE_ID";
    public static final String ORIGINAL_COURSE_TITLE = "com.example.androidassociatedeveloper.ORIGINAL_COURSE_TITLE";
    public static final String ORIGINAL_COURSE_TEXT = "com.example.androidassociatedeveloper.ORIGINAL_COURSE_TEXT";

    public String originalCourseId, originalCourseTitle, originalCourseText;
    public boolean isNewlyCreated = true;

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_COURSE_ID, originalCourseId);
        outState.putString(ORIGINAL_COURSE_TITLE, originalCourseTitle);
        outState.putString(ORIGINAL_COURSE_TEXT, originalCourseText);
    }

    public void restoreState(Bundle instate) {
        originalCourseId = instate.getString(ORIGINAL_COURSE_ID);
        originalCourseTitle = instate.getString(ORIGINAL_COURSE_TITLE);
        originalCourseText = instate.getString(ORIGINAL_COURSE_TEXT);
    }
}
