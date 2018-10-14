package com.app.boletim.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.app.boletim.R;
import com.app.boletim.adapters.DisciplinasAdapter;
import com.app.boletim.dao.ConfiguracaoFirebaseAuth;
import com.app.boletim.dao.DisciplinaDAO;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscplinasActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_disciplinas) protected RecyclerView recyclerDisciplinas;

    private FirebaseAuth auth;
    private DisciplinasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discplinas);
        ButterKnife.bind(this);

        auth = ConfiguracaoFirebaseAuth.getFirebaseAuth();
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new DisciplinasAdapter(this, auth);

        recyclerDisciplinas.setLayoutManager(new LinearLayoutManager(this));
        recyclerDisciplinas.setAdapter(adapter);
    }

    @OnClick(R.id.fab_adicionar_disciplina)
    public void abrirCadastroDisciplinas(View view) {
        startActivity(new Intent(this, CadastroDisciplinasActivity.class));

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerDisciplinas.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_disciplinas, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.remove_all_disciplinas) {
            removerTodos();
        }

        return false;
    }

    private void removerTodos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Boletim")
                .setMessage("Deseja remover todas as disciplinas da lista?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recyclerDisciplinas.setVisibility(View.GONE);
                        Snackbar.make(recyclerDisciplinas, "Todas as disciplinas foram removidas!", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NÃO", null)
                .create().show();
    }
}
