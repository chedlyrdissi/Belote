package com.belote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import bean.RoundsBean;
import database.BeloteRoundsDBM;
import database.BeloteScoreDBM;
import adapter.GameDataListRecyclerViewAdapter;
import adapter.ItemDeleteHandler;
import scorer.BeloteGame;
import scorer.BeloteGameData;
import bean.ScoreBean;
import scorer.BeloteRound;

import static core.RequestCodes.CREATE_NEW_GAME;
import static core.RequestCodes.NEW_GAME_CREATION_FAILED;
import static core.RequestCodes.NEW_GAME_CREATION_SUCCESSFUL;

public class ScoreActivity extends AppCompatActivity implements ItemDeleteHandler<BeloteGameData> {

    private BeloteScoreDBM dbm;

    protected RecyclerView gameList;
    protected GameDataListRecyclerViewAdapter adapter;
    protected LinearLayoutManager layoutManager;

    protected Button createGame, deleteGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if (dbm == null) {
            try {
                dbm = new BeloteScoreDBM();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.connectionIssue, Toast.LENGTH_LONG).show();
            }
        }

        gameList = findViewById(R.id.gamesList);
        createGame = findViewById(R.id.newGameButton);
        deleteGames = findViewById(R.id.deleteGameButton);

        layoutManager = new LinearLayoutManager(this);
        gameList.setLayoutManager(layoutManager);

        adapter = new GameDataListRecyclerViewAdapter(this);
        gameList.setAdapter(adapter);

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewGameActivity.class),CREATE_NEW_GAME);
            }
        });

        deleteGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dbm.deleteGames(adapter.getSelectedGames());
                    adapter.getSelectedGames().clear();
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == NEW_GAME_CREATION_SUCCESSFUL) {
            Toast.makeText(getApplicationContext(), R.string.creationSuccessful, Toast.LENGTH_SHORT).show();
        } else if (resultCode == NEW_GAME_CREATION_FAILED) {
            Toast.makeText(getApplicationContext(), R.string.creationFailed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScoreBean.getInstance().removeGameListObserver(adapter);
    }

    @Override
    public void selectToDeleteGame( boolean canDelete ) {
        if (canDelete) {
            deleteGames.setVisibility(View.VISIBLE);
        } else {
            deleteGames.setVisibility(View.GONE);
        }
    }

    @Override
    public void selectGame(BeloteGameData selectedGameData) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        BeloteRoundsDBM dbm = new BeloteRoundsDBM(selectedGameData.getId());
        List<BeloteRound> rounds = RoundsBean.getInstance().getRounds();
        ScoreBean.getInstance().setSelectedGame(new BeloteGame(selectedGameData,rounds));
        startActivity(intent);
        dbm.destroy();
//        finish();
    }
}
