package com.om.dairy.ramdairy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatoActivity extends AppCompatActivity {


    private float gold5LitterUnitPrice = 220;
    private float tazaUnitPrice = 20;

    private int gold5LitterUnitAm  = 0;
    private int gold5LitterUnitPm  = 0;

    private int tazaUnitAm  = 0;
    private int tazaUnitPm  = 0;

    private TextView mtvGold5LitterLable;
    private EditText metGold5LitterAm;
    private EditText metGold5LitterPm;
    private TextView mtvGold5LitterPriceLable; // Sub total

    private TextView mtvTazaLable;
    private EditText metTazaAm;
    private EditText metTazaPm;
    private TextView mtvTazaPriceLable;


    private int goldUnit = 0;
    private float gold5LitterSubTotal = 0;
    private float tazaSubTotal = 0;
    private TextView mtvTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Grand total price

        mtvTotalPrice = (TextView)toolbar.findViewById(R.id.tvTotalPrice);

        initView();

        initValue();

        calLogic();


    }



    private void initView() {

        mtvGold5LitterLable = (TextView)findViewById(R.id.tvGold5LitterLable);
        metGold5LitterAm = (EditText) findViewById(R.id.etGold5LitterAm);
        metGold5LitterPm = (EditText) findViewById(R.id.etGold5LitterPm);
        mtvGold5LitterPriceLable = (TextView) findViewById(R.id.tvGold5LitterPriceLable);


        mtvTazaLable = (TextView)findViewById(R.id.tvTazaLable);
        metTazaAm = (EditText) findViewById(R.id.etTazaAm);
        metTazaPm = (EditText) findViewById(R.id.etTazaPm);
        mtvTazaPriceLable = (TextView) findViewById(R.id.tvTazaPriceLable);



    }



    private void initValue() {

        String gold5litter = "<font color=#000000>"+mtvGold5LitterLable.getText()+"</font><br> <font color=#cc0029>"+ gold5LitterUnitPrice +"</font>";
        mtvGold5LitterLable.setText(Html.fromHtml(gold5litter));


        String tazaLable = "<font color=#000000>"+mtvTazaLable.getText()+"</font><br> <font color=#cc0029>"+ tazaUnitPrice +"</font>";
        mtvTazaLable.setText(Html.fromHtml(tazaLable));

    }

    private void calLogic() {

        // 5 Litter gold price calculation Am

        metGold5LitterAm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int unit = 0;
                    if(s.toString().equals("")){
                        unit = Integer.parseInt("0");
                    }else {
                        unit = Integer.parseInt(s.toString());
                    }
                    gold5LitterUnitAm =  unit;
                    calculateGold5LitterPrice();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });

        // 5 Litter gold price calculation Pm

        metGold5LitterPm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int unit = 0;
                    if(s.toString().equals("")){
                        unit = Integer.parseInt("0");
                    }else {
                        unit = Integer.parseInt(s.toString());
                    }
                    gold5LitterUnitPm =  unit;
                    calculateGold5LitterPrice();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });


        // taza price calculation Am

        metTazaAm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                        int unit = 0;
                        if(s.toString().equals("")){
                            unit = Integer.parseInt("0");
                        }else {
                            unit = Integer.parseInt(s.toString());
                        }
                        tazaUnitAm =  unit;
                        calculateTazaLitterPrice();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });

        // taza price calculation Pm

        metTazaPm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int unit = 0;
                    if(s.toString().equals("")){
                        unit = Integer.parseInt("0");
                    }else {
                        unit = Integer.parseInt(s.toString());
                    }
                    tazaUnitPm =  unit;
                    calculateTazaLitterPrice();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });



    }


    private void calculateGold5LitterPrice(){
        gold5LitterSubTotal = ( gold5LitterUnitAm + gold5LitterUnitPm ) * gold5LitterUnitPrice;
        mtvGold5LitterPriceLable.setText(""+gold5LitterSubTotal);

        calculateGrandTotal();
    }
    private void calculateTazaLitterPrice(){


        tazaSubTotal = ( tazaUnitPm + tazaUnitAm ) * tazaUnitPrice;
        mtvTazaPriceLable.setText(""+tazaSubTotal);

        calculateGrandTotal();
    }

    private void calculateGrandTotal(){

        float grandTotal = gold5LitterSubTotal + tazaSubTotal ;
        mtvTotalPrice.setText(""+grandTotal);

    }

}
