package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Daniel on 28/10/2017.
 */

public class Filme implements Parcelable {
    public Filme(JSONObject jsonObject) {

        try {
            _id = jsonObject.getInt("id");
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

    public Filme(Parcel parcel) {
        _id = parcel.readInt();
        _titulo = parcel.readString();
        _tituloOriginal = parcel.readString();
        _dataLancamento = parcel.readString();
        _mediaVotos = parcel.readDouble();
        _resumo = parcel.readString();
        _poster = parcel.readString();
        _popularidade = parcel.readDouble();
    }

    private int _id;
    private String _titulo;
    private String _tituloOriginal;
    private String _dataLancamento;
    private Double _mediaVotos;
    private String _resumo;
    private String _poster;
    private Double _popularidade;
    private static final String URL_IMG_PEQUENA = "http://image.tmdb.org/t/p/w92";
    private static final String URL_IMG_NORMAL = "http://image.tmdb.org/t/p/w185";

    public int get_id() {
        return _id;
    }

    public String get_poster_pequeno() {
        return URL_IMG_PEQUENA + _poster;
    }

    public String get_poster() {
        return URL_IMG_NORMAL + _poster;
    }

    public Double get_popularidade() {
        return _popularidade;
    }

    public String get_titulo() {
        return _titulo;
    }

    public String get_tituloOriginal() {
        return _tituloOriginal;
    }

    public String get_dataLancamento() {
        return _dataLancamento;
    }

    public Double get_mediaVotos() {
        return _mediaVotos;
    }

    public String get_resumo() {
        return _resumo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
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
