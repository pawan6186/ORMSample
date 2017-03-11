package com.assignment.pawan.soreboard.responsehandlers;

import com.assignment.pawan.soreboard.models.BaseModel;
import com.assignment.pawan.soreboard.models.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class RecordParser implements BaseParser {

    @Override
    public List<BaseModel> getParsedData(String data) {
        List<BaseModel> words;

        Gson gson = new Gson();

        Type listType = new TypeToken<List<Record>>() {
        }.getType();
        words = new Gson().fromJson(data, listType);

        return words;
    }

}
