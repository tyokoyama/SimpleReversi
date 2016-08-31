package org.takashiyokoyama.simplereversi.ai;

/**
 * Created by yokoyama on 2016/08/31.
 */

public class ReversiChange {
    public static final int NONE = -1;
    public static final int SPACE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public static int[][] change(int[][] board, int player, int x, int y) {
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) continue;
                boolean ret = check(board, player, x, y, i, j, 0);
                if(ret) {
                    // ひっくり返す処理
                    int nextX = x + i;
                    int nextY = y + j;
                    while((nextX >= 0 && nextX < board.length) &&
                            (nextY >= 0 && nextY < board.length) &&
                            (board[nextX][nextY] != SPACE) && (board[nextX][nextY] != player)) {
                        board[nextX][nextY] = player;
                        nextX += i;
                        nextY += j;
                    }
                }
            }
        }

        return board;
    }

    private static boolean check(int[][] board, int player, int x, int y, int moveX, int moveY, int count) {
        int nextX = x + moveX;
        int nextY = y + moveY;

        if((nextX < 0 || nextX >= board.length) ||
                (nextY < 0 || nextY >= board[0].length)) {
            return false;
        }

        System.out.println(String.format("%d, %d = %d", nextX, nextY, board[nextX][nextY]));

        if(board[nextX][nextY] == SPACE) return false;

        if(board[nextX][nextY] != SPACE &&
                board[nextX][nextY] != player) {
            // 次を探索
            return check(board, player, nextX, nextY, moveX, moveY, count + 1);
        }

        if(board[nextX][nextY] == player && count > 0) return true;

        return false;
    }
}
