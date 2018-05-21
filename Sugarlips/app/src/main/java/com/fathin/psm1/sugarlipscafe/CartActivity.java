package com.fathin.psm1.sugarlipscafe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.fathin.psm1.sugarlipscafe.app.AppConfig;
import com.fathin.psm1.sugarlipscafe.app.AppController;
import com.fathin.psm1.sugarlipscafe.app.JSONParser;
import com.fathin.psm1.sugarlipscafe.Cart;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fathin.psm1.sugarlipscafe.Cart.items;
import static com.fathin.psm1.sugarlipscafe.app.AppConfig.URL_CART;

public class CartActivity extends AppCompatActivity {

    private String TAG = CartActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private TableLayout tableLayout;
    private EditText editTextTable;
    private Button buttonContinueShopping, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tableLayout = (TableLayout) findViewById(R.id.tableLayoutProduct);
        editTextTable = (EditText) findViewById(R.id.editTextTable);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread

                // for(int i = 0 ; i < ; i++)
                {
                    new CreateNewProduct().execute();
                }
            }
        });
        buttonContinueShopping = (Button) findViewById(R.id.btnShopping);
        buttonContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        addCart();
        createColumns();
        fillData();
    }
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CartActivity.this);
            pDialog.setMessage("Added to Cart...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            //JSONObject json ;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    String product_id = String.valueOf(items.get(0).getProduct().getProduct_id());
                    String quantity = String.valueOf(items.get(0).getQuantity());
                    String subtotal = String.valueOf(items.get(0).getProduct().getProduct_price() * items.get(0).getQuantity());
                    String table = String.valueOf(editTextTable.getText());

                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("product_id", product_id));
                    params.add(new BasicNameValuePair("order_quantity", quantity));
                    params.add(new BasicNameValuePair("order_total_price", subtotal));
                    params.add(new BasicNameValuePair("table_no", table));

                    JSONObject json = jsonParser.makeHttpRequest(URL_CART,
                            "POST", params);

                    // check log cat fro response
                    Log.d("Create Response", json.toString());

                    try {
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            // successfully created product
                            Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                            startActivity(i);

                            // closing this screen
                            finish();
                        } else {
                            // failed to create product
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread = new Thread(run);
            thread.start();
            //for loop utk setiap item {

            // getting JSON Object
            // Note that create product url accepts POST method


            //}close loop item , kena declare JSONOBject luar loop

            // check for success tag


            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }

    private  void addCart() {
        try {
            Intent intent = getIntent();
            Product product = (Product) intent.getSerializableExtra("product");
            if (!Cart.isExists(product)) {
                Cart.insert(new Item(product, 1));
            } else {
                Cart.update(product);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void createColumns() {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        //Option column
        TextView textViewOption = new TextView(this);
        textViewOption.setText("Option");
        textViewOption.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewOption.setPadding(5,5,5,0);
        tableRow.addView(textViewOption);

        //id column
        TextView textViewId = new TextView(this);
        textViewId.setText("Id");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5,5,5,0);
        tableRow.addView(textViewId);

        //Name column
        TextView textViewName = new TextView(this);
        textViewName.setText("Name");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5,5,5,0);
        tableRow.addView(textViewName);

        //Image Column
//        TextView textViewPhoto = new TextView(this);
//        textViewPhoto.setText("Image");
//        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        textViewPhoto.setPadding(5,5,5,5);
//        tableRow.addView(textViewPhoto);

        //Quantity column
        TextView textViewQuantity = new TextView(this);
        textViewQuantity.setText("Quantity");
        textViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewQuantity.setPadding(5,5,5,0);
        tableRow.addView(textViewQuantity);

        //Price column
        TextView textViewPrice = new TextView(this);
        textViewPrice.setText("Price");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5,5,5,0);
        tableRow.addView(textViewPrice);

        //Calories column
        TextView textViewCalories = new TextView(this);
        textViewCalories.setText("Calories");
        textViewCalories.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewCalories.setPadding(5,5,5,0);
        tableRow.addView(textViewCalories);

        //SubTotal column
        TextView textViewSubTotal = new TextView(this);
        textViewSubTotal.setText("Subtotal");
        textViewSubTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewSubTotal.setPadding(5,5,5,0);
        tableRow.addView(textViewSubTotal);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        //Add divider
        tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        //Option column
        textViewOption = new TextView(this);
        textViewOption.setText("-----");
        textViewOption.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewOption.setPadding(5,5,5,0);
        tableRow.addView(textViewOption);

        //Id column
        textViewId = new TextView(this);
        textViewId.setText("-----");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5,5,5,0);
        tableRow.addView(textViewId);

        //Name column
        textViewName = new TextView(this);
        textViewName.setText("---------");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5,5,5,0);
        tableRow.addView(textViewName);

        //Price column
        textViewPrice = new TextView(this);
        textViewPrice.setText("---------");
        textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPrice.setPadding(5,5,5,0);
        tableRow.addView(textViewPrice);

        //Calories column
        textViewCalories = new TextView(this);
        textViewCalories.setText("---------");
        textViewCalories.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewCalories.setPadding(5,5,5,0);
        tableRow.addView(textViewCalories);

        //Image column
//        textViewPhoto = new TextView(this);
//        textViewPhoto.setText("---------");
//        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        textViewPhoto.setPadding(5,5,5,0);
//        tableRow.addView(textViewPhoto);
//
//        textViewPhoto = new TextView(this);
//        textViewPhoto.setText("---------");
//        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        textViewPhoto.setPadding(3,3,3,0);
//        tableRow.addView(textViewPhoto);
//
//        textViewPhoto = new TextView(this);
//        textViewPhoto.setText("---------");
//        textViewPhoto.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        textViewPhoto.setPadding(3,3,3,0);
//        tableRow.addView(textViewPhoto);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    private void fillData() {
        try {
            for (final Item item : Cart.contents()) {
                final TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Confirm");
                        builder.setMessage("Do you want remove this item?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TableRow currentRow = (TableRow) view;
                                TextView textViewId = (TextView)currentRow.getChildAt(0);
                                //Cart.remove(new Item(Integer.parseInt(textViewId.getText().toString())));
                                String id = textViewId.getText().toString();
                                //Cart.remove(Integer.parseInt(id));
                                tableLayout.removeAllViews();
                                createColumns();
                                fillData();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    }
                });

                //Option column
                ImageButton imageButtonOption = new ImageButton(this);
                imageButtonOption.setImageResource(R.drawable.deletebutton3);
                imageButtonOption.setPadding(5,5,5,5);
                imageButtonOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Cart.remove(item.getProduct());
                                tableLayout.removeAllViews();
                                createColumns();
                                fillData();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    }
                });
                tableRow.addView(imageButtonOption);

                //Id column
                TextView textViewId = new TextView(this);
                textViewId.setText(String.valueOf(item.getProduct().getProduct_id()));
                textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewId.setPadding(5,5,5,0);
                tableRow.addView(textViewId);

                //Name column
                TextView textViewName = new TextView(this);
                textViewName.setText(item.getProduct().getProduct_name());
                textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewName.setPadding(5,5,5,0);
                tableRow.addView(textViewName);

//                Image column
//                ImageView imageViewPhoto = new ImageView(this);
//                Bitmap bitmap = new ImageRequestAsk().execute(item.getProduct().getImage_url()).get();
//                imageViewPhoto.setImageBitmap(bitmap);
//                imageViewPhoto.setScaleType(ImageView.ScaleType.CENTER);
//                imageViewPhoto.setPadding(5,5,5,5);
//                tableRow.addView(imageViewPhoto);

                //Quantity column
                TextView textViewQuantity = new TextView(this);
                textViewQuantity.setText(String.valueOf(item.getQuantity()));
                textViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewQuantity.setPadding(5,5,5,0);
                tableRow.addView(textViewQuantity);

                //Price column
                TextView textViewPrice = new TextView(this);
                textViewPrice.setText(String.valueOf(item.getProduct().getProduct_price()));
                textViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewPrice.setPadding(5,5,5,0);
                tableRow.addView(textViewPrice);

                //Calories column
                TextView textViewCalories = new TextView(this);
                textViewCalories.setText(String.valueOf(item.getProduct().getProduct_calories() * item.getQuantity()));
                textViewCalories.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewCalories.setPadding(5,5,5,0);
                tableRow.addView(textViewCalories);

                //SubTotal Column
                TextView textViewSubtotal = new TextView(this);
                textViewSubtotal.setText(String.valueOf(item.getProduct().getProduct_price() * item.getQuantity()));
                textViewSubtotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                textViewSubtotal.setPadding(5,5,5,0);
                tableRow.addView(textViewSubtotal);

                tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }

            //Add Total Row
            TableRow tableRowCalories = new TableRow(this);
            tableRowCalories.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            TextView textViewCalories = new TextView(this);
            textViewCalories.setText("Calories Intake (Kcal) : ");
            textViewCalories.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewCalories.setPadding(5,5,5,0);
            tableRowCalories.addView(textViewCalories);

            TextView textViewTotalCalories = new TextView(this);
            textViewTotalCalories.setText(String.valueOf(Cart.calories()));
            textViewTotalCalories.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTotalCalories.setPadding(5,5,5,0);
            tableRowCalories.addView(textViewTotalCalories);

            tableLayout.addView(tableRowCalories, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            //Add Total Row
            TableRow tableRowTotal = new TableRow(this);
            tableRowTotal.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            TextView textViewTotal = new TextView(this);
            textViewTotal.setText("Total RM : ");
            textViewTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTotal.setPadding(5,5,5,0);
            tableRowTotal.addView(textViewTotal);

            TextView textViewTotalValue = new TextView(this);
            textViewTotalValue.setText(String.valueOf(Cart.total()));
            textViewTotalValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTotalValue.setPadding(5,5,5,0);
            tableRowTotal.addView(textViewTotalValue);

            tableLayout.addView(tableRowTotal, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

        }catch (Exception e2)
        {
            Toast.makeText(getApplicationContext(), e2.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ImageRequestAsk extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... params) {
            try {
                InputStream inputStream = new java.net.URL(params[0]).openStream();
                return BitmapFactory.decodeStream(inputStream);
            }catch (Exception e) {
                return null;
            }
        }
    }
}
