package senac.ebookstore;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import senac.ebookstore.adapters.EbookAdapter;
import senac.ebookstore.models.Ebook;
import senac.ebookstore.models.EbookFirebaseHelper;

public class MainActivity extends AppCompatActivity {
    private TextView txtNome;
    private TextView mTextMessage;
    private RecyclerView recyclerView;
    EbookAdapter adapter;
    DatabaseReference myRef;
    private List<Ebook> ebookList = new ArrayList<>();
    ProgressDialog progressDialog;
    static Ebook ebookSelecionado;
    static boolean alterar = false;


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            ebookSelecionado = ebookList.get(position);
            Toast.makeText(MainActivity.this, "You Clicked: " + ebookSelecionado.getIsbn(), Toast.LENGTH_SHORT).show();

            alterar = true;

            Intent intent = new Intent(getBaseContext(), EbookActivity.class);
            startActivity(intent);
        }
    };

    private ValueEventListener ListenerGeral = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ebookList.clear();

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Ebook ebook = ds.getValue(Ebook.class);
                ebookList.add(ebook);
            }

            adapter = new EbookAdapter(ebookList, MainActivity.this);
            adapter.setOnItemClickListener(onItemClickListener);
            recyclerView.setAdapter(adapter);

            progressDialog.dismiss();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    myRef.limitToFirst(100).addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_romances:
                    mTextMessage.setText(R.string.title_romance);
                    myRef.limitToFirst(100).orderByChild("tipo").equalTo("Romance").addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_tecnicos:
                    mTextMessage.setText(R.string.title_tecnicos);
                    myRef.limitToFirst(100).orderByChild("tipo").equalTo("Técnico").addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_negocios:
                    mTextMessage.setText(R.string.title_negocios);
                    myRef.limitToFirst(100).orderByChild("tipo").equalTo("Negócios").addValueEventListener(ListenerGeral);
                    return true;
                case R.id.navigation_ebook:
                    alterar = false;
                    Intent intent = new Intent(getBaseContext(), EbookActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        txtNome = findViewById(R.id.txtNome);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("signature", "visitante");

        txtNome.setText("Olá " + name);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ebooks");

        recyclerView = findViewById(R.id.listEbooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        myRef.limitToFirst(100).addValueEventListener(ListenerGeral);

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_config) {
            Intent novaConfig = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(novaConfig);
        }
        return super.onOptionsItemSelected(item);
    }
}
