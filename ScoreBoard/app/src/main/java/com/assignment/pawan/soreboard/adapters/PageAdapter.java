package com.assignment.pawan.soreboard.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.pawan.soreboard.R;
import com.assignment.pawan.soreboard.databinding.PageNumberTemplateBinding;

import java.util.Collections;
import java.util.List;

/**
 * Created by RIG on 08-01-2017.
 */

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.CustomViewHolder> {
    List<Integer> pages = Collections.emptyList();
    private Context context;
    private final OnItemClickListener listener;
    private View selectedPage;

    public PageAdapter(Context context, List<Integer> pages, OnItemClickListener listener) {
        this.context = context;
        this.pages = pages;
        this.listener = listener;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PageNumberTemplateBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.page_number_template, parent, false);
        PageAdapter.CustomViewHolder holder = new PageAdapter.CustomViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.bind(pages.get(position), listener);
        holder.item.pageNumber.setText(pages.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        PageNumberTemplateBinding item;

        public CustomViewHolder(PageNumberTemplateBinding itemView) {
            super(itemView.getRoot());
            this.item = itemView;
        }

        public void bind(final int page, final OnItemClickListener listener) {
            item.pageNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(page);
                    v.setBackgroundColor(Color.LTGRAY);
                    if (selectedPage != null) {
                        selectedPage.setBackgroundColor(Color.WHITE);
                    }
                    selectedPage = v;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(final int item);
    }

}
