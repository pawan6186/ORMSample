package com.assignment.pawan.soreboard.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.assignment.pawan.soreboard.AppController;
import com.assignment.pawan.soreboard.R;
import com.assignment.pawan.soreboard.adapters.PageAdapter;
import com.assignment.pawan.soreboard.adapters.RecordAdapter;
import com.assignment.pawan.soreboard.databinding.ActivityHomeBinding;
import com.assignment.pawan.soreboard.helpers.RecordManager;
import com.assignment.pawan.soreboard.helpers.SortHelper;
import com.assignment.pawan.soreboard.models.BaseModel;
import com.assignment.pawan.soreboard.models.Record;
import com.assignment.pawan.soreboard.responsehandlers.BaseParser;
import com.assignment.pawan.soreboard.responsehandlers.ParserFactory;
import com.assignment.pawan.soreboard.responsehandlers.RecordParser;
import com.assignment.pawan.soreboard.util.Constants;
import com.assignment.pawan.soreboard.util.URLHelper;
import com.assignment.pawan.soreboard.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity implements RecordAdapter.OnItemClickListener, View.OnClickListener {


    private RecordAdapter adapter;
    private List<Record> records;
    private BaseParser parser;
    private Context context;
    private ActivityHomeBinding contentView;
    private List<Record> temp;
    private int currentPage = 1;
    private int pageSize = 3;
    private int totalPages = 0;
    private RecyclerView.LayoutManager lm;
    private Map<String, Double> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = DataBindingUtil.setContentView(this, R.layout.activity_home);
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
        setPageTitle(contentView.appBar.title, R.string.app_name);
        lm = new LinearLayoutManager(context);
        adapter = new RecordAdapter(context, temp, this, Constants.HOME);
        contentView.recordList.setAdapter(adapter);
        contentView.recordList.setLayoutManager(lm);
        registerForContextMenu(contentView.searchBar.sortButton);
        contentView.goToFavorite.setOnClickListener(this);
        contentView.searchBar.sortButton.setOnClickListener(this);
        contentView.pagingButtons.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
        parser = ParserFactory.getParser(RecordParser.class);
        if (!Util.isOnline(context)) {
            Util.showMessesBox(context, R.string.ok, R.string.no_internet_connection, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            return;
        }
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String tag_json_obj = "json_obj_req";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URLHelper.getAPIEndpointURL(URLHelper.RECORDS),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setDataToList(response);
                        pDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    private void setDataToList(JSONObject data) {
        if (data.has("records"))
            try {
                JSONArray jsonArray = data.getJSONArray("records");
                List<? extends BaseModel> list = parser.getParsedData(jsonArray.toString());
                records = (List<Record>) list;
                for (Record r : records) {
                    RecordManager manager = new RecordManager(this);
                    Object obj = manager.findById(r.getId());
                    if (obj != null) {
                        r.setFavorite(true);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        putRelevantDataToList();


    }

    public void setPages(List<Record> list) {
        totalPages = list.size() / pageSize;
        if ((list.size() % pageSize) > 0) {
            totalPages += 1;
        }
        final ArrayList pages = new ArrayList();
        for (int i = 1; i <= totalPages; i++) {
            pages.add(i);
        }
        contentView.pagingButtons.setAdapter(new PageAdapter(context, pages, new PageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                currentPage = item;
                int startIndex = (currentPage - 1) * pageSize;
                lm.scrollToPosition(startIndex);
            }
        }));
    }

    private void putRelevantDataToList() {
        if (temp.size() > 0) {
            temp.clear();
        }
        temp.addAll(records);
        adapter.notifyDataSetChanged();
        setPages(temp);
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
            case R.id.go_to_favorite:
                goToFavoritePage();
                break;
            case R.id.cancel_button:
                contentView.searchBar.searchBox.setText("");
                break;
            case R.id.sort_button:
                openContextMenu(contentView.searchBar.sortButton);
                break;


        }
    }

    private void goToFavoritePage() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);

    }
}

