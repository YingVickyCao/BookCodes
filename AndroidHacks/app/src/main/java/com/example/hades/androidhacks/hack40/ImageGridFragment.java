/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hades.androidhacks.hack40;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hades.androidhacks.BuildConfig;
import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack40.provider.Images;
import com.example.hades.androidhacks.hack40.util.ImageCache;
import com.example.hades.androidhacks.hack40.util.ImageFetcher;
import com.example.hades.androidhacks.hack40.util.ImageResizer;

/**
 * The activity_hack_43_main fragment that powers the ImageGridMainActivity screen. Fairly straight
 * forward GridView implementation with the key addition being the ImageWorker
 * class w/ImageCache to load children asynchronously, keeping the UI nice and
 * smooth and caching thumbnails for quick retrieval. The cache is retained over
 * configuration changes like orientation change so the images are populated
 * quickly as the user rotates the device.
 */
public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = ImageGridFragment.class.getSimpleName();

    private static final String IMAGE_CACHE_DIR = "thumbs";

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageAdapter mAdapter;
    private ImageResizer mImageWorker;

    /**
     * Empty constructor as per the Fragment documentation
     */
    public ImageGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        // The ImageWorker takes care of loading images into our ImageView children asynchronously
        setImageFetcher();
        setImageAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_hack_40_image_grid_fragment, container, false);

        final GridView gridView = v.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        setGridViewSize(gridView);
        return v;
    }

    private void setGridViewSize(final GridView gridView) {
        // https://blog.csdn.net/linghu_java/article/details/46544811
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mAdapter.getNumColumns() == 0) {
                            final int columnNum = getGridViewItemColumnNum(gridView.getWidth());
                            if (columnNum > 0) {
                                final int columnWidth = getGridViewItemColumnWidth(gridView.getWidth(), columnNum);
                                mAdapter.setColumnNum(columnNum);
                                // stretchMode=columnWidth => item height = item width.
                                mAdapter.setItemHeight(columnWidth);
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, "onCreateView - numColumns set to " + columnNum);
                                }
                            }
                        }
                    }
                });
    }

    private int getGridViewItemColumnNum(int width) {
        return (int) Math.floor(width / (mImageThumbSize + mImageThumbSpacing));
    }

    private int getGridViewItemColumnWidth(int width, int columnNum) {
        return (width / columnNum) - mImageThumbSpacing;
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageWorker.setExitTasksEarly(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImageWorker.setExitTasksEarly(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
        i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_cache:
                clearCaches();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearCaches() {
        final ImageCache cache = mImageWorker.getImageCache();
        if (cache != null) {
            mImageWorker.getImageCache().clearCaches();
            // DiskLruCache.clearCache(getActivity(), ImageFetcher.HTTP_CACHE_DIR);
            Toast.makeText(getActivity(), R.string.clear_cache_complete, Toast.LENGTH_SHORT).show();
        }
    }

    private void setImageFetcher() {
        mImageWorker = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageWorker.setAdapter(Images.imageThumbWorkerUrlsAdapter);
        mImageWorker.setLoadingImage(R.drawable.empty_photo);
        mImageWorker.setImageCache(ImageCache.getImageCacheStoredInRetainFragment(getActivity(), getImageCacheParams()));
    }

    private void setImageAdapter() {
        mAdapter = new ImageAdapter(getActivity());
        mAdapter.setmImageWorker(mImageWorker);
    }

    /**
     * http://developer.android.com/training/displaying-bitmaps/
     */
    private ImageCache.ImageCacheParams getImageCacheParams() {
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(IMAGE_CACHE_DIR);
        // TODO: 16/04/2018
//        cacheParams.memCacheSize = 1024 * 1024 * Utils.getDeviceMemory(getActivity()) / 3;
        cacheParams.memCacheSize = 1024 * 1024 * 500;
        return cacheParams;
    }
}
