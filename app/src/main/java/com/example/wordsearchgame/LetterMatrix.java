package com.example.wordsearchgame;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LetterMatrix {
    private Context myContext;
    // private ArrayList<Letter> mat = new ArrayList<Letter>();
    private Letter[][] mat;
    private int[] matShape = {0, 0};
    private ArrayList<Word> words = new ArrayList<Word>();
    private boolean ifSuccessful = false;

    public static final int[] EASYMATSHAPE = {9, 9};
    public static final int[] MEDIUMMATSHAPE = {12, 12};
    public static final int[] HARDMATSHAPE = {12, 15};

    // Constructor:
    public LetterMatrix(Context context, int difficulty, ArrayList<Word> words){
        setMyContext(context);
        setMatShape(difficulty);
        setMat();
        setWords(words);
    }

    // Setters and getters:
    private void setMyContext(Context context){
        myContext = context;
    }
    public Context getMyContext(){
        return myContext;
    }
    private void setMat(){
        Letter[][] initMat = new Letter[getMatShape()[0]][getMatShape()[1]];
        for(int i=0; i < getMatShape()[0]; i++){
            for(int j=0; j < getMatShape()[1]; j++){
                initMat[i][j] = new Letter('@');
            }
        }
        mat = initMat;
    }
    public Letter[][] getMat(){
        return mat;
    }
    private void setMatShape(int difficulty){
        switch (difficulty){
            case 0:
                matShape[0] = EASYMATSHAPE[0];
                matShape[1] = EASYMATSHAPE[1];
                break;
            case 1:
                matShape[0] = MEDIUMMATSHAPE[0];
                matShape[1] = MEDIUMMATSHAPE[1];
                break;
            case 2:
                matShape[0] = HARDMATSHAPE[0];
                matShape[1] = HARDMATSHAPE[1];
            default:
                matShape[0] = EASYMATSHAPE[0];
                matShape[1] = EASYMATSHAPE[1];
        }
    }
    public int[] getMatShape(){
        return this.matShape;
    }
    public void setWords(ArrayList<Word> words){
        this.words = words;
    }
    public ArrayList<Word> getWords(){
        return words;
    }
    public boolean getIfSuccessful(){ return ifSuccessful; }
    // public void setIfSuccessful(){}

    public void placeLetterAt(char letterToPlace, int row, int col){
        mat[row][col] = new Letter(letterToPlace);
        mat[row][col].setPos(row, col);
        // set View?
    }

    public void tryPlaceLetter(Letter letter, int letterRow, int letterCol){
        // boolean ifCanPlace = false;
        ifSuccessful = false;
        // Need to check if the two constrains are satisfied.
        // No.1: if the position is on the matrix (not out of the boundary)
        if(letterRow >= 0 && letterRow < matShape[0] && letterCol >= 0 && letterCol < matShape[1]){
            // No.2: if there are legal overlaps. '@' means that the position is empty.
            if(getMat()[letterRow][letterCol].getCharLetter() == '@' || getMat()[letterRow][letterCol].getCharLetter() == letter.getCharLetter()){
                ifSuccessful = true;
                return;
            }
        }
        ifSuccessful = false;
    }

    public void updateWordPoses(Word word, int[][] letterPoses){
        for(int i=0; i < word.getWordLen(); i++){
            word.getWordLetters().get(i).setPos(letterPoses[i]);
        }

//        int k = 0;
//        for(int[] eachPos:letterPoses){
//            if(k < word.getWordLen()){ word.getWordLetters().get(k).setPos(eachPos); }
//            else{ break; }
//        }
    }

    public void updateMatrix(Word word){
        for(int i=0; i < word.getWordLen(); i++){
            Letter letterToPlace = word.getWordLetters().get(i);
            placeLetterAt(letterToPlace.getCharLetter(), letterToPlace.getPos()[0], letterToPlace.getPos()[1]);
        }
    }
//                int i = 0;
//                for(Letter letterToPlace:word.getWordLetters()) {
//                    if (i < letterPoses.size()) {
//                        placeLetterAt(letterToPlace.getCharLetter(), letterPoses.get(i)[0], letterPoses.get(i)[1]);
//                        i++;
//                        successedWordsNo++;
//                    } else {
//                        break;
//                    }
//                }


    public void tryAllDirections(Word word, int startRow, int startCol){
        // boolean ifCanPlace = false;
//        int[][] directions = {(1,0),(1,-1),(-1,0),(-1,-1),(-1,0),(-1,1),(1,0),(1,1)};
        Integer[][] directions = {{1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}};
        // ArrayList<int[][]> letterPoses = new ArrayList<int[][]>();
        int[][] letterPoses = new int[word.getWordLen()][2];
        // int[][] letterPoses = new int[Math.max(matShape[0], matShape[1])][2];
        // ArrayList<int[]> letterPoses = new ArrayList<int[]>();

        /* Need to shuffle the directions?
        Yes, or the chances of each direction appearing are not equal*/
        Integer[] indexes = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Comparable> list = Arrays.asList(indexes);
        Collections.shuffle(list);
        list.toArray(indexes);
        Integer[] tempDirect = new Integer[2];
        for(int i = 0; i < 8; i++){
            tempDirect = directions[i];
            directions[i] = directions[indexes[i]];
            directions[indexes[i]] = tempDirect;
        }
//        System.out.println("Shuffled Directions: ");
//        for(int i = 0; i < 8; i++){
//            System.out.println(directions[i][0]+", "+directions[i][1]);
//        }
//        System.out.println("----------");
        // Traverse all 8 directions
        int letterRow = 0, letterCol = 0;
        ifSuccessful = false;
        for(int i = 0; i < 8 && !ifSuccessful; i ++){
            // Traverse all letters in a word
//            System.out.println("Positions: ");
            for(int j = 0; j < word.getWordLen(); j++){
                // Calculate the positions for each letter in a word.
                letterRow = startRow + (directions[i][1] * j);
                letterCol = startCol + (directions[i][0] * j);
//                System.out.println("letterRow: "+letterRow+", letterCol:"+letterCol);
                // Need to call tryPlaceLetter
                tryPlaceLetter(word.getWordLetters().get(j), letterRow, letterCol);
                if(ifSuccessful){
                    // letterPoses.add(new int[]{letterRow, letterCol});
                    letterPoses[j][0] = letterRow;
                    letterPoses[j][1] = letterCol;
//                    System.out.println("letterPoses: "+letterPoses.get(i)[0]+", letterCol:"+letterPoses.get(i)[1]);
//                    System.out.println("letterPoses: "+letterPoses[j][0]+", letterCol:"+letterPoses[j][1]);
                    ifSuccessful = false;
                    if(j == (word.getWordLen()-1)){
                        /* if ture: can place the word there, so update the poses of the words.
                        but note that matrix is not updated yet.
                         */
                        ifSuccessful = true;
                        updateWordPoses(word, letterPoses);
//                        int k = 0;
//                        for(int[] eachPos:letterPoses){
//                            if(k < word.getWordLen()){ word.getWordLetters().get(k).setPos(eachPos); }
//                            else{ break; }
//                        }
                    }
                }else{
                    // else break: clear the letterPoses, try next direction
                    break;
                }
            }

        }

        // if this fails for all directions at the current position: return false.
        // return ifCanPlace;
    }

    public void tryAllPositions(Word word){
        // boolean ifCanPlace = false;
        ifSuccessful = false;
        // Create originally ordered rows and columns as: 1, 2, 3,...
        Integer[] rowOrders = new Integer[matShape[0]];
        Integer[] colOrders = new Integer[matShape[1]];
        for(int i = 0; i < Math.max(matShape[0], matShape[1]); i++){
            if(i < matShape[0]){ rowOrders[i] = i; }
            if(i < matShape[0]){ colOrders[i] = i; }
        }

        // Shuffule the rows and column orders, e.g. 7, 1, 3,...
        List<Comparable> list = Arrays.asList(rowOrders);
        Collections.shuffle(list);
        list.toArray(rowOrders);
        list = Arrays.asList(colOrders);
        Collections.shuffle(list);
        list.toArray(colOrders);

        // traverse the matrix according to the row and column orders
        for(int i = 0; i < matShape[0] && !ifSuccessful; i++){
            for(int j =0; j < matShape[1] && !ifSuccessful; j++){
//                mat[rowOrders[i]][colOrders[j]].setCharLetter('&');
                tryAllDirections(word, rowOrders[i], rowOrders[j]);
            }
        }

//        for(Integer each:rowOrders){ System.out.println(each+", "); }
//        System.out.println(" ");
//        for(Integer each:colOrders){ System.out.println(each+", "); }

        // return ifCanPlace;
    }

    public boolean tryAllWords(){
        // ArrayList<int[]> letterPoses = new ArrayList<int[]>();
        int successedWordsNo = 0;
        // Turn the returnVal to a class variable
        // Try all the words:
        for(Word word:words){
            /* For each word, ifSuccessful is originally set to false, and it will stay false
            * unless the positions of all letters for that word are legal, i.e. satisfies the
            * two constrains.*/
            ifSuccessful = false;
            tryAllPositions(word);
            if(ifSuccessful){
                updateMatrix(word);
                successedWordsNo++;
//                int i = 0;
//                for(Letter letterToPlace:word.getWordLetters()) {
//                    if (i < letterPoses.size()) {
//                        placeLetterAt(letterToPlace.getCharLetter(), letterPoses.get(i)[0], letterPoses.get(i)[1]);
//                        i++;
//                        successedWordsNo++;
//                    } else {
//                        break;
//                    }
//                }
            }
        }

        if(ifSuccessful && successedWordsNo == words.size()){
            ifSuccessful = true;
            return ifSuccessful;
        }else{
            ifSuccessful = false;
            return ifSuccessful;
        }


//        for(Word wordToPlace:words){
//
//        }


        /*
        Question: Where should wordGenerator be called?
        Possibility 1: in Letter Matrix:
            If so, LetterMatrix should have a reference to accommodate the words
            If so, LetterMatrix should have a constructor to initialise the words
            If so, LetterMatrix needs to return the words to the Letter Matrix
        Possibility 2: in Game:
            If so, the words have be passed to LetterMatrix.
            If so, LetterMatrix should have a reference to accommodate the words
            If so, LetterMatrix should have a constructor to initialise the words
        * */
    }

    public void fillInTheBlanks(){
//        Character[] alphabet = {'A', 'B', 'C', 'D', 'E',
//                                'F', 'G', 'H', 'I', 'J',
//                                'K', 'L', 'M', 'N', 'O',
//                                'P', 'Q', 'R', 'S', 'T',
//                                'U', 'V', 'W', 'X', 'Y', 'Z'};
//        // shuffle the alphabet:
//        List<Comparable> list = Arrays.asList(alphabet);
//        Collections.shuffle(list);
////        list.toArray(alphabet);
//        char[] alphabet = {'A', 'B', 'C', 'D', 'E',
//                            'F', 'G', 'H', 'I', 'J',
//                            'K', 'L', 'M', 'N', 'O',
//                            'P', 'Q', 'R', 'S', 'T',
//                            'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] alphabet =  {'a', 'b', 'c', 'd', 'e',
                            'f', 'g', 'h', 'i', 'j',
                            'k', 'l', 'm', 'n', 'o',
                            'p', 'q', 'r', 's', 't',
                            'u', 'v', 'w', 'x', 'y', 'z'};

        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        for(int i = 0; i < matShape[0]; i++){
            for(int j = 0; j <matShape[1]; j++){
                if(mat[i][j].getCharLetter() == '@'){
                    mat[i][j].setCharLetter(alphabet[random.nextInt(26)]);
                    mat[i][j].setPos(i, j);
                    // set View?
                }
            }
        }
    }

    public void generateMatrix(){
        tryAllWords();
        fillInTheBlanks();
    }

    public void printMatrix(){
        for(int i = 0; i < matShape[0]; i++){
            for(int j = 0; j <matShape[1]; j++){
                System.out.print(getMat()[i][j].getCharLetter() + "  ");
                if(j== matShape[1]-1){
                    System.out.println("");
                }
            }
        }
    }





}
