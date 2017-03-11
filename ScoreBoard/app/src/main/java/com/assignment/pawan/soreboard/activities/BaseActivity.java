package com.assignment.pawan.soreboard.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Pawan Gupta on 11-03-2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected void setPageTitle(TextView v, int title) {
        v.setText(getResources().getString(title));

    }

    protected void setPageTitle(TextView v, String title) {
        v.setText(title);

    }
}
