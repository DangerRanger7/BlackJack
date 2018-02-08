package kurtandkierra.blackjack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Kurt and Kierra on 1/30/2018.
 */

/*
    1=Ace
        (need to figure out how to make A=(1 || 11))
    2-10 = 2-10
    11=Jack
    12=Queen
    13=King
*/

public class PlayActivity extends AppCompatActivity {

    //Integer currentNumber;
    int clicks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        // final int currentAmount = 0;

        final int STARTING_AMOUNT = 100;


        // deal button
        Button b = findViewById(R.id.deal_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView;
                ImageView imageView;

                /*/ set unseen cards to 0
                textView = findViewById(R.id.player_c3);
                textView.setText("0");
                textView = findViewById(R.id.dealer_c2);
                textView.setText("0");//*/

                // set unseen cards to back
                imageView = findViewById(R.id.player_ci3);
                imageView.setImageResource(R.drawable.card_back);
                imageView = findViewById(R.id.dealer_ci2);
                imageView.setImageResource(R.drawable.card_back);
                // set totals to 0
                textView = findViewById(R.id.dealer_total);
                textView.setText("0");
                textView = findViewById(R.id.player_total);
                textView.setText("0");

                for (int i = 0; i < 3; i++) {// get starting hands
                    draw_card(i);
                }// end for

                // hide deal button
                Button options = findViewById(R.id.deal_button);
                options.setClickable(false);
                options.setVisibility(View.INVISIBLE);
                // activate skip button
                options = findViewById(R.id.hold_button);
                options.setClickable(true);
                options.setVisibility(View.VISIBLE);
                // activate draw button
                options = findViewById(R.id.draw_button);
                options.setClickable(true);
                options.setVisibility(View.VISIBLE);


            }// end onClick
        });// end Listener

        //bet button
        b = findViewById(R.id.bet_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //count number of clicks

                int currentNumber;
                TextView moneyTV = findViewById(R.id.money_textview);
                //check et and get bet amount
                EditText bet_et = findViewById(R.id.betAmount_editText);
                String betStr = bet_et.getText().toString();
                //validate

                if (betStr.isEmpty()) {
                    bet_et.setText("Need Valid Bet Amount");
                    return;
                }

                Integer bet;
                bet = Integer.valueOf(betStr);
                if (clicks == 0) {
                    if (bet > STARTING_AMOUNT) {
                        bet_et.setText("Need Valid Bet Amount");
                        return;
                    }
                     currentNumber = STARTING_AMOUNT - bet;
                }else {
                    String betCheck = betStr;
                    int bet2 = Integer.valueOf(betCheck);

                    //get current money amount
                    String money = moneyTV.getText().toString();
                    money = money.replace("$", "");

                    //convert money amount to int
                    int moneyAmount = Integer.valueOf(money);

                    if (bet2 > moneyAmount) {
                        bet_et.setText("Need Valid Bet Amount");
                        return;
                    }else{
                        /*TextView tv = moneyTV;
                        tv.replace*/
                        currentNumber = moneyAmount - bet2;
                    }

                }
                /*********************************************************************************/

                moneyTV.setText("$" + currentNumber);
                clicks++;
            }
        });//end of Listener

        // hold button
        b = findViewById(R.id.hold_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw_card(4);// dealer card 2

                winner();

                // activate deal button
                Button options = findViewById(R.id.deal_button);
                options.setClickable(true);
                options.setVisibility(View.VISIBLE);
                // hide skip button
                options = findViewById(R.id.hold_button);
                options.setClickable(false);
                options.setVisibility(View.INVISIBLE);
                // hide draw button
                options = findViewById(R.id.draw_button);
                options.setClickable(false);
                options.setVisibility(View.INVISIBLE);
            }// end onClick
        });// end skip Listener

        // draw button
        b=findViewById(R.id.draw_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw_card(3);// player optional card
                draw_card(4);// dealer card 2

                winner();/*********************************************************************************************************/

                // activate deal button
                Button options = findViewById(R.id.deal_button);
                options.setClickable(true);
                options.setVisibility(View.VISIBLE);
                // hide skip button
                options = findViewById(R.id.hold_button);
                options.setClickable(false);
                options.setVisibility(View.INVISIBLE);
                // hide draw button
                options = findViewById(R.id.draw_button);
                options.setClickable(false);
                options.setVisibility(View.INVISIBLE);
            }// end onClick
        });// end draw Listener

    }// end main onCreate

// functions --------------------------------------------------------------------------------

    public void draw_card(int place){
            TextView textView;
            ImageView imageView;
            Random ran = new Random();
            int card_num = ran.nextInt(13) + 1;//13 is max num 1 is min
            /*
                1=Ace
                    (need to figure out how to make A=(1 || 11))
                2-10 = 2-10
                11=Jack
                12=Queen
                13=King
            */
            int card_type = ran.nextInt(4);
            /*
                0 = spade
                1 = club
                2 = diamond
                3 = heart
            */
            int points;

            // set card
            if(card_num > 10) {
                points = 10;
            }else{
                points = card_num;
            }

            // set cards on table
            if (place == 0) { // dealer card 1
                imageView = findViewById(R.id.dealer_ci1);
                set_card_type(imageView,card_type,card_num);

                // dealer total
                textView = findViewById(R.id.dealer_total);
                String d_total = textView.getText().toString().trim();
                int d_total_int = Integer.parseInt(d_total) + points;
                d_total = "" + d_total_int;
                textView.setText(d_total);
            }  else if (place == 1) { // player card 1
                imageView = findViewById(R.id.player_ci1);
                set_card_type(imageView,card_type,card_num);

                // player total
                textView = findViewById(R.id.player_total);
                String p_total = textView.getText().toString().trim();
                int p_total_int = Integer.parseInt(p_total) + points;
                p_total = "" + p_total_int;
                textView.setText(p_total);
            } else if (place == 2) { // player card 2
                imageView = findViewById(R.id.player_ci2);
                set_card_type(imageView,card_type,card_num);

                // player total
                textView = findViewById(R.id.player_total);
                String p_total = textView.getText().toString().trim();
                int p_total_int = Integer.parseInt(p_total) + points;
                p_total = "" + p_total_int;
                textView.setText(p_total);
            }else if (place == 3) { // player optional card 3
                imageView = findViewById(R.id.player_ci3);
                set_card_type(imageView,card_type,card_num);

                // player total
                textView = findViewById(R.id.player_total);
                String p_total = textView.getText().toString().trim();
                int p_total_int = Integer.parseInt(p_total) + points;
                p_total = "" + p_total_int;
                textView.setText(p_total);
            }else if (place == 4) { // dealer card 2
                imageView = findViewById(R.id.dealer_ci2);
                set_card_type(imageView,card_type,card_num);

                // dealer total
                textView = findViewById(R.id.dealer_total);
                String d_total = textView.getText().toString().trim();
                int d_total_int = Integer.parseInt(d_total) + points;
                d_total = "" + d_total_int;
                textView.setText(d_total);
            } else { // if ERROR
                Toast.makeText(
                        PlayActivity.this,
                        "Something went wrong",
                        Toast.LENGTH_LONG
                ).show();
            }// end if
        } // end draw_card


    public void winner(){
        int currentNumber;

        // get dealer total
        TextView textView = findViewById(R.id.dealer_total);
        String d_total_s = textView.getText().toString().trim();
        int d_total_int = Integer.parseInt(d_total_s);

        // get player total
        textView = findViewById(R.id.player_total);
        String p_total_s = textView.getText().toString().trim();
        int p_total_int = Integer.parseInt(p_total_s);

        if(p_total_int == d_total_int){ //tie
            Toast.makeText(
                    PlayActivity.this,
                    "TIE",
                    Toast.LENGTH_LONG
            ).show();
        }else if(p_total_int>21 && d_total_int >21){ //tie 2
            Toast.makeText(
                    PlayActivity.this,
                    "TIE",
                    Toast.LENGTH_LONG
            ).show();
        }else if(p_total_int < d_total_int && d_total_int <= 21){// lose
            Toast.makeText(
                    PlayActivity.this,
                    "You Lose",
                    Toast.LENGTH_LONG
            ).show();
            //GAIN MONEY IF WIN/***********************************************************************************************************/

        TextView moneyTV = findViewById(R.id.money_textview);
        String currentNumStr = moneyTV.getText().toString();
        currentNumStr = currentNumStr.replace("$", "");

        currentNumber = Integer.valueOf(currentNumStr);
        currentNumber -= 20;

            if (currentNumber < 0){
                currentNumber = 0;
            }

        moneyTV.setText("$" + currentNumber);

        }else if(p_total_int > 21){// lose 2
            Toast.makeText(
                    PlayActivity.this,
                    "You Lose",
                    Toast.LENGTH_LONG
            ).show();

            TextView moneyTV = findViewById(R.id.money_textview);
            String currentNumStr = moneyTV.getText().toString();
            currentNumStr = currentNumStr.replace("$", "");

            currentNumber = Integer.valueOf(currentNumStr);
            currentNumber -= 20;

            if (currentNumber < 0){
                currentNumber = 0;
            }

            moneyTV.setText("$" + currentNumber);

        }else if(p_total_int > d_total_int && p_total_int <=21){// win
            Toast.makeText(
                    PlayActivity.this,
                    "You Win!",
                    Toast.LENGTH_LONG
            ).show();

            TextView moneyTV = findViewById(R.id.money_textview);
            String currentNumStr = moneyTV.getText().toString();
            currentNumStr = currentNumStr.replace("$", "");

            currentNumber = Integer.valueOf(currentNumStr);
            currentNumber += 20;

            moneyTV.setText("$" + currentNumber);

        }else if(d_total_int > 21){// win
            Toast.makeText(
                    PlayActivity.this,
                    "You Win!",
                    Toast.LENGTH_LONG
            ).show();

            TextView moneyTV = findViewById(R.id.money_textview);
            String currentNumStr = moneyTV.getText().toString();
            currentNumStr = currentNumStr.replace("$", "");

            currentNumber = Integer.valueOf(currentNumStr);
            currentNumber += 20;

            moneyTV.setText("$" + currentNumber);

        }else{// ERROR
            Toast.makeText(
                    PlayActivity.this,
                    "Something went wrong",
                    Toast.LENGTH_LONG
            ).show();
        }// end if
    }// end winner

    public void set_card_type(ImageView card, int type, int num){
         /*
                0 = spade
                1 = club
                2 = diamond
                3 = heart

                 1=Ace
                    (need to figure out how to make A=(1 || 11))
                2-10 = 2-10
                11=Jack
                12=Queen
                13=King
         */
         if(type == 0){ // spades
             if(num == 1){
                 card.setImageResource(R.drawable.card_sa);
             }else if(num == 2){
                 card.setImageResource(R.drawable.card_s2);
             }else if(num == 3){
                 card.setImageResource(R.drawable.card_s3);
             }else if(num == 4){
                 card.setImageResource(R.drawable.card_s4);
             }else if(num == 5){
                 card.setImageResource(R.drawable.card_s5);
             }else if(num == 6){
                 card.setImageResource(R.drawable.card_s6);
             }else if(num == 7){
                 card.setImageResource(R.drawable.card_s7);
             }else if(num == 8){
                 card.setImageResource(R.drawable.card_s8);
             }else if(num == 9){
                 card.setImageResource(R.drawable.card_s9);
             }else if(num == 10){
                 card.setImageResource(R.drawable.card_s10);
             }else if(num == 11){
                 card.setImageResource(R.drawable.card_sj);
             }else if(num == 12){
                 card.setImageResource(R.drawable.card_sq);
             }else if(num == 13){
                 card.setImageResource(R.drawable.card_sk);
             }
         }else if (type == 1){ // clubs
             if(num == 1){
                 card.setImageResource(R.drawable.card_ca);
             }else if(num == 2){
                 card.setImageResource(R.drawable.card_c2);
             }else if(num == 3){
                 card.setImageResource(R.drawable.card_c3);
             }else if(num == 4){
                 card.setImageResource(R.drawable.card_c4);
             }else if(num == 5){
                 card.setImageResource(R.drawable.card_c5);
             }else if(num == 6){
                 card.setImageResource(R.drawable.card_c6);
             }else if(num == 7){
                 card.setImageResource(R.drawable.card_c7);
             }else if(num == 8){
                 card.setImageResource(R.drawable.card_c8);
             }else if(num == 9){
                 card.setImageResource(R.drawable.card_c9);
             }else if(num == 10){
                 card.setImageResource(R.drawable.card_c10);
             }else if(num == 11){
                 card.setImageResource(R.drawable.card_cj);
             }else if(num == 12){
                 card.setImageResource(R.drawable.card_cq);
             }else if(num == 13){
                 card.setImageResource(R.drawable.card_ck);
             }
         }else if(type == 2){ // diamonds
             if(num == 1){
                 card.setImageResource(R.drawable.card_da);
             }else if(num == 2){
                 card.setImageResource(R.drawable.card_d2);
             }else if(num == 3){
                 card.setImageResource(R.drawable.card_d3);
             }else if(num == 4){
                 card.setImageResource(R.drawable.card_d4);
             }else if(num == 5){
                 card.setImageResource(R.drawable.card_d5);
             }else if(num == 6){
                 card.setImageResource(R.drawable.card_d6);
             }else if(num == 7){
                 card.setImageResource(R.drawable.card_d7);
             }else if(num == 8){
                 card.setImageResource(R.drawable.card_d8);
             }else if(num == 9){
                 card.setImageResource(R.drawable.card_d9);
             }else if(num == 10){
                 card.setImageResource(R.drawable.card_d10);
             }else if(num == 11){
                 card.setImageResource(R.drawable.card_dj);
             }else if(num == 12){
                 card.setImageResource(R.drawable.card_dq);
             }else if(num == 13){
                 card.setImageResource(R.drawable.card_dk);
             }
         }else if(type == 3){ // hearts
             if(num == 1){
                 card.setImageResource(R.drawable.card_ha);
             }else if(num == 2){
                 card.setImageResource(R.drawable.card_h2);
             }else if(num == 3){
                 card.setImageResource(R.drawable.card_h3);
             }else if(num == 4){
                 card.setImageResource(R.drawable.card_h4);
             }else if(num == 5){
                 card.setImageResource(R.drawable.card_h5);
             }else if(num == 6){
                 card.setImageResource(R.drawable.card_h6);
             }else if(num == 7){
                 card.setImageResource(R.drawable.card_h7);
             }else if(num == 8){
                 card.setImageResource(R.drawable.card_h8);
             }else if(num == 9){
                 card.setImageResource(R.drawable.card_h9);
             }else if(num == 10){
                 card.setImageResource(R.drawable.card_h10);
             }else if(num == 11){
                 card.setImageResource(R.drawable.card_hj);
             }else if(num == 12){
                 card.setImageResource(R.drawable.card_hq);
             }else if(num == 13){
                 card.setImageResource(R.drawable.card_hk);
             }
         }else{ // ERROR

         }
    }// end setCardType

}


