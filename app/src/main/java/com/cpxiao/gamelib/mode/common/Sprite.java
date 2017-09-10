package com.cpxiao.gamelib.mode.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cpxiao.AppConfig;

/**
 * @author cpxiao on 2017/7/16.
 * @version cpxiao on 2017/9/8.删除无用方法
 */

public class Sprite {

    protected boolean DEBUG = AppConfig.DEBUG;
    protected String TAG = getClass().getSimpleName();

    private float x, y;//坐标
    private float width, height;//宽高

    /**
     * 精灵矩形
     */
    private RectF mSpriteRectF = new RectF();

    /**
     * 碰撞矩形，根据精灵矩形的百分比计算
     */
    private float mCollideRectFPercentW = 1, mCollideRectFPercentH = 1;
    private RectF mCollideRectF = new RectF();


    private boolean visible = true;//是否可见
    private boolean destroyed = false;//是否已销毁
    private long mFrame = 0;//绘制的次数

    private Bitmap bitmap = null;

    private Sprite() {
    }

    protected Sprite(Build build) {
        x = build.x;
        y = build.y;
        width = build.w;
        height = build.h;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setFrame(long frame) {
        mFrame = frame;
    }

    public long getFrame() {
        return mFrame;
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisibility() {
        return visible;
    }

    public float getCenterX() {
        return x + 0.5F * width;
    }

    public float getCenterY() {
        return y + 0.5F * height;
    }

    public RectF getSpriteRectF() {
        mSpriteRectF.left = x;
        mSpriteRectF.top = y;
        mSpriteRectF.right = mSpriteRectF.left + getWidth();
        mSpriteRectF.bottom = mSpriteRectF.top + getHeight();
        return mSpriteRectF;
    }

    public void setCollideRectFPercent(float percent) {
        mCollideRectFPercentW = percent;
        mCollideRectFPercentH = percent;
    }

    public void setCollideRectFPercent(float percentW, float percentH) {
        mCollideRectFPercentW = percentW;
        mCollideRectFPercentH = percentH;
    }

    public RectF getCollideRectF() {
        mCollideRectF.left = getCenterX() - 0.5F * mCollideRectFPercentW * getWidth();
        mCollideRectF.top = getCenterY() - 0.5F * mCollideRectFPercentH * getHeight();
        mCollideRectF.right = getCenterX() + 0.5F * mCollideRectFPercentW * getWidth();
        mCollideRectF.bottom = getCenterY() + 0.5F * mCollideRectFPercentH * getHeight();
        return mCollideRectF;
    }

    public void moveBy(float offsetX, float offsetY) {
        x += offsetX;
        y += offsetY;
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void centerTo(float centerX, float centerY) {
        float w = getWidth();
        float h = getHeight();
        x = centerX - 0.5F * w;
        y = centerY - 0.5F * h;
    }

    public void draw(Canvas canvas, Paint paint) {
        mFrame++;
        beforeDraw(canvas, paint);
        onDraw(canvas, paint);
        afterDraw(canvas, paint);
    }

    protected void beforeDraw(Canvas canvas, Paint paint) {

    }

    public void onDraw(Canvas canvas, Paint paint) {

    }

    protected void afterDraw(Canvas canvas, Paint paint) {

    }


    public static class Build {
        private float x;
        private float y;
        private float w;
        private float h;
        private Bitmap mBitmap;

        public Build setX(float x) {
            this.x = x;
            return this;
        }

        public Build setY(float y) {
            this.y = y;
            return this;
        }

        public Build setW(float w) {
            this.w = w;
            return this;
        }

        public Build setH(float h) {
            this.h = h;
            return this;
        }

        public Build centerTo(float centerX, float centerY) {
            x = centerX - 0.5F * w;
            y = centerY - 0.5F * h;
            return this;
        }


        public void setBitmap(Bitmap bitmap) {
            mBitmap = bitmap;
        }

        public Sprite build() {
            return new Sprite(this);
        }
    }

}
