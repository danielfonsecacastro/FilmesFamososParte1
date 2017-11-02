package br.com.bitcaseiro.filmesfamososparte1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.bitcaseiro.filmesfamososparte1.Utilidades.Filme;

public class DetalheFilmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);

        TextView votacaoTextView = (TextView) findViewById(R.id.tv_votacao);
        TextView popularidadeTextView = (TextView) findViewById(R.id.tv_popularidade);
        TextView resumoTextView = (TextView) findViewById(R.id.tv_resumo);
        TextView tituloTextView = (TextView) findViewById(R.id.tv_titulo);
        ImageView posterImageView = (ImageView) findViewById(R.id.iv_poster);
        TextView dataLancamentoTextView = (TextView) findViewById(R.id.tv_dataLancamento);


        Bundle data = getIntent().getExtras();
        Filme filme = data.getParcelable("FILME");

        if(filme != null) {
            votacaoTextView.setText(getString(R.string.avaliacao, filme.getMediaVotos().toString()));
            popularidadeTextView.setText(getString(R.string.popularidade, filme.getPopularidade().toString()));
            resumoTextView.setText(filme.getResumo());
            tituloTextView.setText(filme.getTitulo() + "(" + filme.getTituloOriginal() + ")");
            dataLancamentoTextView.setText(getString(R.string.data_lancamento, filme.getDataLancamento()));
            Picasso.with(getApplicationContext()).load(filme.getPoster()).into(posterImageView);
        }
    }
}
