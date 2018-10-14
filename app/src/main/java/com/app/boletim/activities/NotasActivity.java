package com.app.boletim.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.app.boletim.R;
import com.app.boletim.adapters.NotasAdapter;
import com.app.boletim.dao.ConfiguracaoFirebase;
import com.app.boletim.dao.NotaDAO;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotasActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_notas) protected RecyclerView recyclerNotas;

    private NotasAdapter adapter;
    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        ButterKnife.bind(this);

        disciplina = (Disciplina) getIntent().getSerializableExtra("disciplina");
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerNotas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotasAdapter(this, disciplina);
        recyclerNotas.setAdapter(adapter);

        if(!adapter.getNotas().isEmpty()) {
            getSupportActionBar().setTitle(adapter.getNotas().get(0).getDisciplina().getNome());
        }
    }


    @OnClick(R.id.fab_adicionar_nota)
    public void abrirCadastoNotas() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("disciplina", disciplina);

        startActivity(new Intent(this, CadastroNotasActivity.class).putExtras(bundle));
    }

    /*
    public static List<Nota> listarNotas(FirebaseAuth auth, NotasAdapter adapter, Disciplina disciplina) {
        final List<Nota> notas = new ArrayList<>();

        ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").child(disciplina.getId()).child("notas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notas.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Nota nota = snapshot.getValue(Nota.class);
                    notas.add(nota);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return notas;
    }*/
}
