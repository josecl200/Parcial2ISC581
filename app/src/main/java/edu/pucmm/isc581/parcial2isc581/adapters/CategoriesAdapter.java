package edu.pucmm.isc581.parcial2isc581.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.datamodels.CategoriaModel;
import edu.pucmm.isc581.parcial2isc581.datamodels.ProductoModel;
import edu.pucmm.isc581.parcial2isc581.dbModels.CategoriaDB;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>{
    private List<CategoriaModel> categoriaList;
    private Context mContext;

    @NonNull
    @Override
    public CategoriesAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categoria,parent,false);
        return new CategoryViewHolder(view, this);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoryViewHolder holder, int position) {
        holder.bindData(categoriaList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    void deleteItem(int index) {
        categoriaList.remove(index);
        notifyItemRemoved(index);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView nombreCategoria;
        public Button btnDel;
        private CategoriesAdapter adapter;

        public CategoryViewHolder(@NonNull View itemView, CategoriesAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            nombreCategoria = itemView.findViewById(R.id.textViewCategoria);
            btnDel = itemView.findViewById(R.id.buttonBorrar);
        }

        public void bindData(CategoriaModel categoriaModel, Context context){

            nombreCategoria.setText(categoriaModel.getNombre());

            btnDel.setOnClickListener(v -> {
                CategoriaDB categoriaDB= new CategoriaDB(context).open();
                Boolean result = categoriaDB.delete(categoriaModel.getId());
                categoriaDB.close();
                if(result){
                    adapter.deleteItem(getAdapterPosition());
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Error al borrar categoria");
                    alertDialogBuilder.setMessage("Hay productos asociados a esta categoria");
                    alertDialogBuilder.show();
                }
            });
        }
    }
}
