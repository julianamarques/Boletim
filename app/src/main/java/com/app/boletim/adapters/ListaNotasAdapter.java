package com.app.boletim.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.activities.CadastroNotasActivity;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

/**
 * Created by juliana on 16/03/18.
 */

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.ViewHolder> {
    private Context context;
    private Nota nota;
    private List<Nota> notas;
    private Box<Nota> notaBox;

    public ListaNotasAdapter(Context context, List<Nota> notas, Box<Nota> notaBox) {
        this.context = context;
        this.notas = notas;
        this.notaBox = notaBox;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ver_nota) TextView txtVerNota;
        @BindView(R.id.txt_conta_bimestre) TextView txtContaBimestre;
        @BindView(R.id.txt_bimestre_msg) TextView txtBimestreMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notas, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Nota nota = this.notas.get(position);
        double media = nota.getDisciplina().getTarget().getAluno().getTarget().getMediaInstitucional();

        holder.txtContaBimestre.setText(" " + (position + 1) + "º BIMESTRE");
        holder.txtVerNota.setText(" " + String.valueOf(nota.getNotaBimestral()));

        if(nota.getNotaBimestral() < media) {
            holder.txtBimestreMsg.setVisibility(View.VISIBLE);
            holder.txtBimestreMsg.setText("Sua nota está abaixo da média.");
            holder.txtBimestreMsg.setTextColor(Color.parseColor("#f4a142"));
            holder.txtVerNota.setTextColor(Color.RED);
        }

        else {
            holder.txtVerNota.setTextColor(Color.BLUE);
            holder.txtBimestreMsg.setTextColor(Color.BLUE);
            holder.txtBimestreMsg.setVisibility(View.VISIBLE);
        }

        configurarClickLongo(holder.itemView, nota, position);
    }

    @Override
    public int getItemCount() {
        return this.notas.size();
    }

    public void configurarClickLongo(final View itemView, final Nota nota, final int position) {
        itemView.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_notas, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                //if(item.getItemId() == R.id.editar_nota) {
                //    editar(itemView, nota, position);
                //}

                if(item.getItemId() == R.id.remover_nota) {
                    remover(itemView, nota, position);
                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }

    //TODO: Ajeitar o editar das notas
    /*public void editar(View view, Nota nota, int position) {
        Intent intent = new Intent(context, CadastroNotasActivity.class);
        intent.putExtra("notaId", nota.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }*/

    public void remover(View view, Nota nota, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Boletim");
        builder.setMessage("Deseja remover a nota da lista?");

        builder.setPositiveButton("SIM", (dialog, which) -> {
            this.notas.remove(nota);
            this.notaBox.remove(nota);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            Snackbar.make(view, "Nota removida!", Snackbar.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NÃO", null);
        builder.create().show();
    }
}
