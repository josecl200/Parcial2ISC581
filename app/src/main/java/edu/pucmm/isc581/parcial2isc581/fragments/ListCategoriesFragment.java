package edu.pucmm.isc581.parcial2isc581.fragments;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.adapters.CategoriesAdapter;
import edu.pucmm.isc581.parcial2isc581.datamodels.CategoriaModel;
import edu.pucmm.isc581.parcial2isc581.dbModels.CategoriaDB;

import java.util.ArrayList;
import java.util.List;

public class ListCategoriesFragment extends Fragment {

    public ListCategoriesFragment() {
    }

    public static ListCategoriesFragment newInstance() {
        ListCategoriesFragment fragment = new ListCategoriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_categories, container, false);

        List<CategoriaModel> listCategoria = new ArrayList<CategoriaModel>();
        RecyclerView categoriaRecyclerView = view.findViewById(R.id.categoriaRecyclerView);
        CategoriaDB categoriaDB = new CategoriaDB(this.getContext()).open();
        Cursor categorias = categoriaDB.fetchAll();
        while (!categorias.isAfterLast()){
            listCategoria.add(new CategoriaModel(categorias.getInt(0), categorias.getString(1)));
            categorias.moveToNext();
        }
        categoriaDB.close();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        categoriaRecyclerView.setLayoutManager(mLayoutManager);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(listCategoria, this.getContext());
        categoriaRecyclerView.setAdapter(categoriesAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<CategoriaModel> listCategoria = new ArrayList<CategoriaModel>();
        RecyclerView categoriaRecyclerView = getView().findViewById(R.id.categoriaRecyclerView);
        CategoriaDB categoriaDB = new CategoriaDB(this.getContext()).open();
        Cursor categorias = categoriaDB.fetchAll();
        while (!categorias.isAfterLast()){
            listCategoria.add(new CategoriaModel(categorias.getInt(0), categorias.getString(1)));
            categorias.moveToNext();
        }
        categoriaDB.close();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        categoriaRecyclerView.setLayoutManager(mLayoutManager);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(listCategoria, this.getContext());
        categoriaRecyclerView.setAdapter(categoriesAdapter);
    }
}