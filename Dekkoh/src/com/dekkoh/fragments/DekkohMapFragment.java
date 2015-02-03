
package com.dekkoh.fragments;

import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekkoh.BaseFragment;
import com.dekkoh.R;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.datamodel.Question;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.service.GPSTrackerService;
import com.dekkoh.util.CommonUtils;
import com.dekkoh.util.Log;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class DekkohMapFragment extends BaseFragment {
    private MapView dekkohMapView;
    private GoogleMap dekkohGoogleMap;
    private HashMap<String, Integer> idsMAp = new HashMap<String, Integer>();
    private GPSTrackerService dekkohGpsTracker;
    private HashMap<Marker, Question> mMarkersHashMap;
    private ImageFetcher imageFetcher;
    private static final String IMAGE_CACHE_DIR = ".gallery/cache";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dekkoh_map_fragment_layout, container, false);
        dekkohGpsTracker = new GPSTrackerService(activity);
        dekkohMapView = (MapView) rootView.findViewById(R.id.dekkohMapView);
        dekkohMapView.onCreate(savedInstanceState);
        dekkohMapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        dekkohGoogleMap = dekkohMapView.getMap();
        dekkohGoogleMap.setMyLocationEnabled(true);
        idsMAp.put("5436ad8a44656b615a0a0000", R.drawable.onedrawable);
        idsMAp.put("5436ad9644656b615a0b0000", R.drawable.twodrawablee);
        idsMAp.put("5436adb844656b615a0e0000", R.drawable.threedrawable);
        idsMAp.put("5436adce44656b615a0f0000", R.drawable.fourdrawable);
        idsMAp.put("5436ada444656b615a0d0000", R.drawable.fivedrawable);
        idsMAp.put("5436add944656b615a100000", R.drawable.sixdrawable);
        idsMAp.put("5436ade444656b615a110000", R.drawable.sevendrawable);
        idsMAp.put("5436ad9c44656b615a0c0000", R.drawable.eightdrawable);
        idsMAp.put("5436adee44656b615a120000", R.drawable.ninedrawable);
        idsMAp.put("5436adf644656b615a130000", R.drawable.tendrawable);
        dekkohGoogleMap.setOnInfoWindowClickListener(onInfoWindowClickListener);
        dekkohGoogleMap.setInfoWindowAdapter(new DekkohInfoWindowAdapter());
        dekkohGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        dekkohGoogleMap.getUiSettings().setCompassEnabled(false);
        dekkohGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        ImageCacheParams cacheParams = new ImageCacheParams(activity,
                IMAGE_CACHE_DIR);
        // Set memory cache to 25% of app memory
        cacheParams.setMemCacheSizePercent(0.25f);
        imageFetcher = new RemoteImageFetcher(activity, 100);
        imageFetcher.setLoadingImage(R.drawable.loding_album);
        imageFetcher.addImageCache(fragmentManager, cacheParams);
        new GetQuestionTask().execute();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // actionBar.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        dekkohMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dekkohMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dekkohMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dekkohMapView.onLowMemory();
    }

    private OnInfoWindowClickListener onInfoWindowClickListener = new OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker arg0) {
        }
    };

    private class DekkohInfoWindowAdapter implements InfoWindowAdapter {
        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View view = activity.getLayoutInflater().inflate(R.layout.dekkoh_map_marker_view, null);
            Question question = mMarkersHashMap.get(marker);
            CircularImageView userCircularImageView = (CircularImageView) view
                    .findViewById(R.id.userCircularImageView);
            if (CommonUtils.isValidURL(question.getUserImage())) {
                imageFetcher.loadImage(question.getUserImage(), userCircularImageView);
            } else {
                userCircularImageView.setImageResource(R.drawable.ic_noprofilepic);
            }
            TextView userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
            TextView questionTextView = (TextView) view.findViewById(R.id.questionTextView);
            userNameTextView.setText(CommonUtils.getFirstNameFromFullName(question
                    .getUserName()));
            questionTextView.setText(question.getQuestion());
            return view;
        }
    }

    class GetQuestionTask extends AsyncTask<Void, Void, List<Question>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogHandler.showCustomProgressDialog(activity);
            mMarkersHashMap = new HashMap<Marker, Question>();
        }

        @Override
        protected List<Question> doInBackground(Void... params) {
            try {
                return APIProcessor.getQuestions(activity, 0, 0, 0, 0, null, null, 0, null, null,
                        null, null, null);
            } catch (Exception e) {
                Log.e(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Question> questionList) {
            super.onPostExecute(questionList);
            progressDialogHandler.dismissCustomProgressDialog(activity);
            if (questionList != null) {
                for (Question question : questionList) {
                    Marker marker = dekkohGoogleMap.addMarker(
                            new MarkerOptions()
                                    .position(
                                            new LatLng(question.getCoordinates()[1], question
                                                    .getCoordinates()[0]))
                                    .title(question.getQuestion())
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.redlocation))
                            );
                    mMarkersHashMap.put(marker, question);
                    // dekkohGoogleMap.showInfoWindow();
                }
                LatLng latLng = new LatLng(dekkohGpsTracker.getLatitude(),
                        dekkohGpsTracker.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                dekkohGoogleMap.animateCamera(cameraUpdate);
            }
        }
    }

}
