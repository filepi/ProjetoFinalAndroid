package br.edu.unibratec.projetofinalandroid;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import database.MateriaContract;
import database.MateriasProvider;
import fragments.MateriaListFragment;
import fragments.MateriasFavoritasFragment;
import pojo.Materia;

public class MainActivity extends AppCompatActivity implements OnMateriaClickListener{

    public static final String EXTRA_MATERIA = "materia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MateriasPagerAdapter pagerAdapter = new MateriasPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onMateriaClick(View view, Materia materia, int position) {
            Intent it = new Intent(MainActivity.this, MateriaDetalheActivity.class);
            it.putExtra(EXTRA_MATERIA, materia);
            startActivity(it);
    }

    private long adicionaMateriaFavoria(Materia materia)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MateriaContract.COL_MATERIA_DESCRICAO   , materia.getDescricao());
        contentValues.put(MateriaContract.COL_MATERIA_PROFESSOR, materia.getProfessor());
        Toast.makeText(this, materia.toString(), Toast.LENGTH_SHORT).show();

        Uri uri = this.getContentResolver().insert(MateriasProvider.MATERIAS_URI, contentValues);
        return ContentUris.parseId(uri);

    }

    // O PagerAdapter é o que determina o que será exibido em cada aba
    class MateriasPagerAdapter extends FragmentPagerAdapter {
        public MateriasPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 1)
            {
                MateriaListFragment materiasListFragment = new MateriaListFragment();
                return materiasListFragment;
            }
            else
            {
                MateriasFavoritasFragment materiasFavoritasFragment = new MateriasFavoritasFragment();
                return materiasFavoritasFragment;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1)
                return getString(R.string.tab_materias);
            else
                return getString(R.string.tab_materias_favoritas);
        }
        @Override
        public int getCount() {
            return 2;
        }
    }
}
