package scorer;

import android.graphics.Color;
import android.icu.text.CompactDecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.belote.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static scorer.GameListRecyclerViewAdapter.*;

public class GameListRecyclerViewAdapter extends Adapter<GameListViewHolder> implements Observer {

    private List<BeloteGame> games;
    protected List<BeloteGame> selectedGames = new LinkedList<>();
    private GameListItemDeleteHandler context;

    public GameListRecyclerViewAdapter( List<BeloteGame> games, GameListItemDeleteHandler context ) {
        this.games = new ArrayList<>(games);
        this.context = context;
        ScoreBean.getInstance().addGameListObserver(this);
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GameListViewHolder holder, final int position) {
        // setting the labels
        holder.gameName.setText("game #"+position);
        holder.startTime.setText(games.get(position).getStart());
        holder.finishTime.setText(games.get(position).getFinish());
        holder.gameTeam1.setText(games.get(position).getTeam1().toString());
        holder.gameTeam2.setText(games.get(position).getTeam2().toString());
        holder.gameScore1.setText(Integer.toString(games.get(position).getTotalScore1()));
        holder.gameScore2.setText(Integer.toString(games.get(position).getTotalScore2()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( selectedGames.contains(games.get(position)) ) {
                    selectedGames.remove(games.get(position));
                    v.setBackgroundColor(Color.WHITE);
                    if ( selectedGames.isEmpty() ) {
                        context.selectGame(false);
                    }
                } else {
                    selectedGames.add(games.get(position));
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.itemSelected));
                    context.selectGame(true);
                }
            }
        });
    }

    public List<BeloteGame> getSelectedGames() {
        return selectedGames;
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.games.clear();
        this.games.addAll( (List<BeloteGame>) arg );
        notifyDataSetChanged();
    }

    public static class GameListViewHolder extends RecyclerView.ViewHolder{

        View view;
        TextView gameName, startTime, finishTime, gameScore1, gameScore2, gameTeam1, gameTeam2;

        public GameListViewHolder(@NonNull View itemView) {
            super(itemView);
            // getting the views
            this.view = itemView;
            gameName = itemView.findViewById(R.id.gameName);
            startTime = itemView.findViewById(R.id.gameStartTime);
            finishTime = itemView.findViewById(R.id.gameFinishTime);
            gameScore1 = itemView.findViewById(R.id.gameScore1);
            gameScore2 = itemView.findViewById(R.id.gameScore2);
            gameTeam1 = itemView.findViewById(R.id.gameTeam1);
            gameTeam2 = itemView.findViewById(R.id.gameTeam2);

        }
    }

    public static interface GameListItemDeleteHandler {
        void selectGame( boolean canDelete );
    }
}
