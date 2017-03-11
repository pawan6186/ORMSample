package com.assignment.pawan.soreboard.helpers;

import com.assignment.pawan.soreboard.models.Record;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pawan Gupta on 11-03-2017.
 */

public class SortHelper {

    static class  TotalScoreComparator implements Comparator<Record> {
        @Override
        public int compare(Record lhs, Record rhs) {
            return Integer.compare(lhs.getTotalScore(),rhs.getTotalScore());
        }
    }

   static class MatchedPlayedComparator implements Comparator<Record> {
        @Override
        public int compare(Record lhs, Record rhs) {
            return Integer.compare(lhs.getMatchesPlayed(),rhs.getMatchesPlayed());
        }
    }

    public static void sortByTotalScore(List<Record> list){
        Collections.sort(list, new TotalScoreComparator());
    }

    public static void sortByMatchesPlayed(List<Record> list){
        Collections.sort(list, new MatchedPlayedComparator());
    }
}
