package net.practica.simplerssreader.activity;

import java.util.ArrayList;
import java.util.List;

import net.practica.simplerssreader.R;
import net.practica.simplerssreader.adapter.NoticiaAdapter;
import net.practica.simplerssreader.model.Noticia;
import net.practica.simplerssreader.model.dao.FeedDao;
import net.practica.simplerssreader.rss.RssParserSax;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private RssParserSax xmlParser;
	private List<Noticia> noticias;
	private ListView lvFeedItems;
	private Spinner spFeed;
	private FeedDao feedDao;
	private ArrayAdapter<String> spAdapter;
	private ArrayList<String> spItems;

	private final int ADM_FEED_REQUEST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		feedDao = new FeedDao(getApplicationContext());
		lvFeedItems = (ListView) findViewById(R.id.lvFeedItems);
		spFeed = (Spinner) findViewById(R.id.spFeed);
		spItems = feedDao.listarFeeds();

		/*
		 * Abrir detalle de la noticia en una Activity
		 */
		lvFeedItems.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent detailIntent = new Intent(MainActivity.this, DetailFeedActivity.class);
				detailIntent.putExtra("noticia", (Noticia) parent.getItemAtPosition(position));
				startActivity(detailIntent);
			}
		});

		//feedDao.insertarFeed("http://www.europapress.es/rss/rss.aspx");		
		spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spItems);
		spFeed.setAdapter(spAdapter);

		/*
		 * Al seleccionar un feed del spinner, cargamos
		 * sus noticias
		 */
		spFeed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String feed = (String) parent.getItemAtPosition(position);
				TareaNoticias tareaNoticias = new TareaNoticias();
				tareaNoticias.execute(feed);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		//TareaNoticias tareaNoticias = new TareaNoticias();
		//tarea.execute("http://www.europapress.es/rss/rss.aspx");
	}

	private class TareaNoticias extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			xmlParser = new RssParserSax(params[0]);
			noticias = xmlParser.parse();
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			lvFeedItems.setAdapter(new NoticiaAdapter(MainActivity.this, R.layout.feed_layout, (ArrayList<Noticia>) noticias));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.opciones, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.gestionFeeds:
			Intent intentAdmFeed = new Intent(this, AdminFeedActivity.class);
			startActivityForResult(intentAdmFeed, ADM_FEED_REQUEST);
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ADM_FEED_REQUEST:
			if (resultCode == Activity.RESULT_OK) {
				/*
				 * por alguna razon, el metodo notifyDataSetChanged()
				 * no funciona bien con Spinners, la unica solucion que
				 * he encontrado ha sido crear un nuevo adapter.
				 */
				spItems = data.getStringArrayListExtra("feedsArrayList");
				spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spItems);
				spFeed.setAdapter(spAdapter);
			}
		}
	}
}

