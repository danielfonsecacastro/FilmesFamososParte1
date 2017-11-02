package br.com.bitcaseiro.filmesfamososparte1.Utilidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Filme implements Parcelable {

    private static final String URL_IMG_PEQUENA = "http://image.tmdb.org/t/p/w92";
    private static final String URL_IMG_NORMAL = "http://image.tmdb.org/t/p/w185";

    public Filme() {}

    private Filme(Parcel parcel) {
        setTitulo(parcel.readString());
        setTituloOriginal(parcel.readString());
        setDataLancamento(parcel.readString());
        setMediaVotos(parcel.readDouble());
        setResumo(parcel.readString());
        setPoster(parcel.readString());
        setPopularidade(parcel.readDouble());
    }

    private String mTitulo;
    private String mTituloOriginal;
    private String mDataLancamento;
    private Double mMediaVotos;
    private String mResumo;
    private String mPoster;
    private Double mPopularidade;

    public String getPoster() {
        return URL_IMG_NORMAL + mPoster;
    }

    public Double getPopularidade() {
        return mPopularidade;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public String getTituloOriginal() {
        return mTituloOriginal;
    }

    public String getDataLancamento() {
        return mDataLancamento;
    }

    public Double getMediaVotos() {
        return mMediaVotos;
    }

    public String getResumo() {
        return mResumo;
    }

    public void setTitulo(String titulo) {
        this.mTitulo = titulo;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.mTituloOriginal = tituloOriginal;
    }

    public void setDataLancamento(String dataLancamento) {
        this.mDataLancamento = dataLancamento;
    }

    public void setMediaVotos(Double mediaVotos) {
        this.mMediaVotos = mediaVotos;
    }

    public void setResumo(String resumo) {
        this.mResumo = resumo;
    }

    public void setPoster(String poster) {
        this.mPoster = poster;
    }

    public void setPopularidade(Double popularidade) {
        this.mPopularidade = popularidade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitulo);
        parcel.writeString(mTituloOriginal);
        parcel.writeString(mDataLancamento);
        parcel.writeDouble(mMediaVotos);
        parcel.writeString(mResumo);
        parcel.writeString(mPoster);
        parcel.writeDouble(mPopularidade);
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
