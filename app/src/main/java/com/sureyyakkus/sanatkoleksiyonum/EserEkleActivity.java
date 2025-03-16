package com.sureyyakkus.sanatkoleksiyonum;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EserEkleActivity extends AppCompatActivity {

    private EditText etEserAdi, etSanatciAdi, etEserYili;
    private Button btnEkle;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eser_ekle);

        etEserAdi = findViewById(R.id.artNameText);
        etSanatciAdi = findViewById(R.id.artistNameText);
        etEserYili = findViewById(R.id.artYearText);
        btnEkle = findViewById(R.id.ekleButton);

        databaseHelper = new DatabaseHelper(this);

        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eserAdi = etEserAdi.getText().toString().trim();
                String sanatciAdi = etSanatciAdi.getText().toString().trim();
                String eserYiliStr = etEserYili.getText().toString().trim();

                if (eserAdi.isEmpty() || sanatciAdi.isEmpty() || eserYiliStr.isEmpty()) {
                    Toast.makeText(EserEkleActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                    return;
                }

                int eserYili;
                try {
                    eserYili = Integer.parseInt(eserYiliStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(EserEkleActivity.this, "Eser yılı geçerli bir sayı olmalı", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Veri tabanına ekle
                long id = databaseHelper.insertEser(eserAdi, sanatciAdi, eserYili);
                if (id != -1) {
                    Toast.makeText(EserEkleActivity.this, "Eser başarıyla eklendi", Toast.LENGTH_SHORT).show();

                    // MainActivity'ye geri dön
                    Intent intent = new Intent(EserEkleActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EserEkleActivity.this, "Ekleme başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
