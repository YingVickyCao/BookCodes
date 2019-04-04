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

package com.example.hades.androidhacks.hack40.provider;

import com.example.hades.androidhacks.hack40.util.ImageWorker;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

    /**
     * This are PicasaWeb URLs and could potentially change. Ideally the PicasaWeb
     * API should be used to fetch the URLs.
     */
    static final String BASE = "http://i.imgur.com/";
    static final String EXT = ".jpg";

    public final static String[] imageUrls = new String[]{
            BASE + "CqmBjo5" + EXT,
            BASE + "zkaAooq" + EXT,
            BASE + "0gqnEaY" + EXT,
            BASE + "9gbQ7YR" + EXT,
            BASE + "aFhEEby" + EXT,
            BASE + "0E2tgV7" + EXT,
            BASE + "P5JLfjk" + EXT,
            BASE + "nz67a4F" + EXT,
            BASE + "dFH34N5" + EXT,
            BASE + "FI49ftb" + EXT,
            BASE + "DvpvklR" + EXT
    };

    public final static String[] imageThumbUrls = new String[]{
            BASE + "DNKnbG8" + EXT,
            BASE + "yAdbrLp" + EXT,
            BASE + "55w5Km7" + EXT,
            BASE + "NIwNTMR" + EXT,
            BASE + "DAl0KB8" + EXT,
            BASE + "xZLIYFV" + EXT,
            BASE + "HvTyeh3" + EXT,
            BASE + "Ig9oHCM" + EXT,
            BASE + "7GUv9qa" + EXT,
            BASE + "i5vXmXp" + EXT,
    };

    private final static String[] moreUrls = {
            BASE + "glyvuXg" + EXT,
            BASE + "u6JF6JZ" + EXT,
            BASE + "ExwR7ap" + EXT,
            BASE + "Q54zMKT" + EXT,
            BASE + "9t6hLbm" + EXT,
            BASE + "F8n3Ic6" + EXT,
            BASE + "P5ZRSvT" + EXT,
            BASE + "jbemFzr" + EXT,
            BASE + "8B7haIK" + EXT,
            BASE + "aSeTYQr" + EXT,
            BASE + "OKvWoTh" + EXT,
            BASE + "zD3gT4Z" + EXT,
            BASE + "z77CaIt" + EXT
    };

    static final String[] URLS = {
            "http://i.imgur.com/rFLNqWI.jpg",
            "http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };


    /**
     * Simple static adapter to use for images.
     */
    public final static ImageWorker.ImageWorkerAdapter imageWorkerUrlsAdapter = new ImageWorker.ImageWorkerAdapter() {
        @Override
        public Object getItem(int num) {
            return Images.imageUrls[num];
        }

        @Override
        public int getSize() {
            return Images.imageUrls.length;
        }
    };

    /**
     * Simple static adapter to use for image thumbnails.
     */
    public final static ImageWorker.ImageWorkerAdapter imageThumbWorkerUrlsAdapter = new ImageWorker.ImageWorkerAdapter() {
        @Override
        public Object getItem(int num) {
            return Images.imageThumbUrls[num];
        }

        @Override
        public int getSize() {
            return Images.imageThumbUrls.length;
        }
    };

    /**
     * Another simple static adapter to use for image thumbnails, with a different
     * set of images.
     */
    public final static ImageWorker.ImageWorkerAdapter otherUrlAdapter = new ImageWorker.ImageWorkerAdapter() {
        @Override
        public Object getItem(int num) {
            return Images.moreUrls[num];
        }

        @Override
        public int getSize() {
            return Images.moreUrls.length;
        }
    };
}
