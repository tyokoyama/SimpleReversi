package org.takashiyokoyama.simplereversi.ai;

import java.util.ArrayList;

/**
 * Created by yokoyama on 2016/10/31.
 */

public class HardImpl implements ComIf {
    private int[][] mPointTbl;

    public HardImpl() {
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
        ArrayList<FutureStatus> futureList = new ArrayList<FutureStatus>();
        FutureStatus status = null;

        // 置ける場所を探す。
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (ReversiJudge.judge(board, player, i, j)) {
                    // 置ける

                    int[][] futureBoard = new int[8][8];

                    // ボードのコピー
                    futureBoard = createCopyBoard(board);

                    // 初手をひっくり返す。
                    futureBoard = ReversiChange.change(futureBoard, player, i, j);
                    int enemy =  (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE;

                    // 10手先まで読む
                    status = new FutureStatus(new Place(i, j), player, 0, getCount(board, ReversiJudge.BLACK), getCount(board, ReversiJudge.WHITE));
                    futureNext(status, futureBoard, enemy, 0);

                    futureList.add(status);
                }
            }
        }

        int maxPlace = 0;
        int maxPoint = -1000;
        for(int i = 0; i < futureList.size(); i++) {
            FutureStatus s = futureList.get(i);

            while(s.getNext() != null) {
                if(s.getPlace() != null) {
                    System.out.print("->" + s.getPlace().x + ", " + s.getPlace().y + "(" + s.getPlayer() + ")" + ", " + "(" + s.getBlackCount() + ":" + s.getWhiteCount() + ")");
                    if(s.getPlayer() == player && s.getPlayer() == ReversiJudge.BLACK) {
                        // 黒
                        if(maxPoint < s.getBlackCount()) {
                            maxPoint = s.getBlackCount();
                            maxPlace = i;
                        }
                    } else {
                        // 白
                        if(maxPoint < s.getWhiteCount()) {
                            maxPoint = s.getWhiteCount();
                            maxPlace = i;
                        }
                    }
                } else {
                    System.out.print("->-, -" + "(" + s.getPlayer() + ")" + ", " + "(" + s.getBlackCount() + ":" + s.getWhiteCount() + ")");
                }
                s = s.getNext();
            }

            System.out.println();
        }

        if(futureList.size() == 0) return null;
        return futureList.get(maxPlace).getPlace();
    }

    private void futureNext(FutureStatus status, int[][] board, int player, int count) {
        ArrayList<Place> placeList = new ArrayList<Place>();
        int enemy =  (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE;

        // 置ける場所を探す
        for (int m = 0; m < board.length; m++) {
            for (int n = 0; n < board.length; n++) {
                if(ReversiJudge.judge(board, player, m, n)) {
                    Place p = new Place(m, n);
                    placeList.add(p);
                }
            }
        }

        if(placeList.size() <= 0) return;

        // ポイントが一番高いところを選択する。
        int maxPoint = 0;
        Place minP = placeList.get(0);
        for(Place p : placeList) {
            int[][] planBoard = createCopyBoard(board);
            planBoard = ReversiChange.change(planBoard, player, p.x, p.y);
            int currentPoint = getPointSummery(planBoard, enemy);
            if(maxPoint < currentPoint) {
                maxPoint = currentPoint;
                minP = p;
            }
        }
        // 一番枚数が少ない場所を選択する。
//        int minGetCount = 100;
//        Place minP = placeList.get(0);
//        for(Place p : placeList) {
//            int playerCount = getCount(board, player);
//            int enemyCount = getCount(board, (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE);
//
//            int[][] planBoard = createCopyBoard(board);
//            planBoard = ReversiChange.change(planBoard, player, p.x, p.y);
//
//            int addCount = getCount(planBoard, player);
//            if(minGetCount > addCount) {
//                minGetCount = addCount;
//                minP = p;
//            }
//        }

        int win = 0;
        if(getPointSummery(board, player) > getPointSummery(board, enemy)) {
            win = player;
        }

        FutureStatus s = new FutureStatus(new Place(minP.x, minP.y), player, win, getPointSummery(board, ReversiJudge.BLACK), getPointSummery(board, ReversiJudge.WHITE));
        status.setNext(s);

        if(count <= 10) {
            futureNext(status.getNext(), board, enemy, count + 1);
        }

    }

    private int getCount(int[][] board, int player) {
        int count = 0;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == player) {
                    count++;
                }
            }
        }

        return count;
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
        for (int m = 0; m < board.length; m++) {
            for (int n = 0; n < board.length; n++) {
                if (board[m][n] == player) {
                    sumPoint += mPointTbl[m][n];
                }
            }
        }

        return sumPoint;
    }
}
