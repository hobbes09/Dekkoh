
package com.dekkoh.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dekkoh.ApplicationState;
import com.dekkoh.R;
import com.dekkoh.activities.HomeFeedActivity;
import com.dekkoh.custom.handler.QuestionContentManager;
import com.dekkoh.datamodel.Question;
import com.dekkoh.fragments.AnswersFragment;
import com.dekkoh.util.CommonUtils;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class QuestionCardView {

    public static String TAG = "QuestionCardView";
    private int windowWidth, windowHeight;
    private float screenCenterX, screenCenterY;
    private float x_current, y_current, x_initial, y_initial, x_final, y_final;
    private float initPosX = 0, initPosY = 0;
    private float dragLengthX = 0, draglengthY = 0;;
    private RelativeLayout parentView;
    private Context context;

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

    public static Question question;
    private int countLike = 0;
    private int countFollow = 0;

    private static final String IMAGE_CACHE_DIR = ".gallery/cache";
    private ImageFetcher profileImageFetcher;
    private ImageFetcher questionImageFetcher;

    private static VelocityTracker velocityTracker = null;

    private static QuestionCardView questionCardView = new QuestionCardView();

    private QuestionCardView() {
    }

    public static QuestionCardView getInstance() {
        return QuestionCardView.questionCardView;
    }

    public void setWindowWidth(int windowWidth) {
        getInstance().windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        getInstance().windowHeight = windowHeight;
    }

    public void setScreenCenterX(int screenCenterX) {
        getInstance().screenCenterX = screenCenterX;
    }

    public void setScreenCenterY(int screenCenterY) {
        getInstance().screenCenterY = screenCenterY;
    }

    public void setInitPosX(int initPosX) {
        getInstance().initPosX = initPosX;
    }

    public void setInitPosY(int initPosY) {
        getInstance().initPosY = initPosY;
    }

    public void setX_initial(int x_initial) {
        getInstance().x_initial = x_initial;
    }

    public static void setY_initial(int y_initial) {
        getInstance().y_initial = y_initial;
    }

    public static void setParentView(RelativeLayout parentView) {
        getInstance().parentView = parentView;
    }

    public void setContext(Context context) {
        getInstance().context = context;
    }

    public void createQuestionCard(int index) {
        LayoutInflater inflate = (LayoutInflater) getInstance().context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflate.inflate(R.layout.question_fragment, null);

        getInstance().initialize(view);

        if (getInstance().question != null)
            getInstance().setValues();

        view.setX(getInstance().initPosX);
        view.setY(getInstance().initPosY);

        getInstance().initializeInteractionListeners(view, index);

    }

    private void initialize(View root) {
        getInstance().tvLocation = (TextView) root
                .findViewById(R.id.tvLocation);
        getInstance().tvUsername = (TextView) root
                .findViewById(R.id.tvUsername);
        getInstance().tvQuestion = (TextView) root
                .findViewById(R.id.tvQuestion);
        getInstance().tvNumAnswers = (TextView) root
                .findViewById(R.id.tvNumAnswers);
        getInstance().ivHomeImage = (ImageView) root
                .findViewById(R.id.ivHomeImage);
        getInstance().ivProfilePic = (CircularImageView) root
                .findViewById(R.id.ivProfilePic);
        getInstance().sllBack = (SquareLinearLayout) root
                .findViewById(R.id.sllBack);
        getInstance().sllShare = (SquareLinearLayout) root
                .findViewById(R.id.sllShare);
        getInstance().sllLike = (SquareLinearLayout) root
                .findViewById(R.id.sllLike);
        getInstance().sllFollow = (SquareLinearLayout) root
                .findViewById(R.id.sllFollow);
        getInstance().sllAnswerButton = (SquareLinearLayout) root
                .findViewById(R.id.sllAnswerButton);
        getInstance().questionFragmentLayout = (LinearLayout) root
                .findViewById(R.id.questionFragmentLayout);

        Typeface typeFaceQuestion = Typeface
                .createFromAsset(HomeFeedActivity.homeScreenContext.getAssets(),
                        "fonts/SortsMillGoudy-Regular.ttf");
        tvQuestion.setTypeface(typeFaceQuestion);

        Typeface typeFaceUser = Typeface
                .createFromAsset(HomeFeedActivity.homeScreenContext.getAssets(),
                        "fonts/SourceSansPro_Bold.ttf");
        tvUsername.setTypeface(typeFaceUser);
        tvLocation.setTypeface(typeFaceUser);

        ImageCacheParams cacheParams = new ImageCacheParams(
                getInstance().context, IMAGE_CACHE_DIR);
        // Set memory cache to 25% of app memory
        cacheParams.setMemCacheSizePercent(0.25f);
        getInstance().profileImageFetcher = new RemoteImageFetcher(
                getInstance().context, 100);
        getInstance().profileImageFetcher
                .setLoadingImage(R.drawable.loding_album);
        getInstance().profileImageFetcher.addImageCache(
                HomeFeedActivity.supportFragmentManager, cacheParams);
        getInstance().questionImageFetcher = new RemoteImageFetcher(
                getInstance().context, 500);
        getInstance().questionImageFetcher
                .setLoadingImage(R.drawable.loding_album);
        getInstance().questionImageFetcher.addImageCache(
                HomeFeedActivity.supportFragmentManager, cacheParams);

        getInstance().question = QuestionContentManager.getNextQuestion();
    }

    private void setValues() {
        tvLocation.setText(getInstance().question.getLocation().substring(1));
        tvUsername.setText(getInstance().question.getUserName().split(" ", 2)[0] + "  |");
        tvQuestion.setText(getInstance().question.getQuestion());
        tvNumAnswers.setText(Integer.toString(getInstance().question
                .getAnswerCount()));
        if (CommonUtils.isValidURL(getInstance().question.getImage() + "")) {
            questionImageFetcher.loadImage(getInstance().question.getImage(),
                    ivHomeImage);
        } else {
            ivHomeImage.setImageResource(R.drawable.no_image_available);
        }
        if (CommonUtils.isValidURL(getInstance().question.getUserImage() + "")) {
            profileImageFetcher.loadImage(
                    getInstance().question.getUserImage(), ivProfilePic);
        } else {
            ivProfilePic.setImageResource(R.drawable.ic_noprofilepic);
        }
    }

    private void initializeInteractionListeners(final View view, int index) {

        sllAnswerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                AnswersFragment answersFragment = new AnswersFragment();
                Bundle questionBundle = new Bundle();
                questionBundle.putString("userName", getInstance().question.getUserName());
                questionBundle.putString("userLocation", getInstance().question.getLocation());
                questionBundle.putString("userQuestion", getInstance().question.getQuestion());
                questionBundle.putString("questionId", getInstance().question.getQuestionId());
                questionBundle.putString("profilePicUrl", getInstance().question.getUserImage());
                answersFragment.setArguments(questionBundle);
                /*
                 * HomeFeedActivity.supportFragmentManager.beginTransaction()
                 * .replace(R.id.contentHomeActivity, answersFragment).commit();
                 */
                FragmentTransaction fragmentTransaction = HomeFeedActivity.supportFragmentManager
                        .beginTransaction()
                        .add(R.id.contentHomeActivity, answersFragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        questionFragmentLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getInstance().x_current = event.getRawX();
                getInstance().y_current = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getInstance().x_initial = event.getX();
                        getInstance().y_initial = event.getY();
                        velocityTracker = VelocityTracker.obtain();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        getInstance().x_current = event.getRawX();
                        getInstance().y_current = event.getRawY();
                        getInstance().dragLengthX = getInstance().x_current
                                - getInstance().x_initial;
                        getInstance().draglengthY = getInstance().y_current
                                - getInstance().y_initial;

                        view.setX(getInstance().initPosX + getInstance().dragLengthX);
                        view.setY(getInstance().initPosY + getInstance().draglengthY);
                        view.setRotation(getInstance().dragLengthX * 0.001f * 30f);

                        velocityTracker.addMovement(event);

                        break;
                    case MotionEvent.ACTION_UP:
                        velocityTracker.computeCurrentVelocity(1000); // pixels per second
                        float velocityX = velocityTracker.getXVelocity();
                        float velocityY = velocityTracker.getYVelocity();
                        double velocity = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
                        x_final = event.getX();
                        y_final = event.getY();

                        if (velocity >= ApplicationState.getMaxVelocity()) {
                            parentView.removeView(view);
                        } else {
                            TranslateAnimation anim = new TranslateAnimation(Animation.ABSOLUTE,
                                    getInstance().initPosX + getInstance().dragLengthX,
                                    Animation.ABSOLUTE, getInstance().initPosX, Animation.ABSOLUTE,
                                    getInstance().initPosY + getInstance().draglengthY,
                                    Animation.ABSOLUTE, getInstance().initPosY);
                            anim.setDuration(200);
                            anim.setFillAfter(true);
                            view.startAnimation(anim);
                        }
                        view.setX(getInstance().initPosX);
                        view.setY(getInstance().initPosY);
                        view.setRotation(0);
                        break;
                    default:
                        break;
                }

                return true;
            }

        });

        getInstance().parentView.addView(view, index);
        Animation expansion = createExpansion(view);
        expansion.setDuration(1500);
        expansion.setInterpolator(new BounceInterpolator());
        view.startAnimation(expansion);

    }

    private Animation createExpansion(View m_view) {
        return new CustomScaleAnimation(m_view, 0.f, 1f, 0.1f, 1f,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f);

    }

}
