package database;

import android.os.Handler;

import scorer.BeloteGame;
import scorer.BeloteRound;
import scorer.BeloteTeam;
import scorer.Player;

public class Dummy {

    public static void prepGame(String gameName) throws BeloteDBConnectionException {

        try {
            BeloteDataBaseFacade.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BeloteGame game = new BeloteGame(
                gameName,
                new BeloteTeam(new Player("player1"), new Player("player2")),
                new BeloteTeam(new Player("player3"), new Player("player4"))
        ).start();

        BeloteRound round = new BeloteRound();
        round.start();
        round.finish(160, 0);
        game.addRound(round);
        BeloteDataBaseFacade.getInstance().scoreRef.push().setValue(game.finish());
    }
}
