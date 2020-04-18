package scorer;

import java.util.List;

public class ScoreBean {

    private static ScoreBean instance;

    private List<BeloteGame> games;

    private ScoreBean() {}

    public static ScoreBean getInstance() {
        if (instance == null){
            instance = new ScoreBean();
        }
        return instance;
    }

    public List<BeloteGame> getGames() {
        return games;
    }

    public void setGames(List<BeloteGame> games) {
        this.games = games;
    }
}
