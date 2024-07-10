package net.practica.simplerssreader.model;

import android.graphics.Bitmap;

public class Feed {
	private String feed;
	private Bitmap imagen;

	public Feed(String feed) {
		this.feed = feed;
	}

	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public Bitmap getImagen() {
		return imagen;
	}

	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}

}
