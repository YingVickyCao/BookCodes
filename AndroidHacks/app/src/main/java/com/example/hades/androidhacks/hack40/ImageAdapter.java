package com.example.hades.androidhacks.hack40;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.hades.androidhacks.hack40.util.ImageResizer;

/**
 * Created by hades on 16/04/2018.
 */

/**
 * The activity_hack_43_main adapter that backs the GridView. This is fairly standard except
 * the number of columns in the GridView is used to create a fake top row of
 * empty views as we use a transparent ActionBar and don't want the real top
 * row of images to start off covered by it.
 */
class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private int mActionBarHeight = -1;
    private ImageResizer mImageWorker;
    private GridView.LayoutParams mImageViewLayoutParams;

    public ImageAdapter(Context context) {
        super();
        mContext = context;
        mImageViewLayoutParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setmImageWorker(ImageResizer mImageWorker) {
        this.mImageWorker = mImageWorker;
    }

    @Override
    public int getCount() {
        // Size of adapter + number of columns for top empty row
        return mImageWorker.getAdapter().getSize() + mNumColumns;
    }

    @Override
    public Object getItem(int position) {
        return position < mNumColumns ? null : mImageWorker.getAdapter().getItem(position - mNumColumns);
    }

    @Override
    public long getItemId(int position) {
        return position < mNumColumns ? 0 : position - mNumColumns;
    }

    @Override
    public int getViewTypeCount() {
        // Two types of views, the normal ImageView and the top row of empty views
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position < mNumColumns) ? 1 : 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup container) {
        // First check if this is the top row
        if (position < mNumColumns) {
            if (convertView == null) {
                convertView = new View(mContext);
            }
            // Calculate ActionBar height
            if (mActionBarHeight < 0) {
                TypedValue tv = new TypedValue();
                if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    mActionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
                } else {
                    // No ActionBar style (pre-Honeycomb or ActionBar not in theme)
                    mActionBarHeight = 0;
                }
            }
            // Set empty view with height of ActionBar
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionBarHeight));
            return convertView;
        }

        // Now handle the activity_hack_43_main ImageView thumbnails
        ImageView imageView;
        if (convertView == null) { // if it's not recycled, instantiate and
            // initialize
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(mImageViewLayoutParams);
        } else { // Otherwise re-use the converted view
            imageView = (ImageView) convertView;
        }

        // Check the height matches our calculated column width
        if (imageView.getLayoutParams().height != mItemHeight) {
            imageView.setLayoutParams(mImageViewLayoutParams);
        }

        // Finally load the image asynchronously into the ImageView, this also
        // takes care of
        // setting a placeholder image while the background thread runs
        mImageWorker.loadImage(position - mNumColumns, imageView);
        return imageView;
    }

    /**
     * Sets the item height. Useful for when we know the column width so the
     * height can be set to match.
     *
     * @param height
     */
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        mImageWorker.setImageSize(height);
        notifyDataSetChanged();
    }

    public void setColumnNum(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }
}
