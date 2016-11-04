package org.takashiyokoyama.simplereversi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 対人戦
        Button btnHuman = (Button)findViewById(R.id.btnHuman);
        btnHuman.setOnClickListener(this);

        // COM戦
        Button btnCPU = (Button)findViewById(R.id.btnCPU);
        btnCPU.setOnClickListener(this);

        // 中級戦
        Button btnMiddle = (Button)findViewById(R.id.btnMiddle);
        btnMiddle.setOnClickListener(this);

        // 上級戦
        Button btnHard = (Button)findViewById(R.id.btnHard);
        btnHard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.btnHuman: {
                // 対人戦
                Intent intent = new Intent(this, ReversiActivity.class);
                intent.putExtra("com", 0);
                startActivity(intent);
                break;
            }
            case R.id.btnCPU: {
                // COM戦
                Intent intent = new Intent(this, ReversiActivity.class);
                intent.putExtra("com", 1);
                startActivity(intent);

                break;
            }
            case R.id.btnMiddle: {
                // 中級
                Intent intent = new Intent(this, ReversiActivity.class);
                intent.putExtra("com", 2);
                startActivity(intent);
                break;
            }
            case R.id.btnHard: {
                // 上級
                Intent intent = new Intent(this, ReversiActivity.class);
                intent.putExtra("com", 3);
                startActivity(intent);
                break;
            }
        }
    }
}
