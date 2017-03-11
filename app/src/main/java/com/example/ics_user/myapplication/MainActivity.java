package com.example.ics_user.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    TextView calcScreen;

    boolean sign = false;
    boolean signReset = true;
    boolean parenthesis = false;
s
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcScreen = (TextView) findViewById(R.id.calc_screen);

        bindHoldClear();
    }

    public void bindHoldClear(){
        Button clear = (Button) findViewById(R.id.btn_clr);
        clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calcScreen.setText("");
                return true;
            }
        });
    }

    public void addCharacter(String charac){
        calcScreen.setText(calcScreen.getText() + charac);

        if(!signReset){
            signReset = true;
            sign = false;
        }
    }

    public void insertSign(){
        if(!signReset) clear();

        if(!sign) addCharacter("+");
        else addCharacter("-");

        if(signReset) signReset = false;

        sign = !sign;
    }

    public void insertParenthesis(){
        if(!parenthesis) addCharacter("(");
        else addCharacter(")");

        parenthesis = !parenthesis;
    }

    public void clear(){
        String curr = calcScreen.getText().toString();

        if(curr.length() > 0) calcScreen.setText(curr.substring(0,curr.length()-1));
    }

    public void compute(){

    }

    public void buttonClicked(View view){

        switch ( view.getId() ) {

            case R.id.btn_one: addCharacter("1");
                break;
            case R.id.btn_two: addCharacter("2");
                break;
            case R.id.btn_three: addCharacter("3");
                break;
            case R.id.btn_four: addCharacter("4");
                break;
            case R.id.btn_five: addCharacter("5");
                break;
            case R.id.btn_six: addCharacter("6");
                break;
            case R.id.btn_seven: addCharacter("7");
                break;
            case R.id.btn_eight: addCharacter("8");
                break;
            case R.id.btn_nine: addCharacter("9");
                break;
            case R.id.btn_zero: addCharacter("0");
                break;
            case R.id.btn_dot: addCharacter(".");
                break;
            case R.id.btn_add: addCharacter("+");
                break;
            case R.id.btn_sub: addCharacter("-");
                break;
            case R.id.btn_mul: addCharacter("x");
                break;
            case R.id.btn_div: addCharacter("/");
                break;
            case R.id.btn_mod: addCharacter("%");
                break;
            case R.id.btn_sign: insertSign();
                break;
            case R.id.btn_par: insertParenthesis();
                break;
            case R.id.btn_clr: clear();
                break;
            case R.id.btn_equals: compute();
                break;
            default:
                break;
        }
    }
}
