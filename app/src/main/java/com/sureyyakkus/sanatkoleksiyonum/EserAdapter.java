package com.sureyyakkus.sanatkoleksiyonum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EserAdapter extends RecyclerView.Adapter<EserAdapter.EserViewHolder> {

    private Context context;
    private List<Eser> eserList;
    private DatabaseHelper databaseHelper;

    public EserAdapter(Context context, List<Eser> eserList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.eserList = eserList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public EserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanat_eseri_item, parent, false);
        return new EserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EserViewHolder holder, int position) {
        Eser eser = eserList.get(position);
        holder.tvEserAdi.setText(eser.getEserAdi());
        holder.tvSanatciAdi.setText(eser.getSanatciAdi());
        holder.tvEserYili.setText(String.valueOf(eser.getEserYili()));

        // Sil butonu işlemi
        holder.btnSil.setOnClickListener(v -> {
            databaseHelper.deleteEser(eser.getId());
            eserList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, eserList.size());
        });

        // Güncelle butonu işlemi
        holder.btnGuncelle.setOnClickListener(v -> {
            // Güncelleme için bir Activity'ye yönlendirme veya bir dialog açma işlemi yapılabilir.
            if (context instanceof MainActivity) {
                ((MainActivity) context).startUpdateActivity(eser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eserList.size();
    }

    static class EserViewHolder extends RecyclerView.ViewHolder {
        TextView tvEserAdi, tvSanatciAdi, tvEserYili;
        Button btnSil, btnGuncelle;

        public EserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEserAdi = itemView.findViewById(R.id.tvEserAdi);
            tvSanatciAdi = itemView.findViewById(R.id.tvSanatciAdi);
            tvEserYili = itemView.findViewById(R.id.tvEserYili);
            btnSil = itemView.findViewById(R.id.btnSil);
            btnGuncelle = itemView.findViewById(R.id.btnGuncelle);
        }
    }
    // Güncellenmiş verileri listeye ekleyip yeni listeyi tutyoruz
    public void setEserListesi(List<Eser> yeniListe) {
        this.eserList = yeniListe; // Mevcut listeyi güncelle
    }

}

