package br.com.bitcaseiro.filmesfamososparte1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.bitcaseiro.filmesfamososparte1.Utilidades.Filme;

public class DetalheFilmeActivity extends AppCompatActivity {

    private TextView _votacaoPopularidadeTextVIew;
    private TextView _resumoTextView;
    private TextView _tituloTextView;

    private TextView _dataLancamentoTextView;
    private ImageView _posterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);

        _votacaoPopularidadeTextVIew = (TextView) findViewById(R.id.tv_votacao_popularidade);
        _resumoTextView = (TextView) findViewById(R.id.tv_resumo);
        _tituloTextView = (TextView) findViewById(R.id.tv_titulo);
        _posterImageView = (ImageView) findViewById(R.id.iv_poster);
        _dataLancamentoTextView = (TextView) findViewById(R.id.tv_dataLancamento);


        Bundle data = getIntent().getExtras();
        Filme filme = data.getParcelable("FILME");

        _votacaoPopularidadeTextVIew.setText("Avalição : " + filme.getMediaVotos() + " e Popularidade : " +  filme.getPopularidade().toString());
        _resumoTextView.setText(filme.getResumo());
        _tituloTextView.setText(filme.getTitulo());
        _dataLancamentoTextView.setText("Data de Lancamento : " + filme.getDataLancamento());

        Picasso.with(getApplicationContext()).load(filme.getPoster()).into(_posterImageView);
    }
}
