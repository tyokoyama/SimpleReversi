package org.takashiyokoyama.simplereversi.ai;

import java.util.ArrayList;

/**
 * Created by yokoyama on 2016/10/22.
 */

public class MiddleImpl implements ComIf {

    private int[][] mPointTbl;
    public int[][] getPointTbl() {
        return mPointTbl;
    }

    public MiddleImpl() {
        // ポイントテーブルの初期化
        mPointTbl = new int[][] {
                {30, -12, 0, -1, -1, 0, -12, 30},
                {-12, -15, -3, -3, -3, -3, -15, -12},
                {0, -3, 0, -1, -1, 0, -3, 0},
                {-1, -3, -1, -1, -1, -1, -3, -1},
                {-1, -3, -1, -1, -1, -1, -3, -1},
                {0, -3, 0, -1, -1, 0, -3, 0},
                {-12, -15, -3, -3, -3, -3, -15, -12},
                {30, -12, 0, -1, -1, 0, -12, 30},
        };
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
                    int[][] planBoard = createCopyBoard(board);
                    planBoard = ReversiChange.change(planBoard, player, i, j);

                    int sumPoint = getPointSummery(planBoard, player);

                    PointPlace p = new PointPlace(i, j, sumPoint);
                    placeList.add(p);
                }
            }
        }

        // ポイントの高い所に置けるように考える。
        if(placeList.size() <= 0) return null;

        PointPlace p = null;
        for(PointPlace pp : placeList) {
            System.out.println("x = " + pp.x + " y = " + pp.y + " point = " + pp.getPoint());
            if(p == null || p.getPoint() <= pp.getPoint()) {
                p = pp;
            }
        }

        return p;
    }

    private class PointPlace extends Place {
        private int mPoint;

        public PointPlace(int x, int y, int point) {
            super(x, y);
            mPoint = point;
        }

        public int getPoint() {
            return mPoint;
        }

    }

    private int[][] createCopyBoard(int[][] board) {
        int[][] copy = new int[board.length][board.length];

        // ボードのコピー
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                copy[x][y] = board[x][y];
            }
        }

        return copy;
    }

    private int getPointSummery(int[][] board, int player) {
        int sumPoint = 0;
        for(int m = 0; m < board.length; m++) {
            for(int n = 0; n < board.length; n++) {
                if(board[m][n] == player) {
                    sumPoint += mPointTbl[m][n];
                }
            }
        }

        return sumPoint;
    }
}
