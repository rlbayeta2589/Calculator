package com.example.ics_user.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity{

    TextView calcScreen;

    boolean sign = false;
    boolean signReset = true;
    boolean parenthesis = false;

    LinkedList<String> stack = new LinkedList<String>();
    LinkedList<Float> operands = new LinkedList<Float>();
    ArrayList<String> postfix = new ArrayList<String>();

    HashMap<String,Integer> precedenceValue = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcScreen = (TextView) findViewById(R.id.calc_screen);

        precedenceValue.put("x",3);
        precedenceValue.put("/",3);
        precedenceValue.put("%",3);
        precedenceValue.put("+",2);
        precedenceValue.put("-",2);
        precedenceValue.put("(",1);

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

    public boolean isOperand(String op){
        return op.matches("-?\\d+(\\.\\d+)?");
    }

    public void parseOperations(String input){
        //idea of splitting from http://stackoverflow.com/a/13525053

        //algo for infix to postfix
        //http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html

        String[] data = input.split("(?<=[-+x/%(])|(?=[-+x/%)])");

        Log.d("LOG", Arrays.toString(data));

        int i;

        for(i=0;i<data.length;i++) {
            String s = data[i];

            if(isOperand(s)){
                postfix.add(s);
            }else if(s.equals("(")){
                stack.addLast(s);
            }else if(s.equals(")")){
                while(!stack.getLast().equals("(")){
                    postfix.add(stack.removeLast());
                }
                stack.removeLast();
            }else{
                while(!stack.isEmpty() && !stack.getLast().equals("(") &&
                        precedenceValue.get(stack.getLast()) >= precedenceValue.get(s)){
                    postfix.add(stack.removeLast());
                }
                stack.addLast(s);
            }
        }

        while(!stack.isEmpty()){
            postfix.add(stack.removeLast());
        }

        Log.d("LOG", postfix.toString());
        Log.d("LOG", stack.toString());

    }

    public Float computationHelper(String operator, float operand1, float operand2){
        if(operator.equals("+")){
            return operand1 + operand2;
        }else if(operator.equals("-")){
            return operand1 - operand2;
        }else if(operator.equals("x")){
            return operand1 * operand2;
        }else if(operator.equals("/")){
            return operand1 / operand2;
        }else if(operator.equals("%")){
            return operand1 % operand2;
        }else{
            Log.d("LOG","dwjdjawjdwjdwakawdljawd");
            return null;
        }
    }

    public Float compute(String input){
        float op1, op2, res;

        stack.clear();
        postfix.clear();
        operands.clear();

        parseOperations(input);

        for(String s : postfix){
            if(isOperand(s)) operands.addLast(Float.parseFloat(s));
            else{
                op2 = operands.removeLast();
                op1 = operands.removeLast();
                res = computationHelper(s, op1, op2);
                operands.addLast(res);
            }
        }

        return operands.removeLast();
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
            case R.id.btn_equals:
                float result = compute(calcScreen.getText().toString());
                calcScreen.setText(Float.toString(result));
                break;
            default:
                break;
        }
    }
}
