package com.dekkoh.custom.view;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.R;
import com.dekkoh.util.Log;

/**
 * Custom view that shows a pie chart and, optionally, a label.
 */
public class UserProfileChart extends ViewGroup {
	private List<Item> mData = new ArrayList<Item>();
	private int minw, minh;
	private float mTotal = 0.0f;

	private RectF mPieBounds = new RectF();

	private Paint mPiePaint;
	private Paint mShadowPaint;

	private float mHighlightStrength = 1.15f;

	private float mPointerX;
	private float mPointerY;

	private int mPieRotation;

	private OnCurrentItemChangedListener mCurrentItemChangedListener = null;

	private PieView mPieView;
	private float diameter;

	// The angle at which we measure the current item. This is
	// where the pointer points.
	private int mCurrentItemAngle;

	// the index of the current item.
	private int mCurrentItem = 0;
	private ObjectAnimator mAutoCenterAnimator;
	private RectF mShadowBounds = new RectF();

	/**
	 * Draw text to the left of the pie chart
	 */
	public static final int TEXTPOS_LEFT = 0;

	/**
	 * Draw text to the right of the pie chart
	 */
	public static final int TEXTPOS_RIGHT = 1;

	/**
	 * The initial fling velocity is divided by this amount.
	 */
	public static final int FLING_VELOCITY_DOWNSCALE = 4;

	/**
     *
     */
	public static final int AUTOCENTER_ANIM_DURATION = 250;

	/**
	 * Interface definition for a callback to be invoked when the current item
	 * changes.
	 */
	public interface OnCurrentItemChangedListener {
		void OnCurrentItemChanged(UserProfileChart source, int currentItem);
	}

	/**
	 * Class constructor taking only a context. Use this constructor to create
	 * {@link UserProfileChart} objects from your own code.
	 * 
	 * @param context
	 */
	public UserProfileChart(Context context) {
		super(context);
		init();
	}

	/**
	 * Class constructor taking a context and an attribute set. This constructor
	 * is used by the layout engine to construct a {@link UserProfileChart} from
	 * a set of XML attributes.
	 * 
	 * @param context
	 * @param attrs
	 *            An attribute set which can contain attributes from
	 *            {@link com.UserProfileChart.android.customviews.R.styleable.UserProfileChart}
	 *            as well as attributes inherited from {@link android.view.View}
	 *            .
	 */
	public UserProfileChart(Context context, AttributeSet attrs) {
		super(context, attrs);

		// attrs contains the raw values for the XML attributes
		// that were specified in the layout, which don't include
		// attributes set by styles or themes, and which may have
		// unresolved references. Call obtainStyledAttributes()
		// to get the final values for each attribute.
		//
		// This call uses R.styleable.UserProfileChart, which is an array
		// of
		// the custom attributes that were declared in attrs.xml.
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.UserProfileChart, 0, 0);

		try {
			// Retrieve the values from the TypedArray and store into
			// fields of this class.
			//
			// The R.styleable.UserProfileChart_* constants represent the
			// index for
			// each custom attribute in the R.styleable.UserProfileChart
			// array.
			mHighlightStrength = a.getFloat(
					R.styleable.UserProfileChart_highlightStrength, 1.0f);
			mPieRotation = a
					.getInt(R.styleable.UserProfileChart_pieRotation, 0);
		} finally {
			// release the TypedArray so that it can be reused.
			a.recycle();
		}

		init();
	}

	/**
	 * Returns the strength of the highlighting applied to each pie segment.
	 * 
	 * @return The highlight strength.
	 */
	public float getHighlightStrength() {
		return mHighlightStrength;
	}

	/**
	 * Set the strength of the highlighting that is applied to each pie segment.
	 * This number is a floating point number that is multiplied by the base
	 * color of each segment to get the highlight color. A value of exactly one
	 * produces no highlight at all. Values greater than one produce highlights
	 * that are lighter than the base color, while values less than one produce
	 * highlights that are darker than the base color.
	 * 
	 * @param highlightStrength
	 *            The highlight strength.
	 */
	public void setHighlightStrength(float highlightStrength) {
		if (highlightStrength < 0.0f) {
			throw new IllegalArgumentException(
					"highlight strength cannot be negative");
		}
		mHighlightStrength = highlightStrength;
		invalidate();
	}

	/**
	 * Returns the current rotation of the pie graphic.
	 * 
	 * @return The current pie rotation, in degrees.
	 */
	public int getPieRotation() {
		return mPieRotation;
	}

	/**
	 * Returns the index of the currently selected data item.
	 * 
	 * @return The zero-based index of the currently selected data item.
	 */
	public int getCurrentItem() {
		return mCurrentItem;
	}

	/**
	 * Set the currently selected item. Calling this function will set the
	 * current selection and rotate the pie to bring it into view.
	 * 
	 * @param currentItem
	 *            The zero-based index of the item to select.
	 */
	public void setCurrentItem(int currentItem) {
		setCurrentItem(currentItem, true);
	}

	/**
	 * Set the current item by index. Optionally, scroll the current item into
	 * view. This version is for internal use--the scrollIntoView option is
	 * always true for external callers.
	 * 
	 * @param currentItem
	 *            The index of the current item.
	 * @param scrollIntoView
	 *            True if the pie should rotate until the current item is
	 *            centered. False otherwise. If this parameter is false, the pie
	 *            rotation will not change.
	 */
	private void setCurrentItem(int currentItem, boolean scrollIntoView) {
		mCurrentItem = currentItem;
		if (mCurrentItemChangedListener != null) {
			mCurrentItemChangedListener.OnCurrentItemChanged(this, currentItem);
		}
		if (scrollIntoView) {
			centerOnCurrentItem();
		}
		invalidate();
	}

	/**
	 * Register a callback to be invoked when the currently selected item
	 * changes.
	 * 
	 * @param listener
	 *            Can be null. The current item changed listener to attach to
	 *            this view.
	 */
	public void setOnCurrentItemChangedListener(
			OnCurrentItemChangedListener listener) {
		mCurrentItemChangedListener = listener;
	}

	/**
	 * Add a new data item to this view. Adding an item adds a slice to the pie
	 * whose size is proportional to the item's value. As new items are added,
	 * the size of each existing slice is recalculated so that the proportions
	 * remain correct.
	 * 
	 * @param value
	 *            The value of this item.
	 * @param color
	 *            The ARGB color of the pie slice associated with this item.
	 * @return The index of the newly added item.
	 */
	public int addItem(float value, int color) {
		Item it = new Item();
		it.mColor = color;
		it.mValue = value;

		// Calculate the highlight color. Saturate at 0xff to make sure that
		// high values
		// don't result in aliasing.
		it.mHighlight = Color
				.argb(0xff, Math.min(
						(int) (mHighlightStrength * (float) Color.red(color)),
						0xff),
						Math.min((int) (mHighlightStrength * (float) Color
								.green(color)), 0xff), Math.min(
								(int) (mHighlightStrength * (float) Color
										.blue(color)), 0xff));
		mTotal += value;

		mData.add(it);

		onDataChanged();

		return mData.size() - 1;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// Do nothing. Do not call the superclass method--that would start a
		// layout pass
		// on this view's children. UserProfileChart lays out its children
		// in
		// onSizeChanged().
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Draw the shadow
		canvas.drawOval(mShadowBounds, mShadowPaint);
		// Draw the label text
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Try for a width based on our minimum
		minw = getPaddingLeft() + getPaddingRight()
				+ getSuggestedMinimumWidth();

		int w = Math.max(minw, MeasureSpec.getSize(widthMeasureSpec));

		// Whatever the width ends up being, ask for a height that would let the
		// pie
		// get as big as it can
		minh = w + getPaddingBottom() + getPaddingTop();
		int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minh);
		Log.e("onMeasure(" + minw + "," + minh + ")");
		setMeasuredDimension(w, h);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.e("onSizeChanged(" + w + "," + h + ")");
		//
		// Set dimensions for text, pie chart, etc
		//
		// Account for padding
		float xpad = (float) (getPaddingLeft() + getPaddingRight());
		float ypad = (float) (getPaddingTop() + getPaddingBottom());

		// Account for the label
		xpad += 0;

		float ww = (float) w - xpad;
		float hh = (float) h - ypad;

		// Figure out how big we can make the pie.
		diameter = Math.min(ww, hh);
		mPieBounds = new RectF(0.0f, 0.0f, diameter, diameter);
		mPieBounds.offsetTo((w - diameter) / 2, (h - diameter) / 2);

		mPointerY = 0 - (0 / 2.0f);
		float pointerOffset = mPieBounds.centerY() - mPointerY;

		if (pointerOffset < 0) {
			pointerOffset = -pointerOffset;
			mCurrentItemAngle = 315;
		} else {
			mCurrentItemAngle = 45;
		}
		mPointerX = mPieBounds.centerX() + pointerOffset;

		mShadowBounds = new RectF(mPieBounds.left + 10, mPieBounds.bottom + 10,
				mPieBounds.right - 10, mPieBounds.bottom + 20);

		// Lay out the child view that actually draws the pie.
		mPieView.layout((int) mPieBounds.left, (int) mPieBounds.top,
				(int) mPieBounds.right, (int) mPieBounds.bottom);
		mPieView.setPivot(mPieBounds.width() / 2, mPieBounds.height() / 2);
		onDataChanged();
	}

	/**
	 * Calculate which pie slice is under the pointer, and set the current item
	 * field accordingly.
	 */
	private void calcCurrentItem() {
		int pointerAngle = (mCurrentItemAngle + 360 + mPieRotation) % 360;
		for (int i = 0; i < mData.size(); ++i) {
			Item it = mData.get(i);
			if (it.mStartAngle <= pointerAngle && pointerAngle <= it.mEndAngle) {
				if (i != mCurrentItem) {
					setCurrentItem(i, false);
				}
				break;
			}
		}
	}

	/**
	 * Do all of the recalculations needed when the data array changes.
	 */
	private void onDataChanged() {
		// When the data changes, we have to recalculate
		// all of the angles.
		int currentAngle = 0;
		for (Item it : mData) {
			it.mStartAngle = currentAngle;
			it.mEndAngle = (int) ((float) currentAngle + it.mValue * 360.0f
					/ mTotal);
			currentAngle = it.mEndAngle;

			// Recalculate the gradient shaders. There are
			// three values in this gradient, even though only
			// two are necessary, in order to work around
			// a bug in certain versions of the graphics engine
			// that expects at least three values if the
			// positions array is non-null.
			//
			it.mShader = new SweepGradient(mPieBounds.width() / 2.0f,
					mPieBounds.height() / 2.0f, new int[] { it.mColor,
							it.mColor, it.mColor, it.mColor, }, new float[] {
							0, (float) (360 - it.mEndAngle) / 360.0f,
							(float) (360 - it.mStartAngle) / 360.0f, 1.0f });
		}
		calcCurrentItem();
	}

	/**
	 * Initialize the control. This code is in a separate method so that it can
	 * be called from both constructors.
	 */
	private void init() {
		// Force the background to software rendering because otherwise the Blur
		// filter won't work.
		setLayerToSW(this);

		// Set up the paint for the pie slices
		mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPiePaint.setStyle(Paint.Style.FILL);

		// Set up the paint for the shadow
		mShadowPaint = new Paint(0);
		mShadowPaint.setColor(0xff101010);
		mShadowPaint.setMaskFilter(new BlurMaskFilter(8,
				BlurMaskFilter.Blur.NORMAL));

		// Add a child view to draw the pie. Putting this in a child view
		// makes it possible to draw it on a separate hardware layer that
		// rotates
		// independently
		mPieView = new PieView(getContext());
		addView(mPieView);
		// Set up an animator to animate the PieRotation property. This is used
		// to
		// correct the pie's orientation after the user lets go of it.
		if (Build.VERSION.SDK_INT >= 11) {
			mAutoCenterAnimator = ObjectAnimator.ofInt(UserProfileChart.this,
					"PieRotation", 0);

			// Add a listener to hook the onAnimationEnd event so that we can do
			// some cleanup when the pie stops moving.
			mAutoCenterAnimator.addListener(new Animator.AnimatorListener() {
				public void onAnimationStart(Animator animator) {
				}

				public void onAnimationEnd(Animator animator) {
					mPieView.decelerate();
				}

				public void onAnimationCancel(Animator animator) {
				}

				public void onAnimationRepeat(Animator animator) {
				}
			});
		}
		// In edit mode it's nice to have some demo data, so add that here.
		if (this.isInEditMode()) {
			Resources res = getResources();
			addItem(3, res.getColor(R.color.event_and_entertainment));
			addItem(4, res.getColor(R.color.travel_and_adventure));
		}

	}

	private void setLayerToSW(View v) {
		if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	private void setLayerToHW(View v) {
		if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
			setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
	}

	/**
	 * Kicks off an animation that will result in the pointer being centered in
	 * the pie slice of the currently selected item.
	 */
	private void centerOnCurrentItem() {
		Item current = mData.get(getCurrentItem());
		int targetAngle = current.mStartAngle
				+ (current.mEndAngle - current.mStartAngle) / 2;
		targetAngle -= mCurrentItemAngle;
		if (targetAngle < 90 && mPieRotation > 180)
			targetAngle += 360;

		if (Build.VERSION.SDK_INT >= 11) {
			// Fancy animated version
			mAutoCenterAnimator.setIntValues(targetAngle);
			mAutoCenterAnimator.setDuration(AUTOCENTER_ANIM_DURATION).start();
		} else {
			// Dull non-animated version
			// mPieView.rotateTo(targetAngle);
		}
	}

	/**
	 * Internal child class that draws the pie chart onto a separate hardware
	 * layer when necessary.
	 */
	private class PieView extends View {
		// Used for SDK < 11
		private float mRotation = 0;
		private Matrix mTransform = new Matrix();
		private PointF mPivot = new PointF();

		/**
		 * Construct a PieView
		 * 
		 * @param context
		 */
		public PieView(Context context) {
			super(context);
		}

		/**
		 * Enable hardware acceleration (consumes memory)
		 */
		public void accelerate() {
			setLayerToHW(this);
		}

		/**
		 * Disable hardware acceleration (releases memory)
		 */
		public void decelerate() {
			setLayerToSW(this);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			if (Build.VERSION.SDK_INT < 11) {
				mTransform.set(canvas.getMatrix());
				mTransform.preRotate(mRotation, mPivot.x, mPivot.y);
				canvas.setMatrix(mTransform);
			}

			for (Item it : mData) {
				mPiePaint.setShader(it.mShader);
				canvas.drawArc(mBounds, 360 - it.mEndAngle, it.mEndAngle
						- it.mStartAngle, true, mPiePaint);
			}
			Paint inner_circle_paint = new Paint();
			inner_circle_paint.setStyle(Paint.Style.STROKE);
			inner_circle_paint.setColor(Color.BLACK);
			inner_circle_paint.setStrokeWidth(10f);
			float inner_circle_radius = diameter / 2 - 15;
			canvas.drawCircle(mBounds.centerX(), mBounds.centerY(),
					inner_circle_radius, inner_circle_paint);
			inner_circle_paint.setStrokeWidth(10.5f);
			inner_circle_radius = diameter / 2 - 5;
			inner_circle_paint.setColor(Color.parseColor("#E2B72B"));
			canvas.drawCircle(mBounds.centerX(), mBounds.centerY(),
					inner_circle_radius, inner_circle_paint);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			mBounds = new RectF(0, 0, w, h);
		}

		RectF mBounds;

		public void setPivot(float x, float y) {
			mPivot.x = x;
			mPivot.y = y;
			if (Build.VERSION.SDK_INT >= 11) {
				setPivotX(x);
				setPivotY(y);
			} else {
				invalidate();
			}
		}
	}

	/**
	 * Maintains the state for a data item.
	 */
	private class Item {
		public float mValue;
		public int mColor;

		// computed values
		public int mStartAngle;
		public int mEndAngle;

		public int mHighlight;
		public Shader mShader;
	}
}