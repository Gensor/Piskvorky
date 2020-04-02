package com.piskvorky;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Hra extends AppCompatActivity implements View.OnClickListener{



    private int hracia_plocha[][];
    private ImageButton tlacidla[][];
    private Hrac prvy_hrac;
    private Hrac druhy_hrac;
    private Hrac aktualny_hrac;
    TextView textView_meno_aktualneho;
    TextView textView_meno_prveho;
    TextView textView_meno_druheho;
    TextView skore_prveho;
    TextView skore_druheho;


    private final int znak_druheho_hraca = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hra);

        String meno_prveho = getIntent().getStringExtra("meno_prveho");
        String meno_druheho =getIntent().getStringExtra("meno_druheho");
        prvy_hrac = new Hrac(meno_prveho);
        prvy_hrac.setZnak(-1);
        druhy_hrac = new Hrac(meno_druheho);
        druhy_hrac.setZnak(1);

        aktualny_hrac = prvy_hrac;
        textView_meno_aktualneho = findViewById(R.id.textView_hrac_na_rade);
        textView_meno_aktualneho.setText(meno_prveho);
        textView_meno_aktualneho.setTextColor(ContextCompat.getColor(this,R.color.CERVENA));
        naplnPoleTlacidlami();
        textView_meno_prveho= findViewById(R.id.textView_meno_prveho);
        textView_meno_prveho.setText(meno_prveho);

        skore_prveho=findViewById(R.id.textView_skore_prveho);
        skore_druheho=findViewById(R.id.textView_skore_druheho);
        skore_prveho.setText(" "+0);
        skore_druheho.setText(0+" ");
        textView_meno_druheho= findViewById(R.id.textView_meno_druheho);
        textView_meno_druheho.setText(meno_druheho);
    }

    public void naplnPoleTlacidlami(){
        hracia_plocha=new int[3][3];
        tlacidla=new ImageButton[3][3];
        for (int riadok=0;riadok<3;riadok++){
            for(int stlpec=0;stlpec<3;stlpec++){
                String nazov_tlacidla = "button_"+riadok+"."+stlpec;

                int resid =getResources().getIdentifier(nazov_tlacidla,"id",getPackageName());

                tlacidla[riadok][stlpec]=findViewById(resid);
                tlacidla[riadok][stlpec].setOnClickListener(this);
                tlacidla[riadok][stlpec].setImageResource(R.drawable.nic);

            }

        }

    }
    public boolean maBod(int znak,int riadok,int stlpec){
        if (maBodHorizontalne(riadok)||maBodDiagonalneZDola(riadok, stlpec)||maBodDiagonalneZHora(riadok,stlpec)||maBodVertikalne(stlpec)){
                zapisBod(znak);
                naplnPoleTlacidlami();
                return true;
        }
        return false;

    }

    private void zapisBod(int znak) {
        if(znak==-1){
            int skore=prvy_hrac.getSkore()+1;
            prvy_hrac.setSkore(skore);
            skore_prveho.setText(" "+skore);
        }else{
            int skore = druhy_hrac.getSkore()+1;
            druhy_hrac.setSkore(skore);
            skore_druheho.setText(skore+" ");
        }
    }

    public boolean maBodHorizontalne(int riadok){
       int vysledok =0;
       vysledok+=hracia_plocha[riadok][0];
       vysledok+=hracia_plocha[riadok][1];
       vysledok+=hracia_plocha[riadok][2];
        return (vysledok == 3) || (vysledok == -3);
    }

    public boolean maBodVertikalne(int stlpec){
        int vysledok =0;
        vysledok+=hracia_plocha[0][stlpec];
        vysledok+=hracia_plocha[1][stlpec];
        vysledok+=hracia_plocha[2][stlpec];
        return (vysledok == 3) || (vysledok == -3);
    }

    public boolean maBodDiagonalneZHora(int riadok,int stlpec){
        int vysledok=0;
        if(riadok!=stlpec){
            return false;
        }else {
            vysledok+=hracia_plocha[0][0];
            vysledok+=hracia_plocha[1][1];
            vysledok+=hracia_plocha[2][2];
            return vysledok == 3 || vysledok == -3;
        }
    }
    public boolean maBodDiagonalneZDola(int riadok ,int stlpec){
        int vysledok =0;
        vysledok+=hracia_plocha[2][0];
        vysledok+=hracia_plocha[1][1];
        vysledok+=hracia_plocha[0][2];
        return vysledok >= 3 || vysledok <= -3;
    }

    public boolean vlozZnak(int znak,int riadok,int stlpec){
        if(hracia_plocha[riadok][stlpec]==0) {
            hracia_plocha[riadok][stlpec] = znak;
            return true;
        }else {
            System.out.println("nemoze tam pridat znak,pozicia je obsadena");
            int sum =0;
            for (int iriadok=0;iriadok<3;iriadok++){
                for (int istlpec=0;istlpec<3;istlpec++){
                    if(hracia_plocha[iriadok][istlpec]!=0)
                    sum++;
                }
            }
            if(sum==9){
                Toast.makeText(getApplicationContext(),"REMIZA", Toast.LENGTH_SHORT).show();
                naplnPoleTlacidlami();
            }
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        int znak = aktualny_hrac.getZnak();
        if(znak == -1) {
            for (int riadok = 0; riadok < 3; riadok++) {
                for (int stlpec = 0; stlpec < 3; stlpec++) {
                    if (v.getId() == tlacidla[riadok][stlpec].getId())
                        if (vlozZnak(znak,riadok,stlpec)) {
                            ((ImageButton) v).setImageResource(R.drawable.x);
                            aktualny_hrac=druhy_hrac;
                            textView_meno_aktualneho.setText(aktualny_hrac.getMeno());
                            textView_meno_aktualneho.setTextColor(ContextCompat.getColor(this,R.color.MODRA));
                            maBod(znak,riadok,stlpec);
                        }
                }
            }
        }else
        {
            for (int riadok = 0; riadok < 3; riadok++) {
                for (int stlpec = 0; stlpec < 3; stlpec++) {
                    if (v.getId() == tlacidla[riadok][stlpec].getId())
                        if (vlozZnak(znak, riadok, stlpec)) {
                            ((ImageButton) v).setImageResource(R.drawable.o);
                            aktualny_hrac=prvy_hrac;
                            textView_meno_aktualneho.setText(aktualny_hrac.getMeno());
                            textView_meno_aktualneho.setTextColor(ContextCompat.getColor(this,R.color.CERVENA));
                            maBod(znak,riadok,stlpec);
                        }
                }
            }
        }
    }

    public void vratSaNaUvod(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
