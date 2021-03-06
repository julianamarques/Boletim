package com.app.boletim.activities;

import android.app.ActivityOptions;
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
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.app.boletim.R;
import com.app.boletim.adapters.ListaAgendamentosAdapter;
import com.app.boletim.dal.App;
import com.app.boletim.models.Agendamento;
import com.app.boletim.models.Agendamento_;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class AgendamentosActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_agendamentos) protected RecyclerView recyclerAgendamentos;

    private Box<Agendamento> agendamentoBox;
    private long idAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos);
        ButterKnife.bind(this);

        agendamentoBox = ((App)getApplication()).getBoxStore().boxFor(Agendamento.class);
        idAlunoLogado = getIdAlunoLogado();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Agendamento> agendamentos = agendamentoBox.query().equal(Agendamento_.alunoId, idAlunoLogado).build().find();
        recyclerAgendamentos.setLayoutManager(new LinearLayoutManager(this));
        ListaAgendamentosAdapter adapter = new ListaAgendamentosAdapter(this, agendamentos, agendamentoBox);
        recyclerAgendamentos.setAdapter(adapter);
    }

    @OnClick(R.id.fab_adicionar_agendamento)
    public void abrirCadastroAgendamentos(View view) {
        startActivity(new Intent(this, CadastroAgendamentosActivity.class));

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerAgendamentos.setVisibility(View.VISIBLE);
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
        inflater.inflate(R.menu.action_bar_menu_agendamentos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.remove_all_agendamentos) {
            removerTodos();
        }

        return false;
    }

    private void removerTodos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Boletim")
                .setMessage("Deseja remover todos os agendamentos da lista?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Agendamento> agendamentos = agendamentoBox.query().equal(Agendamento_.alunoId, idAlunoLogado).build().find();
                        agendamentoBox.remove(agendamentos);
                        recyclerAgendamentos.setVisibility(View.GONE);
                        Snackbar.make(recyclerAgendamentos, "Todos os agendamentos foram removidos!", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NÃO", null)
                .create().show();
    }
}
