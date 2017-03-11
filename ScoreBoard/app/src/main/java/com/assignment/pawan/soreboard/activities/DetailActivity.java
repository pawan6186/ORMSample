package com.assignment.pawan.soreboard.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.assignment.pawan.soreboard.AppController;
import com.assignment.pawan.soreboard.R;
import com.assignment.pawan.soreboard.databinding.ActivityDetailBinding;
import com.assignment.pawan.soreboard.models.Record;
import com.assignment.pawan.soreboard.util.Constants;

public class DetailActivity extends BaseActivity implements View.OnClickListener {


    private Context context;
    private ActivityDetailBinding contentView;
    private Record record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        context = this;
        setSupportActionBar(contentView.appBar.appBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            record = (Record) bundle.getSerializable(Constants.OBJECT_KEY);
            initViews();
        } else {
            finish();
        }


    }

    private void initViews() {
        setPageTitle(contentView.appBar.title, record.getName());
        contentView.name.setText(record.getName());
        contentView.totalCore.setText(Integer.toString(record.getTotalScore()));
        contentView.totalMatch.setText(Integer.toString(record.getMatchesPlayed()));
        contentView.country.setText(record.getCountry());
        contentView.description.setText(record.getDescription());
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        contentView.image.setImageUrl(record.getImage(), imageLoader);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


}

