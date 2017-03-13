package com.example.ics_user.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    TextView calcScreen;

    boolean sign = false;
    boolean signReset = true;
    boolean parenthesis = false;
    private String TAG;
    private Deque<Character> opStack = new ArrayDeque<Character>();
    private List<String> postfix = new ArrayList<String>();

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
        String expr =  calcScreen.getText().toString();
        List<String> exprList = toPostfix(expr);
        Deque<Double> stack = new ArrayDeque<Double>();

        for(int i = 0; i != exprList.size(); ++i)
        {
            // Determine if current element is digit or not
            if(Character.isDigit(exprList.get(i).charAt(0)))
            {
                stack.addLast(Double.parseDouble(exprList.get(i)));
            }
            else
            {
                double tempResult = 0;
                double temp;

                switch(exprList.get(i))
                {
                    case "+": temp = stack.removeLast();
                        tempResult = stack.removeLast() + temp;
                        break;

                    case "-": temp = stack.removeLast();
                        tempResult = stack.removeLast() - temp;
                        break;

                    case "x": temp = stack.removeLast();
                        tempResult = stack.removeLast() * temp;
                        break;

                    case "/": temp = stack.removeLast();
                        tempResult = stack.removeLast() / temp;
                        break;
                }
                stack.addLast(tempResult);
            }
        }


        calcScreen.setText(String.valueOf(stack.removeLast()));
        stack.clear();
    }

    public List<String> toPostfix(String expr){
        StringBuilder number = new StringBuilder();

        postfix.clear();

        for(int i=0;i<expr.length();i++){
            if(Character.isDigit(expr.charAt(i))){
                number.append(expr.charAt(i));

                while((i+1) != expr.length() && (Character.isDigit(expr.charAt(i+1))
                        || expr.charAt(i+1) == '.'))
                {
                    number.append(expr.charAt(++i));
                }

                postfix.add(number.toString());
                number.delete(0, number.length());

            }
            else{
                inputToStack(expr.charAt(i));
            }
        }

        clearStack();
               return postfix;
    }

    private void inputToStack(char input)
    {
        if(opStack.isEmpty() || input == '(')
            opStack.addLast(input);
        else
        {
            if(input == ')')
            {
                while(!opStack.getLast().equals('('))
                {
                    postfix.add(opStack.removeLast().toString());
                }
                opStack.removeLast();
            }
            else
            {
                if(opStack.getLast().equals('('))
                    opStack.addLast(input);
                else
                {
                    while(!opStack.isEmpty() && !opStack.getLast().equals('(') &&
                            getPrecedence(input) <= getPrecedence(opStack.getLast()))
                    {
                        postfix.add(opStack.removeLast().toString());
                    }
                    opStack.addLast(input);
                }
            }
        }
    }

    private int getPrecedence(char op)
    {
        if (op == '+' || op == '-')
            return 1;
        else if (op == 'x' || op == '/')
            return 2;
        else if (op == '^')
            return 3;
        else return 0;
    }


    private void clearStack()
    {
        while(!opStack.isEmpty())
        {
            postfix.add(opStack.removeLast().toString());
        }
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
