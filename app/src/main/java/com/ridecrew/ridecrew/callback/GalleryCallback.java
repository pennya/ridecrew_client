package com.ridecrew.ridecrew.callback;

import Entity.Gallery;

/**
 * Created by KIM on 2018-03-30.
 */

public interface GalleryCallback {
    void like(Gallery gallery);
    void share(Gallery gallery);
}
