
package com.dekkoh.custom.handler;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dekkoh.R;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.LocalImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class ImageFetcherProvider {
    private static final String IMAGE_CACHE_DIR = ".gallery/cache";
    private static ImageFetcher imageFetcher;

    public static ImageFetcher getRemoteImageFetcher(Fragment fragment,
            int imageThumbSize) {
        imageFetcher = new RemoteImageFetcher(fragment.getActivity(),
                imageThumbSize);
        imageFetcher.setLoadingImage(R.drawable.loding_album);
        ImageCacheParams cacheParams = new ImageCacheParams(
                fragment.getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher.addImageCache(fragment.getFragmentManager(), cacheParams);
        return imageFetcher;
    }

    public static ImageFetcher getRemoteImageFetcher(Activity activity,
            FragmentManager fragmentManager, int imageThumbSize) {
        imageFetcher = new RemoteImageFetcher(activity, imageThumbSize);
        imageFetcher.setLoadingImage(R.drawable.loding_album);
        ImageCacheParams cacheParams = new ImageCacheParams(activity,
                IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher.addImageCache(fragmentManager, cacheParams);
        return imageFetcher;
    }

    public static ImageFetcher getRemoteImageFetcher(Fragment fragment,
            int imageThumbSize, int loadingImageResId) {
        imageFetcher = new RemoteImageFetcher(fragment.getActivity(),
                imageThumbSize);
        imageFetcher.setLoadingImage(loadingImageResId);
        ImageCacheParams cacheParams = new ImageCacheParams(
                fragment.getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher.addImageCache(fragment.getFragmentManager(), cacheParams);
        return imageFetcher;
    }

    public static ImageFetcher getLocalImageFetcher(Fragment fragment,
            int imageThumbSize) {
        imageFetcher = new LocalImageFetcher(fragment.getActivity(),
                imageThumbSize);
        imageFetcher.setLoadingImage(R.drawable.loding_album);
        ImageCacheParams cacheParams = new ImageCacheParams(
                fragment.getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher.addImageCache(fragment.getFragmentManager(), cacheParams);
        return imageFetcher;
    }

    public static ImageFetcher getLocalImageFetcher(Activity activity,
            FragmentManager fragmentManager, int imageThumbSize) {
        imageFetcher = new LocalImageFetcher(activity, imageThumbSize);
        imageFetcher.setLoadingImage(R.drawable.loding_album);
        ImageCacheParams cacheParams = new ImageCacheParams(activity,
                IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher.addImageCache(fragmentManager, cacheParams);
        return imageFetcher;
    }

    public static ImageFetcher getLocalImageFetcher(Fragment fragment,
            int imageThumbSize, int loadingImageResId) {
        imageFetcher = new LocalImageFetcher(fragment.getActivity(),
                imageThumbSize);
        imageFetcher.setLoadingImage(loadingImageResId);
        ImageCacheParams cacheParams = new ImageCacheParams(
                fragment.getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher.addImageCache(fragment.getFragmentManager(), cacheParams);
        return imageFetcher;
    }
}
