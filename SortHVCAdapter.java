package com.hkq.sortlvhvcadapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import geeklub.org.hellovass.common_adapter.BaseRcvAdapter;
import geeklub.org.hellovass.common_adapter.BaseRecyclerViewHolder;

/**
 * Created by Administrator on 2016/3/6 0006.
 */
public class SortHVCAdapter extends BaseRcvAdapter<SortModel> {
    private static final int ITEM_VIEW_TYPE_Group = 0;
    private static final int ITEM_VIEW_TYPE_Item = 1;
    boolean isFilterMode = false;
    private List<SortModel> list = null;

    public SortHVCAdapter(Context context, List<SortModel> sortModels) {
        super(context, sortModels);
        list = sortModels;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder baseRecyclerViewHolder, SortModel sortModel, int itemViewType) {
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_Group:
                TextView tvTitle = baseRecyclerViewHolder.getView(R.id.catalog);
                tvTitle.setText(sortModel.getSortLetters());
                break;


            case ITEM_VIEW_TYPE_Item:
                int section = getSectionForPosition(sortModel);
                FrameLayout lineBottom = baseRecyclerViewHolder.getView(R.id.lineBottom);
                TextView tvTitle4Item = baseRecyclerViewHolder.getView(R.id.title);
                FrameLayout lineTop = baseRecyclerViewHolder.getView(R.id.lineTop);
                tvTitle4Item.setText(sortModel.getName());
                int num = getPositionForSection(section);
                if (isFilterMode) {
                    lineTop.setVisibility(View.GONE);
                    lineBottom.setVisibility(View.VISIBLE);
                } else {
                    lineBottom.setVisibility(View.GONE);
//                    if (position == num + 1) {
//                        Log.d("num", num + "/" + position + "gone");
//                        lineTop.setVisibility(View.GONE);
//                    } else {
//                        Log.d("num", num + "/" + position + "VISIBLE");
//                        lineTop.setVisibility(View.VISIBLE);
//                    }
                }
                break;
        }
    }

    public int getSectionForPosition(SortModel sortModel) {
        return sortModel.getSortLetters().charAt(0);
    }
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    public int getCount() {
        return this.list.size();
    }
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    protected int getItemViewTypeHV(SortModel sortModel) {
        return sortModel.isGroup() ? ITEM_VIEW_TYPE_Group : ITEM_VIEW_TYPE_Item;
    }

    @Override
    protected int getLayoutResId(int itemViewType) {
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_Group:
                return R.layout.item_group;

            case ITEM_VIEW_TYPE_Item:
                return R.layout.item;
            default:
                return R.layout.item;
        }
    }
}
