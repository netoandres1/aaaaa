package eazekart.eazykart;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class ReachUs extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_us);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {


            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            //getSupportActionBar().setIcon(R.drawable.ic_logo);
            getSupportActionBar().setTitle("");
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setIcon(R.drawable.ic_logo);
        }

        ImageView  im = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        im.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reach_us, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        Intent i2 = new Intent(ReachUs.this, MainActivity.class);
        startActivity(i2);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        return super.onOptionsItemSelected(menuItem);
    }
}
