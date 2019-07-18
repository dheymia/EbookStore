package senac.ebookstore.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import senac.ebookstore.R;
import senac.ebookstore.models.Ebook;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    List<Ebook> ebookList;
    private Context context;
    public View.OnClickListener mOnItemClickListener;

    public EbookAdapter(List<Ebook> ebookList, Context context) {
        this.ebookList = ebookList;
        this.context = context;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_ebook,parent,false);
        EbookViewHolder holder = new EbookViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {

        EbookViewHolder viewHolder = (EbookViewHolder) holder;
        final Ebook ebook = ebookList.get(position);

        viewHolder.ebook.setImageURI(Uri.parse(ebook.getImageUrl()));
        viewHolder.titulo.setText(ebook.getTitulo());
        viewHolder.autor.setText(ebook.getAutor());
        viewHolder.tipo.setText(ebook.getTipo());
        viewHolder.sinopse.setText(ebook.getSinopse());

    }

    @Override
    public int getItemCount() {
        return ebookList.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder{

        final ImageView ebook;
        final TextView titulo;
        final TextView autor;
        final TextView tipo;
        final TextView sinopse;
        final LinearLayout item;

        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            ebook = itemView.findViewById(R.id.imgEbook);
            titulo = itemView.findViewById(R.id.txtTitulo);
            autor = itemView.findViewById(R.id.txtAutor);
            tipo = itemView.findViewById(R.id.txtTipo);
            sinopse = itemView.findViewById(R.id.txtSinopse);
            item = itemView.findViewById(R.id.bcItem);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
