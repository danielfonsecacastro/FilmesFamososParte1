package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TheMovieDbRede {

    private static final String API_KEY = "<SUA_API_KEY>";

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
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext())
                return scanner.next();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }
}
