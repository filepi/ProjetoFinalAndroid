package br.edu.unibratec.projetofinalandroid;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import database.MateriaContract;
import database.MateriasProvider;
import pojo.Materia;

import static fragments.MateriasFavoritasFragment.DB_LOADER;

public class MateriaDetalheActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Materia mMateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_detalhe);
        TextView txtDescricao = (TextView) findViewById(R.id.txt_Title);
        TextView txtMoreInfo = (TextView) findViewById(R.id.txt_Description2);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (getIntent().hasExtra(MainActivity.EXTRA_MATERIA))
        {
            mMateria = (Materia) getIntent().getSerializableExtra(MainActivity.EXTRA_MATERIA);
            txtDescricao.setText(mMateria.getDescricao());
            txtMoreInfo.setText(getResources().getString(R.string.full_description_todo));
            updateFavoriteIcon(mMateria);
        }
    }

    private void updateFavoriteIcon(Materia materia)
    {
        if (isFavorite(this, String.valueOf(materia.getCodigo())))
        {
            fab.setImageResource(R.drawable.ic_favorite);

        }
        else {
            fab.setImageResource(R.drawable.ic_unfavorited);
        }
    }
    
    public void toggleFavorite(View v)
    {
        boolean ehfavorito = isFavorite(this, String.valueOf(mMateria.getCodigo()));
        if (ehfavorito) {
            // Se já é favorito, exclua
            if (deleteFavorite(mMateria.getCodigo())) {
                getLoaderManager().destroyLoader(DB_LOADER);
                updateFavoriteIcon(mMateria);
                Toast.makeText(this, R.string.unfavorited, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // Se não é favorito, inclua...

            long id = insertFavorite(mMateria);
            mMateria.setCodigo(id);
            updateFavoriteIcon(mMateria);
            Toast.makeText(this, R.string.favorited, Toast.LENGTH_SHORT).show();

        }
    }

    private long insertFavorite(Materia materia){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MateriaContract._ID , materia.getCodigo());
        contentValues.put(MateriaContract.COL_MATERIA_DESCRICAO   , materia.getDescricao());
        contentValues.put(MateriaContract.COL_MATERIA_PROFESSOR  , materia.getProfessor());
        contentValues.put(MateriaContract.COL_MATERIA_ATIVO  , materia.isAtivo());
        Uri uri = getApplicationContext().getContentResolver().insert(MateriasProvider.MATERIAS_URI, contentValues);
        return ContentUris.parseId(uri);
    }

    private boolean deleteFavorite(long materiaId){
        return getApplicationContext().getContentResolver().delete(
                ContentUris.withAppendedId(MateriasProvider.MATERIAS_URI, materiaId),
                null, null) > 0;
    }

    public static boolean isFavorite(Context ctx, String id){
        Cursor cursor = ctx.getContentResolver().query(
                MateriasProvider.MATERIAS_URI,
                new String[]{ MateriaContract._ID},
                MateriaContract._ID + " = ?",
                new String[]{ id },
                null
        );
        boolean isFavorite = false;
        if (cursor != null) {
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }
        return isFavorite;
    }
}

