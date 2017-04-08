package com.example.oddvicky.cardapio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oddvicky.cardapio.R;
import com.example.oddvicky.cardapio.model.Comida;

import java.util.List;

public class ListAndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Comida> comidas;

    public ListAndroidAdapter(Context context,
                               List<Comida> comidas) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.comidas = comidas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comida_spinner_item, parent, false);
        return new AndroidItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AndroidItemHolder androidItemHolder = (AndroidItemHolder)holder;
        androidItemHolder.tvRestaurante.setText(comidas.get(position).getRestaurante().getNome());
        androidItemHolder.tvNome.setText(comidas.get(position).getNome());
        androidItemHolder.tvPreco.setText("R$ " + String.valueOf(comidas.get(position).getPreco()));

        /*
        Glide.with(context)
                .load(versoes.get(position).getUrlImagem())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(androidItemHolder.ivIcone);
        */

    }

    @Override
    public int getItemCount() {
        return comidas.size();
    }

    private class AndroidItemHolder extends RecyclerView.ViewHolder {

        //ImageView ivIcone;
        TextView tvRestaurante, tvNome, tvPreco;

        public AndroidItemHolder(View itemView) {
            super(itemView);

            //ivIcone = (ImageView) itemView.findViewById(R.id.ivIcone);
            tvRestaurante = (TextView) itemView.findViewById(R.id.tvRestaurante);
            tvNome = (TextView) itemView.findViewById(R.id.tvNome);
            tvPreco = (TextView) itemView.findViewById(R.id.tvPreco);

        }
    }

}

