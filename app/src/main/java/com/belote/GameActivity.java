package com.belote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import scorer.BeloteGame;
import scorer.RoundListRecyclerViewAdapter;
import scorer.ScoreBean;

public class GameActivity extends AppCompatActivity {

    protected BeloteGame game;
    protected TextView team1, score1, team2, score2, name;

    protected RecyclerView roundsList;
    protected RoundListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BeloteGame selected = ScoreBean.getInstance().getSelectedGame();

        if ( selected == null ) {
            Toast.makeText(getApplicationContext(), R.string.selectAGame, Toast.LENGTH_LONG).show();
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 4000);
        } else {
            game = selected;

            name = findViewById(R.id.gameNameLabel);
            team1 = findViewById(R.id.gameTeam1);
            team2 = findViewById(R.id.gameTeam2);
            score1 = findViewById(R.id.gameScore1);
            score2 = findViewById(R.id.gameScore2);
            roundsList = findViewById(R.id.roundList);

            name.setText(R.string.gameName + game.getName());
            team1.setText(game.getTeam1().toString());
            team2.setText(game.getTeam2().toString());
            score1.setText(Integer.toString(game.getTotalScore1()));
            score2.setText(Integer.toString(game.getTotalScore2()));

            adapter = new RoundListRecyclerViewAdapter(game.getRounds());
            roundsList.setLayoutManager(new LinearLayoutManager(this));
            roundsList.setAdapter( adapter );

        }
    }

    @Override
    protected void onDestroy() {
        ScoreBean.getInstance().clearSelectedGame();
        super.onDestroy();
    }
}
