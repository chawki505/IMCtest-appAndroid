package com.example.chawki.imctest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    private DecimalFormat df = new DecimalFormat("##.##");
    private EditText mPoids;
    private EditText mTaille;
    private RadioButton mCentimetre;
    private RadioButton mMetre;
    private CheckBox mConfirmer;
    private Button mCalculer;
    private Button mReset;
    private TextView mResultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        mConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyBoard();
                if (mConfirmer.isChecked())
                    mCalculer.setEnabled(true);
                else
                    mCalculer.setEnabled(false);

            }
        });

        mCalculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();
                if (!mTaille.getText().toString().isEmpty() && !mPoids.getText().toString().isEmpty())
                    calculerIMC();
                else
                    Toast.makeText(MainActivity.this, "Donnez un poids et une taille valide !", Toast.LENGTH_LONG).show();
            }
        });


        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_item_action_close == item.getItemId()) {
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    //fonction pour faire la relation entre les fichier XML et lactivité
    private void init() {
        mPoids = findViewById(R.id.activity_main_inputPoids_editText);
        mTaille = findViewById(R.id.activity_main_inputTaille_editText);
        mCentimetre = findViewById(R.id.activity_main_centimetre_radioButton);
        mMetre = findViewById(R.id.activity_main_metre_radioButton);
        mConfirmer = findViewById(R.id.activity_main_confirmer_checkBox);
        mCalculer = findViewById(R.id.activity_main_calculer_btn);
        mReset = findViewById(R.id.activity_main_reset_btn);
        mResultat = findViewById(R.id.activity_main_resultat_textView);
        reset();
    }


    //fonction pour reset les données entré
    private void reset() {
        mPoids.getText().clear();
        mTaille.getText().clear();
        mResultat.setText(("Vous devez cliquer sur le bouton « Calculer l'IMC » pour obtenir un résultat."));
        mConfirmer.setChecked(false);
        mCalculer.setEnabled(false);
        mCentimetre.setChecked(true);
        mMetre.setChecked(false);
    }

    //fermer le clavier
    private void closeKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(mPoids.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mTaille.getWindowToken(), 0);
    }

    //fonction pour calculer IMC
    private void calculerIMC() {
        // On récupère la taille
        String taille = mTaille.getText().toString();
        // On récupère le poids
        String poids = mPoids.getText().toString();

        //conversion String to float
        float tailleValue = Float.valueOf(taille);
        float poidsValue = Float.valueOf(poids);


        if (mCentimetre.isChecked()) {
            tailleValue = tailleValue / 100;
        }

        float imc = (float) (poidsValue / Math.pow(tailleValue, 2));
        setMessageIMC(imc);

    }


    private float calculePoidIdeal(float imc) {
        // On récupère le poids
        float poidsValue = Float.valueOf(mPoids.getText().toString());
        float tailleValue = Float.valueOf(mTaille.getText().toString());

        if (mCentimetre.isChecked()) {
            tailleValue = tailleValue / 100;
        }

        float poidIdealImcMoin = (float) (18.5 * Math.pow(tailleValue, 2));
        float poidIdealImcPlus = (float) (24.9 * Math.pow(tailleValue, 2));


        if (imc < 18.5) {
            return poidIdealImcMoin - poidsValue;
        } else if (imc > 25) {
            return poidsValue - poidIdealImcPlus;
        }
        return 0;
    }


    //fonction pour afficher un msg de l'imc
    private void setMessageIMC(float imc) {
        if (imc < 16.5)
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  dénutrition ou anorexie"
                    + "\n il faut  imperativement gagner " + df.format(calculePoidIdeal(imc)) + " Kg."));
        else if (imc >= 16.5 && imc < 18.5)
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  maigreur"
                    + "\n il faut gagner " + df.format(calculePoidIdeal(imc)) + " Kg."));
        else if (imc >= 18.5 && imc < 25)
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  corpulence normale"));
        else if (imc >= 25 && imc < 30)
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  surpoids"
                    + "\n il faut perdre " + df.format(calculePoidIdeal(imc)) + " Kg."));
        else if (imc >= 30 && imc < 35)
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  obésité modérée"
                    + "\n il faut vraiment perdre " + df.format(calculePoidIdeal(imc)) + " Kg."));
        else if (imc >= 35 && imc < 40)
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  obésité sévère"
                    + "\n il faut imperativement perdre " + df.format(calculePoidIdeal(imc)) + " Kg."));
        else
            mResultat.setText(("Votre IMC est " + df.format(imc) + "\n  obésité morbide ou massive"
                    + "\n il faut imperativement perdre " + df.format(calculePoidIdeal(imc)) + " Kg. \n la mort vous guette"));


    }


}
