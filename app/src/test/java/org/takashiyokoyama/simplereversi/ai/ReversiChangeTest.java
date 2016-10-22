package org.takashiyokoyama.simplereversi.ai;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yokoyama on 2016/10/22.
 */

public class ReversiChangeTest {
    @Test
    public void changeTest() throws Exception {
        // 板の状態を初期化
        int[][] mBoard = new int[8][8];
        for(int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = 0;
            }
        }
        mBoard[3][4] = 2;
        mBoard[4][3] = 2;
        mBoard[4][4] = 1;
        mBoard[3][3] = 1;

        mBoard = ReversiChange.change(mBoard, 1, 5, 3);
        assertEquals(mBoard[5][3], 1);
        assertEquals(mBoard[3][4], 2);
        assertEquals(mBoard[4][3], 1);
        assertEquals(mBoard[4][4], 1);
        assertEquals(mBoard[3][3], 1);
    }
}
