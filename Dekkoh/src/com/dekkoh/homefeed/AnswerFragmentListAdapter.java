
package com.dekkoh.homefeed;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.datamodel.Answer;
import com.dekkoh.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class AnswerFragmentListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;
    public String sessionid = "";
    private RoundedTransformation transformation = new RoundedTransformation(40, 0);

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
        if (convertView == null) {
            TextView userName, answerOfUser;
            ImageView userProfilePic;
            if (position % 2 != 0) {
                itemView = (RelativeLayout) mLayoutInflater.inflate(
                        R.layout.answers_fragment_listview_cell_left, parent, false);
                userName = (TextView) itemView
                        .findViewById(R.id.userNameInAnswerCellUserAnswerFragmentLeft);
                answerOfUser = (TextView) itemView
                        .findViewById(R.id.answerInAnswerCellUserAnswerFragmentLeft);
                userProfilePic = (ImageView) itemView
                        .findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentLeft);
            } else {
                itemView = (RelativeLayout) mLayoutInflater.inflate(
                        R.layout.answers_fragment_listview_cell_right, parent, false);
                userName = (TextView) itemView
                        .findViewById(R.id.userNameInAnswerCellUserAnswerFragmentRight);
                answerOfUser = (TextView) itemView
                        .findViewById(R.id.answerInAnswerCellUserAnswerFragmentRight);
                userProfilePic = (ImageView) itemView
                        .findViewById(R.id.profileImageInAnswerCellUserAnswerFragmentRight);
            }
            
            Answer presentAnswer=mEntries.get(position);
            
            answerOfUser.setText(presentAnswer.getAnswer());
            if(presentAnswer.getImage().compareTo("")!=0){
                try {
                    Picasso.with(mContext)
                            .load(presentAnswer.getImage())
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

        } else {
            itemView = (RelativeLayout) convertView;
        }

        return itemView;
    }

    public void upDateEntries(List<Answer> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

}
