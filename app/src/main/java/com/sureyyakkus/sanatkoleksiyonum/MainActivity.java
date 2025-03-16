package com.sureyyakkus.sanatkoleksiyonum;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EserAdapter eserAdapter;
    private List<Eser> eserList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Veri tabanından eserleri getir
        eserList = databaseHelper.getAllEserler();

        // Adaptörü bağla
        eserAdapter = new EserAdapter(this, eserList, databaseHelper);
        recyclerView.setAdapter(eserAdapter);
    }
    public void startUpdateActivity(Eser eser) {
        Intent intent = new Intent(this, EserDuzenleActivity.class);
        intent.putExtra("eserId", eser.getId());
        intent.putExtra("eserAdi", eser.getEserAdi());
        intent.putExtra("sanatciAdi", eser.getSanatciAdi());
        intent.putExtra("eserYili", eser.getEserYili());
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(menu.NONE,menu.FIRST,0,"Eser Ekle");

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) { // Menü öğesi id'si
            // EserEkleActivity'ye yönlendir
            Intent intent = new Intent(this, EserEkleActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Güncel eser listesini al ve RecyclerView'u güncelle
        List<Eser> eserList = databaseHelper.getAllEserler();
        eserAdapter = new EserAdapter(this, eserList, databaseHelper);
        recyclerView.setAdapter(eserAdapter);
    }


}
