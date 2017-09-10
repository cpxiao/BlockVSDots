package com.cpxiao.blockvsdots.mode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.cpxiao.blockvsdots.mode.extra.Extra;
import com.cpxiao.gamelib.mode.common.MovingSprite;

/**
 * @author cpxiao on 2017/09/09.
 */

public class Block extends MovingSprite {
    private int mColor;
    private long lastTime = System.currentTimeMillis();
    private Path mPath = new Path();
    private int mBlockEdgeCount;

    private Block(Build build) {
        super(build);
        mColor = build.color;
        mBlockEdgeCount = build.blockEdgeCount;
    }

    @Override
    protected void beforeDraw(Canvas canvas, Paint paint) {
        super.beforeDraw(canvas, paint);
        float deltaTime = System.currentTimeMillis() - lastTime;
        if (deltaTime < 1000) {
            float speedX = 0.99F * getSpeedX();
            setSpeedX(speedX);
            float speedY = getSpeedY() + deltaTime / 1000 * 9.8F * 10;
            setSpeedY(speedY);
        }
        if (DEBUG) {
            Log.d(TAG, "beforeDraw: deltaTime = " + deltaTime);
        }
        lastTime = System.currentTimeMillis();

        //判断移动范围
        RectF movingRangeRectF = getMovingRangeRectF();
        if (movingRangeRectF != null) {
            RectF rectF = getSpriteRectF();
            if (rectF.left <= movingRangeRectF.left || rectF.right >= movingRangeRectF.right) {
                setSpeedX(0.9F * -getSpeedX());
            }
            if (rectF.top <= movingRangeRectF.top || rectF.bottom >= movingRangeRectF.bottom) {
                setSpeedY(0.9F * -getSpeedY());
            }
        }

        int eachAngle = 360 / mBlockEdgeCount;
        mPath.reset();
        float cX = getCenterX();
        float cY = getCenterY();
        mPath.moveTo(cX, cY);
        float delta = 5;
        if (getSpeedX() > 0) {
            delta = -delta;
        }
        for (int i = 0; i <= mBlockEdgeCount; i++) {
            double angle = Math.PI * (i * eachAngle + eachAngle / 2 + getFrame() * delta) / 180;
            float x = (float) (cX + 0.5F * getWidth() * 1.2F * Math.sin(angle));
            float y = (float) (cY + 0.5F * getHeight() * 1.2F * Math.cos(angle));
            mPath.lineTo(x, y);
        }

    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        //        super.onDraw(canvas, paint);
        //        paint.setColor(Color.RED);
        //        canvas.drawRect(getSpriteRectF(), paint);
        paint.setColor(mColor);
        canvas.drawPath(mPath, paint);
    }

    public static class Build extends MovingSprite.Build {
        private int color;
        private int blockEdgeCount = Extra.Key.BLOCK_EDGE_COUNT_DEFAULT;

        public Build setColor(int color) {
            this.color = color;
            return this;
        }

        public Build setBlockEdgeCount(int blockEdgeCount) {
            this.blockEdgeCount = blockEdgeCount;
            return this;
        }

        @Override
        public MovingSprite build() {
            return new Block(this);
        }
    }


}
