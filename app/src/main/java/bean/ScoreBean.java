package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import scorer.BeloteGame;
import scorer.BeloteGameData;

public class ScoreBean extends Observable {

    private static ScoreBean instance;

    private List<BeloteGameData> gamesData;
    private BeloteGame selectedGame;

    private ScoreBean() {
        gamesData = new ArrayList<>();
    }

    public static ScoreBean getInstance() {
        if (instance == null) {
            instance = new ScoreBean();
        }
        return instance;
    }

    public List<BeloteGameData> getGames() {
        return gamesData;
    }

    public void addGameListObserver(Observer observer) {
        addObserver(observer);
    }

    public void removeGameListObserver(Observer observer) {
        deleteObserver(observer);
    }

    public void setGames(List<BeloteGameData> gamesData) {
        this.gamesData.clear();
        this.gamesData.addAll(gamesData);
        setChanged();
        notifyObservers(this.gamesData);
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
