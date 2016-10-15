package adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import br.edu.unibratec.projetofinalandroid.R;
import database.MateriaContract;
import pojo.Materia;

/**
 * Created by felipe on 12/10/16.
 */

public class MateriaCursorAdapter extends SimpleCursorAdapter {

    private static final int LAYOUT = R.layout.item_materia;

    public MateriaCursorAdapter(Context context, Cursor c) {
        super(context, LAYOUT, c, MateriaContract.LIST_COLUMNS, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(LAYOUT, parent, false);

        VH vh = new VH();
        vh.textViewMateria = (TextView) view.findViewById(R.id.materia_item_text_descricao);
        vh.textViewProfessor= (TextView) view.findViewById(R.id.materia_item_text_professor);
        view.setTag(vh);

        ViewCompat.setTransitionName(vh.textViewMateria, "materia");
        ViewCompat.setTransitionName(vh.textViewProfessor, "professor");

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String materia = cursor.getString(cursor.getColumnIndex(MateriaContract.COL_MATERIA_DESCRICAO));
        String professor = cursor.getString(cursor.getColumnIndex(  MateriaContract.COL_MATERIA_PROFESSOR));

        VH vh = (VH)view.getTag();
        vh.textViewMateria.setText(materia);
        vh.textViewProfessor.setText(professor);
    }

    class VH {
        TextView textViewMateria;
        TextView textViewProfessor;
    }
}

