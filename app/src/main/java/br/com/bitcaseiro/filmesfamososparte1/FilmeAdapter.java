package br.com.bitcaseiro.filmesfamososparte1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import br.com.bitcaseiro.filmesfamososparte1.Utilidades.Filme;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeAdapterViewHolder> {

    public FilmeAdapter(FilmeAdapterOnClickHandler clikcHandler){
        _clikcHandler = clikcHandler;
    }

    private List<Filme> _filmes;
    private Context _context;

    private final FilmeAdapterOnClickHandler _clikcHandler;
    public interface FilmeAdapterOnClickHandler{
        void onClick(Filme filme);
    }

    @Override
    public FilmeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(_context);

        View view = inflater.inflate(R.layout.filme_item, parent, false);
        return new FilmeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmeAdapterViewHolder holder, int position) {
        holder._tituloTextView.setText(_filmes.get(position).get_titulo() + " (" + _filmes.get(position).get_tituloOriginal() + ")");
        holder._resumoTextView.setText(_filmes.get(position).get_resumo());
        holder._votacaoTextView.setText("Avaliação : " + _filmes.get(position).get_mediaVotos());

        Picasso.with(_context).load(_filmes.get(position).get_poster_pequeno()).into(holder._posterImageView);
    }

    @Override
    public int getItemCount() {
        if (_filmes == null)
            return 0;

        return _filmes.size();
    }

    public void setFilmes(List<Filme> filmes) {
        _filmes = filmes;
        notifyDataSetChanged();
    }


    public class FilmeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView _tituloTextView;
        public final TextView _resumoTextView;
        public final TextView _votacaoTextView;
        public final ImageView _posterImageView;

        public FilmeAdapterViewHolder(View itemView) {
            super(itemView);
            _tituloTextView = (TextView) itemView.findViewById((R.id.tv_titulo));
            _resumoTextView = (TextView) itemView.findViewById((R.id.tv_resumo));
            _votacaoTextView = (TextView) itemView.findViewById((R.id.tv_votacao));
            _posterImageView = (ImageView) itemView.findViewById((R.id.iv_poster));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            _clikcHandler.onClick(_filmes.get(getAdapterPosition()));
        }
    }
}
