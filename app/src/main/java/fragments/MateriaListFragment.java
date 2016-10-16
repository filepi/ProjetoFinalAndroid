package fragments;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.MateriaAdapter;
import br.edu.unibratec.projetofinalandroid.OnMateriaClickListener;
import br.edu.unibratec.projetofinalandroid.R;
import http.RESTConnection;
import pojo.Materia;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateriaListFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Materia>>
    {

    private static final int LOADER_ID = 0;

    private LoaderManager mLoaderManager;
    RecyclerView mRecyclerView;
    MateriaAdapter mAdapter;
    List<Materia> mMateriaList;

    public MateriaListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMateriaList = new ArrayList<>();
        mAdapter = new MateriaAdapter(getActivity(), mMateriaList);
        mAdapter.setMateriaClickListener(new OnMateriaClickListener() {
            @Override
            public void onMateriaClick(View view, Materia materia, int position) {
                Activity activity = getActivity();
                if (activity instanceof OnMateriaClickListener){
                    ((OnMateriaClickListener)activity).onMateriaClick(view, materia, position);
                }
            }
        });
    }


    //onCreate do Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materia_list, container, false);
        //mEmptyView = view.findViewById(R.id.empty_view_root);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.main_recycler_materias);
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE
                && getResources().getBoolean(R.bool.phone)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setAdapter(mAdapter);

        mLoaderManager = getActivity().getSupportLoaderManager();
        mLoaderManager.initLoader(LOADER_ID, null, this);

        return view;
    }

    //Método inicial do Loader
    @Override
    public Loader<ArrayList<Materia>> onCreateLoader(int id, Bundle args) {
        return new RESTConnection(getContext());
    }

    //Quando o loader é concluído
    @Override
    public void onLoadFinished(Loader<ArrayList<Materia>> loader, ArrayList<Materia> data) {
        if (data != null && data.size() > 0){
            mMateriaList.clear();
            mMateriaList.addAll(data);
            mAdapter.notifyDataSetChanged();
            //mEmptyView.setVisibility(View.GONE);
        } else {
            //mEmptyView.setVisibility(View.VISIBLE);
        }

    }


        @Override
        public void onLoaderReset(Loader<ArrayList<Materia>> loader) {
        }
    }
