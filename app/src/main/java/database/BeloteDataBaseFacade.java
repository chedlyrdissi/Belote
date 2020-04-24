package database;

import android.util.JsonReader;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import scorer.BeloteGame;
import scorer.BeloteRound;
import scorer.BeloteTeam;
import scorer.Player;
import scorer.ScoreBean;

public class BeloteDataBaseFacade {

    private static BeloteDataBaseFacade instance;

    public DatabaseReference scoreRef;

    private BeloteDataBaseFacade() throws BeloteDBConnectionException {
        try {
            this.scoreRef = FirebaseDatabase.getInstance().getReference("scores");
            scoreRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String id;
                    BeloteGame game;
                    List<BeloteGame> list = new ArrayList<>();
                    BeloteRound round;
                    for( DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                        id = snapshot.getKey();
                        game = new BeloteGame(id,
                                getTeamFromSnapshot(snapshot.child("team1")),
                                getTeamFromSnapshot(snapshot.child("team2")));
                        game.setStart((String) snapshot.child("start").getValue());
                        game.setFinish((String) snapshot.child("finish").getValue());
                        for ( DataSnapshot roundSnapshot: snapshot.child("rounds").getChildren() ) {
                            game.addRound(roundSnapshot.getValue(BeloteRound.class));
                        }
                        list.add(game);
                    }
                    ScoreBean.getInstance().setGames(list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeloteDBConnectionException(e.getMessage());
        }
    }

    private BeloteTeam getTeamFromSnapshot( DataSnapshot dataSnapshot ) {
        return new BeloteTeam(
                new Player((String) dataSnapshot.child("player1").child("name").getValue()),
                new Player((String) dataSnapshot.child("player2").child("name").getValue()) );
    }

    public static BeloteDataBaseFacade getInstance() throws BeloteDBConnectionException {
        if ( instance == null ) {
            instance = new BeloteDataBaseFacade();
        }
        return instance;
    }

    public void addGame(BeloteGame game) {
        scoreRef.push().setValue(game);
    }

    public void deleteGames(List<BeloteGame> games) {
        for (BeloteGame game: games) {
            scoreRef.child(game.getName()).removeValue();
        }
    }

    public void addRound() {

    }

    public void deleteRounds(BeloteGame game, List<BeloteRound> rounds) {

    }

}
