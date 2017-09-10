package com.cpxiao.blockvsdots.mode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cpxiao.gamelib.mode.common.MovingSprite;

/**
 * @author cpxiao on 2017/09/09.
 */

public class Dot extends MovingSprite {
    private int mColor;

    private Dot(Build build) {
        super(build);
        mColor = build.color;
    }

    @Override
    protected void beforeDraw(Canvas canvas, Paint paint) {
        super.beforeDraw(canvas, paint);
        if (Math.random() < 0.001) {
            setSpeedX(-getSpeedX());
        }
        if (Math.random() < 0.001) {
            setSpeedY(-getSpeedY());
        }

        //判断移动范围
        RectF movingRangeRectF = getMovingRangeRectF();
        if (movingRangeRectF != null) {
            RectF rectF = getSpriteRectF();
            if (rectF.left <= movingRangeRectF.left || rectF.right >= movingRangeRectF.right) {
                setSpeedX(-getSpeedX());
            }
            if (rectF.top <= movingRangeRectF.top || rectF.bottom >= movingRangeRectF.bottom) {
                setSpeedY(-getSpeedY());
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        //        super.onDraw(canvas, paint);
        paint.setColor(mColor);
        canvas.drawCircle(getCenterX(), getCenterY(), 0.5F * getWidth(), paint);
    }

    public static class Build extends MovingSprite.Build {
        private int color;

        public Build setColor(int color) {
            this.color = color;
            return this;
        }

        @Override
        public MovingSprite build() {
            return new Dot(this);
        }
    }
}
