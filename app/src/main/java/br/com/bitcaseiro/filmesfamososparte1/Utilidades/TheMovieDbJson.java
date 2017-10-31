package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public final class TheMovieDbJson {
    public static ArrayList<Filme> ConverterParaLista(String json) {
        ArrayList<Filme> resultado = new ArrayList<Filme>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray filmes = jsonObject.getJSONArray("results");

            for (int i = 0; i < filmes.length(); i++) {
                resultado.add(new Filme(filmes.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
