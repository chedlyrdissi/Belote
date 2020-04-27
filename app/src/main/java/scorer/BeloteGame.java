package scorer;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BeloteGame {

    private List<BeloteRound> rounds;
    private BeloteGameData data;

    public BeloteGame( BeloteGameData data ) {
        this.data = data;
    }

    public BeloteGame( BeloteGameData data, List<BeloteRound> rounds ) {
        this.data = data;
        this.rounds = rounds;
    }

    public BeloteGame( String id, BeloteTeam team1, BeloteTeam team2 ) {
        this(id,team1,team2,new ArrayList<BeloteRound>());
    }

    public BeloteGame( String id, BeloteTeam team1, BeloteTeam team2,@NonNull List<BeloteRound> rounds ) {
        this(new BeloteGameData(id,team1,team2));
        this.rounds = rounds;
    }

    public List<BeloteRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<BeloteRound> rounds) {
        this.rounds = rounds;
    }

    public void addRound( BeloteRound round ) {
        this.rounds.add(round);
    }

    public String getId() {
        return data.getId();
    }

    public void setId(String name) {
        this.data.setId(name);
    }

    public String getFinish() {
        return data.getFinish();
    }

    public void setFinish(String finish) {
        this.data.setFinish(finish);
    }

    public String getStart() {
        return data.getStart();
    }

    public void setStart(String start) {
        this.data.setStart(start);
    }

    public BeloteGame start() {
        data.setStart(LocalDateTime.now().toString());
        return this;
    }

    public BeloteGame finish() {
        data.setFinish(LocalDateTime.now().toString());
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
        return data.getTeam1();
    }

    public void setTeam1(BeloteTeam team1) {
        this.data.setTeam1(team1);
    }

    public BeloteTeam getTeam2() {
        return data.getTeam2();
    }

    public void setTeam2(BeloteTeam team2) {
        this.data.setTeam2(team2);
    }
}
