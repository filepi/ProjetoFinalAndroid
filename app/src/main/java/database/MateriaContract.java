package database;

import android.provider.BaseColumns;

/**
 * Created by felipe on 27/09/16.
 */

public interface MateriaContract extends BaseColumns {

    String TABLE_NAME = "Materias";
    String COL_MATERIA_DESCRICAO = "materia_descricao";
    String COL_MATERIA_CODIGO = "codigo";
    String COL_MATERIA_ATIVO = "ativo";
    String COL_MATERIA_PROFESSOR = "materia_professor";

    String [] LIST_COLUMNS  = new String[]
    {
        MateriaContract.TABLE_NAME,
        MateriaContract.COL_MATERIA_DESCRICAO,
        MateriaContract.COL_MATERIA_CODIGO,
        MateriaContract.COL_MATERIA_PROFESSOR
    };
}
