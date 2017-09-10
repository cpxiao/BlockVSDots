package com.cpxiao.blockvsdots.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.blockvsdots.OnGameListener;
import com.cpxiao.blockvsdots.mode.extra.Extra;
import com.cpxiao.blockvsdots.views.GameView;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;
import com.cpxiao.gamelib.views.TimeTextView;
import com.cpxiao.zads.utils.ThreadUtils;

/**
 * @author cpxiao on 2017/09/09.
 */

public class GameFragment extends BaseZAdsFragment implements OnGameListener {
    private TimeTextView mTimeTextView;
    private LinearLayout layout;
    private int blockEdgeCount = Extra.Key.BLOCK_EDGE_COUNT_DEFAULT;
    private int dotCount = Extra.Key.BLOCK_EDGE_COUNT_DEFAULT;

    private boolean isSuccess = false;

    public static GameFragment newInstance(Bundle bundle) {
        GameFragment fragment = new GameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            blockEdgeCount = bundle.getInt(Extra.Key.BLOCK_EDGE_COUNT);
            dotCount = bundle.getInt(Extra.Key.DOT_COUNT);
        }

        mTimeTextView = (TimeTextView) view.findViewById(R.id.timeTextView);
        layout = (LinearLayout) view.findViewById(R.id.layout_game_view);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimeTextView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimeTextView.pause();
    }

    private void initData() {
        isSuccess = false;
        mTimeTextView.setTimeMillis(0);
        mTimeTextView.start();
        initGameView();
    }

    private void initGameView() {

        Context context = getHoldingActivity();
        GameView gameView = new GameView(context, blockEdgeCount, dotCount);
        gameView.setOnGameListener(this);
        layout.removeAllViews();
        layout.addView(gameView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    public void onSuccess() {
        if (isSuccess) {
            return;
        }
        isSuccess = true;
        mTimeTextView.pause();
        ThreadUtils.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                showDialog();
            }
        });


    }

    private void showDialog() {
        Context context = getHoldingActivity();
        String key = Extra.Key.getBestScoreKey(dotCount);
        long best = PreferencesUtils.getLong(context, key, 0);
        if (mTimeTextView.getTimeMillis() > best) {
            PreferencesUtils.putLong(context, key, mTimeTextView.getTimeMillis());
            best = mTimeTextView.getTimeMillis();
        }
        String msg = getString(R.string.score) + ": " + mTimeTextView.getTimeMillisFormat() + "\n"
                + getString(R.string.best_score) + ": " + mTimeTextView.timeFormat(best);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.game_over)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        initData();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        removeFragment();
                    }
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}
