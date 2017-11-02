package br.com.bitcaseiro.filmesfamososparte1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.bitcaseiro.filmesfamososparte1.Utilidades.Filme;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeAdapterViewHolder> {

    public FilmeAdapter(FilmeAdapterOnClickHandler clikcHandler){
        _clikcHandler = clikcHandler;
    }

    private List<Filme> mFilmes;
    private Context mContext;

    private final FilmeAdapterOnClickHandler _clikcHandler;
    public interface FilmeAdapterOnClickHandler{
        void onClick(Filme filme);
    }

    @Override
    public FilmeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.filme_item, parent, false);
        return new FilmeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmeAdapterViewHolder holder, int position) {
        Picasso.with(mContext).load(mFilmes.get(position).getPoster()).into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        if (mFilmes == null)
            return 0;

        return mFilmes.size();
    }

    public void setFilmes(List<Filme> filmes) {
        mFilmes = filmes;
        notifyDataSetChanged();
    }


    public class FilmeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mPosterImageView;

        public FilmeAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById((R.id.iv_poster));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            _clikcHandler.onClick(mFilmes.get(getAdapterPosition()));
        }
    }
}
