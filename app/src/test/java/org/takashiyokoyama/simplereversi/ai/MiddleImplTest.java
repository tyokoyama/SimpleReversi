package org.takashiyokoyama.simplereversi.ai;

import org.junit.Test;

/**
 * Created by yokoyama on 2016/10/22.
 */

public class MiddleImplTest {
    @Test
    public void MiddleImplTest() throws Exception {
        MiddleImpl impl = new MiddleImpl();

        int[][] initBoard = impl.getPointTbl();

        for(int i = 0; i < initBoard.length; i++) {
            for(int j = 0; j < initBoard[i].length; j++) {
                System.out.print(initBoard[i][j]);
            }
            System.out.println();
        }
    }
}
