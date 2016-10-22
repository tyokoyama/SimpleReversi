package org.takashiyokoyama.simplereversi.ai;

/**
 * Created by yokoyama on 2016/10/22.
 */

public class BeginnerImpl implements ComIf {
    @Override
    public Place next(int[][] board, int player) {
        Place p = null;

        // とりあえず、ひっくり返せるところが見つかったらおく。
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (ReversiJudge.judge(board, player, i, j)) {
                    // 置ける
                    p = new Place(i, j);
                    i = board.length - 1;
                    j = board.length - 1;
                }
            }
        }

        return p;
    }
}
