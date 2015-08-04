package eu.veldsoft.vitosha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameActivity extends Activity {
	private Game game = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		game = new Game(new Dealer(), new Player("James Bond", 32, "Male", 100));
		
		((Button)findViewById(R.id.one_button)).setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				game.increaseBet(1);
			}} );
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_game, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.credit100:
			//TODO Add 100 to player's credit.
			startActivity(new Intent(this, InnerBannerActivity.class));
			break;
		case R.id.update_player:
			startActivity(new Intent(this, UpdatePlayerActivity.class));
			break;
		case R.id.help:
			startActivity(new Intent(this, HelpActivity.class));
			break;
		case R.id.about:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		}
		return true;
	}

}
