package http;


import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pojo.Materia;

/**
 * Created by felipe on 24/09/16.
 */

public class RESTConnection extends AsyncTaskLoader<ArrayList<Materia>> {

    private static final String URL_GET_MAIN_JSON =
            "https://raw.githubusercontent.com/filepi/S.O.L.I.D./master/database.json";
    private Materia mMovie;

    public RESTConnection(Context context) {
        super(context);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
            forceLoad();
    }

    @Override
    public ArrayList<Materia> loadInBackground()
    {
        ArrayList<Materia> materias = new ArrayList<Materia>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_GET_MAIN_JSON)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Materias");
            String jsonList = jsonArray.toString();

            Gson gson = new Gson();
            Materia[] materiasArray = gson.fromJson(jsonList, Materia[].class);
            materias.addAll(Arrays.asList(materiasArray));
        }
        catch (Exception e )
        {
            String erro = e.getMessage();
        }
        return materias;
    }
}