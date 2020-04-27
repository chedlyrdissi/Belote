package database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bean.ScoreBean;
import scorer.BeloteGameData;

public class BeloteScoreDBM extends DBManager {

    public static final String PATH = "scores";

    private DatabaseReference ref;

    public BeloteScoreDBM(){
        this.ref = FirebaseDatabase.getInstance().getReference(PATH);
        this.ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ScoreBean.getInstance().setGames(new ArrayList<BeloteGameData>());
                for ( DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    ScoreBean.getInstance().getGames().add(snapshot.getValue(BeloteGameData.class));
                    ScoreBean.getInstance().getGames().get(ScoreBean.getInstance().getGames().size() - 1).setId(snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public List<BeloteGameData> getGames() {
        return ScoreBean.getInstance().getGames();
    }

    public void addGame(BeloteGameData gameData) {
        this.ref.push().setValue(gameData);
    }

    public void deleteGames(List<BeloteGameData> data) {
        for ( BeloteGameData gameData: data ) {
            ref.child(gameData.getId()).removeValue();
            FirebaseDatabase.getInstance().getReference().child(BeloteRoundsDBM.PATH).child(gameData.getId()).removeValue();
            ScoreBean.getInstance().getGames().remove(gameData);
        }
    }

    @Override
    public void destroy() {

    }
}
