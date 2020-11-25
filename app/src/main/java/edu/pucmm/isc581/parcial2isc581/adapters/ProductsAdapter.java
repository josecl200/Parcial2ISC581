package edu.pucmm.isc581.parcial2isc581.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import edu.pucmm.isc581.parcial2isc581.activities.CreateProductActivity;
import edu.pucmm.isc581.parcial2isc581.activities.MainActivity;
import edu.pucmm.isc581.parcial2isc581.activities.ModifyProductActivity;
import edu.pucmm.isc581.parcial2isc581.datamodels.ProductoModel;
import edu.pucmm.isc581.parcial2isc581.dbModels.CategoriaDB;
import edu.pucmm.isc581.parcial2isc581.dbModels.ProductoDB;
import edu.pucmm.isc581.parcial2isc581.fragments.CreateUpdateProductFragment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<ProductoModel> productoList;
    private Context mContext;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_producto,parent,false);
        return new ProductViewHolder(view, this);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductViewHolder holder, int position) {
        holder.bindData(productoList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    void deleteItem(int index) {
        productoList.remove(index);
        notifyItemRemoved(index);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        public ImageView cardImageView;
        public TextView nombreProducto;
        public TextView precioProducto;
        public TextView categoriaProducto;
        public Button btnMod, btnDel;
        private ProductsAdapter adapter;

        public ProductViewHolder(@NonNull View itemView, ProductsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            cardImageView = itemView.findViewById(R.id.productImage);
            nombreProducto = itemView.findViewById(R.id.textViewNombre);
            precioProducto = itemView.findViewById(R.id.textViewPrecio);
            categoriaProducto = itemView.findViewById(R.id.textViewCategoria);
            btnMod = itemView.findViewById(R.id.buttonModificar);
            btnDel = itemView.findViewById(R.id.buttonBorrar);
        }

        public void bindData(ProductoModel productoModel, Context context){
            byte[] valueDecoded= new byte[0];
            try {
                valueDecoded = Base64.decode(productoModel.getImage64(), Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cardImageView.setImageBitmap(BitmapFactory.decodeByteArray(valueDecoded,0,valueDecoded.length));
            nombreProducto.setText(productoModel.getName());
            precioProducto.setText(productoModel.getPrice().toString());
            categoriaProducto.setText(productoModel.getCategory());
            btnMod.setOnClickListener(v -> {
                Intent intent = new Intent(context, ModifyProductActivity.class);
                intent.putExtra("id", productoModel.getId());
                context.startActivity(intent);
            });
            btnDel.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(btnDel.getContext());
                alertDialogBuilder.setTitle("Borrar producto");
                alertDialogBuilder.setMessage("Está seguro de que quiere borrar el producto " + productoModel.getName() + "?");
                alertDialogBuilder.setPositiveButton("Sí", (dialogInterface, i) -> {
                    ProductoDB productoDB= new ProductoDB(context).open();
                    Boolean result = productoDB.delete(productoModel.getId());
                    productoDB.close();
                    if(result)
                        adapter.deleteItem(getAdapterPosition());
                });
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.show();

            });
        }
    }
}
