package bean;

import java.util.List;

import scorer.BeloteRound;

public class RoundsBean {

    private static RoundsBean instance;

    private List<BeloteRound> rounds;

    private RoundsBean() {}

    public static RoundsBean getInstance() {
        if (instance == null) {
            instance = new RoundsBean();
        }
        return instance;
    }

    public List<BeloteRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<BeloteRound> rounds) {
        this.rounds = rounds;
    }
}
