package com.example.wordsearchgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

public class GameOnExitDialogFragment extends DialogFragment {
    Context context;
    boolean ifBtm;
    public GameOnExitDialogFragment(Context context){
        super();
        this.context = context;
        this.ifBtm = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // set view:
        View view = inflater.inflate(R.layout.fragment_game_on_exit, null);
        builder.setView(view);

        // register button: yes, then back to menu
        Button btmButton = view.findViewById(R.id.exit_yes);
        btmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                setIfBtm(true);
            }
        });
        // register button: no, then back to game
        Button btgButton = view.findViewById(R.id.exit_no);
        btgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });
        return builder.create();
    }

    public void setIfBtm(boolean ifBtm){
        this.ifBtm = ifBtm;
    }
    public boolean getIfBtm(){
        return ifBtm;
    }
}
