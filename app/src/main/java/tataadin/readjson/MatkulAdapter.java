package tataadin.readjson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MatkulAdapter extends RecyclerView.Adapter<MatkulAdapter.MyViewHolder> {
    private Context context;
    private List<Matkul> matkulList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, kode;

        public MyViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.nama_matkul);
            kode = view.findViewById(R.id.kode_matkul);
        }
    }

    public MatkulAdapter(Context context, List<Matkul> matkulList) {
        this.context = context;
        this.matkulList= matkulList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matkul_list, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Matkul matkul = matkulList.get(position);

        holder.nama.setText(matkul.getNama());
        holder.kode.setText(matkul.getKode());
    }

    public int getItemCount() {
        return matkulList.size();
    }
}
