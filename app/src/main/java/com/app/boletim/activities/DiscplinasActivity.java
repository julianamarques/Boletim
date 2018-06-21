package com.app.boletim.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
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
import com.app.boletim.adapters.ListaDisciplinasAdapter;
import com.app.boletim.dal.App;
import com.app.boletim.models.Agendamento;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Disciplina_;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class DiscplinasActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_disciplinas) protected RecyclerView recyclerDisciplinas;

    private Box<Disciplina> disciplinaBox;
    private long idAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discplinas);
        ButterKnife.bind(this);

        disciplinaBox = ((App)getApplication()).getBoxStore().boxFor(Disciplina.class);
        idAlunoLogado = getIdAlunoLogado();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Disciplina> disciplinas = disciplinaBox.query().equal(Disciplina_.alunoId, idAlunoLogado).build().find();
        ListaDisciplinasAdapter adapter = new ListaDisciplinasAdapter(this, disciplinas, disciplinaBox);

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


    private long getIdAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("boletim.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
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
                        List<Disciplina> disciplinas = disciplinaBox.query().equal(Disciplina_.alunoId, idAlunoLogado).build().find();
                        disciplinaBox.remove(disciplinas);
                        recyclerDisciplinas.setVisibility(View.GONE);
                        Snackbar.make(recyclerDisciplinas, "Todas as disciplinas foram removidas!", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NÃO", null)
                .create().show();

    }
}
