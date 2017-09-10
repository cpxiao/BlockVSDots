package com.cpxiao.blockvsdots.views;

import android.content.Context;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cpxiao.R;
import com.cpxiao.blockvsdots.OnGameListener;
import com.cpxiao.blockvsdots.mode.Block;
import com.cpxiao.blockvsdots.mode.Dot;
import com.cpxiao.blockvsdots.mode.extra.Extra;
import com.cpxiao.gamelib.mode.common.SpriteControl;
import com.cpxiao.gamelib.views.BaseSurfaceViewFPS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cpxiao on 2017/09/08.
 */

public class GameView extends BaseSurfaceViewFPS {
    private int mBlockEdgeCount = Extra.Key.BLOCK_EDGE_COUNT_DEFAULT;
    private int mDotCount = 2;
    private Block mBlock;
    private List<Dot> mDotList = new ArrayList<>();
    private OnGameListener mOnGameListener;


    public GameView(Context context, int blockEdgeCount, int dotCount) {
        super(context);
        mBlockEdgeCount = blockEdgeCount;
        mDotCount = dotCount;
    }

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void timingLogic() {
        if (mDotList != null) {
            for (Dot dot : mDotList) {
                if (SpriteControl.isCollidedByTwoSprite(mBlock, dot)) {
                    //game over
                    if (mOnGameListener != null) {
                        mOnGameListener.onSuccess();
                    }
                }
            }
        }
    }


    @Override
    protected void initWidget() {
        float r = 0.08F * mViewWidth;

        mBlock = (Block) new Block.Build()
                .setColor(ContextCompat.getColor(getContext(), R.color.colorBlock))
                .setBlockEdgeCount(mBlockEdgeCount)
                .setMovingRangeRectF(new RectF(0, 0, mViewWidth, mViewHeight))
                .setSpeedX(0.01F * mViewWidth)
                .setSpeedY(5)
                .setW(r)
                .setH(r)
                .centerTo(0.5F * mViewWidth, 0.6F * mViewHeight)
                .build();
        mBlock.setCollideRectFPercent(0.6F);

        for (int i = 0; i < mDotCount; i++) {
            float cX = i * mViewWidth / mDotCount;
            addDot(cX);
        }

    }

    private void addDot(float cX) {
        float r = 0.08F * mViewWidth;
        float speed = (float) (0.007F * mViewWidth + 2 * Math.random());
        Dot dot = (Dot) new Dot.Build()
                .setColor(ContextCompat.getColor(getContext(), R.color.colorDot))
                .setMovingRangeRectF(new RectF(0, 0, mViewWidth, mViewHeight))
                .setSpeedX(speed)
                .setSpeedY(speed)
                .setW(r)
                .setH(r)
                .centerTo(cX, r)
                .build();
        dot.setCollideRectFPercent(0.8F);
        mDotList.add(dot);
    }


    @Override
    public void drawCache() {
        if (mFrame % (30000 / (1000 / mFPS)) == 0) {
            addDot(0.5F * mViewWidth);
        }
        mBlock.draw(mCanvasCache, mPaint);
        for (Dot dot : mDotList) {
            dot.draw(mCanvasCache, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            float speedX = 0.012F * mViewHeight;
            if (event.getX() < 0.5F * mViewWidth) {
                // left
                mBlock.setSpeedX(-speedX);
            } else {
                // right
                mBlock.setSpeedX(speedX);
            }
            mBlock.setSpeedY(-0.018F * mViewHeight);
        }
        return true;
    }

    public void setOnGameListener(OnGameListener onGameListener) {
        mOnGameListener = onGameListener;
    }
}
