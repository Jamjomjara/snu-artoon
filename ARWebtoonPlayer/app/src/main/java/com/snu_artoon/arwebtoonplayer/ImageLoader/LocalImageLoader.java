// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

package com.snu_artoon.arwebtoonplayer.ImageLoader;

import android.net.Uri;
import android.widget.ImageView;

public class LocalImageLoader {
    /**
     * Load the image indicated by resID into destination ImageView.
     * @param destination imageView to be set
     * @param resID resource id of the image to be set. If null, no image is set.
     */
    public static void load(ImageView destination, Integer resID) {
        if (resID != null) {
            destination.setImageResource(resID);
        }
    }

    /**
     * Load the image indicated by the fileName into destination ImageView.
     * @param destination imageView to be set
     * @param fileName fileName of the image to be set. If null, no image is set.
     */
    public static void load(ImageView destination, String fileName) {
        if (fileName != null) {
            Uri uri = Uri.parse("android.resource://com.snu_artoon.arwebtoonplayer/drawable/"
                    + fileName);
            destination.setImageURI(uri);
        }
    }
}
