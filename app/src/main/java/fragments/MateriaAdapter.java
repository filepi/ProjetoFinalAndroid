package fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.unibratec.projetofinalandroid.R;
import pojo.Materia;

/**
 * Created by felipe on 25/09/16.
 */
public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.VH> {

    private List<Materia> mMaterias;
    private Context mContext;
    private OnMateriaClickListener mMateriaClickListener;

    public MateriaAdapter(Context ctx, List<Materia> materias)
    {
        this.mContext = ctx;
        this.mMaterias = materias;
    }

    public void setMateriaClickListener(OnMateriaClickListener mcl) {
        this.mMateriaClickListener = mcl;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_materia, parent, false);
        final VH viewHolder = new VH(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolder.getAdapterPosition();
                if (mMateriaClickListener != null){
                    mMateriaClickListener.onMovieClick(view, mMaterias.get(pos), pos);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Materia materia = mMaterias.get(position);
        holder.textViewMateriaDescricao.setText(materia.getDescricao());
        holder.textViewProfessor.setText(materia.getProfessor());
    }

    @Override
    public int getItemCount() {
        return mMaterias.size();
    }

    class VH extends RecyclerView.ViewHolder {
        //ImageView imageViewPoster;
        TextView textViewMateriaDescricao;
        TextView textViewProfessor;

        public VH(View itemView) {
            super(itemView);
            //imageViewPoster = (ImageView)itemView.findViewById(R.id.movie_item_image_poster);
            textViewMateriaDescricao = (TextView) itemView.findViewById(R.id.materia_item_text_descricao);
            textViewProfessor = (TextView)itemView.findViewById(R.id.materia_item_text_professor);
        }
    }
}
