package com.assignment.pawan.soreboard.helpers;

import java.util.List;

/**
 * Created by Pawan Gupta on 11-03-2017.
 */

public interface IRecordManager {

        public int create(Object item);
        public int update(Object item);
        public int delete(Object item);
        public Object findById(int id);
        public List<?> findAll();

}
