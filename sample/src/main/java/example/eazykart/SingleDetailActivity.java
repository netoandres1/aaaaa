package eazekart.eazykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SingleDetailActivity extends ActionBarActivity {
    private Toolbar toolbar;
    ImageView im;
    TextView toolbar_title, productName, productRate, productCompany;
    LinearLayout productNamell, productRatell, productCompanyll;
    ListItemBean listobject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_detail);
        productNamell = (LinearLayout) findViewById(R.id.productNamell);
        productRatell = (LinearLayout) findViewById(R.id.productRatell);
        productCompanyll = (LinearLayout) findViewById(R.id.productCompanyll);
        productName = (TextView) findViewById(R.id.productName);
        productRate = (TextView) findViewById(R.id.productRate);
        productCompany = (TextView) findViewById(R.id.productCompany);

        listobject = (ListItemBean) getIntent().getExtras().getSerializable("List_Item");
        if (listobject != null) {

            productCompany.setText(listobject.getItemName());

            productRate.setText("" + listobject.getRate());
            productName.setText("" + listobject.getQuantity());

        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            im = (ImageView) toolbar.findViewById(R.id.toolbar_image);
            toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
            im.setVisibility(View.INVISIBLE);
            //toolbar_title.setVisibility(View.INVISIBLE);
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setIcon(R.drawable.ic_logo);
            getSupportActionBar().setTitle("");
        }
        toolbar_title.setText("LiptonTea");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_single_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent i2 = new Intent(SingleDetailActivity.this, MainActivity.class);
        startActivity(i2);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        return super.onOptionsItemSelected(menuItem);
    }
}
