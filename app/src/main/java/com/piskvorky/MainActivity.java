package com.piskvorky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void novaHra(View view) {
        EditText prve_meno = findViewById(R.id.editText_meno_prveho);
        EditText druhe_meno = findViewById(R.id.editText_meno_druheho);
        Intent intent = new Intent(view.getContext(),Hra.class);
        intent.putExtra("meno_prveho",prve_meno.getText().toString());
        intent.putExtra("meno_druheho",druhe_meno.getText().toString());
        startActivity(intent);
    }
}
