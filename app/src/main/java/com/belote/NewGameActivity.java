package com.belote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import core.PlayerNameTextWatcher;
import database.BeloteDataBaseFacade;
import scorer.BeloteGame;
import scorer.BeloteRound;
import scorer.BeloteTeam;
import scorer.Player;

import static core.Helper.playerNameIsValid;
import static core.RequestCodes.NEW_GAME_CREATION_SUCCESSFUL;

public class NewGameActivity extends AppCompatActivity {

    protected EditText gameName, team1Player1, team1Player2, team2Player1, team2Player2;
    protected Button newGameButton;
    private Map<EditText,Boolean> statusMap = new HashMap<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        gameName = findViewById(R.id.newGameName);
        team1Player1 = findViewById(R.id.team1Player1);
        team1Player2 = findViewById(R.id.team1Player2);
        team2Player1 = findViewById(R.id.team2Player1);
        team2Player2 = findViewById(R.id.team2Player2);
        newGameButton = findViewById(R.id.submitCreateNewGameButton);

        //set initial status
        statusMap.put(team1Player1, false);
        statusMap.put(team1Player2, false);
        statusMap.put(team2Player1, false);
        statusMap.put(team2Player2, false);

        // set the onChange
        team1Player1.addTextChangedListener(new PlayerNameTextWatcher(statusMap,team1Player1));
        team1Player2.addTextChangedListener(new PlayerNameTextWatcher(team1Player2));
        team2Player1.addTextChangedListener(new PlayerNameTextWatcher(team2Player1));
        team2Player2.addTextChangedListener(new PlayerNameTextWatcher(team2Player2));

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do checks
                boolean isValid = true;
                for(boolean key: statusMap.values()) {
                    isValid = isValid && key;
                }
                if ( isValid ) {
                    // create the game
                    BeloteGame newGame = new BeloteGame( gameName.getText().toString(),
                            new BeloteTeam( new Player(team1Player1.getText().toString()),
                                    new Player(team1Player2.getText().toString())),
                            new BeloteTeam( new Player(team1Player1.getText().toString()),
                                    new Player(team1Player2.getText().toString())));
                    try {
                        BeloteDataBaseFacade.getInstance().addGame(newGame);
                        setResult(NEW_GAME_CREATION_SUCCESSFUL, new Intent());
                        finish();
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), R.string.errorCreatingGame, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.newGameRootLayout) ,
                            R.string.invalidPlayerNames,
                            Snackbar.LENGTH_SHORT).setAction( R.string.help, new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String message = new String();
                            int invalids = 0;
                            for ( Map.Entry<EditText, Boolean> entry :statusMap.entrySet()) {
                                if (!entry.getValue()) {
                                    message += entry.getKey().getText().toString();
                                    invalids++;
                                }
                            }
                            if(invalids > 0) {
                                if(invalids == 1){
                                    message = "The name " + message + " is invalid";
                                } else {
                                    message = "The names " + message + " are invalid";
                                }
                            }
                            Snackbar.make( findViewById(R.id.newGameRootLayout),
                                    message, Snackbar.LENGTH_SHORT ).show();
                        }
                    }).show();
                }
            }
        });
    }



}
