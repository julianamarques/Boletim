package com.app.boletim.activities;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotasActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_notas) protected RecyclerView recyclerNotas;
    private long disciplinaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        ButterKnife.bind(this);

        disciplinaId = getIntent().getLongExtra("disciplinaId", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerNotas.setLayoutManager(new LinearLayoutManager(this));
        ListaNotasAdapter adapter = new ListaNotasAdapter(this, notas);
        recyclerNotas.setAdapter(adapter);

        if(!notas.isEmpty()) {
            getSupportActionBar().setTitle(notas.get(0).getDisciplina().getTarget().getNome());
        }
    }
}
