package com.cpxiao.blockvsdots.mode.extra;

/**
 * @author cpxiao on 2017/09/09.
 */

public final class Extra {

    public static final class Key {
        /**
         * 多边形边长数量，可以为3，4，5，6
         */
        public static final String BLOCK_EDGE_COUNT = "BLOCK_EDGE_COUNT";
        public static final int BLOCK_EDGE_COUNT_DEFAULT = 4;

        /**
         * dot数量
         */
        public static final String DOT_COUNT = "DOT_COUNT";
        public static final int DOT_COUNT_DEFAULT = 2;

        /**
         * 最高分
         */
        private static final String BEST_SCORE_FORMAT = "BEST_SCORE_FORMAT_%s";

        public static final String getBestScoreKey(int difficulty) {
            return String.format(BEST_SCORE_FORMAT, difficulty);
        }
    }

    public final class Name {

    }
}
