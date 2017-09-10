package com.cpxiao.blockvsdots.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.blockvsdots.mode.extra.Extra;
import com.cpxiao.gamelib.fragment.BaseFragment;
import com.cpxiao.zads.utils.PrefUtils;

import java.text.DecimalFormat;

/**
 * @author cpxiao on 2017/9/8.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Button play = (Button) view.findViewById(R.id.play);
        Button easy = (Button) view.findViewById(R.id.easy);
        Button normal = (Button) view.findViewById(R.id.normal);
        Button hard = (Button) view.findViewById(R.id.hard);
        Button insane = (Button) view.findViewById(R.id.insane);
        Button bestScore = (Button) view.findViewById(R.id.best_score);
        Button settings = (Button) view.findViewById(R.id.settings);
        Button quit = (Button) view.findViewById(R.id.quit);

        play.setOnClickListener(this);
        easy.setOnClickListener(this);
        normal.setOnClickListener(this);
        hard.setOnClickListener(this);
        insane.setOnClickListener(this);
        bestScore.setOnClickListener(this);
        settings.setOnClickListener(this);
        quit.setOnClickListener(this);

        easy.setVisibility(View.GONE);
        normal.setVisibility(View.GONE);
        hard.setVisibility(View.GONE);
        insane.setVisibility(View.GONE);
        settings.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Context context = getHoldingActivity();
        Bundle bundle = new Bundle();
        int blockEdgeCount = PrefUtils.getInt(context, Extra.Key.BLOCK_EDGE_COUNT, Extra.Key.BLOCK_EDGE_COUNT_DEFAULT);
        bundle.putInt(Extra.Key.BLOCK_EDGE_COUNT, blockEdgeCount);
        if (id == R.id.play) {
            bundle.putInt(Extra.Key.DOT_COUNT, 2);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.easy) {
            bundle.putInt(Extra.Key.DOT_COUNT, 2);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.normal) {
            bundle.putInt(Extra.Key.DOT_COUNT, 3);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.hard) {
            bundle.putInt(Extra.Key.DOT_COUNT, 4);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.insane) {
            bundle.putInt(Extra.Key.DOT_COUNT, 5);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.best_score) {
            showBestScoreDialog(context);
        } else if (id == R.id.settings) {

        } else if (id == R.id.quit) {
            removeFragment();
        }
    }


    private void showBestScoreDialog(Context context) {
        String key = Extra.Key.getBestScoreKey(2);
        long best = PreferencesUtils.getLong(context, key, 0);
        String msg = timeFormat(best);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.best_score)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    public String timeFormat(long time) {
        double result = (double) time / 1000.0;
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(result) + "\"";
    }
}
