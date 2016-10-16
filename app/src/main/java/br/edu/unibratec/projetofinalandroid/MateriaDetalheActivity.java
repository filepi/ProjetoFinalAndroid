package br.edu.unibratec.projetofinalandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pojo.Materia;

public class MateriaDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_detalhe);
        TextView txtDescricao = (TextView) findViewById(R.id.txt_Title);
        TextView txtMoreInfo = (TextView) findViewById(R.id.txt_Description2);

        if (getIntent().hasExtra(MainActivity.EXTRA_MATERIA))
        {
            Materia materia = (Materia) getIntent().getSerializableExtra(MainActivity.EXTRA_MATERIA);
            txtDescricao.setText(materia.getDescricao());
            String futureDescriptionVersion2= "Este espaço está reservado para uma descrição mais detalhada da activity. A implementação virá na versão 2.0 deste aplicativo =). A ideia aqui, por enquanto, é ser aprovado na cadeira de Android, abrangendo todos os requisitos solicitados por @nglauber";
            txtMoreInfo.setText(futureDescriptionVersion2);
        }
    }
}

