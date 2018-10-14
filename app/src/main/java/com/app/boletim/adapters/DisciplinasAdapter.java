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
import com.app.boletim.activities.CadastroDisciplinasActivity;
import com.app.boletim.activities.NotasActivity;
import com.app.boletim.dao.DisciplinaDAO;
import com.app.boletim.models.Disciplina;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by Juliana on 16/02/2018.
 */

public class DisciplinasAdapter extends RecyclerView.Adapter<DisciplinasAdapter.ViewHolder> {
    private Context context;
    private List<Disciplina> disciplinas;
    private FirebaseAuth auth;

    public DisciplinasAdapter(Context context, FirebaseAuth auth) {
        this.context = context;
        this.disciplinas = DisciplinaDAO.listarDisciplinas(auth, this);
        this.auth = auth;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_nome_disciplina) TextView txtNomeDisciplina;
        @BindView(R.id.txt_nome_professor) TextView txtNomeProfessor;
        @BindView(R.id.txt_msg) TextView txtMsg;
        @BindView(R.id.txt_media) TextView txtMedia;
        @BindView(R.id.txt_label_media) TextView txtLabelMedia;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_disciplina, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Disciplina disciplina = this.disciplinas.get(position);

        holder.txtNomeDisciplina.setText(disciplina.getNome());
        holder.txtNomeProfessor.setText(disciplina.getProfessor());

        if (disciplina.getDisciplinaExtra()) {
            holder.txtLabelMedia.setText("Disciplina Extra");
        }

        else {
            holder.txtMedia.setText(" " + disciplina.getMedia());
        }

        configurarClickCurto(holder.itemView, disciplina, position);
        configurarClickLongo(holder.itemView, disciplina, position);
    }

    @Override
    public int getItemCount() {
        return this.disciplinas.size();
    }

    public void configurarClickLongo(final View itemView, final Disciplina disciplina, final int position) {
        itemView.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_disciplinas, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.adicionar_prova_final) {}

                else if (item.getItemId() == R.id.editar_disciplina) {
                    editarDisciplina(itemView, disciplina, position);
                }

                else if (item.getItemId() == R.id.remover_disciplina) {
                    deletarDisciplina(itemView, disciplina, position);
                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }

    public void configurarClickCurto(final View itemView, Disciplina disciplina, final int position) {
        disciplina = this.disciplinas.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("disciplina", disciplina);

        itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, NotasActivity.class).putExtras(bundle));
        });
    }

    public void deletarDisciplina(View view, Disciplina disciplina, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        builder.setTitle("Boletim");
        builder.setMessage("Deseja remover " + disciplina.getNome()+ " permanentemente?");
        builder.setPositiveButton("SIM", (dialog, which) -> {
            DisciplinaDAO.deletarDisciplina(disciplina.getId());
            this.disciplinas.remove(disciplina);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            Snackbar.make(view, "Disciplina " + disciplina.getNome() + " removida", Snackbar.LENGTH_LONG).show();
        });

        builder.setNegativeButton("NÃO", null);
        builder.create().show();
    }

    public void editarDisciplina(View view, Disciplina disciplina, int position) {
        disciplina = this.disciplinas.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("disciplina", disciplina);

        context.startActivity(new Intent(context, CadastroDisciplinasActivity.class).putExtras(bundle));
    }
}