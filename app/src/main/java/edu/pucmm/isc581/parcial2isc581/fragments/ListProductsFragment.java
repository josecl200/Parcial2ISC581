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
import edu.pucmm.isc581.parcial2isc581.adapters.ProductsAdapter;
import edu.pucmm.isc581.parcial2isc581.datamodels.CategoriaModel;
import edu.pucmm.isc581.parcial2isc581.datamodels.ProductoModel;
import edu.pucmm.isc581.parcial2isc581.dbModels.CategoriaDB;
import edu.pucmm.isc581.parcial2isc581.dbModels.ProductoDB;

import java.util.ArrayList;
import java.util.List;

public class ListProductsFragment extends Fragment {


    public ListProductsFragment() {}

    public static ListProductsFragment newInstance() {
        ListProductsFragment fragment = new ListProductsFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_products, container, false);
        List<ProductoModel> listProducto = new ArrayList<>();
        RecyclerView productoRecyclerView = view.findViewById(R.id.productoRecyclerView);
        ProductoDB productoDB = new ProductoDB(this.getContext()).open();
        CategoriaDB categoriaDB = new CategoriaDB(this.getContext());
        Cursor productos = productoDB.fetchAll();
        while (!productos.isAfterLast()){
            String categoria = categoriaDB.open().fetchByID(productos.getInt(3)).getString(1);
            categoriaDB.close();
            listProducto.add(new ProductoModel(productos.getInt(0), productos.getString(1),
                    productos.getInt(2), categoria, productos.getString(4)));
            productos.moveToNext();
        }
        productoDB.close();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        productoRecyclerView.setLayoutManager(mLayoutManager);
        ProductsAdapter productsAdapter = new ProductsAdapter(listProducto, this.getContext());
        productoRecyclerView.setAdapter(productsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<ProductoModel> listProducto = new ArrayList<>();
        RecyclerView productoRecyclerView = getView().findViewById(R.id.productoRecyclerView);
        ProductoDB productoDB = new ProductoDB(this.getContext()).open();
        CategoriaDB categoriaDB = new CategoriaDB(this.getContext());
        Cursor productos = productoDB.fetchAll();
        while (!productos.isAfterLast()){
            String categoria = categoriaDB.open().fetchByID(productos.getInt(3)).getString(1);
            categoriaDB.close();
            listProducto.add(new ProductoModel(productos.getInt(0), productos.getString(1),
                    productos.getInt(2), categoria, productos.getString(4)));
            productos.moveToNext();
        }
        productoDB.close();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        productoRecyclerView.setLayoutManager(mLayoutManager);
        ProductsAdapter productsAdapter = new ProductsAdapter(listProducto, this.getContext());
        productoRecyclerView.setAdapter(productsAdapter);
    }
}