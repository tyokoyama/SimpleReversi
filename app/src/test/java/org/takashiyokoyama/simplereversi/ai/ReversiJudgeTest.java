package org.takashiyokoyama.simplereversi.ai;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by yokoyama on 2016/08/31.
 */
public class ReversiJudgeTest {
    @Test
    public void judge() throws Exception {
        // 板の状態を初期化
        int[][] mBoard = new int[8][8];
        for(int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = 0;
            }
        }
        mBoard[3][4] = 1;
        mBoard[4][3] = 1;
        mBoard[4][4] = 2;
        mBoard[3][3] = 2;


        assertFalse(ReversiJudge.judge(mBoard, 1, 2, 2));
        assertTrue(ReversiJudge.judge(mBoard, 1, 2, 3));
        assertFalse(ReversiJudge.judge(mBoard, 1, 5, 2));
        assertFalse(ReversiJudge.judge(mBoard, 1, 3, 3));           // 白がおいてある
        assertFalse(ReversiJudge.judge(mBoard, 1, 3, 4));           // 黒がおいてある。
    }

}