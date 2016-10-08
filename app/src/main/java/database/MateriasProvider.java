package database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by felipe on 27/09/16.
 */

public class MateriasProvider extends ContentProvider {

    private static final String PATH = "materias";
    private static final String AUTHORITY = "br.com.aprendendodireito";

    // BASE_URI   = "content://" + AUTHORITY +"/"+ PATH
    public static Uri BASE_URI = Uri.parse("content://"+ AUTHORITY);

    // MOVIES_URI = "content://" + AUTHORITY +"/"+ PATH +"/movies"
    public static Uri MOVIES_URI = BASE_URI.withAppendedPath(BASE_URI, PATH);

    // Conforme implementação do getType(), nosso provider aceita dois tipos de Uri:
    // GENERICA, usada no insert e query   = content://br.com.nglauber.movies/movies
    // POR ID, usada no delete e query = content://br.com.nglauber.movies/movies/{id_movie}
    private static final int TYPE_GENERIC = 0;
    private static final int TYPE_ID = 1;

    private UriMatcher mMatcher;
    private MateriaDBHelper mHelper;

    public MateriasProvider()
    {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, PATH, TYPE_GENERIC);
        mMatcher.addURI(AUTHORITY, PATH +"/#", TYPE_ID);
    }

    @Override
    public boolean onCreate() {
        mHelper = new MateriaDBHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        int uriType = mMatcher.match(uri);
        switch (uriType){
            case TYPE_GENERIC:
                // Informamos ao android que vamos retornar vários registros
                return ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+ AUTHORITY;
            case TYPE_ID:
                // Informamos ao android que vamos retornar um único registro
                return ContentResolver.CURSOR_ITEM_BASE_TYPE +"/"+ AUTHORITY;
            default:
                throw new IllegalArgumentException("Invalid Uri");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = mMatcher.match(uri);
        // Como não temos o ID no momento da inserção, só aceitamos
        // inserir usando a Uri genérica.
        if (uriType == TYPE_GENERIC){
            SQLiteDatabase db = mHelper.getWritableDatabase();
            long id = db.insert(MateriaContract.TABLE_NAME, null, values);
            db.close();
            // Se der erro na inclusão o id retornado é -1,
            // então levantamos a exceção para ser tratada na tela.
            if (id == -1){
                throw new RuntimeException("Error inserting moving.");
            }
            notifyChanges(uri);
            return ContentUris.withAppendedId(MOVIES_URI, id);

        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }
    }

    private void notifyChanges(Uri uri) {
        // Caso a operação no banco ocorra sem problemas, notificamos a Uri
        // para que a listagem de favoritos seja atualizada.
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = mMatcher.match(uri);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor;
        // Na nossa implementação, o método query é o único que aceita os dois tipos de Uris.
        switch (uriType){
            // Esse tipo faz uma busca genérica. Estamos usando ele na listagem dos favoritos
            // e para checar se um filme é favorito (ou seja, se já existe no banco)
            case TYPE_GENERIC:
                cursor = db.query(MateriaContract.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;

            // Esse segundo tipo de Uri está sendo usado na tela de detalhes
            // para trazer todas as informações do filme.
            case TYPE_ID:
                long id = ContentUris.parseId(uri);
                cursor = db.query(MateriaContract.TABLE_NAME,
                        projection, MateriaContract._ID +" = ?",
                        new String[] { String.valueOf(id) }, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Invalid Uri");
        }
        // Essa linha está definindo a Uri que será notificada para que o cursor
        // seja atualizado. Veja método notifyChanges abaixo.
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int uriType = mMatcher.match(uri);
        if (uriType == TYPE_ID){
            SQLiteDatabase db = mHelper.getWritableDatabase();
            long id = ContentUris.parseId(uri);
            int rowsAffected = db.delete(
                    MateriaContract.TABLE_NAME,
                    MateriaContract._ID +" = ?",
                    new String[] { String.valueOf(id) } );
            db.close();
            // Se nenhuma linha foi afetada pela exclusão, levantamos uma exceção
            if (rowsAffected == 0){
                throw new RuntimeException("Fail deleting matéria");
            }
            notifyChanges(uri);

            return rowsAffected;

        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
