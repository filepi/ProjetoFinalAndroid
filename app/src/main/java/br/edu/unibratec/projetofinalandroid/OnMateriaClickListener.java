package br.edu.unibratec.projetofinalandroid;

import android.view.View;

import pojo.Materia;

/**
 * Created by felipe on 25/09/16.
 */
public interface OnMateriaClickListener {
    void onMateriaClick(View view, Materia materia, int position);
}
