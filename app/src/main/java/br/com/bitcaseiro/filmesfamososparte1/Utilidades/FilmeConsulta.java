package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import java.net.URL;

/**
 * Created by Daniel on 31/10/2017.
 */

public class FilmeConsulta {
    private URL _url;
    private String _json;


    public URL getUrl() {
        return _url;
    }

    public void setUrl(URL _url) {
        this._url = _url;
    }

    public String getJson() {
        return _json;
    }

    public void setJson(String json) {
        this._json = json;
    }

    public Boolean ordenadoPorPopular(){
        return _url.toString().contains("popular");
    }

    public Boolean ordenadoPorRecomendados(){
        return _url.toString().contains("top_rated");
    }
}
