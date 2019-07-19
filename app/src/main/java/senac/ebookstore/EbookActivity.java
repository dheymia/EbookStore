package senac.ebookstore;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import senac.ebookstore.models.Ebook;

public class EbookActivity extends AppCompatActivity {

    TextView txtTitulo, txtAutor, txtSinopse, txtIsbn, txtUrlebook, txtUrlImagem;
    Spinner spTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        spTipo = findViewById(R.id.spiTipo);
        txtSinopse = findViewById(R.id.txtSinopse);
        txtIsbn = findViewById(R.id.txtIsbn);
        txtUrlebook = findViewById(R.id.txtUrlEbook);
        txtUrlImagem = findViewById(R.id.txtUrlImagem);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = txtTitulo.getText().toString();
                String autor = txtAutor.getText().toString();
                String tipo = spTipo.getSelectedItem().toString();
                String sinopse = txtSinopse.getText().toString();
                String isbn = txtIsbn.getText().toString();
                String urlebook = txtUrlebook.getText().toString();
                String urlimagem = txtUrlImagem.getText().toString();

                Ebook ebook = new Ebook(isbn, urlimagem, titulo, autor, sinopse, tipo, urlebook);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ebooks/" + ebook.getTipo() + "/" + ebook.getIsbn());

                myRef.setValue(ebook);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
