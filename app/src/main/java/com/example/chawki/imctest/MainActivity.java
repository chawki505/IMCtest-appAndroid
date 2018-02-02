package com.example.chawki.imctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


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
                if (mConfirmer.isChecked())
                    mCalculer.setEnabled(true);
                else
                    mCalculer.setEnabled(false);
            }
        });

        mCalculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTaille.getText().toString().isEmpty() && !mPoids.getText().toString().isEmpty())
                    calculerIMC();
                else
                    Toast.makeText(MainActivity.this, "Hého, tu es un Minipouce ou quoi ?" +
                            "\n donne ton poid et ta taille correctement", Toast.LENGTH_LONG).show();
            }
        });


        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

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


    //fonction pour afficher un msg de l'imc
    private void setMessageIMC(float imc) {
        if (imc < 16.5)
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = dénutrition ou anorexie"));
        else if (imc >= 16.5 && imc < 18.5)
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = maigreur"));
        else if (imc >= 18.5 && imc < 25)
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = corpulence normale"));
        else if (imc >= 25 && imc < 30)
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = surpoids"));
        else if (imc >= 30 && imc < 35)
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = obésité modérée"));
        else if (imc >= 35 && imc < 40)
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = obésité sévère"));
        else
            mResultat.setText(("Votre IMC est " + String.valueOf(imc) + "\n  = obésité morbide ou massive"));


    }


}
