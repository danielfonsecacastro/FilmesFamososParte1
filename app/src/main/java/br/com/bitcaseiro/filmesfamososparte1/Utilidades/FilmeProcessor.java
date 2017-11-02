package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilmeProcessor {

    public static ArrayList<Filme> processar(JSONObject jsonObject) {
        ArrayList<Filme> resultado = new ArrayList<>();

        try {
            JSONArray resultados = jsonObject.getJSONArray("results");

            for (int i = 0; i < resultados.length(); i++) {
                Filme filme = new Filme();
                JSONObject json = resultados.getJSONObject(i);

                filme.setTitulo(json.optString("title"));
                filme.setTituloOriginal(json.optString("original_title"));
                filme.setTitulo(json.optString("original_title"));
                filme.setDataLancamento(json.optString("release_date"));
                filme.setMediaVotos(json.optDouble("vote_average"));
                filme.setResumo(json.optString("overview"));
                filme.setPoster(json.optString("poster_path"));
                filme.setPopularidade(json.optDouble("popularity"));

                resultado.add(filme);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  resultado;
    }
}
