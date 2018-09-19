package com.app.boletim.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.activities.CadastroAgendamentosActivity;
import com.app.boletim.activities.CadastroAnotacoesActivity;
import com.app.boletim.activities.VisualizacaoAnotacoesActivity;
import com.app.boletim.models.Agendamento;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by juliana on 16/03/18.
 */

public class ListaAgendamentosAdapter extends RecyclerView.Adapter<ListaAgendamentosAdapter.ViewHolder> {
    private Context context;
    private Agendamento agendamento;
    private List<Agendamento> agendamentos;

    public ListaAgendamentosAdapter(Context context, List<Agendamento> agendamentos) {
        this.context = context;
        this.agendamentos = agendamentos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_data) TextView txtData;
        @BindView(R.id.txt_hora) TextView txtHora;
        @BindView(R.id.txt_titulo)
        TextView txtTitulo;

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
                    editar(itemView, agendamento, position);
                }

                else if(item.getItemId() == R.id.remover_agendamento) {
                    remover(itemView, agendamento, position);
                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }

    public void configurarClickCurto(final View itemView, final Agendamento agendamento, final int position) {
        itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, VisualizacaoAnotacoesActivity.class).putExtra("agendamentoId", agendamento.getId()));
        });
    }

    public void adicionarAnotacao(View view, Agendamento agendamento, int position) {
        Intent intent = new Intent(context, CadastroAnotacoesActivity.class);
        intent.putExtra("agendamentoId", agendamento.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void editar(View view, Agendamento agendamento, int position) {
        Intent intent = new Intent(context, CadastroAgendamentosActivity.class);

        intent.putExtra("agendamentoId", agendamento.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void remover(View view, Agendamento agendamento, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Boletim");
        builder.setMessage("Deseja remover o agendamento da lista?");

        builder.setPositiveButton("SIM", (dialog, which) -> {
            this.agendamentos.remove(agendamento);

            notifyItemRemoved(position);
            notifyItemChanged(position);
            Snackbar.make(view, "Agendamento removido!", Snackbar.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NÃO", null);
        builder.create().show();
    }
}
