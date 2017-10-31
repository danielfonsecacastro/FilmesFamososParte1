package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Filme implements Parcelable {
    public Filme(JSONObject jsonObject) {

        try {
            _titulo = jsonObject.getString("title");
            _tituloOriginal = jsonObject.getString("original_title");
            _dataLancamento = jsonObject.getString("release_date");
            _mediaVotos = jsonObject.getDouble("vote_average");
            _resumo = jsonObject.getString("overview");
            _poster = jsonObject.getString("poster_path");
            _popularidade = jsonObject.getDouble("popularity");
        } catch (JSONException e) {
            Log.e("Filme", e.getMessage());
            e.printStackTrace();
        }

    }

    private Filme(Parcel parcel) {
        _titulo = parcel.readString();
        _tituloOriginal = parcel.readString();
        _dataLancamento = parcel.readString();
        _mediaVotos = parcel.readDouble();
        _resumo = parcel.readString();
        _poster = parcel.readString();
        _popularidade = parcel.readDouble();
    }

    private String _titulo;
    private String _tituloOriginal;
    private String _dataLancamento;
    private Double _mediaVotos;
    private String _resumo;
    private String _poster;
    private Double _popularidade;
    private static final String URL_IMG_PEQUENA = "http://image.tmdb.org/t/p/w92";
    private static final String URL_IMG_NORMAL = "http://image.tmdb.org/t/p/w185";

    public String getPosterPequeno() {
        return URL_IMG_PEQUENA + _poster;
    }

    public String getPoster() {
        return URL_IMG_NORMAL + _poster;
    }

    public Double getPopularidade() {
        return _popularidade;
    }

    public String getTitulo() {
        return _titulo;
    }

    public String getTituloOriginal() {
        return _tituloOriginal;
    }

    public String getDataLancamento() {
        return _dataLancamento;
    }

    public Double getMediaVotos() {
        return _mediaVotos;
    }

    public String getResumo() {
        return _resumo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_titulo);
        parcel.writeString(_tituloOriginal);
        parcel.writeString(_dataLancamento);
        parcel.writeDouble(_mediaVotos);
        parcel.writeString(_resumo);
        parcel.writeString(_poster);
        parcel.writeDouble(_popularidade);
    }

    public static final Parcelable.Creator<Filme> CREATOR = new Parcelable.Creator<Filme>() {
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };


}
