package br.edu.unibratec.projetofinalandroid;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import database.MateriaContract;
import database.MateriasProvider;
import pojo.Materia;

public class MateriaDetalheActivity extends AppCompatActivity {

    Materia mMateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_detalhe);
        TextView txtDescricao = (TextView) findViewById(R.id.txt_Title);
        TextView txtMoreInfo = (TextView) findViewById(R.id.txt_Description2);

        if (getIntent().hasExtra(MainActivity.EXTRA_MATERIA))
        {
            mMateria = (Materia) getIntent().getSerializableExtra(MainActivity.EXTRA_MATERIA);
            txtDescricao.setText(mMateria.getDescricao());
            txtMoreInfo.setText(getResources().getString(R.string.full_description_todo));
        }
    }
    
    public void toggleFavorite(View v)
    {
        boolean ehfavorito = isFavorite(this, String.valueOf(mMateria.getCodigo()));
    }

    public static boolean isFavorite(Context ctx, String id){
        Cursor cursor = ctx.getContentResolver().query(
                MateriasProvider.MATERIAS_URI,
                new String[]{ MateriaContract._ID},
                MateriaContract.COL_MATERIA_CODIGO + " = ?",
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

