package org.takashiyokoyama.simplereversi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.takashiyokoyama.simplereversi.ai.BeginnerImpl;
import org.takashiyokoyama.simplereversi.ai.ComIf;
import org.takashiyokoyama.simplereversi.ai.MiddleImpl;
import org.takashiyokoyama.simplereversi.ai.Place;
import org.takashiyokoyama.simplereversi.ai.ReversiChange;
import org.takashiyokoyama.simplereversi.ai.ReversiJudge;
import org.takashiyokoyama.simplereversi.listener.OnBoardTapListener;
import org.takashiyokoyama.simplereversi.view.ReversiView;

public class ReversiActivity extends AppCompatActivity implements OnBoardTapListener {

    private int[][] mBoard;
    private ReversiView mReversiView;
    private int mPlayer = 1;
    private int mComPlay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi);

        Intent intent = getIntent();
        mComPlay = intent.getIntExtra("com", 0);

        mReversiView = (ReversiView)findViewById(R.id.reversiview);
        mReversiView.setOnBoardTapListener(this);

        // 板の状態を初期化
        mBoard = new int[8][8];
        for(int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = ReversiJudge.SPACE;
            }
        }
        mBoard[3][4] = ReversiJudge.WHITE;
        mBoard[4][3] = ReversiJudge.WHITE;
        mBoard[4][4] = ReversiJudge.BLACK;
        mBoard[3][3] = ReversiJudge.BLACK;

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

            // COM戦のときはコンピュータの処理を行う。
            if(mComPlay > 0) {
                // COM戦
                boolean judge = false;
                while(!judge) {
                    int player = (mPlayer == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE;

                    ComIf com = null;
                    switch(mComPlay) {
                        case 1:
                            // Beginner
                            com = new BeginnerImpl();
                            break;
                        case 2:
                            // Middle
                            com = new MiddleImpl();
                            break;
                        case 3:
                            // Hard
                            com = new BeginnerImpl();
                            break;
                    }
                    Place p = com.next(mBoard, player);
                    if(p != null) {
                        mBoard = ReversiChange.change(mBoard, player, p.x, p.y);
                    } else {
                        break;
                    }

                    // 挟むところがあるかどうかをチェックする。
                    for (int i = 0; i < mBoard.length; i++) {
                        for (int j = 0; j < mBoard[i].length; j++) {
                            if(mBoard[i][j] == ReversiJudge.SPACE) {
                                judge |= ReversiJudge.judge(mBoard, mPlayer, i, j);
                            }
                        }
                    }
                }

                // COM戦の場合はプレイヤーの変更がない。

            } else {


                mPlayer = (mPlayer == ReversiJudge.WHITE) ? ReversiJudge.BLACK : ReversiJudge.WHITE;
            }

            mReversiView.setBoard(mBoard);
            mReversiView.invalidate();
        }
    }
}
