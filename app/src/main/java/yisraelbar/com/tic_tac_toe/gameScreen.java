package yisraelbar.com.tic_tac_toe;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class gameScreen extends AppCompatActivity
        implements  View.OnClickListener, AdapterView.OnItemSelectedListener {

   private   game g1;
   private    TextView tvWinner,tvsumwinsX,tvsumwinsO;
   private   Button b0,b1,b2,b3,b4,b5,b6,b7,b8;
   private   int bestNext,difficultLevel=0;
   private   boolean onePlayer=false,gameEnd=false,turnX=true;
   private static final String X_WIN_COUNT="X_WIN_COUNT";
   private static final String O_WIN_COUNT="O_WIN_COUNT";
   private static final String BORD="BORD";
   private static final String ONE_FLAYER="ONE_FLAYER";
   private static final String DIFFICULTY_LEVEL="DIFFICULTY_LEVEL";
   private static final String TURN_X="TURN_X";
   private static final String GAME_END="GAME_END";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        g1 = new game();
        tvWinner =  findViewById(R.id.tvwinner);
        tvsumwinsX =  findViewById(R.id.tvsumWinsX);
        tvsumwinsO =  findViewById(R.id.tvsumWinsO);
        b0 = (Button) findViewById(R.id.bt0);
        b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        b3 = (Button) findViewById(R.id.bt3);
        b4 = (Button) findViewById(R.id.bt4);
        b5 = (Button) findViewById(R.id.bt5);
        b6 = (Button) findViewById(R.id.bt6);
        b7 = (Button) findViewById(R.id.bt7);
        b8 = (Button) findViewById(R.id.bt8);
        //make the spinner list
        Spinner sp1=(Spinner) findViewById(R.id.spLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(this);
        //load all previous data from onsavedInstanceState
         try {
             if (savedInstanceState != null) {
                 g1.setXNumOfWin(savedInstanceState.getInt(X_WIN_COUNT));
                 g1.setONumOfWin(savedInstanceState.getInt(O_WIN_COUNT));
                 g1.setBord(savedInstanceState.getStringArray(BORD));
                 displayScreen();
                 onePlayer=savedInstanceState.getBoolean(ONE_FLAYER);
                 if (g1.getXNumOfWin() != 0)
                     tvsumwinsX.setText("x is won " + g1.getXNumOfWin() + " times");
                 if (g1.getONumOfWin() != 0)
                     tvsumwinsO.setText("o is won " + g1.getONumOfWin() + " times");
                 difficultLevel=savedInstanceState.getInt(DIFFICULTY_LEVEL);
                 turnX=savedInstanceState.getBoolean(TURN_X);
                 gameEnd=savedInstanceState.getBoolean(GAME_END);
             }
         }catch (Exception e){
             Toast.makeText(this, e+"",Toast.LENGTH_SHORT).show();
         }
    }

    //save the data on bundle-if you flip the phone the data will be saved
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(X_WIN_COUNT,g1.getXNumOfWin());
        outState.putInt(O_WIN_COUNT,g1.getONumOfWin());
        outState.putStringArray(BORD,g1.getBord());
        outState.putBoolean(ONE_FLAYER,onePlayer);
        outState.putInt(DIFFICULTY_LEVEL,difficultLevel);
        outState.putBoolean(TURN_X,turnX);
        outState.putBoolean(GAME_END,gameEnd);
    }
    //when you choose level from the spinner -so it's accordingly
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        difficultLevel=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        difficultLevel=0;
    }
//display the bord with the values on screen
    private void displayScreen() {
        String[] bord = g1.getBord();
        b0.setText(bord[0]);
        b1.setText(bord[1]);
        b2.setText(bord[2]);
        b3.setText(bord[3]);
        b4.setText(bord[4]);
        b5.setText(bord[5]);
        b6.setText(bord[6]);
        b7.setText(bord[7]);
        b8.setText(bord[8]);
    }
//computer doing a move between 0-3 level (by choice)
    private void computerMove() {
        bestNext = g1.difficultyLevel(difficultLevel);
        g1.setBord(bestNext, "o");
        g1.setCountNumOfTurn();
        displayScreen();
        if (g1.checkIfWin(g1.getBord(), "o")) {
            tvWinner.setText("o is the winner");
            g1.setONumOfWinPlusOne();
            tvsumwinsO.setText("o is won " + g1.getONumOfWin() + " times");
            gameEnd=true;
            return;
        } else if (g1.getCountNumOfTurn() == 9) {
            tvWinner.setText("it's a tie");
            gameEnd=true;
            return;
        }
    }

    //one player against computer
    private void userMove(int num){

        if (g1.checkIfValid(num) && !gameEnd ) {
            g1.setBord(num, "x");
            displayScreen();
            g1.setCountNumOfTurn();
            if (g1.checkIfWin(g1.getBord(), "x")) {
                tvWinner.setText("x is the winner");
                 g1.setXNumOfWinPlusOne();
                tvsumwinsX.setText("x is won "+g1.getXNumOfWin()+" times");
                gameEnd=true;
                return;
            } else if (g1.getCountNumOfTurn() == 9) {
                tvWinner.setText("it's a tie");
                gameEnd=true;
                return;
            }
            //computer make move
            computerMove();
        } else tvWinner.setText("This place is not valid");

    }
//when you touch a cell its set it  accordingly
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt0:
                if (onePlayer)
                userMove(0);
                else {
                    if (turnX){
                        move(0,"x");
                    }else move(0,"O");
                }
                break;
            case R.id.bt1:
                if (onePlayer)
                    userMove(1);
                else {
                    if (turnX){
                        move(1,"x");
                    }else move(1,"O");
                }
                break;
            case R.id.bt2:
                if (onePlayer)
                    userMove(2);
                else {
                    if (turnX){
                        move(2,"x");
                    }else move(2,"O");
                }                break;
            case R.id.bt3:
                if (onePlayer)
                    userMove(3);
                else {
                    if (turnX){
                        move(3,"x");
                    }else move(3,"O");
                }
                break;
            case R.id.bt4:
                if (onePlayer)
                    userMove(4);
                else {
                    if (turnX){
                        move(4,"x");
                    }else move(4,"O");
                }                break;
            case R.id.bt5:
                if (onePlayer)
                    userMove(5);
                else {
                    if (turnX){
                        move(5,"x");
                    }else move(5,"O");
                }
                break;
            case R.id.bt6:
                if (onePlayer)
                    userMove(6);
                else {
                    if (turnX){
                        move(6,"x");
                    }else move(6,"O");
                }
                break;
            case R.id.bt7:
                if (onePlayer)
                    userMove(7);
                else {
                    if (turnX){
                        move(7,"x");
                    }else move(7,"O");
                }
                break;
            case R.id.bt8:
                if (onePlayer)
                    userMove(8);
                else {
                    if (turnX){
                        move(8,"x");
                    }else move(8,"O");
                }
                break;
            case R.id.btreset:
                gameEnd=false;
                g1.clear();
                b0.setText("");
                b1.setText("");
                b2.setText("");
                b3.setText("");
                b4.setText("");
                b5.setText("");
                b6.setText("");
                b7.setText("");
                b8.setText("");
                tvWinner.setText("");
                if (onePlayer)
                    computerMove();
                break;
            case R.id.sagainstComputer:
                Switch s=findViewById(R.id.sagainstComputer);
                if (s.isChecked()) {
                    onePlayer = true;
                    computerMove();
                }else onePlayer=false;
                break;
            default:
        }
    }
    //two players
    private  void move(int num,String s){
        if (g1.checkIfValid(num) && !gameEnd ) {
            g1.setBord(num, s);
            displayScreen();
            g1.setCountNumOfTurn();
            if (g1.checkIfWin(g1.getBord(), s)) {
                tvWinner.setText(s +" is the winner");
                gameEnd=true;
                if (s.equals("x")) {
                    g1.setXNumOfWinPlusOne();
                    tvsumwinsX.setText(s + " is won " + g1.getXNumOfWin() + " times");
                }else{
                    g1.setONumOfWinPlusOne();
                    tvsumwinsO.setText(s + " is won " + g1.getONumOfWin() + " times");
                }
                gameEnd=true;
                return;
            } else if (g1.getCountNumOfTurn() == 9) {
                tvWinner.setText("it's a tie");
                gameEnd=true;
                return;
            }
          } else tvWinner.setText("This place is not valid");
        turnX=!turnX;
        }


}
