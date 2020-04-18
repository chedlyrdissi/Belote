package scorer;

import java.util.ArrayList;
import java.util.List;

public class BeloteGame {

    private String id;
    private List<BeloteRound> rounds;

    public BeloteGame(){}

    public BeloteGame( String id ) {
        this.id = id;
        this.rounds = new ArrayList<>();
    }

    public List<BeloteRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<BeloteRound> rounds) {
        this.rounds.addAll(rounds);
    }

    public void addRound( BeloteRound round ) {
        this.rounds.add(round);
    }
}
