package com.assignment.pawan.soreboard.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.assignment.pawan.soreboard.R;
import com.assignment.pawan.soreboard.adapters.RecordAdapter;
import com.assignment.pawan.soreboard.databinding.ActivityFavoriteBinding;
import com.assignment.pawan.soreboard.helpers.RecordManager;
import com.assignment.pawan.soreboard.helpers.SortHelper;
import com.assignment.pawan.soreboard.models.Record;
import com.assignment.pawan.soreboard.responsehandlers.BaseParser;
import com.assignment.pawan.soreboard.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteActivity extends BaseActivity implements RecordAdapter.OnItemClickListener, View.OnClickListener {


    private RecordAdapter adapter;
    private List<Record> records;
    private BaseParser parser;
    private Context context;
    private ActivityFavoriteBinding contentView;
    private List<Record> temp;
    private int currentPage = 1;
    private int pageSize = 3;
    private int totalPages = 0;
    private RecyclerView.LayoutManager lm;
    private Map<String, Double> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        context = this;
        setSupportActionBar(contentView.appBar.appBar);
        records = new ArrayList<Record>();
        temp = new ArrayList<Record>();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        initViews();
        getData();


    }

    private void initViews() {
        setPageTitle(contentView.appBar.title, R.string.favorites);
        lm = new LinearLayoutManager(context);
        adapter = new RecordAdapter(context, temp, this, Constants.FAVORITE);
        contentView.recordList.setAdapter(adapter);
        contentView.recordList.setLayoutManager(lm);
        registerForContextMenu(contentView.searchBar.sortButton);
        contentView.searchBar.sortButton.setOnClickListener(this);
        contentView.searchBar.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
        contentView.searchBar.searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                contentView.searchBar.searchBox.setText("");
                return true;
            }
        });
        contentView.searchBar.cancelButton.setOnClickListener(this);
    }

    private void getData() {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RecordManager manager = new RecordManager(this);
        records = (List<Record>) manager.findAll();
        bindData();
        pDialog.hide();
    }


    private void bindData() {
        if (temp.size() > 0) {
            temp.clear();
        }
        temp.addAll(records);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.OBJECT_KEY, temp.get(pos));
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Sort by");
        menu.add(0, v.getId(), 0, "Total Score");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Match Played");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Total Score") {
            SortHelper.sortByTotalScore(temp);
            adapter.notifyDataSetChanged();
        } else if (item.getTitle() == "Match Played") {
            SortHelper.sortByMatchesPlayed(temp);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                contentView.searchBar.searchBox.setText("");
                break;
            case R.id.sort_button:
                openContextMenu(contentView.searchBar.sortButton);
                break;


        }
    }
}

