package org.takashiyokoyama.simplereversi.ai;

/**
 * Created by yokoyama on 2016/08/31.
 */

public class ReversiJudge {
    public static final int NONE = -1;
    public static final int SPACE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public static boolean judge(int[][] board, int player, int x, int y) {
        if(board[x][y] != SPACE) return false;

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) continue;
                boolean ret = check(board, player, x, y, i, j, 0);
                System.out.println(String.format("%d, %d = %d[%b]", x, x, board[x][y], ret));
                if(ret) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean check(int[][] board, int player, int x, int y, int moveX, int moveY, int count) {
        int nextX = x + moveX;
        int nextY = y + moveY;

        if((nextX < 0 || nextX >= board.length) ||
           (nextY < 0 || nextY >= board[0].length)) {
            return false;
        }

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
