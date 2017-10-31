package br.com.bitcaseiro.filmesfamososparte1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import br.com.bitcaseiro.filmesfamososparte1.Utilidades.Filme;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.FilmeConsulta;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.TheMovieDbJson;
import br.com.bitcaseiro.filmesfamososparte1.Utilidades.TheMovieDbRede;

public class PrincipalActivity extends AppCompatActivity implements FilmeAdapter.FilmeAdapterOnClickHandler {

    private FilmeAdapter _fimeAdapter;
    private RecyclerView _filmeRecyclerView;
    private ProgressBar _carregandoProgressBar;
    private TextView _mensagemErroTextView;
    private ArrayList<Filme> _filmesPopulares;
    private ArrayList<Filme> _filmesRecomendados;
    private String _ultimaOrdenacao;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("FILMES_POPULARES", _filmesPopulares);
        outState.putParcelableArrayList("FILMES_RECOMENDADOS", _filmesRecomendados);
        outState.putString("ULTIMA_ORDENACAO", _ultimaOrdenacao);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        if (savedInstanceState != null && savedInstanceState.containsKey("FILMES_POPULARES"))
            _filmesPopulares = savedInstanceState.getParcelableArrayList("FILMES_POPULARES");

        if (savedInstanceState != null && savedInstanceState.containsKey("FILMES_RECOMENDADOS"))
            _filmesRecomendados = savedInstanceState.getParcelableArrayList("FILMES_RECOMENDADOS");

        if (savedInstanceState != null && savedInstanceState.containsKey("ULTIMA_ORDENACAO"))
            _ultimaOrdenacao = savedInstanceState.getString("ULTIMA_ORDENACAO");

        _carregandoProgressBar = (ProgressBar) findViewById(R.id.pb_carregando);
        _mensagemErroTextView = (TextView) findViewById(R.id.tv_mensagem_erro);

        _fimeAdapter = new FilmeAdapter(this);

        _filmeRecyclerView = (RecyclerView) findViewById(R.id.filme_recyclerView);

        _filmeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        _filmeRecyclerView.setHasFixedSize(false);
        _filmeRecyclerView.setAdapter(_fimeAdapter);

        if (temConexaoComInternet()) {
            if (_ultimaOrdenacao == null || _ultimaOrdenacao.isEmpty() || _ultimaOrdenacao.equals("Populares"))
                carregarPopulares();
            else
                carregarRecomendados();

        } else {
            _mensagemErroTextView.setVisibility(View.VISIBLE);
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
            _ultimaOrdenacao = "Populares";

            if (_filmesPopulares == null) {
                new TheMovieDbConsultaTask().execute(TheMovieDbRede.construirUrlPopulares());
                return;
            }

            _fimeAdapter.setFilmes(_filmesPopulares);
        } catch (Exception e) {
            _carregandoProgressBar.setVisibility(View.INVISIBLE);
            _mensagemErroTextView.setVisibility(View.VISIBLE);
        }
    }

    private void carregarRecomendados() {
        try {
            _ultimaOrdenacao = "Recomendados";

            if (_filmesRecomendados == null) {
                new TheMovieDbConsultaTask().execute(TheMovieDbRede.construirUrlRecomendados());
                return;
            }

            _fimeAdapter.setFilmes(_filmesRecomendados);
        } catch (Exception e) {
            _carregandoProgressBar.setVisibility(View.INVISIBLE);
            _mensagemErroTextView.setVisibility(View.VISIBLE);
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

    public class TheMovieDbConsultaTask extends AsyncTask<URL, Void, FilmeConsulta> {
        @Override
        protected void onPreExecute() {
            _carregandoProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected FilmeConsulta doInBackground(URL... urls) {

            FilmeConsulta resultado = new FilmeConsulta();
            resultado.setJson(TheMovieDbRede.consultar(urls[0]));
            resultado.setUrl(urls[0]);

            return resultado;
        }

        @Override
        protected void onPostExecute(FilmeConsulta resultado) {
            _carregandoProgressBar.setVisibility(View.INVISIBLE);

            if (resultado.ordenadoPorPopular()) {
                _filmesPopulares = TheMovieDbJson.ConverterParaLista(resultado.getJson());
                _fimeAdapter.setFilmes(_filmesPopulares);
            }

            if (resultado.ordenadoPorRecomendados()) {
                _filmesRecomendados = TheMovieDbJson.ConverterParaLista(resultado.getJson());
                _fimeAdapter.setFilmes(_filmesRecomendados);
            }
        }
    }
}
