package com.example.cse110mb260t14.ffs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseObject;

public class PostItemActivity extends AppCompatActivity {

    private Spinner locationSpinner;
    private Button postListingButton;
    private EditText postTitle;
    private EditText postPrice;
    private EditText postDescripttion;
    private EditText postCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        locationSpinner = (Spinner) findViewById(R.id.location_spinner);
        postListingButton = (Button) findViewById(R.id.post_listing_button);
        postTitle = (EditText)findViewById(R.id.item_title_edit_text);
        postPrice = (EditText)findViewById(R.id.item_price_edit_text);
        postDescripttion = (EditText)findViewById(R.id.item_description_edit_text);
        postCategories = (EditText)findViewById(R.id.item_categories_edit_text);



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.locations_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locationSpinner.setAdapter(adapter);

        postListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean posted = postItem();
                AlertDialog.Builder db = new AlertDialog.Builder(PostItemActivity.this);

                if (posted){
                    db.setMessage("You successfully posted an item!")
                            .setTitle("Congrats!");
                }
                else{
                    db.setMessage("Please double check your data")
                            .setTitle("Failed Post");
                }

                db.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int arg1) {
                        d.cancel();
                    }
                });
                db.show();
            }
        });

    }

    private boolean postItem(){
        String title = postTitle.getText().toString();
        String price = postPrice.getText().toString();
        String description = postDescripttion.getText().toString();
        String categories = postCategories.getText().toString();
        String location = locationSpinner.getSelectedItem().toString();

        if(locationSpinner.getSelectedItemPosition() == 0){
            System.out.println("MUST SELECT LOCATION");
            return false;
        }
        if (title.equals("") || price.equals("") || description.equals("") || categories.equals("")){
            System.out.println("PLEASE MAKE SURE TO FILL IN ALL THE INFORMATION!");
            return false;
        }

        ParseObject item = new ParseObject("Listings");
        item.put("Location", location);
        item.put("Price", price);
        item.put("Title", title);
        item.put("Description", description);
        item.put("Categories", categories);

        try {
            item.save();
        }
        catch (ParseException e){
            System.out.println("ERROR POSTING TO DB");
            return false;
        }




        return true;

    }


}
