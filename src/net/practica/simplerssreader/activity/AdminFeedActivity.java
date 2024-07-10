package net.practica.simplerssreader.activity;

import java.util.ArrayList;

import net.practica.simplerssreader.R;
import net.practica.simplerssreader.model.Feed;
import net.practica.simplerssreader.model.dao.FeedDao;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AdminFeedActivity extends ListActivity {

	private Button btAddFeed;
	ListView lvFeeds;
	private EditText etAddFeed;
	private FeedDao feedDao;
	private ArrayList<String> feeds;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.admin_feeds);

		feedDao = new FeedDao(getApplicationContext());
		feeds = feedDao.listarFeeds();

		lvFeeds = ((ListView) findViewById(android.R.id.list));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feeds);
		setListAdapter(adapter);
		registerForContextMenu(lvFeeds);

		etAddFeed = (EditText) findViewById(R.id.etAddFeed);
		(btAddFeed = (Button) findViewById(R.id.btAddFeed))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * si el campo esta vacio mostrar una alerta
				 */
				if (etAddFeed.getText().toString().equals("")) {
					AlertDialog.Builder emptyFeedDialog = new AlertDialog.Builder(AdminFeedActivity.this)
					.setTitle("Introduce un Feed")
					.setCancelable(true)
					.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					emptyFeedDialog.show();
				} 
				/*
				 * añadir la url del feed al listview y refrescar el listview
				 */
				else {
					Feed newFeed;
					newFeed = new Feed(etAddFeed.getText().toString());

					boolean res = feedDao.insertarFeed(newFeed);
					if (res) {
						etAddFeed.setText("");
						feeds.add(newFeed.getFeed());
						adapter.notifyDataSetChanged();
						/*
						 * mandamos de vuelta el nuevo feed 
						 * que el usuario ha introducido
						 */
						Intent resultFeedIntent = new Intent();
						resultFeedIntent.putStringArrayListExtra("feedsArrayList", feeds);
						setResult(Activity.RESULT_OK, resultFeedIntent);
					}
					else {
						Log.e("SimpleRss", "AdminFeedActivity feedDao.insertarFeed(newFeed) error");
					}
				}
			}
		});


	}

	/*
	 * menu contextual que aparece al pulsar sobre un feed
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_feed, menu);
	}

	/*
	 * acciones del menu contextual, de momento
	 * solo hay una; eliminar.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterContextMenuInfo menuInfo;

		switch (item.getItemId()) {
		case R.id.eliminarFeed:
			menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();

			//Log.d("RssReader", "eliminar " + feed);
			String feed = adapter.getItem(menuInfo.position);
			feedDao.eliminarFeed(new Feed(feed));
			adapter.remove(adapter.getItem(menuInfo.position));
			adapter.notifyDataSetChanged();

			/*
			 * mandamos de vuelta el nuevo feed 
			 * que el usuario ha introducido
			 */
			Intent resultFeedIntent = new Intent();
			resultFeedIntent.putStringArrayListExtra("feedsArrayList", feeds);
			setResult(Activity.RESULT_OK, resultFeedIntent);

			break;
		}

		return true;
	}

}
