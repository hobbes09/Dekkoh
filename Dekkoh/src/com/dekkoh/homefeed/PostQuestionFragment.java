package com.dekkoh.homefeed;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.application.DekkohApplication;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class PostQuestionFragment extends BaseFragment {

	
	private DekkohApplication dekkohApplication;
	
	private DekkohUser dekkohUser;
	
	private RoundedTransformation transformation= new RoundedTransformation(40, 0);
	
	private Context mContext;
	
	private ImageView userProfilePic;
	private ImageView userQuestionImage;
	private TextView userName;
	private TextView userLocation;
	private EditText userQuestion;
	
	
	
	//TODO: Interests Ids for the question and how to post Question Image and get it's Url from server ans place it in postQuestion method in AsyncTask.
	
	//Button to be replaced by action bar icon click for post question
	private Button postQuestion;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.post_question_fragment_layout, null);

		//Context
		mContext=getActivity().getApplicationContext();
		
		//getting Application
		dekkohApplication = (DekkohApplication)getActivity().getApplication();
		
		//getting present loggedin user info
		dekkohUser = (DekkohUser)dekkohApplication.getDekkohUser();
		
		
		//Initialization
		userProfilePic = (ImageView)root.findViewById(R.id.post_question_fragment_userProfileImage);
		userQuestionImage = (ImageView)root.findViewById(R.id.post_question_fragment_questionImage);
		userName=(TextView)root.findViewById(R.id.post_question_fragment_userName);
		userLocation = (TextView)root.findViewById(R.id.post_question_fragment_userLocation);
		userQuestion = (EditText)root.findViewById(R.id.post_question_fragment_userQuestionEditText);
		postQuestion = (Button)root.findViewById(R.id.post_question_fragment_postbutton);
		
		//Loading Image of user Profile Picture
		if(dekkohUser.getProfilePic()!=null){
			try{
				Picasso.with(mContext)
				.load(dekkohUser.getProfilePic())
				.placeholder(R.drawable.user_profile_pic_temporary_image)
				.transform(transformation)
				.into(userProfilePic);
			}catch(Exception e){
				Picasso.with(mContext)
				.load(R.drawable.user_profile_pic_temporary_image)
				.placeholder(R.drawable.user_profile_pic_temporary_image)
				.transform(transformation)
				.into(userProfilePic);
			}
			
		}else{
			Picasso.with(mContext)
			.load(R.drawable.user_profile_pic_temporary_image)
			.placeholder(R.drawable.user_profile_pic_temporary_image)
			.transform(transformation)
			.into(userProfilePic);
		}
		
		userName.setText(dekkohUser.getName());
		
		try{
			Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
			List<Address> addresses = gcd.getFromLocation(dekkohApplication.getLocationLatitude(), dekkohApplication.getLocationLongitude(), 1);
			if (addresses.size() > 0) 
			    userLocation.setText(addresses.get(0).getLocality());
		}catch(Exception e){
			
		}
		
		
		postQuestion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  if(userQuestion.getText().toString().compareTo("")!=0){
				  postQuestion.setText("Posting...");
				  new PostQuestion(PostQuestionFragment.this,userQuestion.getText().toString(),dekkohUser.getDekkohUserID(),userLocation.getText().toString(),String.valueOf(dekkohApplication.getLocationLatitude()),String.valueOf(dekkohApplication.getLocationLongitude()),dekkohUser.getProfilePic(),"5462071f6666f6f29891f0000").execute();
			  }
			}
		});
		
		
        return root;		
	}
	
	
	public void showNoNetToast(){
		Toast.makeText(getActivity().getApplicationContext(), "Unable to reach Servers. Check you net connectivity", Toast.LENGTH_LONG).show();
	}
	
	public void showSuccessToast(){
		postQuestion.setText("Success!");
		Toast.makeText(getActivity().getApplicationContext(), "Posted Successfully", Toast.LENGTH_LONG).show();
	}
	
}


class PostQuestion extends AsyncTask<Void,Void,Void>{
	PostQuestionFragment parent;
	String userId="";
	String question="";
	String location="";
	String lati="",longi="";
	String userPictureUrl="";
	String interest_id="";
	
	boolean no_net=false;
	
	public PostQuestion(PostQuestionFragment parent,String question,String userId,String location,String lati,String longi,String userPictureUrl,String interest_id){
	
	   this.parent=parent;
	   this.userId=userId;
	   this.question=question;
	   this.location=location;
	   this.lati=lati;
	   this.longi=longi;
	   this.userPictureUrl=userPictureUrl;
	   this.interest_id=interest_id;
		
	}
	@Override
	protected Void doInBackground(Void... params) {
		
		if(hasActiveInternetConnection(parent.getActivity().getApplicationContext())){
			no_net=false;
			try {
				APIProcessor.postQuestion(parent.getActivity(), question, location, longi, lati, "", interest_id, userPictureUrl, String.valueOf(System.currentTimeMillis()/1000L));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//Toast.makeText(getApplicationContext(), "Unable to Login ... Please Try Again.\n"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			
		}else{
			no_net=true;
		}
		
		
		return null;
		// TODO Auto-generated method stub
		
		
	}
	protected void onPostExecute(Void result){
		
		if(no_net==true){
			parent.showNoNetToast();
		}else{
			parent.showSuccessToast();
		}
	}
	
	
	public static boolean hasActiveInternetConnection(Context myContext) {
        if (isNetworkAvailable(myContext)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.co.in").openConnection());
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
        ConnectivityManager connectivityManager = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}






