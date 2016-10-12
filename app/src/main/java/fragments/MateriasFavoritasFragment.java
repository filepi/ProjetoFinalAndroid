package fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import database.MateriaContract;
import database.MateriasProvider;

/**
 * Created by felipe on 25/09/16.
 */
public class MateriasFavoritasFragment  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Toast.makeText(getContext(), "onCreateLoader", Toast.LENGTH_SHORT).show();
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
