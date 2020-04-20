package dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.belote.R;

import scorer.BeloteRound;

public class RoundDialog extends Dialog {

    private BeloteRound round;
    private EditText score1, score2;
    private Button update;

    public RoundDialog(@NonNull Context context, BeloteRound round) {
        super(context);
        setContentView(R.layout.dialog_round);

        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        update = findViewById(R.id.updateRoundButton);
        update.setVisibility(View.GONE);

        this.round = round;

        score1.setText(Integer.toString(round.getScore1()));
        score2.setText(Integer.toString(round.getScore2()));

        score1.setTag(R.string.changed, false);
        score2.setTag(R.string.changed, false);

        score1.addTextChangedListener(new RoundScoreTextWatcher(score1));
        score2.addTextChangedListener(new RoundScoreTextWatcher(score2));
    }

    public void updateChangeButtonVisibility() {
        if ( (Boolean) score1.getTag(R.string.changed)
                || (Boolean) score2.getTag(R.string.changed) ) {
            update.setVisibility(View.VISIBLE);
        } else {
            update.setVisibility(View.GONE);
        }
    }

    public class RoundScoreTextWatcher implements TextWatcher {

        private EditText score;

        public RoundScoreTextWatcher( EditText score ){
            this.score = score;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
             score.setTag(R.string.changed, !s.toString().equals(score));
             updateChangeButtonVisibility();
        }
    }
}
