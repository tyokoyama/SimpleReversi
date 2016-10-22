package org.takashiyokoyama.simplereversi.ai;

/**
 * Created by yokoyama on 2016/10/22.
 */

/**
 * コマの置き場所を保存するクラス
 */
public class Place {
    public int x;
    public int y;

    public Place() {

    }

    public Place(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
