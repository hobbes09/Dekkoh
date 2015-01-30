
package com.dekkoh.homefeed;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.application.ApplicationState;
import com.dekkoh.application.BaseFragmentActivity;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.custom.view.SquareLinearLayout;
import com.dekkoh.datamodel.Question;
import com.dekkoh.following.Following;
import com.dekkoh.map.DekkohMapFragment;
import com.dekkoh.myactivity.MyActivity;
//import com.dekkoh.myconnections.MyConnectionsActivity;
import com.dekkoh.myprofile.MyProfileActivity;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.slidingmenu.NavDrawerItem;
import com.dekkoh.slidingmenu.NavDrawerListAdapter;
import com.dekkoh.util.CommonUtils;
import com.dekkoh.util.FileManager;
import com.dekkoh.util.FlipAnimator;
import com.dekkoh.util.Log;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;

public class HomeScreen extends BaseFragmentActivity implements OnClickListener {
    private ImageButton ibPost;
    private ImageButton ibMap;
    private TextView tvTitle;
    
    private int windowWidth, windowHeight;
    private float screenCenterX, screenCenterY;
    private float x_current, y_current, x_initial, y_initial;
    private float initPosX = 0, initPosY = 0;
    private float dragLengthX = 0,  draglengthY = 0;;
    private RelativeLayout parentView;
    
    private TextView tvLocation;
    private TextView tvUsername;
    private TextView tvQuestion;
    private TextView tvNumAnswers;
    private ImageView ivHomeImage;
    private CircularImageView ivProfilePic;
    private SquareLinearLayout sllBack;
    private SquareLinearLayout sllShare;
    private SquareLinearLayout sllLike;
    private SquareLinearLayout sllFollow;
    private SquareLinearLayout sllAnswerButton;
    private LinearLayout questionFragmentLayout;
    private static final String IMAGE_CACHE_DIR = ".gallery/cache";
    private ImageFetcher profileImageFetcher;
    private ImageFetcher questionImageFetcher;

    private Question question;
    private Bundle instanceState;
    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    static FragmentManager supportFragmentManager;
    public static Context homeScreenContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customizeActionBar(); // MUST BE PLACED BEFORE setContentView() and
                              // super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        initialize(savedInstanceState);
        showActionBar();
    }

    private void initialize(Bundle savedInstanceState) {
        ibMap = (ImageButton) findViewById(R.id.ibMap);
        ibPost = (ImageButton) findViewById(R.id.ibPost);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        QuestionCardView.getInstance().setParentView(
                (RelativeLayout) findViewById(R.id.contentHomeActivity));

        ibMap.setOnClickListener(this);
        ibPost.setOnClickListener(this);

        supportFragmentManager = getSupportFragmentManager();
        homeScreenContext = HomeScreen.this;

        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenCenterX = windowWidth / 2;
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenCenterY = windowHeight / 2;

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvNumAnswers = (TextView) findViewById(R.id.tvNumAnswers);
        ivHomeImage = (ImageView) findViewById(R.id.ivHomeImage);
        ivProfilePic = (CircularImageView) findViewById(R.id.ivProfilePic);
        sllBack = (SquareLinearLayout) findViewById(R.id.sllBack);
        sllShare = (SquareLinearLayout) findViewById(R.id.sllShare);
        sllLike = (SquareLinearLayout) findViewById(R.id.sllLike);
        sllFollow = (SquareLinearLayout) findViewById(R.id.sllFollow);
        sllAnswerButton = (SquareLinearLayout) findViewById(R.id.sllAnswerButton);
        questionFragmentLayout = (LinearLayout) findViewById(R.id.questionFragmentLayout);
        parentView = (RelativeLayout)findViewById(R.id.viewCardContainer);
        
        Typeface typeFaceQuestion=Typeface
                .createFromAsset(HomeScreen.homeScreenContext.getAssets(),"fonts/SortsMillGoudy-Regular.ttf");
        tvQuestion.setTypeface(typeFaceQuestion);
        
        Typeface typeFaceUser=Typeface
                .createFromAsset(HomeScreen.homeScreenContext.getAssets(),"fonts/SourceSansPro_Bold.ttf");
        tvUsername.setTypeface(typeFaceUser);
        tvLocation.setTypeface(typeFaceUser);


        ImageCacheParams cacheParams = new ImageCacheParams(
                homeScreenContext, IMAGE_CACHE_DIR);
        // Set memory cache to 25% of app memory
        cacheParams.setMemCacheSizePercent(0.25f);
        profileImageFetcher = new RemoteImageFetcher(homeScreenContext, 100);
        profileImageFetcher.setLoadingImage(R.drawable.loding_album);
        profileImageFetcher.addImageCache(HomeScreen.supportFragmentManager, cacheParams);
        questionImageFetcher = new RemoteImageFetcher( homeScreenContext, 500);
        questionImageFetcher.setLoadingImage(R.drawable.loding_album);
        questionImageFetcher.addImageCache(HomeScreen.supportFragmentManager, cacheParams);

        question = QuestionContentManager.getNextQuestion();
        instanceState = savedInstanceState;
        
        setQuestionViewValues();

        navigationDrawerInitialisation(savedInstanceState);
    }

    private void customizeActionBar() {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#00000000")));
        ActionBar actionBar = getActionBar();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View mCustomView = layoutInflater.inflate(R.layout.custom_actionbar,
                null);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    private void navigationDrawerInitialisation(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        View headerView = getLayoutInflater().inflate(
                R.layout.navigation_drawer_header, null);
        mDrawerList.addHeaderView(headerView);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        // adding nav drawer items to array
        // Empty for app title
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
                .getResourceId(0, -1)));
        // My Profile
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
                .getResourceId(1, -1)));
        // My Activity
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
                .getResourceId(2, -1)));
        // Following
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
                .getResourceId(3, -1)));
        // Messages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
                .getResourceId(4, -1)));
        // Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
                .getResourceId(5, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for
                                   // accessibility
                R.string.app_name // nav drawer close - description for
                                  // accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(-1); // Display HomeFeed here
        }
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);
        // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;
        switch (position) {
            case 2:
                Intent myProfileIntent = new Intent(activity,
                        MyProfileActivity.class);
                startActivity(myProfileIntent);
                break;
            case 3:
                Intent intent = new Intent(this, MyActivity.class);
                startActivity(intent);
                return;
            case 4:
                fragment = new Following();
                break;
            case 5:
                // Intent connectionsIntent = new Intent(this, MyConnectionsActivity.class);
                // startActivity(connectionsIntent);
                break;
            case 6:
                fragment  =  new DekkohMapFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentHomeActivity, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            // setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            createQuestionAnswerCard(ApplicationState.getHomefeedQuestion_CurrentIndex());
            
        }
    }

    private void createQuestionAnswerCard(int homefeedQuestion_CurrentIndex) {
        
        LayoutInflater inflate = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View viewFront = (View) findViewById(R.id.questionFragmentLayout);
        final View viewBack = (View) findViewById(R.id.answerFragmentLayout);
        
        if(this.instanceState == null){
            viewBack.setVisibility(View.INVISIBLE);
        }
        
        initializeInterationListeners(viewFront);
        
        sllAnswerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                FlipAnimator animator = new FlipAnimator(viewFront, viewBack,
                        viewBack.getWidth() / 2, viewBack.getHeight() / 2);
                if (viewFront.getVisibility() == View.GONE) {
                    animator.reverse();
                }
                parentView.startAnimation(animator);
            }
        });
    }

private void initializeInterationListeners(final View viewFront) {
        
        viewFront.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x_current = event.getRawX();
                y_current = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x_initial = event.getX();
                        y_initial = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x_current = event.getRawX();
                        y_current = event.getRawY();
                        dragLengthX = x_current - x_initial;
                        draglengthY = y_current - y_initial;
                        
                        viewFront.setX(initPosX + dragLengthX);
                        viewFront.setY(initPosY + draglengthY);
                        viewFront.setRotation(dragLengthX * 0.001f * 30f);                       
                        break;
                    case MotionEvent.ACTION_UP:
                        float tempDragX = dragLengthX; float tempDragY = draglengthY;
                        if (Math.abs(dragLengthX) > screenCenterX || Math.abs(draglengthY) > screenCenterX) {                           
                            while(Math.abs(tempDragX) < 2*screenCenterX || Math.abs(tempDragY) < 2*screenCenterY){
                                tempDragX = (tempDragX > 0) ? tempDragX+0.1f : tempDragX-0.1f;
                                tempDragY = (tempDragY > 0) ? tempDragY+0.1f : tempDragY-0.1f;
                                viewFront.setX(initPosX + tempDragX);
                                viewFront.setY(initPosY + tempDragY);
                                viewFront.setRotation(tempDragX * 0.001f * 60f);                                 
                            }
                            parentView.removeView(viewFront); 
                            
                        }else{
                            while(Math.abs(tempDragX) > 1 && Math.abs(tempDragY) > 1 ){
                                tempDragX = (tempDragX > 0) ? tempDragX-0.1f : tempDragX+0.1f;
                                tempDragY = (tempDragY > 0) ? tempDragY-0.1f : tempDragY+0.1f;
                                viewFront.setX(initPosX + tempDragX);
                                viewFront.setY(initPosY + tempDragY);
                                viewFront.setRotation(tempDragX * 0.001f * 60f); 
                            }
                            viewFront.setX(initPosX);
                            viewFront.setY(initPosY);
                            viewFront.setRotation(0);        
                        }
                        
                        break;
                    default:
                        break;
                }

                return true;
            }

        });     
    }

    private void setQuestionViewValues(){
        tvLocation.setText(question.getLocation().substring(1));
        tvUsername.setText(question.getUserName().split(" ", 2)[0] + "  |");
        tvQuestion.setText(question.getQuestion());
        tvNumAnswers.setText(Integer.toString(question
                .getAnswerCount()));
        if (CommonUtils.isValidURL(question.getImage() + "")) {
            questionImageFetcher.loadImage(question.getImage(),
                    ivHomeImage);
        } else {
            ivHomeImage.setImageResource(R.drawable.no_image_available);
        }
        if (CommonUtils.isValidURL(question.getUserImage() + "")) {
            profileImageFetcher.loadImage(question.getUserImage(), ivProfilePic);
        } else {
            ivProfilePic.setImageResource(R.drawable.ic_noprofilepic);
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate() and
     * onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ibMap:
                Fragment dekkohMapFragment = new DekkohMapFragment();
                fragmentManager.beginTransaction()
                .replace(R.id.contentHomeActivity, dekkohMapFragment).commit();
                break;
            case R.id.ibPost:
                Fragment postQuestionFragment = new PostQuestionFragment();
                fragmentManager.beginTransaction()
                .replace(R.id.contentHomeActivity, postQuestionFragment).commit();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (FileManager.getInstance().isObjectFileExistsInExternalStorage(
                    (Activity) HomeScreen.homeScreenContext,
                    ApplicationState.getQuestionsfile())) {
                FileManager.getInstance().deleteObjectFileFromExternalStorage(
                        (Activity) HomeScreen.homeScreenContext,
                        ApplicationState.getQuestionsfile());
            }
        } catch (Exception e) {

        }
    }

    public class FetchQuestionTask extends
            AsyncTask<Void, Void, List<Question>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogHandler.showCustomProgressDialog(activity);
        }

        @Override
        protected List<Question> doInBackground(Void... params) {
            try {
                return APIProcessor.getQuestions(activity, 0, 20, 0, 0, null,
                        null, 0, null, true, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<Question> qlist) {
            if (qlist != null) {
                progressDialogHandler.dismissCustomProgressDialog(activity);
                QuestionContentManager.getInstance().setQuestionList(qlist);

            }
        }
    }

    @Override
    public void onResume(){
        Log.e("Home Screen", "Resumed");
        super.onResume();
    }
    
    @Override
    public void onPause(){
        Log.e("Home Screen", "Paused");
        super.onPause();
    }

}
