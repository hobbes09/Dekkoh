package com.dekkoh.homefeed;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dekkoh.R;

public class AnswerFragmentListAdapter extends BaseAdapter {
	 
    private Context mContext;

    private LayoutInflater mLayoutInflater;     
    public String sessionid="";

    //Can make custom Util class for user answer info
    private ArrayList<String> mEntries = new ArrayList<String>();   

    public AnswerFragmentListAdapter(Context context,LayoutInflater inflater) {                        
       mContext = context;
       mLayoutInflater = (LayoutInflater)inflater;
    }

    @Override
    public int getCount() {
       return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
       return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
       return position;
    }

    @Override
    public View getView(final int position, View convertView,
          ViewGroup parent) {                                           
       RelativeLayout itemView;
       if (convertView == null) { 
    	   TextView userName,answerOfUser; 
    	   ImageView userProfilePic;
         if(position%2!=0){
        	 itemView = (RelativeLayout) mLayoutInflater.inflate(
                     R.layout.answers_fragment_listview_cell_left, parent, false);
        	  userName= (TextView)itemView.findViewById(R.id.userNameInAnswerCellUserAnswerFragmentLeft);
              answerOfUser = (TextView) itemView.findViewById(R.id.answerInAnswerCellUserAnswerFragmentLeft);
              userProfilePic = (ImageView)itemView.findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentLeft);
         }else{
        	 itemView = (RelativeLayout) mLayoutInflater.inflate(
                     R.layout.answers_fragment_listview_cell_right, parent, false);
        	  userName= (TextView)itemView.findViewById(R.id.userNameInAnswerCellUserAnswerFragmentRight);
              answerOfUser = (TextView) itemView.findViewById(R.id.answerInAnswerCellUserAnswerFragmentRight);
              userProfilePic = (ImageView)itemView.findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentRight);
         }
         
       
       } else {
          itemView = (RelativeLayout) convertView;
       }

       
       return itemView;
    }

    public void upDateEntries(ArrayList<String> entries) {
       mEntries = entries;
       notifyDataSetChanged();
    }
  
    
	}

