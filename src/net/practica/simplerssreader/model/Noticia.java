package net.practica.simplerssreader.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Noticia implements Parcelable {
	private String titulo;
	private String link;
	private String descripcion;
	private String guid;
	private String fecha;

	public Noticia() {}

	public Noticia(Parcel in) {
		this.titulo = in.readString();
		this.link = in.readString();
		this.descripcion = in.readString();
		this.guid = in.readString();
		this.fecha = in.readString();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getLink() {
		return link;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getGuid() {
		return guid;
	}

	public String getFecha() {
		return fecha;
	}

	public void setTitulo(String t) {
		titulo = t;
	}

	public void setLink(String l) {
		link = l;
	}

	public void setDescripcion(String d) {
		descripcion = d;
	}

	public void setGuid(String g) {
		guid = g;
	}

	public void setFecha(String f) {
		fecha = f;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(titulo);
		dest.writeString(link);
		dest.writeString(descripcion);
		dest.writeString(guid);
		dest.writeString(fecha);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Noticia> CREATOR = new Parcelable.Creator<Noticia>() {

		@Override
		public Noticia createFromParcel(Parcel source) {
			return new Noticia(source);
		}

		@Override
		public Noticia[] newArray(int size) {
			return new Noticia[size];
		}

	};
}
