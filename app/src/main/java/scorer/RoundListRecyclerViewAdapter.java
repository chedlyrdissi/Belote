package scorer;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belote.R;

import java.util.List;

import dialog.RoundDialog;

public class RoundListRecyclerViewAdapter extends RecyclerView.Adapter<RoundListRecyclerViewAdapter.RoundListViewHolder> {

    private List<BeloteRound> rounds;

    public RoundListRecyclerViewAdapter(List<BeloteRound> rounds) {
        this.rounds = rounds;
    }

    @NonNull
    @Override
    public RoundListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.round_item, parent, false);
        return new RoundListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoundListViewHolder holder, final int position) {
        holder.score1.setText(Integer.toString(rounds.get(position).getScore1()));
        holder.score2.setText(Integer.toString(rounds.get(position).getScore2()));
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                (new RoundDialog(holder.view.getContext(), rounds.get(position))).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return rounds.size();
    }

    public static class RoundListViewHolder extends RecyclerView.ViewHolder {

        TextView score1, score2;
        View view;

        public RoundListViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            score1 = itemView.findViewById(R.id.score1);
            score2 = itemView.findViewById(R.id.score2);
        }
    }
}
