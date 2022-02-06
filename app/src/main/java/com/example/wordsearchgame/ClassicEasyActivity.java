package com.example.wordsearchgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class ClassicEasyActivity extends AppCompatActivity {
    private int[] direction = {0,0};
//    private ArrayList<int[]> letterViewPoses;
//    private ArrayList<Letter> letters = new ArrayList<Letter>();
    private ArrayList<Letter> selectedLetters = new ArrayList<Letter>();
    private Game game;
    private int[] barParam = {74,74};

    final int[] gridBounds = {-1,-1,-1,-1};

    public ArrayList<Letter> getSelectedLetters(){
        return selectedLetters;
    }


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

//                Log.d("eachLetter",String.valueOf(eachLetter.getCharLetter()));
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

//        initBarParam();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Capture a press down movement
        // If the press down is within a letterView
            // Change the appearance of the letter
            // Capture the movement
                /*
                get the position of the movement
                if within a letterView:
                    Sure about one of the 8 cases (directions)
                    Sure about the scope of selection:
                        in the same row, col, or diagonal line.
                    if out of the scope, then the selection fails (or completes, judges)
                    else:
                        capture the leaving of press (or out of the layout)
                        selection completes
                        remove redundancy
                        judges the answer
                        if it is a correct answer
                            change the state of letter, changes the letterView.
                * */
        // Else not within a letterView: nothing happens
    }


//    float x1 = 0;
//    float x2 = 0;
//    float y1 = 0;
//    float y2 = 0;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("inOnTouch","inOnTouch");
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if(y1 - y2 > 50) {
//                Toast.makeText(ClassicEasyActivity.this, "向上^滑", Toast.LENGTH_SHORT).show();
//            } else if(y2 - y1 > 50) {
//                Toast.makeText(ClassicEasyActivity.this, "向下v滑", Toast.LENGTH_SHORT).show();
//            } else if(x1 - x2 > 50) {
//                Toast.makeText(ClassicEasyActivity.this, "向左<滑", Toast.LENGTH_SHORT).show();
//            } else if(x2 - x1 > 50) {
//                Toast.makeText(ClassicEasyActivity.this, "向右>滑", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }

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
                            addCorrectView();
                            // remove from the answer
                            removeFromAnswers(selectedLetters);
                            setAnswerView(selectedLetters);
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
                    addCorrectView();
                    // remove from the answer

                }

                clearTempView();
                selectedLetters.clear();
                break;
        }
//        return super.dispatchTouchEvent(event);
        return super.onTouchEvent(event);
    }

//    private void initBarParam(){
//        ImageView barImageView = new ImageView(this);
//        barImageView.setImageResource(R.drawable.bar_test);
//        ConstraintLayout constraintLayout = findViewById(R.id.activity_classic_easy_layout);
//
//        barImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                barParam[0] = barImageView.getWidth();
//                barParam[1] = barImageView.getHeight();
//                Log.d("barImageView", ""+barImageView.getWidth()+", "+barImageView.getHeight());
//            }
//        });
//    }

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

    private void placeViewAtPos(int X, int Y, String order, int[] size, String color){
        Resources resources = getResources();

        // get direction
        int imageId = 0;
//        imageId = resources.getIdentifier("bar_left_red", "drawable", getPackageName());
        int opt = direction[1] + direction[0] * 3;
        Log.d("opt", "opt: "+opt+"; "+direction[1]+", "+direction[0]);
        switch(opt){
            case 1:
                // direction: (1,0)
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_left_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_right_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_middle_" + color, "drawable", getPackageName());
                }
                break;
            case -2:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_rlb_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_rlt_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_lrm_" + color, "drawable", getPackageName());
                }
                break;
            case -3:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_bottom_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_top_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_middle_" + color, "drawable", getPackageName());
                }
                break;
            case -4:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_lrb_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_lrt_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_lrm_" + color, "drawable", getPackageName());
                }
                break;
            case -1:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_right_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_left_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_middle_" + color, "drawable", getPackageName());
                }
                break;
            case 2:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_lrt_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_lrb_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_lrm_" + color, "drawable", getPackageName());
                }
                break;
            case 3:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_top_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_bottom_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_middle_" + color, "drawable", getPackageName());
                }
                break;
            case 4:
                if(order == "first"){
                    imageId = resources.getIdentifier("bar_rlt_" + color, "drawable", getPackageName());
                }else if(order == "last"){
                    imageId = resources.getIdentifier("bar_rlb_" + color, "drawable", getPackageName());
                }else{
                    imageId = resources.getIdentifier("bar_lrm_" + color, "drawable", getPackageName());
                }
                break;
        }

        ImageView barImageView = new ImageView(this);
//        barImageView.setImageResource(R.drawable.bar_left_red);
        barImageView.setImageResource(imageId);
        ConstraintLayout constraintLayout = findViewById(R.id.ghost_classic_easy_layout);

        barImageView.setX(X-(float)(barParam[0])/2);
        barImageView.setY(Y-(float)(barParam[1])/2);

        if(size[0] != 0 && size[1] != 0){
            // set size
        }
        constraintLayout.addView(barImageView);
    }

    private void placeViewAtLetter(Letter letter, String order, String color){
        Resources resources = getResources();
        int letterViewID = resources.getIdentifier("letter_easy_" + letter.getPos()[1] + letter.getPos()[0], "id", getPackageName());

        /*calculate the position and parameter(size) of the view*/
        // get size of letter
        ImageView letterImageView = findViewById(letterViewID);
        int width = letterImageView.getWidth();
        int height = letterImageView.getHeight();
//        Log.d("letterSize", ""+width+", "+height);

        // get pos of letter
        int letterPosX = letter.getViewPos()[2];
        int letterPosY = letter.getViewPos()[0];
//        Log.d("letterPos", ""+letterPosX+", "+letterPosY);

        // get center of letter
        int letterCenterX = letterPosX + (height / 2);
        int letterCenterY = letterPosY + (width / 2);
//        Log.d("width", ""+width+", "+height);

        // calculate the size of the view:
        int[] size = {0, 0}; // width, height
        if(order == "middle"){
            // The left of its left letterView to the right of its right letter view

            // Then rotate 45 degree

        }

        placeViewAtPos(letterCenterY, letterCenterX, order, size, color);
    }

    private void addCorrectView(){
        String color = getRandomBarColor();
        for(int i = 0; i < selectedLetters.size(); i++){
            if(i == 0){
                // for the very first letter add single round view
                placeViewAtLetter(selectedLetters.get(i), "first", color);
            }else if(i == (selectedLetters.size()-1)){
                // for the last one, add single round view
                placeViewAtLetter(selectedLetters.get(i), "last", color);
            }
            else{
                // for the middle ones, add none round view
                placeViewAtLetter(selectedLetters.get(i), "middle", color);
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

        return (new int[]{x, y});
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

    }
    private void setAnswerView(ArrayList<Letter> selectedLetters){
        LetterSeries selectedSeries = new LetterSeries(selectedLetters);
        for(Word eachWord: game.getWords()){
            if(eachWord.equals(selectedSeries)){
            }
        }
    }

    private boolean judgeTheAnswer(ArrayList<Letter> selectedLetters){
        LetterSeries selectedSeries = new LetterSeries(selectedLetters);
        for(Word eachWord: game.getWords()){
            if(eachWord.equals(selectedSeries)){
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
