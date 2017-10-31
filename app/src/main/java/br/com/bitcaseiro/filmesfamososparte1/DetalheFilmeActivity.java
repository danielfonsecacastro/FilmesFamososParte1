package br.com.bitcaseiro.filmesfamososparte1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;

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

        _votacaoPopularidadeTextVIew.setText("Avalição : " + filme.get_mediaVotos() + " e Popularidade : " +  filme.get_popularidade().toString());
        _resumoTextView.setText(filme.get_resumo());
        _tituloTextView.setText(filme.get_titulo());
        _dataLancamentoTextView.setText("Data de Lancamento : " + filme.get_dataLancamento());

        Picasso.with(getApplicationContext()).load(filme.get_poster()).into(_posterImageView);
    }
}
