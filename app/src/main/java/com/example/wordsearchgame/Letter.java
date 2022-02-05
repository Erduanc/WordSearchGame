package com.example.wordsearchgame;


import android.util.Log;

public class Letter{
    /*Letter: Char, Pic, Pos*/
    private char charLetter;
    private int letterImageId;
    private int letterTempImageId;
    private int pos[] = new int[2];
    private int viewPos[] = new int[4];
    private int status;


    public Letter(char charLetter){
        setCharLetter(charLetter);
        //getCharPic(charLetter);
        setPos(0,0);
        setViewPos(0,0,0,0);
        setStatus(0);

        switch(charLetter){
            case 'a':
            case 'A':
                setLetterImageId(R.drawable.a);
                setLetterTempImageId(R.drawable.a_tmp);
                break;
            case 'b':
            case 'B':
                setLetterImageId(R.drawable.b);
                setLetterTempImageId(R.drawable.b_tmp);
                break;
            case 'c':
            case 'C':
                setLetterImageId(R.drawable.c);
                setLetterTempImageId(R.drawable.c_tmp);
                break;
            case 'd':
            case 'D':
                setLetterImageId(R.drawable.d);
                setLetterTempImageId(R.drawable.d_tmp);
                break;
            case 'e':
            case 'E':
                setLetterImageId(R.drawable.e);
                setLetterTempImageId(R.drawable.e_tmp);
                break;
            case 'f':
            case 'F':
                setLetterImageId(R.drawable.f);
                setLetterTempImageId(R.drawable.f_tmp);
                break;
            case 'g':
            case 'G':
                setLetterImageId(R.drawable.g);
                setLetterTempImageId(R.drawable.g_tmp);
                break;
            case 'h':
            case 'H':
                setLetterImageId(R.drawable.h);
                setLetterTempImageId(R.drawable.h_tmp);
                break;
            case 'i':
            case 'I':
                setLetterImageId(R.drawable.i);
                setLetterTempImageId(R.drawable.i_tmp);
                break;
            case 'j':
            case 'J':
                setLetterImageId(R.drawable.j);
                setLetterTempImageId(R.drawable.j_tmp);
                break;
            case 'k':
            case 'K':
                setLetterImageId(R.drawable.k);
                setLetterTempImageId(R.drawable.k_tmp);
                break;
            case 'l':
            case 'L':
                setLetterImageId(R.drawable.l);
                setLetterTempImageId(R.drawable.l_tmp);
                break;
            case 'm':
            case 'M':
                setLetterImageId(R.drawable.m);
                setLetterTempImageId(R.drawable.m_tmp);
                break;
            case 'n':
            case 'N':
                setLetterImageId(R.drawable.n);
                setLetterTempImageId(R.drawable.n_tmp);
                break;
            case 'o':
            case 'O':
                setLetterImageId(R.drawable.o);
                setLetterTempImageId(R.drawable.o_tmp);
                break;
            case 'p':
            case 'P':
                setLetterImageId(R.drawable.p);
                setLetterTempImageId(R.drawable.p_tmp);
                break;
            case 'q':
            case 'Q':
                setLetterImageId(R.drawable.q);
                setLetterTempImageId(R.drawable.q_tmp);
                break;
            case 'r':
            case 'R':
                setLetterImageId(R.drawable.r);
                setLetterTempImageId(R.drawable.r_tmp);
                break;
            case 's':
            case 'S':
                setLetterImageId(R.drawable.s);
                setLetterTempImageId(R.drawable.s_tmp);
                break;
            case 't':
            case 'T':
                setLetterImageId(R.drawable.t);
                setLetterTempImageId(R.drawable.t_tmp);
                break;
            case 'u':
            case 'U':
                setLetterImageId(R.drawable.u);
                setLetterTempImageId(R.drawable.u_tmp);
                break;
            case 'v':
            case 'V':
                setLetterImageId(R.drawable.v);
                setLetterTempImageId(R.drawable.v_tmp);
                break;
            case 'w':
            case 'W':
                setLetterImageId(R.drawable.w);
                setLetterTempImageId(R.drawable.w_tmp);
                break;
            case 'x':
            case 'X':
                setLetterImageId(R.drawable.x);
                setLetterTempImageId(R.drawable.x_tmp);
                break;
            case 'y':
            case 'Y':
                setLetterImageId(R.drawable.y);
                setLetterTempImageId(R.drawable.y_tmp);
                break;
            case 'z':
            case 'Z':
                setLetterImageId(R.drawable._z);
                setLetterTempImageId(R.drawable._z_tmp);
                break;
        }

//        switch(charLetter){
//            case 'a':
//            case 'A':
//                setLetterTempImageId(R.drawable.a_tmp);
//                break;
//            case 'b':
//            case 'B':
//                setLetterTempImageId(R.drawable.b_tmp);
//                break;
//            case 'c':
//            case 'C':
//                setLetterTempImageId(R.drawable.c_tmp);
//                break;
//            case 'd':
//            case 'D':
//                setLetterTempImageId(R.drawable.d_tmp);
//                break;
//            case 'e':
//            case 'E':
//                setLetterTempImageId(R.drawable.e_tmp);
//                break;
//            case 'f':
//            case 'F':
//                setLetterTempImageId(R.drawable.f_tmp);
//                break;
//            case 'g':
//            case 'G':
//                setLetterTempImageId(R.drawable.g_tmp);
//                break;
//            case 'h':
//            case 'H':
//                setLetterTempImageId(R.drawable.h_tmp);
//                break;
//            case 'i':
//            case 'I':
//                setLetterTempImageId(R.drawable.i_tmp);
//                break;
//            case 'j':
//            case 'J':
//                setLetterTempImageId(R.drawable.j_tmp);
//                break;
//            case 'k':
//            case 'K':
//                setLetterTempImageId(R.drawable.k_tmp);
//                break;
//            case 'l':
//            case 'L':
//                setLetterTempImageId(R.drawable.l_tmp);
//                break;
//            case 'm':
//            case 'M':
//                setLetterTempImageId(R.drawable.m_tmp);
//                break;
//            case 'n':
//            case 'N':
//                setLetterTempImageId(R.drawable.n_tmp);
//                break;
//            case 'o':
//            case 'O':
//                setLetterTempImageId(R.drawable.o_tmp);
//                break;
//            case 'p':
//            case 'P':
//                setLetterTempImageId(R.drawable.p_tmp);
//                break;
//            case 'q':
//            case 'Q':
//                setLetterTempImageId(R.drawable.q_tmp);
//                break;
//            case 'r':
//            case 'R':
//                setLetterTempImageId(R.drawable.r_tmp);
//                break;
//            case 's':
//            case 'S':
//                setLetterTempImageId(R.drawable.s_tmp);
//                break;
//            case 't':
//            case 'T':
//                setLetterTempImageId(R.drawable.t_tmp);
//                break;
//            case 'u':
//            case 'U':
//                setLetterTempImageId(R.drawable.u_tmp);
//                break;
//            case 'v':
//            case 'V':
//                setLetterTempImageId(R.drawable.v_tmp);
//                break;
//            case 'w':
//            case 'W':
//                setLetterTempImageId(R.drawable.w_tmp);
//                break;
//            case 'x':
//            case 'X':
//                setLetterTempImageId(R.drawable.x_tmp);
//                break;
//            case 'y':
//            case 'Y':
//                setLetterTempImageId(R.drawable.y_tmp);
//                break;
//            case 'z':
//            case 'Z':
//                setLetterTempImageId(R.drawable._z_tmp);
//                break;
//        }

    }

    public void setCharLetter(char charLetter){ this.charLetter = charLetter; }
    public char getCharLetter(){ return this.charLetter; }
    public void setLetterImageId(int letterImageId){ this.letterImageId = letterImageId; }
    public int getLetterImageId(){ return this.letterImageId; }
    public void setLetterTempImageId(int letterTempImageId){this.letterTempImageId = letterTempImageId; }
    public int getLetterTempImageId(){ return this.letterTempImageId; }
    //getter for charPic, setter for charPic
    public void setPos(int rowNum, int colNum){ this.pos[0]= rowNum; this.pos[1] = colNum; }
    public void setPos(int[] pos){ this.pos[0] = pos[0]; this.pos[1] = pos[1]; }
    public int[] getPos(){ return this.pos; }
    public void setStatus(int status){ this.status = status; }
    public int getStatus(){ return status; }
    public void setViewPos(int[] viewPos/*L,R,Top, Bottom*/){
        this.viewPos[0] = viewPos[0]; this.viewPos[1] = viewPos[1];
        this.viewPos[2] = viewPos[2]; this.viewPos[3] = viewPos[3];
    }
    public void setViewPos(int left, int right, int top, int bottom){
        this.viewPos[0] = left; this.viewPos[1] = right;
        this.viewPos[2] = top; this.viewPos[3] = bottom;
    }
    public int[] getViewPos(){
        return viewPos;
    }

    public boolean ifWithinView(int x, int y){
        return (x >= viewPos[0] && x <= viewPos[1] && y >= viewPos[2] && y <= viewPos[3]);
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Letter){
            Letter letter  = (Letter) object;
            return ((letter.getCharLetter() == this.getCharLetter() || (char)(letter.getCharLetter()+32) == this.getCharLetter() || (char)(letter.getCharLetter()-32) == this.getCharLetter())
                    && letter.getLetterImageId() == this.getLetterImageId()
                    && letter.getStatus() == this.getStatus()
                    && letter.getPos()[0] == this.getPos()[0] && letter.getPos()[1] == this.getPos()[1]
                    && letter.getViewPos()[0] == this.getViewPos()[0] && letter.getViewPos()[1] == this.getViewPos()[1]
                    && letter.getViewPos()[2] == this.getViewPos()[2] && letter.getViewPos()[3] == this.getViewPos()[3]);
        }
        return false;
    }

    public boolean caseLessCharEquals(Letter letter){
        char a = this.getCharLetter();
        char b = letter.getCharLetter();

        if(a>=32 && b>= 32){
            return a == b || (char) (a + 32) == b || (char) (a - 32) == b;
        }
        return false;
    }

}
