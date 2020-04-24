package database;

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

public class BeloteGameDBM {

    private DatabaseReference gameRef, roundsRef;
    private BeloteGame game;

    public BeloteGameDBM(final String id) {
        // setting game ref
        this.roundsRef = FirebaseDatabase.getInstance().getReference(BeloteDBRef.SCOREREF + "/" + id + "/" + BeloteDBRef.ROUNDSREF);
        this.roundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (game != null) {
                    System.out.println("rounds Changed");
                    List<BeloteRound> rounds = new ArrayList<>((int) dataSnapshot.getChildrenCount());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        rounds.add(snapshot.getValue(BeloteRound.class));
                    }
                    game.setRounds(rounds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // setting game ref
        this.gameRef = FirebaseDatabase.getInstance().getReference(BeloteDBRef.SCOREREF + "/" + id);
        this.gameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("game changed");
                game = dataSnapshot.getValue(BeloteGame.class);
                game.setName(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public BeloteGame getGame() {
        return this.game;
    }
}
