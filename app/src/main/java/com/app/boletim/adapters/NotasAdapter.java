package com.app.boletim.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.dao.DisciplinaDAO;
import com.app.boletim.dao.NotaDAO;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by juliana on 16/03/18.
 */

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolder> {
    private Context context;
    private List<Nota> notas;

    public NotasAdapter(Context context, Disciplina disciplina) {
        this.context = context;
        this.notas = NotaDAO.listarNotas(this, disciplina);
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

        holder.txtContaBimestre.setText(" " + (position + 1) + "º BIMESTRE");
        holder.txtVerNota.setText(" " + String.valueOf(nota.getNotaBimestral()));

        configurarClickLongo(holder.itemView, nota, position);
    }

    @Override
    public int getItemCount() {
        return this.notas.size();
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void configurarClickLongo(final View itemView, final Nota nota, final int position) {
        itemView.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_notas, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.remover_nota) {
                    //remover(itemView, nota, position);
                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }
}
