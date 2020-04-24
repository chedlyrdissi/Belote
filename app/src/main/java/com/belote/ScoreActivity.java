package com.belote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import database.BeloteDataBaseFacade;
import scorer.BeloteGame;
import scorer.GameListRecyclerViewAdapter;
import scorer.ItemDeleteHandler;
import scorer.ScoreBean;

import static core.RequestCodes.CREATE_NEW_GAME;
import static core.RequestCodes.NEW_GAME_CREATION_FAILED;
import static core.RequestCodes.NEW_GAME_CREATION_SUCCESSFUL;

public class ScoreActivity extends AppCompatActivity implements ItemDeleteHandler<BeloteGame> {

    protected RecyclerView gameList;
    protected GameListRecyclerViewAdapter adapter;
    protected LinearLayoutManager layoutManager;

    protected Button createGame, deleteGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        try {
            BeloteDataBaseFacade.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.connectionIssue, Toast.LENGTH_LONG).show();
        }

        gameList = findViewById(R.id.gamesList);
        createGame = findViewById(R.id.newGameButton);
        deleteGames = findViewById(R.id.deleteGameButton);

        layoutManager = new LinearLayoutManager(this);
        gameList.setLayoutManager(layoutManager);

        adapter = new GameListRecyclerViewAdapter(ScoreBean.getInstance().getGames(), this);
        gameList.setAdapter(adapter);
        gameList.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);

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
                    BeloteDataBaseFacade.getInstance().deleteGames(adapter.getSelectedGames());
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
    public void selectGame(BeloteGame selectedGame) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        ScoreBean.getInstance().setSelectedGame(selectedGame);
        startActivity(intent);
    }
}
