package tatteam.com.app;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import tatteam.com.R;

/**
 * Created by ThanhNH on 2/16/2016.
 */
public class FlatEffectDark extends RelativeLayout {
    private View highlight;
    private int color;

    public FlatEffectDark(Context context) {
        super(context);
    }

    public FlatEffectDark(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public FlatEffectDark(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        this.onTouchEvent(event);
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            highlight.setBackgroundColor(color);
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            highlight.setBackgroundColor(Color.TRANSPARENT);
        }
        return super.onTouchEvent(event);
    }

    private void setup() {
        color = Color.parseColor("#8db1acad");
        this.setClickable(true);
        highlight = new FrameLayout(getContext());
        highlight.setBackgroundColor(Color.TRANSPARENT);
        this.post(new Runnable() {
            @Override
            public void run() {
                highlight.setLayoutParams(new LayoutParams(FlatEffectDark.this.getWidth(), FlatEffectDark.this.getHeight()));
                addView(highlight);
            }
        });
    }

}
