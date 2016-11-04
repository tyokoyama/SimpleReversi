package org.takashiyokoyama.simplereversi.ai;

/**
 * Created by yokoyama on 2016/10/31.
 */

public class FutureStatus {
    private Place mPlace;
    private int mWin;
    private int mPlayer;
    private int mBlackCount;
    private int mWhiteCount;

    public int getBlackCount() {
        return mBlackCount;
    }

    public void setBlackCount(int count) {
        this.mBlackCount = count;
    }


    public int getWhiteCount() {
        return mWhiteCount;
    }

    public void setWhiteCount(int count) {
        this.mWhiteCount = count;
    }

    private FutureStatus mNext;

    public FutureStatus getNext() {
        return mNext;
    }

    public void setNext(FutureStatus next) {
        this.mNext = next;
    }

    public FutureStatus(Place place, int player, int win, int black, int white) {
        mPlace = place;
        mPlayer = player;
        mWin = win;
        mBlackCount = black;
        mWhiteCount = white;
    }

    public int getPlayer() { return mPlayer;}

    public Place getPlace() {
        return mPlace;
    }

    public void setPlace(Place mPlace) {
        this.mPlace = mPlace;
    }
}
