package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TheMovieDbRede {

    private static final String API_KEY = "<SUA KEY>";

    public static URL construirUrlPopulares() {
        return construirUrl("https://api.themoviedb.org/3/movie/popular");
    }

    public static URL construirUrlRecomendados() {
        return construirUrl("https://api.themoviedb.org/3/movie/top_rated");
    }

    private static URL construirUrl(String baseUrl) {
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", "1")
                .appendQueryParameter("language", "pt-BR")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String consultar(URL url) {
        try {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}