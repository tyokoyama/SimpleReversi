package org.takashiyokoyama.simplereversi.ai;

import java.util.ArrayList;

/**
 * Created by yokoyama on 2016/10/31.
 */

public class HardImpl implements ComIf {
    private int[][] mPointTbl = new int[8][8];

    public HardImpl() {
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

        int minCount = 100;
        int minPlace = 0;
        int minPoint = 0;
        for(int i = 0; i < futureList.size(); i++) {
            FutureStatus s = futureList.get(i);

            while(s.getNext() != null) {
                if(s.getPlace() != null) {
                    System.out.print("->" + s.getPlace().x + ", " + s.getPlace().y + "(" + s.getPlayer() + ")" + ", " + "(" + s.getBlackCount() + ":" + s.getWhiteCount() + ")");
                    if(s.getPlayer() != player) {
                        // 敵が有利な所には置かない
                        if(minPoint > mPointTbl[s.getPlace().x][s.getPlace().y]) {
                            minPoint = mPointTbl[s.getPlace().x][s.getPlace().y];
                            minPlace = i;
                        }
                    } else {
                        // TODO: とりあえず、白固定
                        if (minCount > s.getWhiteCount()) {
                            minCount = s.getWhiteCount();
                            minPlace = i;
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
        return futureList.get(minPlace).getPlace();
    }

    private void futureNext(FutureStatus status, int[][] board, int player, int count) {
        ArrayList<Place> placeList = new ArrayList<Place>();
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

        // 一番相手が置ける場所が少ない場所、かつポイントが一番高いところを選択する。
        int minCount = 100;
        Place minP = placeList.get(0);
        int maxPoint = 0;
        for(Place p : placeList) {
            int[][] planBoard = createCopyBoard(board);
            planBoard = ReversiChange.change(planBoard, player, p.x, p.y);
            int currentCount = 0;
            for(int i = 0; i < planBoard.length; i++) {
                for(int j = 0; j < planBoard.length; j++) {
                    if(ReversiJudge.judge(board, (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE, i, j)) {
                        currentCount++;
                    }
                }
            }
            if(minCount > currentCount) {
                minCount = currentCount;
                maxPoint = mPointTbl[p.x][p.y];
                minP = p;
            } else {
                if(maxPoint < mPointTbl[p.x][p.y]) {
                    minCount = currentCount;
                    maxPoint = mPointTbl[p.x][p.y];
                    minP = p;
                }
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
        int enemy =  (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE;
        if(getCount(board, player) > getCount(board, enemy)) {
            win = player;
        }

        FutureStatus s = new FutureStatus(new Place(minP.x, minP.y), player, win, getCount(board, ReversiJudge.BLACK), getCount(board, ReversiJudge.WHITE));
        status.setNext(s);

        if(count <= 10) {
            futureNext(status.getNext(), board, (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE, count + 1);
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
}
