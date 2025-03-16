package com.sureyyakkus.sanatkoleksiyonum;
/*
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EserDuzenleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eser_duzenle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}*/
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EserDuzenleActivity extends AppCompatActivity {

    private EditText eserAdiGuncelleText, sanatciAdiGuncelleText, eserTarihiGuncelleText;
    private Button guncelleButton;
    private DatabaseHelper dbHelper;
    private int eserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eser_duzenle);

        // XML bileşenlerini bağla
        eserAdiGuncelleText = findViewById(R.id.eserAdıGuncelleText);
        sanatciAdiGuncelleText = findViewById(R.id.sanatcıAdiGuncelleText);
        eserTarihiGuncelleText = findViewById(R.id.eserTarihiGuncelleText);
        guncelleButton = findViewById(R.id.guncelleButton);

        dbHelper = new DatabaseHelper(this);

        // MainActivity'den gelen verileri al
        Intent intent = getIntent();
        eserId = intent.getIntExtra("eserId", -1);
        String eserAdi = intent.getStringExtra("eserAdi");
        String sanatciAdi = intent.getStringExtra("sanatciAdi");
        int eserYili = intent.getIntExtra("eserYili", 0);

        // Alınan bilgileri EditText'lere yerleştir
        eserAdiGuncelleText.setText(eserAdi);
        sanatciAdiGuncelleText.setText(sanatciAdi);
        eserTarihiGuncelleText.setText(String.valueOf(eserYili));

        // Güncelle butonuna tıklandığında
        guncelleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yeniEserAdi = eserAdiGuncelleText.getText().toString();
                String yeniSanatciAdi = sanatciAdiGuncelleText.getText().toString();
                int yeniEserYili = Integer.parseInt(eserTarihiGuncelleText.getText().toString());

                // Veri tabanında güncelle
                boolean isUpdated = dbHelper.updateEser(eserId, yeniEserAdi, yeniSanatciAdi, yeniEserYili);

                if (isUpdated) {
                    Toast.makeText(EserDuzenleActivity.this, "Eser başarıyla güncellendi!", Toast.LENGTH_SHORT).show();
                    // MainActivity'ye dön
                    Intent intent = new Intent(EserDuzenleActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Bu Activity'yi kapat
                } else {
                    Toast.makeText(EserDuzenleActivity.this, "Güncelleme sırasında hata oluştu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
