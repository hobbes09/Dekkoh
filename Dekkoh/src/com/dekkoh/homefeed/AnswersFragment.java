package com.dekkoh.homefeed;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.datamodel.Answer;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class AnswersFragment extends BaseFragment implements OnClickListener{

    
    ImageView userProfilePic;
    TextView userName,userLocation,userQuestion;
    private RoundedTransformation transformation = new RoundedTransformation(40, 0);
    Context mContext;
    ProgressDialog progress;
    ListView answers_listView;
    AnswerFragmentListAdapter adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.answers_fragment, null);
        
        Bundle incoming = this.getArguments();
        
        mContext = this.getActivity().getApplicationContext();
        
        adapter =  new AnswerFragmentListAdapter(mContext,inflater);
        
        // Progress Dialog
        progress = new ProgressDialog(mContext);
        progress.setMessage("Saving Your Selection...");
        progress.setIndeterminate(true); 
        progress.show();
        //
        
        
        userProfilePic = (ImageView)root.findViewById(R.id.answers_fragment_userProfileImage);
        userName = (TextView)root.findViewById(R.id.answers_fragment_userName);
        userLocation = (TextView)root.findViewById(R.id.answers_fragment_userLocation);
        userQuestion = (TextView)root.findViewById(R.id.answers_fragment_question_text_textView);
        answers_listView = (ListView)root.findViewById(R.id.answersListUserAnswerFragment);
        
        userName.setText(incoming.getString("userName"));
        userLocation.setText(incoming.getString("userLocation"));
        userQuestion.setText(incoming.getString("userQuestion"));
       
        
        new getAnswers(this,incoming.getString("questionId"),0,10,-1,-1,"").execute();
        
        String userProfilePicUrl = incoming.getString("profilePicUrl");
        if(userProfilePicUrl.compareTo("")!=0){
            try {
                Picasso.with(mContext)
                        .load(userProfilePicUrl)
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
        }else{
            Picasso.with(mContext)
            .load(R.drawable.user_profile_pic_temporary_image)
            .placeholder(R.drawable.user_profile_pic_temporary_image)
            .transform(transformation)
            .into(userProfilePic);
        }
        
        
        
        
        
        return root;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
    }

    public void showNoNetToast() {
        // TODO Auto-generated method stub
        progress.cancel();
        Toast.makeText(getActivity().getApplicationContext(),
                "Unable to reach Servers. Check you net connectivity", Toast.LENGTH_LONG).show();
    }

    public void showSuccessToast(List<Answer> result) {
        // TODO Auto-generated method stub
        progress.cancel();
         adapter.upDateEntries(result);
    }
    
}

class getAnswers extends AsyncTask<Void, Void, List<Answer>> {
    AnswersFragment parent;
    String questionId = "";
    int offSet =-1;
    int limit =-1;
    long fromTimestamp = -1, toTimestamp=-1;
    String sort ;
    List<Answer> listAnswers = new ArrayList<Answer>();

    boolean no_net = false;

    public getAnswers(AnswersFragment parent, String questionId, int offSet,
            int limit, long fromTimestamp, long toTimestamp, String sort) {

        this.parent = parent;
        this.questionId = questionId;
        this.offSet = offSet;
        this.limit = limit;
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
        this.sort = sort;

    }

    @Override
    protected List<Answer> doInBackground(Void... params) {

        if (hasActiveInternetConnection(parent.getActivity().getApplicationContext())) {
            no_net = false;
            try {
              listAnswers=  APIProcessor.getAnswersWithQuestionID((Activity)parent.getActivity(), questionId, offSet, limit, -1, -1, "");
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

    protected void onPostExecute(List<Answer> result) {

        if (no_net == true) {
            parent.showNoNetToast();
        } else {
            parent.showSuccessToast(result);
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
