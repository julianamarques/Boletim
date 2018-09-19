package com.app.boletim.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.app.boletim.R;
import com.app.boletim.adapters.ListaNotasAdapter;
import com.app.boletim.dao.ConfiguracaoFirebase;
import com.app.boletim.models.Nota;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotasActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_notas) protected RecyclerView recyclerNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerNotas.setLayoutManager(new LinearLayoutManager(this));
        ListaNotasAdapter adapter = new ListaNotasAdapter(this, listarNotas());
        recyclerNotas.setAdapter(adapter);

        if(!listarNotas().isEmpty()) {
            getSupportActionBar().setTitle(listarNotas().get(0).getDisciplina().getNome());
        }
    }

    public List<Nota> listarNotas() {
        final List<Nota> notas = new ArrayList<>();

        ConfiguracaoFirebase.getDatabaseReference().child("disciplinas").child("notas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notas.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Nota nota = snapshot.getValue(Nota.class);
                    notas.add(nota);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return notas;
    }
}
