package br.com.bitcaseiro.filmesfamososparte1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.bitcaseiro.filmesfamososparte1.Utilidades.AsyncTaskDelegate;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.Filme;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.FilmeConsulta;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.TheMovieDbJson;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.TheMovieDbRede;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.TheMovieDbService;

public class PrincipalActivity extends AppCompatActivity implements FilmeAdapter.FilmeAdapterOnClickHandler, AsyncTaskDelegate {

    private static final String FILMES_POPULARES = "FILMES_POPULARES";
    private static final String FILMES_RECOMENDADOS = "FILMES_RECOMENDADOS";
    private static final String ULTIMA_ORDENACAO = "ULTIMA_ORDENACAO";


    private FilmeAdapter mFimeAdapter;
    private ProgressBar mCarregandoProgressBar;
    private TextView mMensagemErroTextView;
    private ArrayList<Filme> mFilmesPopulares;
    private ArrayList<Filme> mFilmesRecomendados;
    private String mUltimaOrdenacao;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(FILMES_POPULARES, mFilmesPopulares);
        outState.putParcelableArrayList(FILMES_RECOMENDADOS, mFilmesRecomendados);
        outState.putString(ULTIMA_ORDENACAO, mUltimaOrdenacao);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        if (savedInstanceState != null && savedInstanceState.containsKey(FILMES_POPULARES))
            mFilmesPopulares = savedInstanceState.getParcelableArrayList(FILMES_POPULARES);

        if (savedInstanceState != null && savedInstanceState.containsKey(FILMES_RECOMENDADOS))
            mFilmesRecomendados = savedInstanceState.getParcelableArrayList(FILMES_RECOMENDADOS);

        if (savedInstanceState != null && savedInstanceState.containsKey(ULTIMA_ORDENACAO))
            mUltimaOrdenacao = savedInstanceState.getString(ULTIMA_ORDENACAO);

        mCarregandoProgressBar = (ProgressBar) findViewById(R.id.pb_carregando);
        mMensagemErroTextView = (TextView) findViewById(R.id.tv_mensagem_erro);

        mFimeAdapter = new FilmeAdapter(this);

        RecyclerView mFilmeRecyclerView = (RecyclerView) findViewById(R.id.filme_recyclerView);

        mFilmeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFilmeRecyclerView.setHasFixedSize(false);
        mFilmeRecyclerView.setAdapter(mFimeAdapter);

        if (temConexaoComInternet()) {
            if (mUltimaOrdenacao == null || mUltimaOrdenacao.isEmpty() || mUltimaOrdenacao.equals("Populares"))
                carregarPopulares();
            else
                carregarRecomendados();

        } else {
            mMensagemErroTextView.setVisibility(View.VISIBLE);
        }
    }

    private boolean temConexaoComInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void carregarPopulares() {
        try {
            mUltimaOrdenacao = "Populares";

            if (mFilmesPopulares == null) {
                mCarregandoProgressBar.setVisibility(View.VISIBLE);
                new TheMovieDbService(getApplicationContext(), this).execute(TheMovieDbRede.construirUrlPopulares());
                return;
            }

            mFimeAdapter.setFilmes(mFilmesPopulares);
        } catch (Exception e) {
            mCarregandoProgressBar.setVisibility(View.INVISIBLE);
            mMensagemErroTextView.setVisibility(View.VISIBLE);
        }
    }

    private void carregarRecomendados() {
        try {
            mUltimaOrdenacao = "Recomendados";

            if (mFilmesRecomendados == null) {
                mCarregandoProgressBar.setVisibility(View.VISIBLE);
                new TheMovieDbService(getApplicationContext(), this).execute(TheMovieDbRede.construirUrlRecomendados());
                return;
            }

            mFimeAdapter.setFilmes(mFilmesRecomendados);
        } catch (Exception e) {
            mCarregandoProgressBar.setVisibility(View.INVISIBLE);
            mMensagemErroTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.acao_populares == item.getItemId()) {
            carregarPopulares();
            return true;
        }

        if (R.id.acao_recomendados == item.getItemId()) {
            carregarRecomendados();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Filme filme) {
        Intent intent = new Intent(this, DetalheFilmeActivity.class);
        intent.putExtra("FILME", filme);
        startActivity(intent);
    }

    @Override
    public void processFinish(Object output) {
        mCarregandoProgressBar.setVisibility(View.INVISIBLE);

        if (output != null) {
            FilmeConsulta resultado = (FilmeConsulta) output;

            if (resultado.ordenadoPorPopular()) {
                mFilmesPopulares = TheMovieDbJson.converterParaLista(resultado.getJson());
                mFimeAdapter.setFilmes(mFilmesPopulares);
            }

            if (resultado.ordenadoPorRecomendados()) {
                mFilmesRecomendados = TheMovieDbJson.converterParaLista(resultado.getJson());
                mFimeAdapter.setFilmes(mFilmesRecomendados);
            }
        } else {
            mMensagemErroTextView.setVisibility(View.VISIBLE);
        }
    }
}
