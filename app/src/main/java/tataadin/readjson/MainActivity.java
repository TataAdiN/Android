package tataadin.readjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView Nama, NIM, SKS, Semester;
    private RecyclerView recyclerView;
    private List<Matkul> matkulList = new ArrayList<>();
    private String url = "http://decemberend98.000webhostapp.com/biodatajson.php", tnama, tnim, tsks, tsemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nama = findViewById(R.id.nama);
        NIM = findViewById(R.id.nim);
        SKS = findViewById(R.id.sks);
        Semester = findViewById(R.id.semester);
        recyclerView = findViewById(R.id.list_matkul);
        Button baca = findViewById(R.id.baca);
        baca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReadJSON().execute();
            }
        });
    }
    public class ReadJSON extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Tunggu Sebentar", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HTTPHandler httpHandler = new HTTPHandler();
            String jsonString = httpHandler.makeServiceCall(url);
            if(jsonString != null){
                try{
                    JSONObject JSON = new JSONObject(jsonString);
                    tnama = JSON.getString("nama");
                    tnim = JSON.getString("nim");
                    JSONObject Akademik = JSON.getJSONObject("akademik");
                    tsks = Akademik.getString("sks");
                    tsemester = Akademik.getString("semester");
                    JSONArray matakuliah = Akademik.getJSONArray("matakuliah");
                    for(int i = 0; i < matakuliah.length(); i++){
                        JSONObject index = matakuliah.getJSONObject(i);
                        Matkul matkul = new Matkul();
                        matkul.setNama(index.getString("nama"));
                        matkul.setKode(index.getString("kode"));
                        matkulList.add(matkul);
                    }
                }catch (JSONException jsne){
                    jsne.printStackTrace();
                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void hasil) {
            super.onPostExecute(hasil);
            Nama.setText(tnama);
            NIM.setText(tnim);
            SKS.setText(tsks);
            Semester.setText(tsemester);
            MatkulAdapter mAdapter = new MatkulAdapter(getBaseContext(), matkulList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }
}
