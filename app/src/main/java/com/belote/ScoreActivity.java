package com.belote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import database.BeloteDBConnectionException;
import database.BeloteDataBaseFacade;
import scorer.BeloteGame;
import scorer.BeloteRound;
import scorer.BeloteTeam;
import scorer.Player;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
//        try {
//            BeloteDataBaseFacade.getInstance();
//            final BeloteGame game = new BeloteGame("NEWGAME");
//            final BeloteRound round = new BeloteRound();
//            round.setTeam1( new BeloteTeam( new Player("player1"), new Player("player2")) );
//            round.setTeam2( new BeloteTeam( new Player("player3"), new Player("player4")) );
//            round.start();
//            ( new Handler()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    round.finish(160,0);
//                    game.addRound(round);
//                    try {
//                        BeloteDataBaseFacade.getInstance().scoreRef.push().setValue(game);
//                    } catch (BeloteDBConnectionException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, 5000);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

    }
}
