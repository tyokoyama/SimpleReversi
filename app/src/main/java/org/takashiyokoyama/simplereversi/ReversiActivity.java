package org.takashiyokoyama.simplereversi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.takashiyokoyama.simplereversi.ai.ReversiChange;
import org.takashiyokoyama.simplereversi.ai.ReversiJudge;
import org.takashiyokoyama.simplereversi.listener.OnBoardTapListener;
import org.takashiyokoyama.simplereversi.view.ReversiView;

public class ReversiActivity extends AppCompatActivity implements OnBoardTapListener {

    private int[][] mBoard;
    private ReversiView mReversiView;
    private int mPlayer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi);

        mReversiView = (ReversiView)findViewById(R.id.reversiview);
        mReversiView.setOnBoardTapListener(this);

        // 板の状態を初期化
        mBoard = new int[8][8];
        for(int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = 0;
            }
        }
        mBoard[3][4] = 1;
        mBoard[4][3] = 1;
        mBoard[4][4] = 2;
        mBoard[3][3] = 2;

        mReversiView.setBoard(mBoard);
    }

    @Override
    public void onTap(int x, int y) {
        Log.d("TEST", String.format("X = %d, Y = %d", x, y));

        // 置けるかどうかの判定
        if(ReversiJudge.judge(mBoard, mPlayer, x, y)) {

            // 置けるときはひっくり返す。
            mBoard = ReversiChange.change(mBoard, mPlayer, x, y);
            mBoard[x][y] = mPlayer;

            mReversiView.setBoard(mBoard);
            mReversiView.invalidate();

            mPlayer = (mPlayer == 1) ? 2 : 1;
        }
    }
}
