
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

public class PostAnswerFragment extends BaseFragment {

    private DekkohApplication dekkohApplication;

    private DekkohUser dekkohUser;

    private RoundedTransformation transformation = new RoundedTransformation(40, 0);

    private Context mContext;

    private ImageView userProfilePic, questionAskerProfilePic;
    private ImageView userAnswerImage;
    private TextView userName, questionAskerName;
    private TextView userLocation, questionAskerLocation;
    private TextView questionAskerQuestion;
    private EditText userAnswer;

    // Data that should be grought here via bundle
    private String questionAskerUserName, questionAskerUserProfilePicUrl,
            questionAskerUserQuestion, questionAskerUserLocation, questionId;

    // Button to be replaced by action bar icon click for post question
    private Button postAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.post_answer_fragment_layout, null);

        // Get Data From Previous Screen
        Bundle incoming = this.getArguments();
        questionAskerUserName = incoming.getString("name");
        questionAskerUserProfilePicUrl = incoming.getString("profileUrl");
        questionAskerUserLocation = incoming.getString("userLocation");
        questionAskerUserQuestion = incoming.getString("userQuestion");
        questionId = incoming.getString("questionId");

        // Context
        mContext = getActivity().getApplicationContext();

        // getting Application
        dekkohApplication = (DekkohApplication) getActivity().getApplication();

        // getting present loggedin user info
        dekkohUser = (DekkohUser) dekkohApplication.getDekkohUser();

        // Initialization
        userProfilePic = (ImageView) root
                .findViewById(R.id.post_answer_fragment_answering_userProfileImage);
        userAnswerImage = (ImageView) root
                .findViewById(R.id.post_answer_fragment_answering_user_layout);
        userName = (TextView) root.findViewById(R.id.post_answer_fragment_answering_userName);
        userLocation = (TextView) root
                .findViewById(R.id.post_answer_fragment_answering_userLocation);
        userAnswer = (EditText) root
                .findViewById(R.id.post_answer_fragment_edittext_for_user_answer);
        postAnswer = (Button) root.findViewById(R.id.post_answer_fragment_postbutton);
        questionAskerProfilePic = (ImageView) root
                .findViewById(R.id.post_answer_fragment_questioned_user_ProfileImage);
        questionAskerName = (TextView) root
                .findViewById(R.id.post_answer_fragment_questioned_user_Name);
        questionAskerLocation = (TextView) root
                .findViewById(R.id.post_answer_fragment_questioned_user_Location);
        questionAskerQuestion = (TextView) root.findViewById(R.id.post_answer_fragment_question);

        // Set Question Asker's textviews and stuff
        questionAskerName.setText(questionAskerUserName);
        questionAskerLocation.setText(questionAskerUserLocation);
        questionAskerQuestion.setText(questionAskerUserQuestion);
        if (questionAskerUserProfilePicUrl != null) {
            try {
                Picasso.with(mContext)
                        .load(questionAskerUserProfilePicUrl)
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

        userName.setText(dekkohUser.getName());

        try {
            Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(dekkohApplication.getLocationLatitude(),
                    dekkohApplication.getLocationLongitude(), 1);
            if (addresses.size() > 0)
                userLocation.setText(addresses.get(0).getLocality());
        } catch (Exception e) {

        }

        postAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (userAnswer.getText().toString().compareTo("") != 0) {
                    postAnswer.setText("Posting...");
                    new PostAnswer(PostAnswerFragment.this, userAnswer.getText().toString(),
                            questionId, userLocation.getText().toString(), String
                                    .valueOf(dekkohApplication.getLocationLatitude()), String
                                    .valueOf(dekkohApplication.getLocationLongitude()), dekkohUser
                                    .getProfilePic(), "send answer pic url here").execute();
                }
            }
        });

        return root;
    }

    public void showNoNetToast() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Unable to reach Servers. Check you net connectivity", Toast.LENGTH_LONG).show();
    }

    public void showSuccessToast() {
        postAnswer.setText("Success!");
        Toast.makeText(getActivity().getApplicationContext(), "Posted Successfully",
                Toast.LENGTH_LONG).show();
    }

}

class PostAnswer extends AsyncTask<Void, Void, Void> {
    PostAnswerFragment parent;
    String questionId = "";
    String answer = "";
    String location = "";
    String lati = "", longi = "";
    String userPictureUrl = "";
    String answerPicUrl = "";

    boolean no_net = false;

    public PostAnswer(PostAnswerFragment parent, String answer, String questionId, String location,
            String lati, String longi, String userPictureUrl, String answerPicUrl) {

        this.parent = parent;
        this.questionId = questionId;
        this.answer = answer;
        this.location = location;
        this.lati = lati;
        this.longi = longi;
        this.userPictureUrl = userPictureUrl;
        this.answerPicUrl = answerPicUrl;

    }

    @Override
    protected Void doInBackground(Void... params) {

        if (hasActiveInternetConnection(parent.getActivity().getApplicationContext())) {
            no_net = false;
            try {
                APIProcessor.postAnswer(parent.getActivity(), answer, questionId, answerPicUrl,
                        location, String.valueOf(System.currentTimeMillis() / 1000L));
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
