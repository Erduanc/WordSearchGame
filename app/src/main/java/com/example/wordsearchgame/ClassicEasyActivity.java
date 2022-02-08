package com.example.wordsearchgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class ClassicEasyActivity extends AppCompatActivity {
    private int[] direction = {0,0};
    private ArrayList<Letter> selectedLetters = new ArrayList<Letter>();
    private Game game = null;
    private int[] barParam1 = {74,74};
    private int[] barParam2 = {105, 105};
    private int[] barParam3 = {74, 105};
//    private ArrayList<TextView> answers = new ArrayList<>();
    final int[] gridBounds = {-1,-1,-1,-1};

//    public ArrayList<Letter> getSelectedLetters(){
//        return selectedLetters;
//    }
//    public synchronized void increaseI(){
//        i++;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_easy);

        // get data from last activity: difficulty level:
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty", 0);
        game = new Game(difficulty, this, "Classic");
        game.getLetterMatrix().printMatrix();
        game.printWords();

        // get the boundary of grid, i.e. the constraint layout that contains the letters
        View grid = findViewById(R.id.grid_of_letters_layout);
//        final int[] gridBounds = {-1,-1,-1,-1};
//        final int[] gridLeft = {0};
//        final int[] gridRight = {0};
//        final int[] gridTop = 0; int gridBottom = 0;
        grid.post(new Runnable() {
            @Override
            public void run() {
                gridBounds[0] = grid.getLeft();
                gridBounds[1] = grid.getRight();
                gridBounds[2] = grid.getTop();
                gridBounds[3] = grid.getBottom();
//                Log.d("grid", "not null: "+ grid.getLeft()+", "+ grid.getRight()+", "+ grid.getTop()+", "+ grid.getBottom() );

            }
        });
//        Log.d("grid", "not null: "+ gridBounds[0]+", "+ gridBounds[1]+", "+ gridBounds[2]+", "+ gridBounds[3] );



//        // Adjust the size of the grid according to the screen size:
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenWidth = displayMetrics.widthPixels;
//
//        LayoutInflater inflater = getLayoutInflater();
//        ConstraintLayout grid = (ConstraintLayout) inflater.inflate(R.layout.activity_classic_easy, null);
//        grid.getLayoutParams().width = screenWidth;


        Resources resources = getResources();

        int rowInt = 0; int colInt = 0;
        for (Letter[] eachRow : game.getLetterMatrix().getMat()) {
//            String rowString = rowInt + "";
            for (Letter eachLetter : eachRow) {
                // get the letterImageView of the letter at (rowInt, colInt) by id
                int orgLetterImageId = resources.getIdentifier("letter_easy_" + colInt + rowInt, "id", getPackageName());
                ImageView letterImageView = (ImageView) findViewById(orgLetterImageId);
                // get the letterImageView of the letter in the matrix to replace the letter at (rowInt, colInt)
                int newLetterImageId;
                if(eachLetter.getCharLetter() == 'z' || eachLetter.getCharLetter()== 'Z'){
                    if(eachLetter.getCharLetter() >= 'A' && eachLetter.getCharLetter() <= 'Z'){
                        newLetterImageId = resources.getIdentifier("_" + (char)(eachLetter.getCharLetter()+32), "drawable", getPackageName());
                    }else{
                        newLetterImageId = resources.getIdentifier("_" + eachLetter.getCharLetter(), "drawable", getPackageName());
                    }
                }
                else{
                    if(eachLetter.getCharLetter() >= 'A' && eachLetter.getCharLetter() <= 'Z'){
                        newLetterImageId = resources.getIdentifier(String.valueOf((char)(eachLetter.getCharLetter()+32)), "drawable", getPackageName());

                    }else{
                        newLetterImageId = resources.getIdentifier(String.valueOf(eachLetter.getCharLetter()), "drawable", getPackageName());

                    }
                }

//                Button buttonHint = (Button)findViewById(R.id.button_hint_easy);
                // replace the old view with the new view
                letterImageView.setImageResource(newLetterImageId);
                // post, then get and set the positions of letterImageView:
                letterImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        while(gridBounds[0]==-1 || gridBounds[1]==-1 || gridBounds[2]==-1 || gridBounds[3]==-1){
                            try {

                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
//                        eachLetter.setViewPos((int)letterImageView.getLeft()+gridBounds[0], (int)letterImageView.getRight()+gridBounds[0], (int)letterImageView.getTop()+gridBounds[2], (int)letterImageView.getBottom()+gridBounds[2]);
                        eachLetter.setViewPos((int)letterImageView.getLeft()+gridBounds[0], (int)letterImageView.getRight()+gridBounds[0], (int)letterImageView.getTop()+gridBounds[2], (int)letterImageView.getBottom()+gridBounds[2]);

                        Log.d("viewPos", ""+eachLetter.getCharLetter()+": "+eachLetter.getViewPos()[0] + ", " + eachLetter.getViewPos()[1] + ", " + eachLetter.getViewPos()[2] +", "+ eachLetter.getViewPos()[3]);
                    }
                });
                // get and set the position (on view) of letterImageView
//                eachLetter.setViewPos((int)letterImageView.getLeft(), (int)letterImageView.getRight(), (int)letterImageView.getTop(), (int)letterImageView.getBottom());
//                Log.d("viewPos", ""+(int)letterImageView.getLeft() + ", " + (int)letterImageView.getRight() + ", " + (int)letterImageView.getTop() +", "+ (int)letterImageView.getBottom());
                colInt++;
            }
            rowInt++;
            colInt = 0;
        }

        // set the font and content of answers (textViews) below the grid
        for(int i=0; i < game.getWords().size(); i++){
            // get TextView
            int wordTextID = resources.getIdentifier("word_easy_" + (i+1), "id", getPackageName());
            if(wordTextID != 0){
                TextView wordText = (TextView)findViewById(wordTextID);
                // set content
//                wordText.setText("Hello");
                wordText.setText(game.getWords().get(i).getWordString());
                // set font
                Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/Courier-Prime.ttf");
                wordText.setTypeface(typeface);
            }
        }

        // init buttons: hint:
        Button buttonHint = (Button)findViewById(R.id.button_hint_easy);
        buttonHint.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(game != null){
                    for(Word eachWord:game.getWords()){
                        if(eachWord.getStatus() == 0){
                            setHintView(eachWord);
                            break;
                        }
                    }
                }
            }
        });
////        //init buttons: pause:
////        Button buttonPause = (Button)findViewById(R.id.button_pause_easy);
////        buttonHint.setOnClickListener(new View.OnClickListener(){
////            @Override
////            public void onClick(View v){
//////                Intent intent = new Intent(ClassicLevelActivity.this, ClassicEasyActivity.class);
//////                intent.putExtra("difficulty", 0);
//////                startActivity(intent);
////            }
////        });

//        initBarParam();

    }

    private void setHintView(Word word){
        Resources resources = getResources();
        // task1: set letterView's source to white
        int letterImageViewId = resources.getIdentifier("letter_easy_" + word.getStartPos()[1] + word.getStartPos()[0], "id", getPackageName());
        ImageView letterImageView = findViewById(letterImageViewId);
        int newSrcId = getLetterSrcIdByLetter(word.getWordLetters().get(0), "_white");
        letterImageView.setImageResource(newSrcId);

        // task2: add hint imageView as "background" of white letterViews
        int directX = word.getEndPos()[1] - word.getStartPos()[1];
        int directY = -(word.getEndPos()[0] - word.getStartPos()[0]);
        if(directX!=0){
            directX = directX/Math.abs(directX);
        }
        if(directY!=0){
            directY = directY/Math.abs(directY);
        }
        Log.d("firstLetterPos", ""+word.getWordLetters().get(0).getPos()[0]+", "+word.getWordLetters().get(0).getPos()[1]);
        Letter letter = game.getLetterMatrix().getMat()[word.getWordLetters().get(0).getPos()[0]][word.getWordLetters().get(0).getPos()[1]];

//        placeImageViewAtLetter(word.getWordLetters().get(0), barParam2, "hint_"+(directX+1)+(directY+1),"ghost_classic_easy_layout");
        int opt = directX + directY*3;
        Log.d("optHint", ""+opt+": "+directX+", "+directY);
        switch(opt){
            case 1:
                placeImageViewAtLetter(letter, barParam1, "hint_"+(directX+1)+(directY+1),"ghost_classic_easy_layout");
                break;
            case -3:
                placeImageViewAtLetter(letter, barParam1, "hint_"+(directX+1)+(directY+1),"ghost_classic_easy_layout");
                break;
            case -1:
                placeImageViewAtLetter(letter, new int[]{barParam2[0], barParam1[0]}, "hint_"+(directX+1)+(directY+1),"ghost_classic_easy_layout");
                break;
            case 3:
                placeImageViewAtLetter(letter, new int[]{barParam1[0], barParam2[0]}, "hint_"+(directX+1)+(directY+1),"ghost_classic_easy_layout");
                break;
            case 2:
            case -2:
            case -4:
            case 4:
                placeImageViewAtLetter(letter, barParam1, "hint_"+(directX+1)+(directY+1),"ghost_classic_easy_layout");
                break;
        }
    }

    private int getLetterSrcIdByLetter(Letter letter, String padding){
        Resources resources = getResources();
        int newLetterImageId=0;
        if(letter.getCharLetter() == 'z'){
            newLetterImageId=resources.getIdentifier("_" + (char)(letter.getCharLetter())+padding, "drawable", getPackageName());
        }else if(letter.getCharLetter() == 'Z'){
            newLetterImageId=resources.getIdentifier("_" + (char)(letter.getCharLetter()+32)+padding, "drawable", getPackageName());
        }else if(letter.getCharLetter() >= 'a' && letter.getCharLetter()<= 'y'){
            newLetterImageId=resources.getIdentifier("" + (char)(letter.getCharLetter())+padding, "drawable", getPackageName());
        }else if(letter.getCharLetter() >= 'A' && letter.getCharLetter()<= 'Y'){
            newLetterImageId=resources.getIdentifier("" + (char)(letter.getCharLetter()+32)+padding, "drawable", getPackageName());
        }

        return newLetterImageId;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // get the height of title:
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight;
        Resources resources = getResources();

        // get the position where event happens:
        int eventPosX = (int)event.getX(); int eventPosY = ((int)event.getY()- statusBarHeight-contentTop);
        // Check if the event happens within one letterView. If so, return the letter, else, return null.
        Letter letter = withInOneLetterView(eventPosX, eventPosY);

        switch (event.getAction()) {
            // Capture a press down movement
            case MotionEvent.ACTION_DOWN:
                clearTempView();
                // set direction to {0,0} (default)
                direction = new int[]{0, 0};
                // if the withInOneLetterView returns a letter:
                if(letter != null){
                    Log.d("selectedLetter", "-----------------------");
                    Log.d("selectedLetter", ""+letter.getCharLetter());

                    Log.d("ACTION_DOWN","on "+letter.getCharLetter());
                    // add the letterView/ letter to selectedLetters:
                    selectedLetters.add(letter);
                    // remove redundancy
                    removeRedundancy(selectedLetters);
                    /* Change the appearance of the letter or add a view on top of the letter */
                    setTempView(letter);
                    // change status
                }
                break;

            // Capture the movement
            case MotionEvent.ACTION_MOVE:
//                Log.d("direction", ""+direction[0]+", "+direction[1]);
//                Toast.makeText(ClassicEasyActivity.this, "ACTION_MOVE", Toast.LENGTH_SHORT).show();
//                Toast.makeText(ClassicEasyActivity.this, ""+letter.getCharLetter(), Toast.LENGTH_SHORT).show();
                // If the movement is within a letterView:
                if(letter != null) {
                    Log.d("selectedLetter", ""+letter.getCharLetter());
                    // add the letterView/ letter to selectedLetters:
                    selectedLetters.add(letter);
                    // remove redundancy
                    removeRedundancy(selectedLetters);
                    // change the view of letter
                    setTempView(letter);
                    // set status

                    // if the direction is not set yet, i.e. this is the second letter selected:
                    if(direction[0] == 0 && direction[1] == 0) {
                        // Sure about one of the 8 cases (directions)
                        direction = getDirection(selectedLetters);
                    }
                    /* If the direction has already been set, i.e. after the second letter selected:
                    but out of the direction: selection completes
                     */
//                    else if(direction != getDirection(selectedLetters)){
                    else if((direction[0] != getDirection(selectedLetters)[0] || direction[1] != getDirection(selectedLetters)[1])
                            || !ifConsecutiveToLast(letter)){
                        // remove redundancy
                        removeRedundancy(selectedLetters);
                        clearTempView();
                        // remove the wrong letter
                        selectedLetters.remove(letter);

                        Log.d("conciseSelectedLetters","mov-----");
                        for(Letter l:selectedLetters){
                            Log.d("conciseSelectedLetters",""+l.getCharLetter());
                        }

                        // judges the answer
                        if(judgeTheAnswer(selectedLetters)) {
                            // if it is a correct answer
                            // change the state of letter,
                            // changes the letterView.
//                            addCorrectView();
                            placeColorfulBarAtWord();
                            // remove from the answer
                            setAnswerView(selectedLetters);
                            removeFromAnswers(selectedLetters);

//                            game.deleteWord(new Word(selectedLetters));
                        }
                        // finishes
                        selectedLetters.clear();
                        break;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
//                Toast.makeText(ClassicEasyActivity.this, "ACTION_UP", Toast.LENGTH_SHORT).show();
                // remove redundancy
                removeRedundancy(selectedLetters);

                Log.d("conciseSelectedLetters","fin-----");
                for(Letter l:selectedLetters){
                    Log.d("conciseSelectedLetters",""+l.getCharLetter());
                }

                // judges the answer
                if(judgeTheAnswer(selectedLetters)) {
                    // if it is a correct answer
                    // change the state of letter,
                    // changes the letterView.
//                    addCorrectView();
                    placeColorfulBarAtWord();
                    // remove from the answer
                    setAnswerView(selectedLetters);
                    removeFromAnswers(selectedLetters);
                }

                clearTempView();
                selectedLetters.clear();
                break;
        }
//        return super.dispatchTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private String getRandomBarColor(){
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int randomColor = random.nextInt(4);

        switch(randomColor){
            case 0:
                return "red";
            case 1:
                return "blue";
            case 2:
                return "green";
            case 3:
                return "yellow";
            default:
                return "red";
        }
    }

    private boolean placeImageViewAtPos(int X, int Y, int width, int height, int imageViewId, int viewGroupId){
        Log.d("imageViewId2", ""+imageViewId);
        if(imageViewId != 0){
            // create imageView
            ImageView imageView = new ImageView(ClassicEasyActivity.this);
            imageView.setImageResource(imageViewId);
            if(imageView != null){
                // set Position
                imageView.setX(X);
                imageView.setY(Y);
                // set size:
                /**/
                // add to layout
                ViewGroup viewGroup = findViewById(viewGroupId);
                viewGroup.addView(imageView);
                return true;
            }
        }
        return false;
    }

    private boolean placeImageViewAtLetter(Letter letter, int[] param, String imageViewName, String viewGroupName){
        /*calculate the position and parameter(size) of the view*/
        // get size of letter
        if(param.length!=2){ return false; }

        Resources resources = getResources();
        int letterViewID = resources.getIdentifier("letter_easy_" + letter.getPos()[1] + letter.getPos()[0], "id", getPackageName());

        ImageView letterImageView = findViewById(letterViewID);
        int letterWidth = letterImageView.getWidth();
        int letterHeight = letterImageView.getHeight();
//        Log.d("letterSize", ""+width+", "+height);

        // get pos of letter
        int letterPosX = letter.getViewPos()[2];
        int letterPosY = letter.getViewPos()[0];
//        Log.d("letterPos", ""+letterPosX+", "+letterPosY);

        // get center of letter
        int letterCenterX = letterPosX + (letterHeight / 2);
        int letterCenterY = letterPosY + (letterWidth / 2);

        // get the start of the view:
        int temp = letterCenterX;
        letterCenterX = letterCenterY;
        letterCenterY = temp;
        int viewPosX = letterCenterX-(param[0]/2);
        int viewPosY = letterCenterY-(param[1]/2);

        // get the size of barView:
        int height = 0;
        int width = 0;

        // get the barViewId
        int imageViewId = resources.getIdentifier(imageViewName, "drawable", getPackageName());
        Log.d("imageViewId1", ""+imageViewId);

        // get the viewGroupId
        int viewGroupId = resources.getIdentifier(viewGroupName, "id", getPackageName());

        return placeImageViewAtPos(viewPosX, viewPosY, width, height, imageViewId, viewGroupId);
//        Log.d("width", ""+width+", "+height);
    }

    private void placeColorfulBarAtLetter(Letter letter, String order, String color){
        Resources resources = getResources();
        String viewGroupName = "ghost_classic_easy_layout";
        String imageViewName = "";

        int opt = direction[0] + direction[1] * 3;
        Log.d("opt", "opt: "+opt+"; "+direction[0]+", "+direction[1]);

        switch(opt){
            case 1:
                // direction: (1,0)
                if(order == "first"){
                    imageViewName = "bar_left_"+color;
                }else if(order == "last"){
                    imageViewName = "bar_right_" + color;
                }else{
                    imageViewName = "bar_middle_" + color;
                }
                placeImageViewAtLetter(letter, barParam1, imageViewName, viewGroupName);
                break;
            case -2:
                if(order == "first"){
                    imageViewName = "bar_lrt_" + color;
                    placeImageViewAtLetter(letter, barParam1, imageViewName, viewGroupName);
                }else if(order == "last"){
                    imageViewName = "bar_lrb_" + color;
                    placeImageViewAtLetter(letter, barParam2, imageViewName, viewGroupName);
                }else{
                    imageViewName = "bar_lrm_" + color;
                    placeImageViewAtLetter(letter, barParam2, imageViewName, viewGroupName);
                }
                break;
            case -3:
                if(order == "first"){
                    imageViewName = "bar_top_" + color;
                }else if(order == "last"){
                    imageViewName = "bar_bottom_" + color;
                }else{
                    imageViewName = "bar_middle_" + color;
                }
                placeImageViewAtLetter(letter, barParam1, imageViewName, viewGroupName);
                break;
            case -4:
                if(order == "first"){
                    imageViewName = "bar_rlt_" + color;
                    placeImageViewAtLetter(letter, new int[]{barParam2[0], barParam1[0]}, imageViewName, viewGroupName);

                }else if(order == "last"){
                    imageViewName = "bar_rlb_" + color;
                    placeImageViewAtLetter(letter, new int[]{barParam1[0], barParam2[0]}, imageViewName, viewGroupName);

                }else{
                    imageViewName = "bar_lrm_" + color;
                    placeImageViewAtLetter(letter, barParam2, imageViewName, viewGroupName);
                }
                break;
            case -1:
                if(order == "first"){
                    imageViewName = "bar_right_" + color;
                }else if(order == "last"){
                    imageViewName = "bar_left_" + color;
                }else{
                    imageViewName = "bar_middle_" + color;
                }
                placeImageViewAtLetter(letter, barParam1, imageViewName, viewGroupName);
                break;
            case 2:
                if(order == "first"){
                    imageViewName = "bar_lrb_" + color;
                    placeImageViewAtLetter(letter, barParam2, imageViewName, viewGroupName);
                }else if(order == "last"){
                    imageViewName = "bar_lrt_" + color;
                    placeImageViewAtLetter(letter, barParam1, imageViewName, viewGroupName);
                }else{
                    imageViewName = "bar_lrm_" + color;
                    placeImageViewAtLetter(letter, barParam2, imageViewName, viewGroupName);
                }
                break;
            case 3:
                if(order == "first"){
                    imageViewName = "bar_bottom_" + color;
                }else if(order == "last"){
                    imageViewName = "bar_top_" + color;
                }else{
                    imageViewName = "bar_middle_" + color;
                }
                placeImageViewAtLetter(letter, barParam1, imageViewName, viewGroupName);
                break;
            case 4:
                if(order == "first"){
                    imageViewName = "bar_rlb_" + color;
                    placeImageViewAtLetter(letter, new int[]{barParam1[0], barParam2[0]}, imageViewName, viewGroupName);
                }else if(order == "last"){
                    imageViewName = "bar_rlt_" + color;
                    placeImageViewAtLetter(letter, new int[]{barParam2[0], barParam1[0]}, imageViewName, viewGroupName);
                }else{
                    imageViewName = "bar_lrm_" + color;
                    placeImageViewAtLetter(letter, barParam2, imageViewName, viewGroupName);
                }
                break;
        }
        Log.d("imageViewName", ""+imageViewName);
    }

    private void placeColorfulBarAtWord(){
        String color = getRandomBarColor();
        for(int i = 0; i < selectedLetters.size(); i++){
            if(i == 0){
                // for the very first letter add single round view
                placeColorfulBarAtLetter(selectedLetters.get(i), "first", color);
            }else if(i == (selectedLetters.size()-1)){
                // for the last one, add single round view
                placeColorfulBarAtLetter(selectedLetters.get(i), "last", color);
            }
            else{
                // for the middle ones, add none round view
                placeColorfulBarAtLetter(selectedLetters.get(i), "middle", color);
            }
        }
    }

    private Letter withInOneLetterView(int x, int y){
        for(Letter[] eachRow: game.getLetterMatrix().getMat()){
            for(Letter eachLetter: eachRow){
                if(eachLetter.ifWithinView(x, y)){
                    return eachLetter;
                }
            }
        }
        return null;
    }

    private int[] getDirection(ArrayList<Letter> letters){
        if(letters.size() == 0 || letters.size() == 1){
            return (new int[]{0,0});
        }
        int currentPos = letters.size() - 1;
        int lastPos = letters.size() - 2;
//        Log.d("directionLettersSize",""+letters.size());
//        Log.d("directionCurrentPos", ""+currentPos);
//        Log.d("directionLastPos", ""+lastPos);

        /* To get the direction, we need to get the difference between the later letter and the
        former one. Then normalise that value to [-1, 1], or: -1, 0, 1.*/
        // get differences
        int x = letters.get(currentPos).getPos()[0] - letters.get(lastPos).getPos()[0];
        int y = letters.get(currentPos).getPos()[1] - letters.get(lastPos).getPos()[1];
        // normalisation:
        if(x != 0 ){ x = x/Math.abs(x); }
        if(y != 0){ y = y/Math.abs(y); }

//        Log.d("getDirection", ""+ letters.get(currentPos).getCharLetter() +", "+ letters.get(lastPos).getCharLetter()+": "+x+", "+y);

        return (new int[]{y, -x});
    }

    public boolean ifConsecutiveToLast(Letter letter){
        int index = selectedLetters.indexOf(letter);
        boolean returnVal = false;

        if(index == -1){
            returnVal = false;
        }else if( index == 0 ){
            returnVal = true;
            //return true;
        }else {
            returnVal = (Math.abs(selectedLetters.get(index).getPos()[0] - selectedLetters.get((index - 1)).getPos()[0]) <= 1
                    && Math.abs(selectedLetters.get(index).getPos()[1] - selectedLetters.get((index - 1)).getPos()[1]) <= 1);
        }
        Log.d("ifConsecutiveToLast", ""+returnVal);
        return returnVal;
    }

    private void removeFromAnswers(ArrayList<Letter> selectedLetters){
        LetterSeries selectedSeries = new LetterSeries(selectedLetters);
        for(Word eachWord: game.getWords()){
            if(eachWord.equals(selectedSeries)){
                eachWord.setStatus(1);
                Log.d("removeFromAnswers",eachWord.getWordString());
            }
        }
    }

    private void setAnswerView(ArrayList<Letter> selectedLetters){
        Log.d("setAnswerView", "in method");
        Resources resources = getResources();
        LetterSeries selectedSeries = new LetterSeries(selectedLetters);

        for(int i = 0; i < game.getWords().size(); i++){
            Log.d("setAnswerView","in loop");
            if(game.getWords().get(i).getStatus() == 0 && game.getWords().get(i).equals(selectedSeries)){
                Log.d("setAnswerView","equals");
                int textViewId = resources.getIdentifier("word_easy_"+(i+1),"id",getPackageName());
                if(textViewId != 0){
                    Log.d("setAnswerView","not 0");
                    TextView textView = (TextView) findViewById(textViewId);
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        }
    }

    private boolean judgeTheAnswer(ArrayList<Letter> selectedLetters){
        LetterSeries selectedSeries = new LetterSeries(selectedLetters);
        for(Word eachWord: game.getWords()){
            if(eachWord.getStatus() == 0 && eachWord.equals(selectedSeries)){
                Log.d("judgeTheAnswer","true");
                return true;
            }
        }
        Log.d("judgeTheAnswer","false");
        return false;
    }

    private void removeRedundancy(ArrayList<Letter> selectedLetters){
        for(int i = 0; i < selectedLetters.size(); i++){
            for(int j = i+1; j < selectedLetters.size(); j++){
                if(selectedLetters.get(i).equals(selectedLetters.get(j))
                        && i != j){
                    selectedLetters.remove(j);
                    j--;
//                    if(i+1 == j){ i--; }
                }
            }
        }
    }

    private void setTempView(Letter letter){
        // given the letter, find the view corresponded
        Resources resources = getResources();
        int orgLetterImageId = resources.getIdentifier("letter_easy_" + letter.getPos()[1] + letter.getPos()[0], "id", getPackageName());
        ImageView letterImageView = (ImageView) findViewById(orgLetterImageId);
        // for the view of letter found, set the view to the temp one.
        int newLetterImageId = 0;
        if(letterImageView != null){
            if(letter.getCharLetter() == 'z'){
                newLetterImageId=resources.getIdentifier("_" + (char)(letter.getCharLetter())+"_tmp", "drawable", getPackageName());
            }else if(letter.getCharLetter() == 'Z'){
                newLetterImageId=resources.getIdentifier("_" + (char)(letter.getCharLetter()+32)+"_tmp", "drawable", getPackageName());
            }else if(letter.getCharLetter() >= 'a' && letter.getCharLetter()<= 'y'){
                newLetterImageId=resources.getIdentifier("" + (char)(letter.getCharLetter())+"_tmp", "drawable", getPackageName());
            }else if(letter.getCharLetter() >= 'A' && letter.getCharLetter()<= 'Y'){
                newLetterImageId=resources.getIdentifier("" + (char)(letter.getCharLetter()+32)+"_tmp", "drawable", getPackageName());
            }

            if(newLetterImageId != 0 ){
                letterImageView.setImageResource(newLetterImageId);
            }
        }
    }

    private void clearTempView(){
        Log.d("clearTempView", "1");
        // given the letter, find the view corresponded
        Resources resources = getResources();
        for(Letter letter:selectedLetters){
//            Log.d("clearTempView", ""+letter.getCharLetter());
            int orgLetterImageId = resources.getIdentifier("letter_easy_" + letter.getPos()[1] + letter.getPos()[0], "id", getPackageName());
            ImageView letterImageView = (ImageView) findViewById(orgLetterImageId);
            // for the view of letter found, set the view to the original one.
            int newLetterImageId = 0;
            if(letterImageView != null){
//                Log.d("letterImageView", "not null");
                if(letter.getCharLetter() == 'z'){
                    newLetterImageId=resources.getIdentifier("_" + (char)(letter.getCharLetter()), "drawable", getPackageName());
                }else if(letter.getCharLetter() == 'Z'){
                    newLetterImageId=resources.getIdentifier("_" + (char)(letter.getCharLetter()+32), "drawable", getPackageName());
                }else if(letter.getCharLetter() >= 'a' && letter.getCharLetter()<= 'y'){
                    newLetterImageId=resources.getIdentifier("" + (char)(letter.getCharLetter()), "drawable", getPackageName());
                }else if(letter.getCharLetter() >= 'A' && letter.getCharLetter()<= 'Y'){
                    newLetterImageId=resources.getIdentifier("" + (char)(letter.getCharLetter()+32), "drawable", getPackageName());
                }

//                Log.d("newLetterImageId", ""+newLetterImageId);
                if(newLetterImageId != 0 ){
                    letterImageView.setImageResource(newLetterImageId);
                }
            }
        }
    }

    private void addColorBar(ArrayList<Letter> selectedLetters){
        // Remove Redundency
        removeRedundancy(selectedLetters);
        // get the position to add the bar
        int currentPos = selectedLetters.size()-1;
        int lastPos = selectedLetters.size()-2;
        // change former bar.

        // add round bar
    }
}
