package com.martin.promob.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.martin.promob.R;

import java.util.ArrayList;
import java.util.List;

public class MemoryAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<MemoryCard> cards;
    private int nDiscover;


    public MemoryAdapter(Context mContext, ArrayList<MemoryCard> cards) {
        this.mContext = mContext;
        this.cards = cards;
        this.nDiscover=0;

    }


    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MemoryCard card=cards.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_memorycard, null);
        }

        ImageView imageView  = (ImageView)convertView.findViewById(R.id.imageView_MemoryCard);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        imageView.setImageResource(
                card.isDiscover() ? card.getImageUrl() : R.drawable.doscartecemory);

        return convertView;


    }
}
