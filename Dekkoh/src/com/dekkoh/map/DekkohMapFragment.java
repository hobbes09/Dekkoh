
package com.dekkoh.map;

import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.datamodel.Question;
import com.dekkoh.gpstracker.GPSTracker;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Log;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DekkohMapFragment extends BaseFragment {
    private MapView dekkohMapView;
    private GoogleMap dekkohGoogleMap;
    private HashMap<String, Integer> idsMAp = new HashMap<String, Integer>();
    private GPSTracker dekkohGpsTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dekkoh_map_fragment_layout, container, false);
        dekkohGpsTracker = new GPSTracker(activity);
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
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        actionBar.hide();
        new GetQuestionTask().execute();
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

    class GetQuestionTask extends AsyncTask<Void, Void, List<Question>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogHandler.showCustomProgressDialog(activity);
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
                    dekkohGoogleMap.addMarker(
                            new MarkerOptions()
                                    .position(
                                            new LatLng(question.getCoordinates()[1], question
                                                    .getCoordinates()[0]))
                                    .title(question.getQuestion())
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                                    // .icon(BitmapDescriptorFactory.fromResource(R.drawable.locationn))
                                    .alpha(0.7f)
                            ).showInfoWindow();
                }
                LatLng latLng = new LatLng(dekkohGpsTracker.getLatitude(),
                        dekkohGpsTracker.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                dekkohGoogleMap.animateCamera(cameraUpdate);
            }
        }
    }

}
