package com.belote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import database.BeloteDataBaseFacade;
import scorer.BeloteGame;
import scorer.BeloteRound;
import scorer.ItemDeleteHandler;
import scorer.RoundListRecyclerViewAdapter;
import scorer.ScoreBean;

public class GameActivity extends AppCompatActivity implements ItemDeleteHandler<BeloteRound> {

    protected BeloteGame game;
    protected TextView team1, score1, team2, score2, name;

    protected RecyclerView roundsList;
    protected RoundListRecyclerViewAdapter adapter;

    protected Button createButton, deleteButton, startGame, finishGame;

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
            createButton = findViewById(R.id.createRoundButton);
            deleteButton = findViewById(R.id.deleteRoundbutton);
            startGame = findViewById(R.id.startGameButton);
            finishGame = findViewById(R.id.finishGameButton);

            selectToDeleteGame(false);

            name.setText(R.string.gameName + game.getName());
            team1.setText(game.getTeam1().toString());
            team2.setText(game.getTeam2().toString());
            score1.setText(Integer.toString(game.getTotalScore1()));
            score2.setText(Integer.toString(game.getTotalScore2()));

            updateFinishButtonVisibility();
            updateStartButtonVisibility();

            adapter = new RoundListRecyclerViewAdapter(game.getRounds(), this);
            roundsList.setLayoutManager(new LinearLayoutManager(this));
            roundsList.setAdapter( adapter );

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        BeloteDataBaseFacade.getInstance().deleteGames(adapter.getSelectedRounds());
                        adapter.getSelectedRounds().clear();
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public void updateStartButtonVisibility() {
        if (this.game.getStart() == null) {
            startGame.setVisibility(View.GONE);
        } else {
            startGame.setVisibility(View.VISIBLE);
        }
    }

    public void updateFinishButtonVisibility() {
        if (this.game.getFinish() == null) {
            finishGame.setVisibility(View.GONE);
        } else {
            finishGame.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        ScoreBean.getInstance().clearSelectedGame();
        super.onDestroy();
    }

    @Override
    public void selectToDeleteGame(boolean canDelete) {
        if (canDelete) {
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void selectGame(BeloteRound selected) {

    }
}
