package com.app.boletim.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.activities.CadastroAnotacoesActivity;
import com.app.boletim.activities.VisualizacaoAnotacoesActivity;
import com.app.boletim.dao.AgendamentoDAO;
import com.app.boletim.dao.DisciplinaDAO;
import com.app.boletim.models.Agendamento;
import com.app.boletim.models.Disciplina;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by juliana on 16/03/18.
 */

public class AgendamentosAdapter extends RecyclerView.Adapter<AgendamentosAdapter.ViewHolder> {
    private Context context;
    private List<Agendamento> agendamentos;

    public AgendamentosAdapter(Context context, FirebaseAuth auth) {
        this.context = context;
        this.agendamentos = AgendamentoDAO.listarAgendamentos(auth, this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_data) TextView txtData;
        @BindView(R.id.txt_hora) TextView txtHora;
        @BindView(R.id.txt_titulo) TextView txtTitulo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_agendamento, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Agendamento agendamento = this.agendamentos.get(position);

        holder.txtTitulo.setText(agendamento.getTitulo());
        holder.txtData.setText(" " + new SimpleDateFormat("dd/MM/yyyy").format(agendamento.getData()));
        holder.txtHora.setText(" " + agendamento.getHora());

        configurarClickLongo(holder.itemView, agendamento, position);
        configurarClickCurto(holder.itemView, agendamento, position);
    }

    @Override
    public int getItemCount() {
        return this.agendamentos.size();
    }

    public void configurarClickLongo(final View itemView, final Agendamento agendamento, final int position) {
        itemView.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_agendamentos, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.adicionar_anotacoes) {
                    adicionarAnotacao(itemView, agendamento, position);
                }

                else if(item.getItemId() == R.id.editar_agendamento) {
                    //editar(itemView, agendamento, position);
                }

                else if(item.getItemId() == R.id.remover_agendamento) {
                    deletarAgendamento(itemView, agendamento, position);
                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }

    public void configurarClickCurto(final View itemView, Agendamento agendamento, final int position) {
        agendamento = this.agendamentos.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("agendamento", agendamento);

        itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, VisualizacaoAnotacoesActivity.class).putExtras(bundle));
        });
    }

    public void adicionarAnotacao(View view, Agendamento agendamento, int position) {
        agendamento = this.agendamentos.get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("agendamento", agendamento);

        context.startActivity(new Intent(context, CadastroAnotacoesActivity.class).putExtras(bundle));
    }

    public void deletarAgendamento(View view, Agendamento agendamento, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        builder.setTitle("Boletim");
        builder.setMessage("Deseja remover " + agendamento.getTitulo()+ " permanentemente?");
        builder.setPositiveButton("SIM", (dialog, which) -> {
            AgendamentoDAO.deletarAgendamento(agendamento.getId());
            this.agendamentos.remove(agendamento);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            Snackbar.make(view, agendamento.getTitulo() + " removido", Snackbar.LENGTH_LONG).show();
        });

        builder.setNegativeButton("NÃO", null);
        builder.create().show();
    }
}
