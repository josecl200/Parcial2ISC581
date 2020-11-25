package edu.pucmm.isc581.parcial2isc581.fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.dbModels.CategoriaDB;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCategoryFragment extends Fragment {

    public CreateCategoryFragment() {}


    public static CreateCategoryFragment newInstance() {
        CreateCategoryFragment fragment = new CreateCategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Agregar categoria");
        View view =  inflater.inflate(R.layout.fragment_create_category, container, false);
        Button btnSave = view.findViewById(R.id.btnSaveCategory);
        EditText name = view.findViewById(R.id.categoryNameEditText);
        btnSave.setOnClickListener(v -> {
            if(!name.getText().toString().isEmpty()) {
                CategoriaDB categoriaDB = new CategoriaDB(getContext()).open();
                categoriaDB.insert(name.getText().toString());
                categoriaDB.close();
                this.getActivity().finish();
            }else {
                Toast.makeText(getContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}