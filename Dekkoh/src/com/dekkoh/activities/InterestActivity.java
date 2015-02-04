
package com.dekkoh.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.BaseActivity;
import com.dekkoh.DekkohApplication;
import com.dekkoh.R;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Constants.SharedPreferenceConstants;
import com.dekkoh.util.Log;
import com.dekkoh.util.SharedPreferenceManager;

public class InterestActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    private ArrayList<Integer> flipIds = new ArrayList<Integer>();
    private static ArrayList<String> selectedInterests;
    private static int fragmentPosition = 0;

    public ArrayList<String> completeInterests = new ArrayList<String>();
    public String[] comp_interests;
    public String[] interest_id;

    ProgressDialog progress;

    ImageView confirmInterestSelection;

    Context mContext;

    DekkohUser dekkohUser;

    DekkohApplication dekkohApplication;

    List<DekkohUser.InterestID> interestIdList = new ArrayList<DekkohUser.InterestID>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.interests_activity_layout);

        mContext = getApplicationContext();

        // Progress Dialog
        progress = new ProgressDialog(this);
        progress.setMessage("Saving Your Selection...");
        progress.setIndeterminate(true);
        //

        dekkohApplication = (DekkohApplication) activity.getApplication();

        dekkohUser = dekkohApplication.getDekkohUser();

        comp_interests = getResources().getStringArray(R.array.Interests_list);
        interest_id = getResources().getStringArray(R.array.Interest_ids);
        completeInterests.clear();
        for (int i = 0; i < comp_interests.length; i++) {
            completeInterests.add(comp_interests[i]);
        }

        confirmInterestSelection = (ImageView) findViewById(R.id.interests_activity_layout_continue);

        if (savedInstanceState == null) {
            // If there is no saved instance state, add a fragment representing
            // the
            // front of the card to this activity. If there is saved instance
            // state,
            // this fragment will have already been added to the activity.

            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container1, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container2, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container3, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container4, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container5, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container6, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container7, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container8, new CardFrontFragment()).commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.container9, new CardFrontFragment()).commit();
        } else {
            // mShowingBack = (getFragmentManager().getBackStackEntryCount() >
            // 0);
        }
        selectedInterests = new ArrayList<String>();

        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        FrameLayout container1 = (FrameLayout) findViewById(R.id.container1);
        FrameLayout container2 = (FrameLayout) findViewById(R.id.container2);
        FrameLayout container3 = (FrameLayout) findViewById(R.id.container3);
        FrameLayout container4 = (FrameLayout) findViewById(R.id.container4);
        FrameLayout container5 = (FrameLayout) findViewById(R.id.container5);
        FrameLayout container6 = (FrameLayout) findViewById(R.id.container6);
        FrameLayout container7 = (FrameLayout) findViewById(R.id.container7);
        FrameLayout container8 = (FrameLayout) findViewById(R.id.container8);
        FrameLayout container9 = (FrameLayout) findViewById(R.id.container9);
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 0;
                flipCard(R.id.container);
            }

        });
        container1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 1;
                flipCard(R.id.container1);
            }

        });
        container2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 2;
                flipCard(R.id.container2);
            }

        });
        container3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 3;
                flipCard(R.id.container3);
            }

        });
        container4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 4;
                flipCard(R.id.container4);
            }

        });
        container5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 5;
                flipCard(R.id.container5);
            }

        });
        container6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 6;
                flipCard(R.id.container6);
            }

        });
        container7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 7;
                flipCard(R.id.container7);
            }

        });
        container8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 8;
                flipCard(R.id.container8);
            }

        });
        container9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentPosition = 9;
                flipCard(R.id.container9);
            }

        });

        confirmInterestSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("Clicked on options selected location image clicked");

                String allInterests = "";

                for (int i = 0; i < selectedInterests.size(); i++) {
                    String interest = selectedInterests.get(i);
                    if (completeInterests.contains(interest)) {
                        DekkohUser.InterestID interest_ID_new = dekkohUser.new InterestID();
                        interest_ID_new
                                .setInterestID(interest_id[completeInterests
                                        .indexOf(interest)]);
                        interestIdList.add(interest_ID_new);
                        allInterests += interest_ID_new.getInterestID() + ",";
                    }

                }

                if (allInterests.length() > 1) {
                    allInterests = allInterests.substring(0,
                            allInterests.length() - 1);
                    // Toast.makeText(mContext, allInterests, Toast.LENGTH_SHORT)
                    // .show();
                    SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
                            .getInstance(InterestActivity.this);
                    sharedPreferenceManager.save(
                            SharedPreferenceConstants.DEKKOH_USER_INTERESTS,
                            allInterests);
                    sharedPreferenceManager
                            .save(SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS,
                                    true);
                    dekkohUser.setInterestIds(interestIdList);

                    if (dekkohApplication != null) {
                        dekkohApplication.setDekkohUser(dekkohUser);
                    }

                    progress.show();
                    new saveInterestsTask().execute();

                } else {
                    Toast.makeText(mContext,
                            "Please select interests to proceed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void flipCard(int containerId) {

        if (!flipIds.contains(containerId)) {
            flipIds.add(containerId);
            getFragmentManager()
                    .beginTransaction()

                    .setCustomAnimations(R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(containerId, new CardBackFragment()).commit();
        } else {
            flipIds.remove((Integer) containerId);
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(containerId, new CardFrontFragment()).commit();
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.interests, menu);

        /*
         * // Associate searchable configuration with the SearchView SearchManager searchManager =
         * (SearchManager) getSystemService(Context.SEARCH_SERVICE); SearchView searchView =
         * (SearchView) menu.findItem(R.id.action_location_found) .getActionView();
         * searchView.setSearchableInfo(searchManager .getSearchableInfo(getComponentName()));
         */

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A fragment representing the front of the card.
     */
    public static class CardFrontFragment extends Fragment {
        public CardFrontFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View frontView = inflater.inflate(R.layout.interest_card_front,
                    container, false);
            if (fragmentPosition == 0) {
                frontView.setBackgroundResource(R.drawable.travel_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Travel and Adventure");
                if (selectedInterests.contains("Travel and Adventure"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Travel and Adventure"));
            } else if (fragmentPosition == 1) {
                frontView.setBackgroundResource(R.drawable.shopping_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Shopping and Lifestyle");
                if (selectedInterests.contains("Shopping and LifeStyle"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Shopping and LifeStyle"));
            } else if (fragmentPosition == 2) {
                frontView.setBackgroundResource(R.drawable.sport_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Sports and Activities");
                if (selectedInterests.contains("Sports and Activities"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Sports and Activities"));
            } else if (fragmentPosition == 3) {
                frontView.setBackgroundResource(R.drawable.food_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Food and Restaurants");
                if (selectedInterests.contains("Food and Restaurants"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Food and Restaurants"));
            } else if (fragmentPosition == 4) {
                frontView.setBackgroundResource(R.drawable.pubs_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Pubs and NightLife");
                if (selectedInterests.contains("Pubs and NightLife"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Pubs and NightLife"));
            } else if (fragmentPosition == 5) {
                frontView.setBackgroundResource(R.drawable.accomodation_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Accomodation");
                if (selectedInterests.contains("Accomodation"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Accomodation"));
            } else if (fragmentPosition == 6) {
                frontView.setBackgroundResource(R.drawable.arts_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Arts and Culture");
                if (selectedInterests.contains("Arts and Culture"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Arts and Culture"));
            } else if (fragmentPosition == 7) {
                frontView.setBackgroundResource(R.drawable.utilities_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Utilities");
                if (selectedInterests.contains("Utilities"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Utilities"));
            } else if (fragmentPosition == 8) {
                frontView.setBackgroundResource(R.drawable.entertainment_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Entertainment and Events");
                if (selectedInterests.contains("Entertainment and Events"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Entertainment and Events"));
            } else if (fragmentPosition == 9) {
                frontView.setBackgroundResource(R.drawable.miscellaneous_black);
                TextView tv = (TextView) frontView
                        .findViewById(R.id.tvInterestName_black);
                tv.setText("Miscellaneous");
                if (selectedInterests.contains("Miscellaneous"))
                    selectedInterests.remove(selectedInterests
                            .indexOf("Miscellaneous"));
            }
            fragmentPosition++; // only used while initial imagesview display
            return frontView;

        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        public CardBackFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View backView = inflater.inflate(R.layout.interest_card_back,
                    container, false);
            if (fragmentPosition == 0) {
                backView.setBackgroundResource(R.drawable.travel_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Travel and Adventure");
                if (!selectedInterests.contains("Travel and Adventure"))
                    selectedInterests.add("Travel and Adventure");
            } else if (fragmentPosition == 1) {
                backView.setBackgroundResource(R.drawable.shopping_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Shopping and Lifestyle");
                if (!selectedInterests.contains("Shopping and LifeStyle"))
                    selectedInterests.add("Shopping and LifeStyle");
            } else if (fragmentPosition == 2) {
                backView.setBackgroundResource(R.drawable.sport_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Sports and Activities");
                if (!selectedInterests.contains("Sports and Activities"))
                    selectedInterests.add("Sports and Activities");
            } else if (fragmentPosition == 3) {
                backView.setBackgroundResource(R.drawable.food_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Food and Restaurants");
                if (!selectedInterests.contains("Food and Restaurants"))
                    selectedInterests.add("Food and Restaurants");
            } else if (fragmentPosition == 4) {
                backView.setBackgroundResource(R.drawable.pubs_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Pubs and NightLife");
                if (!selectedInterests.contains("Pubs and NightLife"))
                    selectedInterests.add("Pubs and NightLife");
            } else if (fragmentPosition == 5) {
                backView.setBackgroundResource(R.drawable.accomodation_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Accomodation");
                if (!selectedInterests.contains("Accomodation"))
                    selectedInterests.add("Accomodation");
            } else if (fragmentPosition == 6) {
                backView.setBackgroundResource(R.drawable.arts_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Arts and Culture");
                if (!selectedInterests.contains("Arts and Culture"))
                    selectedInterests.add("Arts and Culture");
            } else if (fragmentPosition == 7) {
                backView.setBackgroundResource(R.drawable.utilities_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Utilities");
                if (!selectedInterests.contains("Utilities"))
                    selectedInterests.add("Utilities");
            } else if (fragmentPosition == 8) {
                backView.setBackgroundResource(R.drawable.entertainment_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Entertainment and Events");
                if (!selectedInterests.contains("Entertainment and Events"))
                    selectedInterests.add("Entertainment and Events");
            } else if (fragmentPosition == 9) {
                backView.setBackgroundResource(R.drawable.miscellaneous_color);
                TextView tv = (TextView) backView
                        .findViewById(R.id.tvInterestName);
                tv.setText("Miscellaneous");
                if (!selectedInterests.contains("Miscellaneous"))
                    selectedInterests.add("Miscellaneous");
            }
            return backView;

        }
    }

    class saveInterestsTask extends AsyncTask<Void, Void, String> {

        String result = null;

        @Override
        protected String doInBackground(Void... params) {
            try {

                String[] listOfInterestIds = new String[interestIdList.size()];
                for (int i = 0; i < interestIdList.size(); i++) {
                    listOfInterestIds[i] = interestIdList.get(i)
                            .getInterestID();
                }

                dekkohUser = APIProcessor.updateUserInterest(
                        InterestActivity.this, listOfInterestIds);

            } catch (Exception e) {
                Log.e(e);
                result = e.getMessage();
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null && dekkohUser != null) {
                progress.cancel();
                dekkohApplication.setDekkohUser(dekkohUser);
                Intent intent = new Intent(InterestActivity.this,
                        PreHomeActivity.class);
                startActivity(intent);
            } else {
                progress.cancel();
            }

        }
    }
}
