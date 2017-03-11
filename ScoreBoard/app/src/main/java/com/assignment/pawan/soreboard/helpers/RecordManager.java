package com.assignment.pawan.soreboard.helpers;
/**
 * Created by Pawan Gupta on 11-03-2017.
 */

import android.content.Context;

import com.assignment.pawan.soreboard.models.Record;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.List;

public class RecordManager implements IRecordManager {

    private DatabaseHelper helper;

    public RecordManager(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Record object = (Record) item;
        try {
            index = helper.getRecordDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) {
        int index = -1;
        Record object = (Record) item;
        try {
            helper.getRecordDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;
        Record object = (Record) item;
        try {
            helper.getRecordDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;

    }

    @Override
    public Object findById(int id) {
        Record record = null;
        try {
            record = helper.getRecordDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;

    }


    public List<?> findFavorites(boolean check) {
        QueryBuilder<Record, Integer> queryBuilder = helper.getRecordDao().queryBuilder();
        List<Record> records = null;
        SelectArg titleArg = new SelectArg();
        try {
            queryBuilder.where().eq("isFavorite", titleArg);
            PreparedQuery<Record> preparedQuery = queryBuilder.prepare();
            records = (List<Record>) helper.getRecordDao().query(preparedQuery);
            titleArg.setValue(check);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;

    }

    @Override
    public List<?> findAll() {
        List<Record> items = null;
        try {
            items = helper.getRecordDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

}
