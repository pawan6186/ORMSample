package com.assignment.pawan.soreboard.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.assignment.pawan.soreboard.AppController;
import com.assignment.pawan.soreboard.R;
import com.assignment.pawan.soreboard.databinding.RowBinding;
import com.assignment.pawan.soreboard.helpers.RecordManager;
import com.assignment.pawan.soreboard.models.Record;
import com.assignment.pawan.soreboard.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by pawan.b.gupta on 21/10/15.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.CustomViewHolder> implements Filterable {
    List<Record> records = Collections.emptyList();
    List<Record> originalRecords;
    int type;

    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    private final RecordAdapter.OnItemClickListener listener;

    public RecordAdapter(Context context, List<Record> records, RecordAdapter.OnItemClickListener listener, int type) {
        this.context = context;
        this.records = records;
        this.originalRecords = records;
        this.type = type;
        this.listener = listener;
    }

    public void delete(int position) {
        records.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Record record = records.get(position);
        holder.item.cricketerName.setText(record.getName());
        holder.item.country.setText(record.getCountry());
        holder.item.matches.setText(Integer.toString(record.getMatchesPlayed()));
        holder.item.runs.setText(Integer.toString(record.getTotalScore()));
        holder.bind(position, listener);
        holder.item.favIcon.setChecked(record.isFavorite());
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.item.icon.setImageUrl(record.getImage(), imageLoader);
        holder.item.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ToggleButton) {
                    ToggleButton toggleButton = (ToggleButton) v;
                    if (toggleButton.isChecked()) {
                        Record r = records.get(position);
                        r.setFavorite(true);
                        RecordManager manager = new RecordManager(context);
                        manager.create(r);
                    } else {
                        Record r = records.get(position);
                        r.setFavorite(true);
                        RecordManager manager = new RecordManager(context);
                        manager.delete(r);
                        if (type == Constants.FAVORITE) {
                            records.remove(r);
                            notifyDataSetChanged();
                        }

                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        RowBinding item;


        public CustomViewHolder(RowBinding itemView) {
            super(itemView.getRoot());
            this.item = itemView;

        }

        public void bind(final int pos, final RecordAdapter.OnItemClickListener listener) {
            item.upperSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pos);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(final int pos);
    }

    class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Record> list = originalRecords;
            int count = list.size();
            final ArrayList<Record> nlist = new ArrayList<Record>(count);
            Record filterableRecord;
            for (int i = 0; i < count; i++) {
                filterableRecord = list.get(i);
                if (filterableRecord.getName().toLowerCase().contains(filterString)) {
                    nlist.add(filterableRecord);
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            records = (ArrayList<Record>) results.values;
            notifyDataSetChanged();
        }
    }
}
