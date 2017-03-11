package com.assignment.pawan.soreboard.responsehandlers;

import com.assignment.pawan.soreboard.models.BaseModel;

import java.util.List;



public interface BaseParser {
	public List<BaseModel> getParsedData(String data);


}
