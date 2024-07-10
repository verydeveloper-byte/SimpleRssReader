package net.practica.simplerssreader.adapter;

import java.util.ArrayList;

import net.practica.simplerssreader.R;
import net.practica.simplerssreader.model.Noticia;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoticiaAdapter extends ArrayAdapter<Noticia> {

	private ArrayList<Noticia> noticia;
	private Context context;

	public NoticiaAdapter(Context context, int textViewResourceId, ArrayList<Noticia> elementos) {
		super(context, textViewResourceId, elementos);
		this.noticia = elementos;
		this.context = context;
	}

	/*
	 * getView() se llama cada vez que se pinta un elemento del ListView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		/*
		 * El reciclaje de Views consiste en lo siguiente: Si convertView 
		 * es un valor nulo dentro de getView(), significa que el ListView 
		 * esta empezando a dibujar el objeto y aun no se ha inflado, por
		 * tanto se inflan los elementos del XML y despues se instancia 
		 * un objeto ViewHolder en el que se guardan los elementos del XML
		 * para despues con un setTag() (todos los Views tienen una propiedad 
		 * llamada Tag) guardar el ViewHolder.
		 */
		if (convertView == null) {
			/*
			 * coge el layout XML layout_provincia.xml e infla
			 * (inflar = convertir a un objeto de java los elementos del XML)
			 * los elementos en el objeto convertView.
			 */
			LayoutInflater vi;
			vi = LayoutInflater.from(context);
			convertView = vi.inflate(R.layout.feed_layout, null);

			holder = new ViewHolder();
			holder.titulo = (TextView) convertView.findViewById(R.id.tvFeedTitle);
			holder.fecha = (TextView) convertView.findViewById(R.id.tvFecha);

			/* 
			 * se le pone una etiqueta al View, que es el ID del objeto del elemento en el que estamos.
			 * es decir, vinculamos el objeto ViewHolder (clase interna) a convertView
			 */ 
			convertView.setTag(holder);
		} 
		/*
		 * si no es null significa que el ListView esta reusando el objeto
		 * convertView del argumento, por lo que no necesitamos reinflar el 
		 * layout XML de provincias, ni tampoco asociar los elementos del
		 * XML con findViewById().
		 * Simplemente hay que recuperar la info que guardamos con setTag()
		 * la primera vez que creamos el objeto, con lo que se evitan llamadas
		 * innecesarias a findViewById() y volver a recuperar los datos.
		 */
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		/*
		 * a partir de aqui ya tenemos el objeto holder preparado,
		 * solo queda asignarles valores 
		 */
		Noticia n = noticia.get(position);
		if (n != null) {
			holder.titulo.setText(n.getTitulo());
			holder.fecha.setText(n.getFecha());
		}
		return convertView;
	}

	public int getCount() {
		return noticia.size();
	}

	public Noticia getItem(int position) {
		return noticia.get(position);
	}

	/*
	 * al crear un adapter se suelen "cachear" las propiedades de los objetos View
	 * (nombre, descripcion, imagen), para mejorar el renderizado cuando se dibuje
	 * el ListView.
	 * Esta clase tiene que contener los mismos miembros (y del mismo tipo) que los
	 * elementos del XML layout_provincias.
	 */
	static class ViewHolder {
		TextView titulo;
		TextView fecha;
	}

}
