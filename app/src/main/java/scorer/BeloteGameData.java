package scorer;

public class BeloteGameData {

    private String id;

    private BeloteTeam team1;
    private BeloteTeam team2;

    private int score1;
    private int score2;

    private String start;
    private String finish;

    public BeloteGameData() {}

    public BeloteGameData(String id) {
        this(id,null,null);
    }

    public BeloteGameData(String id, BeloteTeam team1, BeloteTeam team2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }
}
