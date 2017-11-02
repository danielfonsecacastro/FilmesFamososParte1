package br.com.bitcaseiro.filmesfamososparte1.Utilidades;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public final class TheMovieDbJson {
    public static ArrayList<Filme> converterParaLista(String json) {

        try {
            return FilmeProcessor.processar(new JSONObject(json));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
