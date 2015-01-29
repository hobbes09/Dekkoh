
package com.dekkoh.homefeed;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.application.DekkohApplication;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.gpstracker.GPSTracker;
import com.dekkoh.map.DekkohMapFragment;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Log;
import com.dekkoh.util.RoundedTransformation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class PostQuestionFragment extends BaseFragment implements OnClickListener {

    private DekkohApplication dekkohApplication;

    private DekkohUser dekkohUser;

    private RoundedTransformation transformation = new RoundedTransformation(40, 0);

    private Context mContext;

    private ImageView userProfilePic;
    private ImageView userQuestionImage;
    private TextView userName;
    private TextView userLocation;
    private EditText userQuestion;
    private RelativeLayout userNameAndLocationHolder;
    private ImageView editAddress;
    private LinearLayout editTextForAddressHolder;
    private ImageView tickForAddressChanged;
    private EditText editTextForUserAddress;
    
    private boolean paused=false;
    
    GoogleMap googleMap;
    
    LocationManager locationManager;
    
    View googleMapFragmentHolder;
    
    private int RESULT_LOAD_IMAGE = 100;
    
    GPSTracker gpsTracker;

    // TODO: Interests Ids for the question and how to post Question Image and get it's Url from
    // server ans place it in postQuestion method in AsyncTask.

   
    private ImageView postQuestion;
   private ImageView selectImage;

    private Double latitude,longitude;
    private String changedAddressByUser="";
    private boolean addressChanged=false;
    
    public PostQuestionFragment(){
        
    }
    
    public PostQuestionFragment(Double latitude,Double longitude,boolean addressChanged,String changedAddress){
        this.latitude=latitude;
        this.longitude=longitude;
        this.addressChanged=addressChanged;
        this.changedAddressByUser=changedAddress;
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.post_question_fragment_layout, null);

        // Context
        mContext = getActivity().getApplicationContext();
        
        //customize action bar
        customizeActionBar();

        // getting Application
        dekkohApplication = (DekkohApplication) getActivity().getApplication();

        // getting present loggedin user info
        dekkohUser = (DekkohUser) dekkohApplication.getDekkohUser();

        // Initialization
        userProfilePic = (ImageView) root
                .findViewById(R.id.post_question_fragment_userProfileImage);
        userQuestionImage = (ImageView) root
                .findViewById(R.id.post_question_fragment_questionImage);
        userName = (TextView) root.findViewById(R.id.post_question_fragment_userName);
        userLocation = (TextView) root.findViewById(R.id.post_question_fragment_userLocation);
        userQuestion = (EditText) root
                .findViewById(R.id.post_question_fragment_userQuestionEditText);
        googleMapFragmentHolder = (View)root.findViewById(R.id.post_question_fragment_layout_map);
        userNameAndLocationHolder = (RelativeLayout)root.findViewById(R.id.userNameAndLocationHolderPostQuestion);
        editAddress = (ImageView)root.findViewById(R.id.post_question_fragment_userEditIcon);
        editTextForAddressHolder = (LinearLayout)root.findViewById(R.id.userLocationEditTextHolderPostQuestion);
        tickForAddressChanged = (ImageView)root.findViewById(R.id.continueAfterUserAddressChangePostQuetion);
        editTextForUserAddress = (EditText) root.findViewById(R.id.editTextForUserAddressEditPostQuestion);
        
        editAddress.setOnClickListener(this);
        tickForAddressChanged.setOnClickListener(this);
        
        // Loading Image of user Profile Picture
        if (dekkohUser.getProfilePic() != null) {
            try {
                Picasso.with(mContext)
                        .load(dekkohUser.getProfilePic())
                        .placeholder(R.drawable.user_profile_pic_temporary_image)
                        .transform(transformation)
                        .into(userProfilePic);
            } catch (Exception e) {
                Picasso.with(mContext)
                        .load(R.drawable.user_profile_pic_temporary_image)
                        .placeholder(R.drawable.user_profile_pic_temporary_image)
                        .transform(transformation)
                        .into(userProfilePic);
            }

        } else {
            Picasso.with(mContext)
                    .load(R.drawable.user_profile_pic_temporary_image)
                    .placeholder(R.drawable.user_profile_pic_temporary_image)
                    .transform(transformation)
                    .into(userProfilePic);
        }

        userName.setText(dekkohUser.getName().split(" ")[0]);

        try {
             
             locationManager = (LocationManager) activity
                     .getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);                                    // 1*60*1000 - 1min
            
             
             gpsAndMapUpdate();
            
           
        } catch (Exception e) {

            Toast.makeText(mContext, "Unable to get Location:"+e.toString(), Toast.LENGTH_LONG).show();
            
        }

        return root;
    }
    
    private void customizeActionBar() {
     //   getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getActivity().getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#00000000")));
        ActionBar actionBar = getActivity().getActionBar();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View mCustomView = layoutInflater.inflate(R.layout.action_bar_post_question_fragment,
                null);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        
        selectImage = (ImageView)mCustomView.findViewById(R.id.selectImage_postQuestionFragment_actionbar);
        postQuestion = (ImageView) mCustomView.findViewById(R.id.postQuestion_postQuestionFragment_actionbar);
        
        selectImage.setOnClickListener(this);
        postQuestion.setOnClickListener(this);
    }
    
    
    
    public void gpsAndMapUpdate(){
       try{
           if(addressChanged==false){
           gpsTracker = new GPSTracker(activity,
                   dekkohApplication, 5, 1 * 60 * 1000);// Meters : 5 ; Time :
           if (gpsTracker.canGetLocation() && locationManager
                   .isProviderEnabled(LocationManager.GPS_PROVIDER)==true) {
               
               
               dekkohApplication.updateLocationOfUser(gpsTracker.getLocation());
               // Acquire a reference to the system Location Manager
               Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
               List<Address> addressList = geocoder.getFromLocation(
                       gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
               if (addressList != null && addressList.size() > 0) {
                   Address address = addressList.get(0);
                   
                   try{
                       StringBuilder sb1 = new StringBuilder();
                       for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                           if(i!=0){
                               sb1.append(",").append(address.getAddressLine(i));
                           }else{
                               sb1.append(address.getAddressLine(i));
                           }
                       }
                       StringBuilder sb2 = new StringBuilder();
                       String sub_admin_area = address.getSubAdminArea();
                       if(sub_admin_area!=null && sub_admin_area.compareTo("")!=0){
                           sb2.append(sub_admin_area);
                       }else{
                            sub_admin_area = address.getSubLocality();
                           if(sub_admin_area!=null && sub_admin_area.compareTo("")!=0){
                               sb2.append(sub_admin_area);
                           }   
                       }
                       
                       
                       String Locality=address.getLocality();
                       if(Locality!=null && Locality.compareTo("")!=0){
                           if(sb2.length()>0){
                               sb2.append(","+Locality);
                           }else{
                               sb2.append(Locality);
                           }
                       }
                       
                      
                       if(sb2.toString()!=null && sb2.toString().compareTo("")!=0){

                           int flag=0;
                           while(sb2.charAt(0)==',' && flag<4){
                               sb2.deleteCharAt(0);
                               flag++;
                           }
                           userLocation.setText(sb2.toString());
                           //Toast.makeText(mContext, sb2.toString(), Toast.LENGTH_LONG).show();   
                       }else{

                           
                           userLocation.setText(sb1.toString().replaceAll("[^A-Za-z]",""));
                           //Toast.makeText(mContext, sb1.toString(), Toast.LENGTH_LONG).show();
                       }
                   }catch(Exception e){
                       Toast.makeText(mContext, "Unable to get Location:"+e.toString(), Toast.LENGTH_LONG).show();
                   }
               }
              
               
               
               //Google Map Load
               

               try {
                   // Loading map
                if (googleMap == null) {
                       googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(
                               R.id.post_question_fragment_layout_map)).getMap();
            
                }
                       // check if map is created successfully or not
                       if (googleMap == null) {
                           Toast.makeText(getActivity().getApplicationContext(),
                                   "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                                   .show();
                       }else{
                           googleMap.clear();
                           
                         final LatLng latlng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                      Marker self_marker = googleMap.addMarker(new MarkerOptions()
                          .position(latlng)
                           .title(userLocation.getText().toString())
                           .icon(BitmapDescriptorFactory
                               .fromResource(R.drawable.redlocation_marker)));
                         self_marker.showInfoWindow();
                         googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                        googleMap.getUiSettings().setZoomControlsEnabled(false);
                       //  googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                         googleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
                       }
                   
        
               } catch (Exception e) {

                   Toast.makeText(mContext, "Unable to load Map Location", Toast.LENGTH_SHORT).show();
               }
               
               
               //End Google Map Load
              

           } else {
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

               // Setting Dialog Title
               alertDialog.setTitle("GPS - settings");

               // Setting Dialog Message
               alertDialog
                       .setMessage("GPS is not enabled. Do you want to go to settings menu?");

               // On pressing Settings button
               alertDialog.setPositiveButton("Settings",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                               Intent intent = new Intent(
                                       Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               mContext.startActivity(intent);
                              
                           }
                       });

              

               // Showing Alert Message
               alertDialog.show();
           }
           }else if(addressChanged==true){
               //Google Map Load
               

               try {
                   // Loading map
                   userLocation.setText(changedAddressByUser);
                if (googleMap == null) {
                       googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(
                               R.id.post_question_fragment_layout_map)).getMap();
                }
                       // check if map is created successfully or not
                       if (googleMap == null) {
                           Toast.makeText(getActivity().getApplicationContext(),
                                   "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                                   .show();
                       }else{
                         
                           googleMap.clear();
                         final LatLng latlng = new LatLng(latitude, longitude);
                      Marker self_marker = googleMap.addMarker(new MarkerOptions()
                          .position(latlng)
                           .title(userLocation.getText().toString())
                           .icon(BitmapDescriptorFactory
                               .fromResource(R.drawable.redlocation_marker)));
                         self_marker.showInfoWindow();
                         googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                      // googleMap.getUiSettings().setZoomControlsEnabled(false);
                       //  googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                         googleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
                       }
                   
        
               } catch (Exception e) {

                   Toast.makeText(mContext, "Unable to load Map Location", Toast.LENGTH_SHORT).show();
               }

           }
       }catch(Exception e){
           
       }
    }

    public void showNoNetToast() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Unable to reach Servers. Check you net connectivity", Toast.LENGTH_LONG).show();
    }

    public void showSuccessToast() {
        Toast.makeText(getActivity().getApplicationContext(), "Posted Successfully",
                Toast.LENGTH_LONG).show();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       if (data != null && requestCode==RESULT_LOAD_IMAGE) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = mContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            userQuestionImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            userQuestionImage.setVisibility(View.VISIBLE);
            googleMapFragmentHolder.setVisibility(View.GONE);
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
        }

}
    
    @Override
    public void onResume(){
      //  Toast.makeText(mContext, "Resumed", Toast.LENGTH_LONG).show();
        if(paused==true){
            paused=false;
            gpsAndMapUpdate();
        }
        Log.e("Post Question Fragment", "Resumed");
        super.onResume();
    }
    
    @Override
    public void onPause(){
        Log.e("Post Question Fragment", "Paused");
       // Toast.makeText(mContext, "Paused", Toast.LENGTH_LONG).show();
        paused=true;
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.postQuestion_postQuestionFragment_actionbar:
                Bitmap userQuestionImageToPostToDekkohServer=null;
                if(userQuestionImage.isShown()){
                    userQuestionImage.buildDrawingCache();
                     userQuestionImageToPostToDekkohServer = userQuestionImage.getDrawingCache();
                }else if(googleMapFragmentHolder.isShown()){
                    googleMapFragmentHolder.buildDrawingCache();
                    userQuestionImageToPostToDekkohServer = googleMapFragmentHolder.getDrawingCache();
                }
                
                
                if (userQuestion.getText().toString().compareTo("") != 0) {
                    new PostQuestion(PostQuestionFragment.this, userQuestion.getText().toString(),
                            dekkohUser.getDekkohUserID(), userLocation.getText().toString(), String
                                    .valueOf(dekkohApplication.getLocationLatitude()), String
                                    .valueOf(dekkohApplication.getLocationLongitude()), dekkohUser
                                    .getProfilePic(), "5462071f6666f6f29891f0000").execute();
                }
                break;
            case R.id.selectImage_postQuestionFragment_actionbar:
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.post_question_fragment_userEditIcon:
                userNameAndLocationHolder.setVisibility(View.GONE);
                editTextForAddressHolder.setVisibility(View.VISIBLE);
                break;
            case R.id.continueAfterUserAddressChangePostQuetion:
                if(editTextForUserAddress.getText().toString().replaceAll("\\s+", "").compareTo("")==0){
                    userNameAndLocationHolder.setVisibility(View.VISIBLE);
                    editTextForAddressHolder.setVisibility(View.GONE);
                }else{
                    userAddressChangedUpdateUi();
                }
                break;
        }

    }
    
    public void userAddressChangedUpdateUi(){
        userNameAndLocationHolder.setVisibility(View.VISIBLE);
        editTextForAddressHolder.setVisibility(View.GONE);
        Geocoder coder = new Geocoder(this.getActivity());
        List<Address> address;
      

        try {
            address = coder.getFromLocationName(editTextForUserAddress.getText().toString(),5);
            if (address == null) {
               
            }
            Address location = address.get(0);
            if(location!=null ){
                location.getLatitude();
                location.getLongitude();
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                addressChanged=true;
                changedAddressByUser = editTextForUserAddress.getText().toString();
                gpsAndMapUpdate();
                userLocation.setText(changedAddressByUser);
            }
        }catch(Exception e){
            
        }
    }


}

class PostQuestion extends AsyncTask<Void, Void, Void> {
    PostQuestionFragment parent;
    String userId = "";
    String question = "";
    String location = "";
    String lati = "", longi = "";
    String userPictureUrl = "";
    String interest_id = "";

    boolean no_net = false;

    public PostQuestion(PostQuestionFragment parent, String question, String userId,
            String location, String lati, String longi, String userPictureUrl, String interest_id) {

        this.parent = parent;
        this.userId = userId;
        this.question = question;
        this.location = location;
        this.lati = lati;
        this.longi = longi;
        this.userPictureUrl = userPictureUrl;
        this.interest_id = interest_id;

    }

    @Override
    protected Void doInBackground(Void... params) {

        if (hasActiveInternetConnection(parent.getActivity().getApplicationContext())) {
            no_net = false;
            try {
                APIProcessor.postQuestion(parent.getActivity(), question, location, longi, lati,
                        "", interest_id, userPictureUrl,
                        String.valueOf(System.currentTimeMillis() / 1000L));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                // Toast.makeText(getApplicationContext(),
                // "Unable to Login ... Please Try Again.\n"+e.getLocalizedMessage(),
                // Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        } else {
            no_net = true;
        }

        return null;
        // TODO Auto-generated method stub

    }

    protected void onPostExecute(Void result) {

        if (no_net == true) {
            parent.showNoNetToast();
        } else {
            parent.showSuccessToast();
        }
    }

    public static boolean hasActiveInternetConnection(Context myContext) {
        if (isNetworkAvailable(myContext)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.co.in")
                        .openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
            }
        } else {
        }
        return false;
    }

    private static boolean isNetworkAvailable(Context myContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) myContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
