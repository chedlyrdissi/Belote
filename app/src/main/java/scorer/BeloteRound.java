package scorer;

import java.time.LocalDateTime;

public class BeloteRound {

    private int score1 = 0;

    private int score2 = 0;

    private String start;
    private String finish;

    public BeloteRound(){}

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
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

    public BeloteRound start() {
        start = LocalDateTime.now().toString();
        return this;
    }

    public BeloteRound finish( int score1, int score2){
        finish = LocalDateTime.now().toString();
        this.score1 = score1;
        this.score2 = score2;
        return this;
    }
}
