package edu.pucmm.isc581.parcial2isc581.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.fragments.CreateUpdateProductFragment;

public class CreateProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        CreateUpdateProductFragment fragment = CreateUpdateProductFragment.newInstance(false, -1);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmento,fragment);
        fragmentTransaction.commit();
    }
}