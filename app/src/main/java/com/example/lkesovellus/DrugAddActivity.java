package com.example.lkesovellus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class DrugAddActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText drugName;
    private EditText drugPrice;
    private EditText drugAmount;
    private String name;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_add);

        saveButton = findViewById(R.id.buttonAddToList);
        drugName = findViewById(R.id.drugNameInput);
        drugPrice = findViewById(R.id.drugPriceInput);
        drugAmount = findViewById(R.id.amountTextEdit);
    }

    public void onSaveButtonClick (View v){

        Drug newDrug = new Drug(drugName.getText().toString(), Integer.parseInt(drugPrice.getText().toString()), Integer.parseInt(drugAmount.getText().toString()));
        Global.getInstance().getDrugs().add(newDrug);

        Intent drugSaveActivity = new Intent(DrugAddActivity.this, MainActivity.class);
        Log.d("TAG", String.valueOf(newDrug.getDrugAmount()));
        startActivity(drugSaveActivity);
    }
}