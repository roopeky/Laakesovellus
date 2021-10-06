package com.example.lkesovellus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DrugInfoActivity extends AppCompatActivity {

    private TextView drugName;
    private TextView drugAmount;
    private TextView drugPrice;
    private int taken;
    int infoAmountOfDrug;
    double infoPriceOfDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_info);

        drugName = findViewById(R.id.drugNameTextView);
        drugAmount = findViewById(R.id.drugAmountTextView);
        drugPrice = findViewById(R.id.drugPriceTextView);
        taken = 0;

        Bundle b = getIntent().getExtras();
        int i = b.getInt(MainActivity.EXTRA, 0);

        int infoAmountOfDrug = Global.getInstance().getDrugs().get(i).getDrugAmount();
        double infoPriceOfDrug = Global.getInstance().getDrugs().get(i).getDrugPrice();

        drugName.setText(Global.getInstance().getDrugs().get(i).getDrugName());
        drugAmount.setText("Doses left: " + infoAmountOfDrug + "/" + infoAmountOfDrug);
        drugPrice.setText(infoPriceOfDrug + "€, Cost per dose: " + (infoPriceOfDrug / infoAmountOfDrug) + "€");
    }
}
