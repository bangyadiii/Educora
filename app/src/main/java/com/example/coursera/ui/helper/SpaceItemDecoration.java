package com.example.coursera.ui.helper;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int horizontalSpaceHeight;
    private final int vertical;

    public SpaceItemDecoration(int horizontalSpaceHeight, int vertical) {
        this.horizontalSpaceHeight = horizontalSpaceHeight;
        this.vertical = vertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = vertical;
        outRect.bottom = vertical;


        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.right = horizontalSpaceHeight;
        }
    }
}