package adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.belote.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import scorer.BeloteGameData;
import bean.ScoreBean;

import static adapter.GameDataListRecyclerViewAdapter.*;

public class GameDataListRecyclerViewAdapter extends Adapter<GameListViewHolder> implements Observer {

    protected List<BeloteGameData> selectedGames = new LinkedList<>();
    private ItemDeleteHandler<BeloteGameData> context;

    public GameDataListRecyclerViewAdapter( ItemDeleteHandler<BeloteGameData> context ) {
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
        holder.startTime.setText(ScoreBean.getInstance().getGames().get(position).getStart());
        holder.finishTime.setText(ScoreBean.getInstance().getGames().get(position).getFinish());
        holder.gameTeam1.setText(ScoreBean.getInstance().getGames().get(position).getTeam1().toString());
        holder.gameTeam2.setText(ScoreBean.getInstance().getGames().get(position).getTeam2().toString());
        holder.gameScore1.setText(Integer.toString(ScoreBean.getInstance().getGames().get(position).getScore1()));
        holder.gameScore2.setText(Integer.toString(ScoreBean.getInstance().getGames().get(position).getScore2()));
        if ( selectedGames.contains(ScoreBean.getInstance().getGames().get( position )) ) {
            holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.getContext(), R.color.itemSelected));
        } else {
            holder.view.setBackgroundColor(Color.WHITE);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( selectedGames.contains(ScoreBean.getInstance().getGames().get(position)) ) {
                    selectedGames.remove(ScoreBean.getInstance().getGames().get(position));
                    v.setBackgroundColor(Color.WHITE);
                    if ( selectedGames.isEmpty() ) {
                        context.selectToDeleteGame(false);
                    }
                } else {
                    selectedGames.add(ScoreBean.getInstance().getGames().get(position));
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.itemSelected));
                    context.selectToDeleteGame(true);
                }
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.selectGame(ScoreBean.getInstance().getGames().get(position));
                return true;
            }
        });
    }

    public List<BeloteGameData> getSelectedGames() {
        return selectedGames;
    }

    @Override
    public int getItemCount() {
        return ScoreBean.getInstance().getGames().size();
    }

    @Override
    public void update(Observable o, Object arg) {
        ScoreBean.getInstance().getGames().clear();
        ScoreBean.getInstance().getGames().addAll( (List<BeloteGameData>) arg );
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
}
