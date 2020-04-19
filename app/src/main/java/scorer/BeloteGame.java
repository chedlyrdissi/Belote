package scorer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BeloteGame {

    private String name;
    private List<BeloteRound> rounds;

    private BeloteTeam team1;
    private BeloteTeam team2;

    private String start;
    private String finish;

    public BeloteGame(){}

    public BeloteGame( String name ) {
        this(name,null,null);
    }

    public BeloteGame( String name, BeloteTeam team1, BeloteTeam team2 ) {
        this.name = name;
        this.team1 = team1;
        this.team2 = team2;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public BeloteGame start() {
        start = LocalDateTime.now().toString();
        return this;
    }

    public BeloteGame finish() {
        finish = LocalDateTime.now().toString();
        return this;
    }

    public int getTotalScore1() {
        int result = 0;
        for(BeloteRound round: rounds) {
            result += round.getScore1();
        }
        return result;
    }

    public int getTotalScore2() {
        int result = 0;
        for(BeloteRound round: rounds) {
            result += round.getScore2();
        }
        return result;
    }

    public BeloteTeam getTeam1() {
        return team1;
    }

    public void setTeam1(BeloteTeam team1) {
        this.team1 = team1;
    }

    public BeloteTeam getTeam2() {
        return team2;
    }

    public void setTeam2(BeloteTeam team2) {
        this.team2 = team2;
    }
}
