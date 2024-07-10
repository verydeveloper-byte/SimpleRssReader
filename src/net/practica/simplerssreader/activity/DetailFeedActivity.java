package net.practica.simplerssreader.activity;

import net.practica.simplerssreader.R;
import net.practica.simplerssreader.model.Noticia;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailFeedActivity extends Activity {

	TextView tvDetailFecha;
	TextView tvDetailTitulo;
	TextView tvDetailDescripcion;
	TextView tvDetailEnlace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_feed);
		
		tvDetailFecha = (TextView) findViewById(R.id.tvDetailFecha);
		tvDetailTitulo = (TextView) findViewById(R.id.tvDetailTitulo);
		tvDetailDescripcion = (TextView) findViewById(R.id.tvDetailDescripcion);
		tvDetailEnlace = (TextView) findViewById(R.id.tvDetailEnlace);
		
		Noticia noticia = this.getIntent().getParcelableExtra("noticia");
		tvDetailFecha.setText(noticia.getFecha());
		tvDetailTitulo.setText(noticia.getTitulo());
		tvDetailDescripcion.setText(noticia.getDescripcion());
		tvDetailEnlace.setText(noticia.getLink());
	}
}
