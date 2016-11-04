package org.takashiyokoyama.simplereversi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.takashiyokoyama.simplereversi.ai.ReversiJudge;
import org.takashiyokoyama.simplereversi.listener.OnBoardTapListener;

/**
 * Created by yokoyama on 2016/08/31.
 */

public class ReversiView extends View {

    private Context mContext;

    private OnBoardTapListener mListener;

    private DisplayMetrics mMetrics;

    //TODO: 暫定的にボードの状態を持つ
    private int[][] mBoard;

    public void setOnBoardTapListener(OnBoardTapListener mListener) {
        this.mListener = mListener;
    }

    public void setBoard(int[][] board) {
        mBoard = board;
    }

    public ReversiView(Context context) {
        super(context);
        initialize(context);
    }

    public ReversiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipRect(0, 0, this.getWidth(), this.getHeight());
        Paint p = new Paint();

        Log.d("TEST", String.format("scaledDensity = %f", mMetrics.scaledDensity));

        p.setARGB(255, 0, 0x66, 0);
        canvas.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), p);

        p.setARGB(255, 0, 0, 0);
        float width = getBoardWidth();
        p.setStrokeWidth(1 * mMetrics.scaledDensity);
        for(int i = 0; i <= 8; i++) {
            float x = width / 8;

            // 横線を引く
            canvas.drawLine(0.0f, x * i, width, x * i, p);

            // 縦線を引く
            canvas.drawLine(x * i, 0.0f, x * i, width, p);
        }

        // TODO:コマの状態を描画する処理を追加する。
        for(int i = 0; i < mBoard.length; i++) {
            for(int j = 0; j < mBoard[i].length; j++) {
                float x = width / 8;
                switch(mBoard[i][j]) {
                    case 0:
                        // 空白
                        break;
                    case 1:
                        // 黒
                        p.setARGB(255, 0, 0, 0);
                        canvas.drawCircle((x * i) + (x / 2), (x * j) + (x / 2), x / 2, p);
                        break;
                    case 2:
                        // 白
                        p.setARGB(255, 255, 255, 255);
                        canvas.drawCircle((x * i) + (x / 2), (x * j) + (x / 2), x / 2, p);
                        break;
                }
            }
        }

        p.setARGB(255, 255, 255, 255);
        p.setTextSize(16 * mMetrics.scaledDensity);
        canvas.drawText("黒: " + getCount(ReversiJudge.BLACK) + "白: " + getCount(ReversiJudge.WHITE), 30, this.getHeight() - 50f, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float width = getBoardWidth();
        float x = event.getX();
        float y = event.getY();
        if(x >= 0 &&
           x <= width &&
           y >= 0 &&
           y <= width) {

            // 盤の中をタップされた時のみ処理する。

            // 座標算出
            int posX = (int)(x / (width / 8));
            int posY = (int)(y / (width / 8));

            if(mListener != null) {
                mListener.onTap(posX, posY);
            }
        }

        return super.onTouchEvent(event);
    }

    private int getCount(int player) {
        int count = 0;

        for(int i = 0; i < mBoard.length; i++) {
            for(int j = 0; j < mBoard[i].length; j++) {
                if(mBoard[i][j] == player) {
                    count++;
                }
            }
        }

        return count;
    }

    /***
     * Viewの初期化
     * @param context
     */
    private void initialize(Context context) {
        mContext = context;
        mListener = null;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        mMetrics = new DisplayMetrics();
        disp.getMetrics(mMetrics);

        // 板の状態を初期化
        mBoard = new int[8][8];
        for(int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = ReversiJudge.SPACE;
            }
        }
    }

    /***
     * 盤の幅を求める。
     *
     * @return
     */
    private float getBoardWidth() {
        float width = 0.0f;
        if(this.getWidth() > this.getHeight()) {
            width = this.getHeight();
        } else {
            width = this.getWidth();
        }
        return width;
    }
}
