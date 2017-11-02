package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;



public class TheMovieDbService extends AsyncTask<URL, Void, FilmeConsulta> {
    private AsyncTaskDelegate delegate = null;

    public TheMovieDbService(Context context, AsyncTaskDelegate responder){
        this.delegate = responder;
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
        super.onPostExecute(resultado);
        if(delegate != null)
            delegate.processFinish(resultado);

    }
}