package com.app.boletim.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.app.boletim.R;
import com.app.boletim.activities.CadastroAgendamentosActivity;
import com.app.boletim.activities.CadastroDisciplinasActivity;
import com.app.boletim.activities.CadastroNotaFinalActivity;
import com.app.boletim.activities.CadastroNotasActivity;
import com.app.boletim.activities.NotasActivity;
import com.app.boletim.models.Disciplina;
import com.app.boletim.models.Nota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

/**
 * Created by Juliana on 16/02/2018.
 */

public class ListaDisciplinasAdapter extends RecyclerView.Adapter<ListaDisciplinasAdapter.ViewHolder> {
    private Context context;
    private Disciplina disciplina;
    private List<Disciplina> disciplinas;
    private Box<Disciplina> disciplinaBox;
    private Nota nota;
    private Box<Nota> notaBox;

    public ListaDisciplinasAdapter(Context context, List<Disciplina> disciplinas, Box<Disciplina> disciplinaBox) {
        this.context = context;
        this.disciplinas = disciplinas;
        this.disciplinaBox = disciplinaBox;
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
        int totalDeProvas = disciplina.getAluno().getTarget().getQtdProvas();
        double media = disciplina.getMedia();
        double mediaInstitucional = disciplina.getAluno().getTarget().getMediaInstitucional();


        holder.txtNomeDisciplina.setText(disciplina.getNome());
        holder.txtNomeProfessor.setText(" " + disciplina.getProfessor());
        holder.txtMedia.setText(" " + disciplina.getMedia());

        if(disciplina.getDisciplinaExtra()) {
            holder.txtMsg.setText("Disciplina Extra");
            holder.txtMsg.setVisibility(View.VISIBLE);
            holder.txtMedia.setVisibility(View.GONE);
            holder.txtLabelMedia.setVisibility(View.GONE);
        }

        else {
            configurarClickCurto(holder.itemView, disciplina, position);
        }

        if(media >= mediaInstitucional) {
            holder.txtMedia.setTextColor(Color.BLUE);
        }

        else {
            holder.txtMedia.setTextColor(Color.RED);
        }

        if(disciplina.getNotas().size() == totalDeProvas) {
            holder.txtMsg.setVisibility(View.VISIBLE);
            holder.txtMsg.setText(disciplina.informarSituacao());

            if(media >= mediaInstitucional) {
                holder.txtMsg.setTextColor(Color.BLUE);
            }

            else if(disciplina.estaDeProvaFinal()) {
                holder.txtMsg.setTextColor(Color.parseColor("#f4a142"));
                holder.txtMsg.setText("Você está de prova final!");
            }

            else {
                holder.txtMsg.setTextColor(Color.RED);
            }
        }

        configurarClickLongo(holder.itemView, disciplina, position);
    }

    @Override
    public int getItemCount() {
        return this.disciplinas.size();
    }

    public void configurarClickLongo(final View itemView, final Disciplina disciplina, final int position) {
        int totalDeProvas = this.disciplinas.get(position).getAluno().getTarget().getQtdProvas();
        int tamanhoDaListaDeNotas = this.disciplinas.get(position).getNotas().size();
        double mediaInstitucional = this.disciplinas.get(position).getAluno().getTarget().getMediaInstitucional();

        itemView.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_disciplinas, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.adicionar_notas) {
                    if(tamanhoDaListaDeNotas == totalDeProvas) {
                        Snackbar.make(view, "Não é possível adicionar mais notas!", Snackbar.LENGTH_LONG).show();

                    }

                    else {
                        if(this.disciplinas.get(position).getDisciplinaExtra()) {
                            Snackbar.make(view, "Disciplinas extras somente serão exibidas, não sendo possível adicionar notas.", Snackbar.LENGTH_LONG).show();
                        }

                        else {
                            adicionarNotas(itemView, disciplina, position);
                        }

                    }
                }

                else if(item.getItemId() == R.id.adicionar_prova_final) {
                    if(tamanhoDaListaDeNotas < totalDeProvas) {
                        if(this.disciplinas.get(position).getDisciplinaExtra()) {
                            Snackbar.make(view, "Você não pode adicionar nota final em disciplinas extras!", Snackbar.LENGTH_LONG).show();
                        }

                        else {
                            Snackbar.make(view, "Adicione todas as notas antes de cadastrar a nota final!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    else {
                        if(disciplina.getMedia() >= mediaInstitucional) {
                            Snackbar.make(view, "Você não precisa de Prova Final!", Snackbar.LENGTH_LONG).show();
                        }

                        else {
                            adicionarNotaFinal(itemView, disciplina, position);
                        }
                    }
                }

                else if(item.getItemId() == R.id.editar_disciplina) {
                    editar(itemView, disciplina, position);
                }

                else if(item.getItemId() == R.id.remover_disciplina) {
                    remover(itemView, disciplina, position);

                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }

    public void editar(View view, Disciplina disciplina, int position) {
        Intent intent = new Intent(context, CadastroDisciplinasActivity.class);

        intent.putExtra("disciplinaId", disciplina.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void adicionarNotaFinal(View view, Disciplina disciplina, int position) {
        Intent intent = new Intent(context, CadastroNotaFinalActivity.class);

        intent.putExtra("disciplinaId", disciplina.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void adicionarNotas(View view, Disciplina disciplina, int position) {
        Intent intent = new Intent(context, CadastroNotasActivity.class);
        intent.putExtra("disciplinaId", disciplina.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void remover(View view, Disciplina disciplina, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Boletim");
        builder.setMessage("Deseja remover a disciplina da lista?");

        builder.setPositiveButton("SIM", (dialog, which) -> {
            this.disciplinas.remove(disciplina);
            this.disciplinaBox.remove(disciplina);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            Snackbar.make(view, "Disciplina removida!", Snackbar.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NÃO", null);
        builder.create().show();
    }

    public void configurarClickCurto(final View itemView, final Disciplina disciplina, final int position) {
        itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, NotasActivity.class).putExtra("disciplinaId", disciplina.getId()));
        });
    }
}