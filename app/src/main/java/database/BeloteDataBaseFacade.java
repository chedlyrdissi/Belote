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
                        game = new BeloteGame(id);
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

    public static BeloteDataBaseFacade getInstance() throws BeloteDBConnectionException {
        if ( instance == null ) {
            instance = new BeloteDataBaseFacade();
        }
        return instance;
    }

}
