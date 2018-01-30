package kurtandkierra.blackjack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kierra on 1/30/2018.
 */

public class PlayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
/*
        final TextView points_tv = findViewById(R.id.player_points);
        String points_s = points_tv.getText().toString().trim();
        final int points_i = Integer.parseInt(points_s);
*/
        Button b = findViewById(R.id.win_points);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView points_tv = findViewById(R.id.player_points);
                String points_s = points_tv.getText().toString().trim();
                int points_i = Integer.parseInt(points_s);
                String temp = ""+(points_i + 1);
                points_tv.setText(temp);
            }
        });

        b = findViewById(R.id.lose_points);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView points_tv = findViewById(R.id.player_points);
                String points_s = points_tv.getText().toString().trim();
                int points_i = Integer.parseInt(points_s);
                String temp = ""+(points_i - 1);
                points_tv.setText(temp);
            }
        });
    }
}
