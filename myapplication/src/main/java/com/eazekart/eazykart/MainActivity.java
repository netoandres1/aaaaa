package com.eazekart.eazykart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;

    public ListView list;
    public static ArrayList<ListItemBean> listData = new ArrayList<ListItemBean>();
    TextView totalRate;
    ListViewAdapter mAdapter = null;

    public static int totalvalue = 0;
    private Context mContext = this;
    public Button payButton;
    public ImageView productIcon;
    Button entermanually;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        payButton = (Button) findViewById(R.id.payButton);
        entermanually= (Button) findViewById(R.id.enterManually);


        ImageView image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i2 = new Intent(MainActivity.this, ReachUs.class);
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

            }
        });

        productIcon = (ImageView) findViewById(R.id.productIcon);

        productIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setCaptureLayout(R.layout.custom_capture_layout);
                integrator.setLegacyCaptureLayout(R.layout.custom_legacy_capture_layout);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.autoWide();
                integrator.initiateScan();
            }
        });
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            //getSupportActionBar().setIcon(R.drawable.ic_logo);
            getSupportActionBar().setTitle("");
        }
        entermanually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Enter BarCode");
                alert.setMessage("Enter the product BarCode");

                final EditText input = new EditText(MainActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();

                        ListItemBean lll = new ListItemBean();
                        lll.setItemName(value);
                        lll.setItemresid(R.drawable.product);
                        lll.setQuantity(500);
                        lll.setRate(3);
                        listData.add(lll);
                        mAdapter.refreshAdapter(listData);

                        if (listData.size() > 0) {
                            list.setVisibility(View.VISIBLE);
                            productIcon.setVisibility(View.GONE);
                        } else {
                            list.setVisibility(View.GONE);
                            productIcon.setVisibility(View.VISIBLE);
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mAdapter.getData().size() >0){
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            MainActivity.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Payment Verification");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please wait for the Merchant Terminal for verifying the cart items.");

                    // Setting Icon to Dialog
                    // alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("Transfer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to execute after dialog closed
                            Toast toast=  Toast.makeText(getApplicationContext(), "Verifying & Transferring the cart item...", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP,0,150);
                            toast.show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }else{
                    Toast toast=  Toast.makeText(getApplicationContext(), "Please Scan the Products...", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP,0,150);
                    toast.show();
                }

            }
        });

        list = (ListView) findViewById(R.id.list);
        totalRate = (TextView) findViewById(R.id.totalRate);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i2 = new Intent(MainActivity.this, SingleDetailActivity.class);
                ListItemBean lll = mAdapter.getData().get(position);
                i2.putExtra("List_Item", lll);
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        if (listData.size() > 0) {
            list.setVisibility(View.VISIBLE);
            productIcon.setVisibility(View.GONE);
        }

        mAdapter = new ListViewAdapter(this, list, listData);
        list.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);

        updateRate();

    }

    public void listVisibiltiy() {
        if (mAdapter == null)
            mAdapter = new ListViewAdapter(this);

        if (mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            list.setVisibility(View.VISIBLE);
            productIcon.setVisibility(View.GONE);
        } else {
            list.setVisibility(View.GONE);
            productIcon.setVisibility(View.VISIBLE);
        }
    }

    public void scanBarcode(View view) {

        new IntentIntegrator(this).initiateScan();
    }


    public void scanBarcodeCustomLayout(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureLayout(R.layout.custom_capture_layout);
        integrator.setLegacyCaptureLayout(R.layout.custom_legacy_capture_layout);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.autoWide();
        integrator.initiateScan();

    }

    public void encodeBarcode(View view) {
        new IntentIntegrator(this).shareText("Test Barcode");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();


            } else {

                ListItemBean lll = new ListItemBean();
                lll.setItemName(result.getContents());
                lll.setItemresid(R.drawable.product);
                lll.setQuantity(500);
                lll.setRate(3);
                listData.add(lll);
                mAdapter.refreshAdapter(listData);

                if (listData.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    productIcon.setVisibility(View.GONE);
                } else {
                    list.setVisibility(View.GONE);
                    productIcon.setVisibility(View.VISIBLE);
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setMessage("Product added to cart " + result.getContents());
                alertDialogBuilder.setPositiveButton("Scan Another",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                                integrator.setCaptureLayout(R.layout.custom_capture_layout);
                                integrator.setLegacyCaptureLayout(R.layout.custom_legacy_capture_layout);
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                                integrator.autoWide();
                                integrator.initiateScan();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Done",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                updateRate();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateRate() {
        if (listData.size() > 0) {
            list.setVisibility(View.VISIBLE);
            productIcon.setVisibility(View.GONE);
        } else {
            list.setVisibility(View.GONE);
            productIcon.setVisibility(View.VISIBLE);
        }
        ArrayList<ListItemBean> totaldata = mAdapter.getData();
        int totalv = 0;
        for (int i = 0; i < totaldata.size(); i++) {
            ListItemBean data1 = totaldata.get(i);
            totalvalue = data1.getRate();
            totalv = totalv + data1.getRate();
            Log.d("total value", "" + totalv);
        }
        // Toast.makeText(MainActivity.this, "toalva" + totalv, Toast.LENGTH_SHORT).show();
        totalRate.setText("$" + totalv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_barcode) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setCaptureLayout(R.layout.custom_capture_layout);
            integrator.setLegacyCaptureLayout(R.layout.custom_legacy_capture_layout);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            integrator.autoWide();
            integrator.initiateScan();

        }
        return super.onOptionsItemSelected(menuItem);
    }
}
