package org.takashiyokoyama.simplereversi.ai;

import java.util.ArrayList;

/**
 * Created by yokoyama on 2016/10/31.
 */

public class HardImpl implements ComIf {

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
        for(int i = 0; i < futureList.size(); i++) {
            FutureStatus s = futureList.get(i);
            while(s.getNext() != null) {
                // TODO: とりあえず、白固定
                if(minCount > s.getWhiteCount()) {
                    minCount = s.getWhiteCount();
                    minPlace = i;
                }

                if(s.getPlace() != null) {
                    System.out.print("->" + s.getPlace().x + ", " + s.getPlace().y + "(" + s.getPlayer() + ")" + ", " + "(" + s.getBlackCount() + ":" + s.getWhiteCount() + ")");
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
        // 置ける場所を探す
        for (int m = 0; m < board.length; m++) {
            for (int n = 0; n < board.length; n++) {
                if (ReversiJudge.judge(board, player, m, n)) {
                    // TODO: とりあえず最初に挟める場所を選択
                    board = ReversiChange.change(board, player, m, n);

                    int win = 0;
                    int enemy =  (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE;
                    if(getCount(board, player) > getCount(board, enemy)) {
                        win = player;
                    }

                    FutureStatus s = new FutureStatus(new Place(m, n), player, win, getCount(board, ReversiJudge.BLACK), getCount(board, ReversiJudge.WHITE));
                    status.setNext(s);

                    if(count <= 10) {
                        futureNext(status.getNext(), board, (player == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE, count + 1);
                    }
                }
            }
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
