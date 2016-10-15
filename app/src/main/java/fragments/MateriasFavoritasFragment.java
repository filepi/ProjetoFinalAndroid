package fragments;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import adapter.MateriaCursorAdapter;
import br.edu.unibratec.projetofinalandroid.OnMateriaClickListener;
import br.edu.unibratec.projetofinalandroid.R;
import database.MateriaContract;
import database.MateriasProvider;
import pojo.Materia;

/**
 * Created by felipe on 25/09/16.
 */
public class MateriasFavoritasFragment  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView;
    OnMateriaClickListener mMateriaClickListener;
    MateriaCursorAdapter mAdapter;
    public static boolean firstrun = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materias_favoritas_list, container, false);
        listView = (ListView) view.findViewById(R.id.lista_materias_favoritas);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mMateriaClickListener) {
                    // Pegamos o cursor do adapter
                    Cursor cursor = mAdapter.getCursor();
                    // Movemos para a posição correspondente da lista
                    selectMateria(view, position, cursor);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        // Inicializamos e definimos o adapter da lista
        mAdapter = new MateriaCursorAdapter(getActivity(), null);
        listView.setAdapter(mAdapter);

        // Definimos a view a ser exibida se a lista estiver vazia
        //listView.setEmptyView(view.findViewById(R.id.empty_view_root));

        if (!firstrun)
        // Inicializamos o loader para trazer os registros em background
        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    private void selectMateria(View view, int position, Cursor cursor) {
        if (cursor.moveToPosition(position)) {
            // Criamos um objeto Movie para passamos para a MainActivity
            // perceba que esse Movie não tem todos os campos. Pois na tela
            // de listagem apenas os campos necessários são utilizados
            Materia materia = materiaItemFromCursor(cursor);
            mMateriaClickListener.onMovieClick(view, materia, position);
        }
    }

    public Materia materiaItemFromCursor(Cursor cursor){
        Materia materia = new Materia();
        materia.setCodigo(cursor.getLong(cursor.getColumnIndex(MateriaContract._ID)));
        materia.setProfessor(cursor.getString(cursor.getColumnIndex(MateriaContract.COL_MATERIA_PROFESSOR)));
        materia.setDescricao(cursor.getString(cursor.getColumnIndex(MateriaContract.COL_MATERIA_DESCRICAO)));
        return materia;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader
                (
                getActivity(),
                MateriasProvider.MATERIAS_URI,
                MateriaContract.LIST_COLUMNS, null, null, null
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Toast.makeText(getContext(), "onLoadFinished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Toast.makeText(getContext(), "onLoadReset", Toast.LENGTH_SHORT).show();

    }
}
