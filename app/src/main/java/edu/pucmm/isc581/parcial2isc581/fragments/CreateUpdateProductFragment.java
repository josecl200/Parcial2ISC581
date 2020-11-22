package edu.pucmm.isc581.parcial2isc581.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.activities.CreateCategoryActivity;
import edu.pucmm.isc581.parcial2isc581.datamodels.CategoriaModel;
import edu.pucmm.isc581.parcial2isc581.dbModels.CategoriaDB;
import edu.pucmm.isc581.parcial2isc581.dbModels.ProductoDB;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateUpdateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateUpdateProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "modify";
    private static final String ARG_PARAM2 = "id";

    // TODO: Rename and change types of parameters
    private Boolean modify;
    private Integer id;

    public CreateUpdateProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateUpdateProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateUpdateProductFragment newInstance(Boolean modify, Integer id) {
        CreateUpdateProductFragment fragment = new CreateUpdateProductFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, modify);
        args.putInt(ARG_PARAM2, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modify = getArguments().getBoolean(ARG_PARAM1);
            id = getArguments().getInt(ARG_PARAM2, -1);
        }

           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_update_product, container, false);
        ImageView productImage;
        EditText nombre, precio;
        Button btnUpdate, btnSave, btnDelete, btnAddCategory;
        Spinner spnCategory;

        productImage = view.findViewById(R.id.productImage);
        nombre = view.findViewById(R.id.textEditName);
        precio = view.findViewById(R.id.textEditPrice);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnSave = view.findViewById(R.id.btnSave);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnAddCategory = view.findViewById(R.id.buttonAddCategory);
        spnCategory = view.findViewById(R.id.spnCategory);
        CategoriaDB categoriaDB = new CategoriaDB(this.getContext()).open();
        ArrayList<String> listCategory = new ArrayList<String>();
        Cursor categorias = categoriaDB.fetchAll();
        while (!categorias.isAfterLast()){
            listCategory.add(categorias.getString(1));
            categorias.moveToNext();
        }
        System.out.println(listCategory);
        ArrayAdapter<String> spnCategoryAdapter = new ArrayAdapter<>(this.getContext(),R.layout.support_simple_spinner_dropdown_item, listCategory);
        spnCategory.setAdapter(spnCategoryAdapter);


        btnSave.setOnClickListener(v -> {
            if(nombre.getText().toString().isEmpty() || precio.getText().toString().isEmpty() || spnCategory.getSelectedItem().toString().isEmpty())
                Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
            else{
                ProductoDB productoDB = new ProductoDB(this.getContext()).open();
                categoriaDB.open();
                Bitmap bitmap = Bitmap.createBitmap(productImage.getDrawable().getIntrinsicWidth(), productImage.getDrawable().getIntrinsicHeight(), Bitmap.Config.RGBA_F16);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Log.wtf("B64IMGPUT", Base64.encodeToString(byteArray, Base64.URL_SAFE));
                productoDB.insert(nombre.getText().toString(),Integer.parseInt(precio.getText().toString()),
                        categoriaDB.fetchByName(spnCategory.getSelectedItem().toString()).getInt(0), Base64.encodeToString(byteArray, Base64.DEFAULT));
                categoriaDB.close();
                productoDB.close();
                bitmap.recycle();
                this.getActivity().finish();
            }
        });

        btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreateCategoryActivity.class);
            startActivity(intent);
        });

        if (modify)
            btnSave.setVisibility(View.INVISIBLE);
        else
            btnUpdate.setVisibility(View.INVISIBLE); btnDelete.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Spinner spnCategory = getView().findViewById(R.id.spnCategory);
        CategoriaDB categoriaDB = new CategoriaDB(this.getContext()).open();
        List<String> listCategory = new ArrayList<>();
        Cursor categorias = categoriaDB.fetchAll();
        while (categorias.moveToNext())
            listCategory.add(categorias.getString(1));
        ArrayAdapter<String> spnCategoryAdapter = new ArrayAdapter<>(this.getContext(),R.layout.support_simple_spinner_dropdown_item, listCategory);
        spnCategory.setAdapter(spnCategoryAdapter);
    }
}