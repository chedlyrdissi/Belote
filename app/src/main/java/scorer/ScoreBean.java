package scorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ScoreBean extends Observable {

    private static ScoreBean instance;

    private List<BeloteGame> games;
    private BeloteGame selectedGame;

    private ScoreBean() {
        games = new ArrayList<>();
    }

    public static ScoreBean getInstance() {
        if (instance == null) {
            instance = new ScoreBean();
        }
        return instance;
    }

    public List<BeloteGame> getGames() {
        return games;
    }

    public void addGameListObserver(Observer observer) {
        addObserver(observer);
    }

    public void removeGameListObserver(Observer observer) {
        deleteObserver(observer);
    }

    public void setGames(List<BeloteGame> games) {
        this.games.clear();
        this.games.addAll(games);
        setChanged();
        notifyObservers(this.games);
    }

    public BeloteGame getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(BeloteGame selectedGame) {
        this.selectedGame = selectedGame;
    }

    public void clearSelectedGame() {
        this.selectedGame = null;
    }
}
