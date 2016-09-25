package br.edu.unibratec.projetofinalandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.widget.Toast;

import fragments.MateriaListFragment;
import fragments.MateriasFavoritasFragment;
import fragments.OnMateriaClickListener;
import pojo.Materia;

public class MainActivity extends AppCompatActivity implements OnMateriaClickListener {

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
    public void onMovieClick(Materia materia, int position) {
        // Esse método é chamado pelas telas de listagem quando o usuário
        // clica em um item da lista (ver MovieListFragment e FavoriteMoviesFragment)
        if (getResources().getBoolean(R.bool.phone)) {
            // Se for smartphone, abra uma nova activity
            Toast.makeText(this, materia.getDescricao() + " salva com sucesso. #sqn", Toast.LENGTH_SHORT).show();
            /*
            Intent it = new Intent(MainActivity.this, MateriaDetalheActivity.class);
            it.putExtra(Materia.EXTRA_MATERIA, materia);
            startActivity(it);
            * */
        } else {
            // Se for tablet, DetalheActivityexiba um fragment a direita
            /*DetailMovieFragment detailMovieFragment = DetailMovieFragment.newInstance(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholderDetail, detailMovieFragment)
                    .commit();
                    */
        }

    }

    // O PagerAdapter é o que determina o que será exibido em cada aba
    class MateriasPagerAdapter extends FragmentPagerAdapter {
        public MateriasPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 1){
                MateriaListFragment materiasListFragment = new MateriaListFragment();
                return materiasListFragment;
            } else {
                MateriasFavoritasFragment materiasFavoritasFragment = new MateriasFavoritasFragment();
                return materiasFavoritasFragment;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1) return getString(R.string.tab_materias);
            else return getString(R.string.tab_materias_favoritas);
        }
        @Override
        public int getCount() {
            return 2;
        }
    }
}
