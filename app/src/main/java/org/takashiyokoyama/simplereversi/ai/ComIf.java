package org.takashiyokoyama.simplereversi.ai;

/**
 * Created by yokoyama on 2016/10/22.
 */

public interface ComIf {
    public Place next(int[][] board, int player);
}
