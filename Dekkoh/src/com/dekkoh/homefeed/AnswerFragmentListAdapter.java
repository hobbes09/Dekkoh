
package com.dekkoh.homefeed;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.datamodel.Answer;
import com.dekkoh.util.Log;
import com.dekkoh.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class AnswerFragmentListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;
    public String sessionid = "";
    private RoundedTransformation transformation = new RoundedTransformation(20, 0);

    // Can make custom Util class for user answer info
    private List<Answer> mEntries = new ArrayList<Answer>();

    public AnswerFragmentListAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) inflater;
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
            TextView userName,userLocation, answerOfUser;
            ImageView userProfilePic,answerImage;
            Answer presentAnswer=mEntries.get(position);
         if(convertView==null){
             /*   if (position % 2 != 0) {
             
             if(presentAnswer.getImage()==null || presentAnswer.getImage().compareTo("")==0){
                 itemView = (RelativeLayout) mLayoutInflater.inflate(
                         R.layout.answers_fragment_listview_cell_left_withoutimage, parent, false);
                 userName = (TextView) itemView
                         .findViewById(R.id.userNameInAnswerCellUserAnswerFragmentLeftWithOutImageWithOutImage);
                 userLocation = (TextView) itemView
                         .findViewById(R.id.answerCell_question_fragment_userLocationLeftWithOutImage);
                 answerOfUser = (TextView) itemView
                         .findViewById(R.id.answerInAnswerCellUserAnswerFragmentLeftWithOutImage);
                 userProfilePic = (ImageView) itemView
                         .findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentLeftWithOutImage);
             }else{
                 Log.e("Answer Image",presentAnswer.getImage());
                 itemView = (RelativeLayout) mLayoutInflater.inflate(
                         R.layout.answers_fragment_listview_cell_left, parent, false);
                 userName = (TextView) itemView
                         .findViewById(R.id.userNameInAnswerCellUserAnswerFragmentLeft);
                 userLocation = (TextView) itemView
                         .findViewById(R.id.answerCell_question_fragment_userLocationLeft);
                 answerOfUser = (TextView) itemView
                         .findViewById(R.id.answerInAnswerCellUserAnswerFragmentLeft);
                 userProfilePic = (ImageView) itemView
                         .findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentLeft);
                 answerImage = (ImageView) itemView
                         .findViewById(R.id.answers_fargemnt_imageAnswerImageViewLeft);
                 if(convertView==null){
                     Picasso.with(mContext)
                     .load(presentAnswer.getImage())
                     .transform(transformation)
                     .into(answerImage);
                 }
               
             }
             
            
         } else { */
             if(presentAnswer.getImage()==null || presentAnswer.getImage().compareTo("")==0 || !presentAnswer.getImage().contains("http")){
                 itemView = (RelativeLayout) mLayoutInflater.inflate(
                         R.layout.answers_fragment_listview_cell_right_withoutimage, parent, false);
                 userName = (TextView) itemView
                         .findViewById(R.id.userNameInAnswerCellUserAnswerFragmentRightWithOutImage);
                 userLocation = (TextView) itemView
                         .findViewById(R.id.answerCell_answer_fragment_answering_userLocationRightWithOutImage);
                 answerOfUser = (TextView) itemView
                         .findViewById(R.id.answerInAnswerCellUserAnswerFragmentRightWithOutImage);
                 userProfilePic = (ImageView) itemView
                         .findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentRightWithOutImage);
             }else{
                 Log.e("Answer Image",presentAnswer.getImage());
                 itemView = (RelativeLayout) mLayoutInflater.inflate(
                         R.layout.answers_fragment_listview_cell_right, parent, false);
                 userName = (TextView) itemView
                         .findViewById(R.id.userNameInAnswerCellUserAnswerFragmentRight);
                 userLocation = (TextView) itemView
                         .findViewById(R.id.answerCell_answer_fragment_answering_userLocationRight);
                 answerOfUser = (TextView) itemView
                         .findViewById(R.id.answerInAnswerCellUserAnswerFragmentRight);
                 userProfilePic = (ImageView) itemView
                         .findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentRight);
                 answerImage = (ImageView) itemView
                         .findViewById(R.id.answerImageInAnswerCellUserAnswerFragmentRight);
                 
                 Picasso.with(mContext)
                     .load(presentAnswer.getImage())
                     .transform(transformation)
                     .into(answerImage);
                 
             }
             
       //  }
         
        
         
         answerOfUser.setText(presentAnswer.getAnswer());
         userName.setText(presentAnswer.getUserName());
         userLocation.setText(presentAnswer.getLocation());
        
               Picasso.with(mContext)
               .load(R.drawable.user_profile_pic_temporary_image)
               .placeholder(R.drawable.user_profile_pic_temporary_image)
               .transform(transformation)
               .into(userProfilePic);
             
         }else{
             itemView=(RelativeLayout)convertView;
         }

        return itemView;
    }

    public void upDateEntries(List<Answer> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

}
