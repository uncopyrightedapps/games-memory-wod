package org.uncopyrightedapps.games.memory_wod.media;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GameImageView extends ImageView {
    public GameImageView(Context context) {
        super(context);
    }

    public GameImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}