
package com.dekkoh.custom.adapter;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.datamodel.DekkohUserConnection;
import com.dekkoh.util.CommonUtils;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class MyConnectionsAdapter extends ArrayAdapter<DekkohUserConnection> {
    private List<DekkohUserConnection> dekkohUserConnectionList;
    private LayoutInflater layoutInflater;
    private ImageFetcher mImageFetcher;
    private static final String IMAGE_CACHE_DIR = ".dekkoh/cache";

    public MyConnectionsAdapter(Activity activity, int textViewResourceId,
            List<DekkohUserConnection> dekkohUserConnectionList, FragmentManager fragmentManager) {
        super(activity, textViewResourceId, dekkohUserConnectionList);
        this.dekkohUserConnectionList = dekkohUserConnectionList;
        layoutInflater = activity.getLayoutInflater();
        ImageCacheParams cacheParams = new ImageCacheParams(activity,
                IMAGE_CACHE_DIR);
        // Set memory cache to 25% of app memory
        cacheParams.setMemCacheSizePercent(0.25f);
        mImageFetcher = new RemoteImageFetcher(activity, 100);
        mImageFetcher.setLoadingImage(R.drawable.loding_album);
        mImageFetcher.addImageCache(fragmentManager, cacheParams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DekkohUserConnection dekkohUserConnection = dekkohUserConnectionList.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.conections_list_item,
                    null);
        }
        TextView connectionsTextView = (TextView) convertView
                .findViewById(R.id.connectionsTextView);
        TextView lastSeenTextView = (TextView) convertView
                .findViewById(R.id.lastSeenTextView);
        TextView distanceTextView = (TextView) convertView
                .findViewById(R.id.distanceTextView);
        TextView userNameTextView = (TextView) convertView
                .findViewById(R.id.userNameTextView);
        TextView createdTextView = (TextView) convertView
                .findViewById(R.id.createdTextView);
        createdTextView.setText(CommonUtils.getDateDifference(
                dekkohUserConnection.getDate(), new Date()));
        userNameTextView.setText(dekkohUserConnection
                .getConnectionName());
        connectionsTextView.setText("0");
        distanceTextView.setText("0 km");
        lastSeenTextView.setText("0 minute");
        CircularImageView circularImageView = (CircularImageView) convertView
                .findViewById(R.id.userCircularImageView);
        if (CommonUtils.isValidURL(dekkohUserConnection.getConnectionPic())) {
            mImageFetcher.loadImage(dekkohUserConnection.getConnectionPic(), circularImageView);
        } else {
            circularImageView.setImageResource(R.drawable.ic_noprofilepic);
        }
        return convertView;
    }
}
