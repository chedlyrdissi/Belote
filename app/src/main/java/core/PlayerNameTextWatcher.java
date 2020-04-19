package core;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.belote.R;

import java.util.Map;

public class PlayerNameTextWatcher implements TextWatcher {

    private static Map<EditText,Boolean> statusMap;
    private EditText id;

    public PlayerNameTextWatcher(Map<EditText,Boolean> statusMap, EditText id ) {
        this(id);
        PlayerNameTextWatcher.statusMap = statusMap;
    }

    public PlayerNameTextWatcher( EditText id ) {
        this.id = id;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        // check validity
        boolean isValid = true;
        isValid = isValid && Helper.playerNameIsValid(text);
        for(EditText name: statusMap.keySet()) {
            isValid = isValid && !text.equals(name);
        }
        statusMap.replace(id, isValid);
        // update ui
        if (isValid) {
            id.setTextColor(ContextCompat.getColor(id.getContext(), R.color.colorValid));
        } else {
            id.setTextColor(ContextCompat.getColor(id.getContext(), R.color.colorInvalid));
        }
    }
}
