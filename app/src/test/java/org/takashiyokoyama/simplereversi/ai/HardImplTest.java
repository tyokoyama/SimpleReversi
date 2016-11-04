package org.takashiyokoyama.simplereversi.ai;

import org.junit.Test;

/**
 * Created by yokoyama on 2016/10/31.
 */

public class HardImplTest {
    @Test
    public void HardImplTest() {
        HardImpl impl = new HardImpl();

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

        impl.next(mBoard, ReversiJudge.BLACK);
    }
}
