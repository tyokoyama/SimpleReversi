package org.takashiyokoyama.simplereversi.ai;

import java.util.ArrayList;

/**
 * Created by yokoyama on 2016/10/22.
 */

public class MiddleImpl implements ComIf {

    private int[][] mPointTbl = new int[8][8];
    public int[][] getPointTbl() {
        return mPointTbl;
    }

    public MiddleImpl() {
        // ポイントテーブルの初期化
        for(int i = 0; i < mPointTbl.length; i++) {
            for(int j = 0; j < mPointTbl.length; j++) {
                mPointTbl[i][j] = 1;
            }
        }

        // 端のマスは一番高い
        mPointTbl[0][0] = 4;
        mPointTbl[0][mPointTbl.length-1] = 4;
        mPointTbl[mPointTbl.length-1][0] = 4;
        mPointTbl[mPointTbl.length-1][mPointTbl.length-1] = 4;

        // 端のマス以外の端は次に高い
        for(int i = 2; i < mPointTbl.length - 2; i++) {
            mPointTbl[0][i] = 3;
            mPointTbl[mPointTbl.length - 1][i] = 3;
            mPointTbl[i][0] = 3;
            mPointTbl[i][mPointTbl.length - 1] = 3;
        }

        // 端のマスを挟むためのマスは高い
        mPointTbl[1][2] = 2;
        mPointTbl[1][5] = 2;
        mPointTbl[2][1] = 2;
        mPointTbl[2][2] = 2;
        mPointTbl[2][5] = 2;
        mPointTbl[2][6] = 2;
        mPointTbl[5][1] = 2;
        mPointTbl[5][2] = 2;
        mPointTbl[5][5] = 2;
        mPointTbl[5][6] = 2;
        mPointTbl[6][2] = 2;
        mPointTbl[6][5] = 2;

        // 端の手前のマスは置くと取られるのでポイントが低い
        mPointTbl[1][1] = 0;
        mPointTbl[6][1] = 0;
        mPointTbl[1][6] = 0;
        mPointTbl[6][6] = 0;

    }

    @Override
    public Place next(int[][] board, int player) {
        ArrayList<PointPlace> placeList = new ArrayList<>();

        // 1手先読みポイント制

        // 置ける所を全て列挙する
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (ReversiJudge.judge(board, player, i, j)) {
                    // 置ける
                    PointPlace p = new PointPlace(i, j, mPointTbl[i][j]);
                    placeList.add(p);
                }
            }
        }

        // ポイントの高い所に置けるように考える。
        if(placeList.size() <= 0) return null;

        PointPlace p = null;
        for(PointPlace pp : placeList) {
            if(p == null || p.getPoint() <= pp.getPoint()) {
                p = pp;
            }
        }

        return p;
    }

    private class PointPlace extends Place {
        private int point;

        public PointPlace(int x, int y, int point) {
            super(x, y);
        }

        public int getPoint() {
            return point;
        }

    }
}
