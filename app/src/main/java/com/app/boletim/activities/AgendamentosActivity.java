package com.app.boletim.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import com.app.boletim.adapters.ListaAgendamentosAdapter;
import com.app.boletim.dao.ConfiguracaoFirebase;
import com.app.boletim.models.Agendamento;
import com.app.boletim.models.Aluno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgendamentosActivity extends AppCompatActivity {
    @BindView(R.id.rv_lista_agendamentos) protected RecyclerView recyclerAgendamentos;

    private long idAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos);
        ButterKnife.bind(this);

        idAlunoLogado = getIdAlunoLogado();
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerAgendamentos.setLayoutManager(new LinearLayoutManager(this));
        ListaAgendamentosAdapter adapter = new ListaAgendamentosAdapter(this, listarAgendamentos());
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

                        recyclerAgendamentos.setVisibility(View.GONE);
                        Snackbar.make(recyclerAgendamentos, "Todos os agendamentos foram removidos!", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NÃO", null)
                .create().show();
    }

    public List<Agendamento> listarAgendamentos() {
        final List<Agendamento> agendamentos = new ArrayList<>();

        ConfiguracaoFirebase.getDatabaseReference().child("agendamentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                agendamentos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Agendamento agendamento = snapshot.getValue(Agendamento.class);
                    agendamentos.add(agendamento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return agendamentos;
    }
}
