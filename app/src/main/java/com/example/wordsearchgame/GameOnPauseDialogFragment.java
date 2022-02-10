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

public class GameOnPauseDialogFragment extends DialogFragment{
    Context context;
    boolean ifBtm;

    public GameOnPauseDialogFragment(Context context){
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
        View view = inflater.inflate(R.layout.fragment_game_on_pause, null);
        builder.setView(view);

        // register button: back to menu
        Button btmButton = view.findViewById(R.id.pause_btm_button);
        btmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GameOnExitDialogFragment exitDialogFragment = new GameOnExitDialogFragment(context);
                exitDialogFragment.show(getFragmentManager(), "pauseDialogFragment");
                setIfBtm(exitDialogFragment.ifBtm);
//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);
            }
        });
        // register button: back to game
        Button btgButton = view.findViewById(R.id.pause_btg_button);
        btgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Get the layout inflater
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_game_on_pause, null);
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(view);
//        // Add action buttons
//        builder.setPositiveButton("game", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                dismiss();
//            }
//        });
//        builder.setNegativeButton("menu", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int id){
//                if(context != null){
//                    Intent intent = new Intent(context, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        return builder.create();
    }

    public void setIfBtm(boolean ifBtm){
        this.ifBtm = ifBtm;
    }
    public boolean getIfBtm(){
        return ifBtm;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_game_on_pause, container);
//        Button btmButton = view.findViewById(R.id.pause_btm_button);
//        btmButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button btgButton = view.findViewById(R.id.pause_btg_button);
//        btgButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        return view;
//    }


//    public void showPauseDialog(View view)
//    {
//        GameOnPauseDialogFragment pauseDialogFragment = new GameOnPauseDialogFragment();
//        pauseDialogFragment.show(getFragmentManager(), "EditNameDialog");
//    }
}
