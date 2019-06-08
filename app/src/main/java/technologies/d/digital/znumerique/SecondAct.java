package technologies.d.digital.znumerique;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondAct extends AppCompatActivity {




    private ArrayList<ClientData> cdata;
    private ArrayList<TableModel> tableModel;
    int counter=0;
    TableLayout tl;
    TableRow tr;



    int Qtyval;
    float Basicval;
    int Discval;
    int gstval;
    float net_amount ;
    float disc_amount;
    float basic_amount_after_disc;
    float total_amount ;
    float temptotal;

    Button done;


    ArrayList<String> ar ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        done = findViewById(R.id.done);

        loadJSON();



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });

    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            //add the function to perform here

            addrow();

            return(true);
        case R.id.reset:
            //add the function to perform here
           deleterow();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }



    public void savedata(){

        ArrayList<TableModel> tableModel = new ArrayList<TableModel>();
        TextView totgst18 = findViewById(R.id.totgst18);
        TextView gst18 = findViewById(R.id.gst18);
        TextView sgst18 = findViewById(R.id.sgst18);
        TextView cgst18 = findViewById(R.id.cgst18);

        TextView totgst28 = findViewById(R.id.totgst28);
        TextView gst28 = findViewById(R.id.gst28);
        TextView sgst28 = findViewById(R.id.sgst28);
        TextView cgst28 = findViewById(R.id.cgst28);

        TextView finaltotal = findViewById(R.id.finaltotal);
        TextView amountwords = findViewById(R.id.amtwords);



        String text1="";
        String text2="";
        String slno="";
        String part="";
        String basic="";
        String qty="";
        String disc="";
        String gst="";
        String tamt="";


        TableLayout PartDetailLayout = ((TableLayout) findViewById(R.id.TableLayout01));
        int childParts = PartDetailLayout.getChildCount();
        if (PartDetailLayout != null) {
            for (int i = 1; i < childParts; i++) {
                ArrayList<String> arryList=new ArrayList<>();
                View viewChild = PartDetailLayout.getChildAt(i);
                if (viewChild instanceof TableRow) {
                    int rowChildParts = ((TableRow) viewChild).getChildCount();
                    for (int j = 0; j < rowChildParts; j++) {
                        View viewChild2 = ((TableRow) viewChild).getChildAt(j);
                        if (viewChild2 instanceof AutoCompleteTextView) {
                            // get text from edit text
                             text1 = ((AutoCompleteTextView) viewChild2).getText().toString();

                             part = text1;




                        } else if (viewChild2 instanceof EditText) {
                            // get text from text view
                             text2 = ((EditText) viewChild2).getText().toString();


                             arryList.add(text2);


                             }


                    }



                    slno = arryList.get(0);
                    basic = arryList.get(1);
                    qty = arryList.get(2);
                    disc = arryList.get(3);
                    gst = arryList.get(4);
                    tamt = arryList.get(5);


                    TableModel trans = new TableModel(slno,part,basic,qty,disc,gst,tamt);
                    tableModel.add(trans);

                    Log.d("array ilst",slno+" "+part+" "+basic+" "+qty+" "+disc+" "+gst+" "+tamt);






                }
            }
        }



        Float totamt18= 0.0f;

        Float total18 = 0.0f;

        ArrayList<Float> arryList=new ArrayList<>();

        Float totamt28= 0.0f;

        Float total28 = 0.0f;

        ArrayList<Float> arryList28=new ArrayList<>();





        for (int i=0; i<tableModel.size(); i++){
                int index18 =0;

                if (tableModel.get(i).getGst().equals("18")) {
                    index18 = i;
                    totamt18 = Float.parseFloat(tableModel.get(index18).getTamt().toString());
                    arryList.add(totamt18);

                    }
            }
        for(int j=0; j<arryList.size(); j++){
            total18 += Float.parseFloat(arryList.get(j).toString());
        }
                Float gst18val = (total18 *18)/100;
                Float sgst18val = gst18val/2;

                totgst18.setText(total18.toString());
                gst18.setText(gst18val.toString());
                sgst18.setText(sgst18val.toString());
                cgst18.setText(sgst18val.toString());






        for (int i=0; i<tableModel.size(); i++){
            int index28 =0;
            if (tableModel.get(i).getGst().equals("28")) {
                index28 = i;
                totamt28 = Float.parseFloat(tableModel.get(index28).getTamt().toString());
                arryList28.add(totamt28);

            }


        }
        for(int j=0; j<arryList28.size(); j++){
            total28 += Float.parseFloat(arryList28.get(j).toString());
        }


        Float gst28val = (total28*28)/100;
        Float sgst28val = gst28val/2;

        totgst28.setText(total28.toString());
        gst28.setText(gst28val.toString());
        sgst28.setText(sgst28val.toString());
        cgst28.setText(sgst28val.toString());

        Float finaltotalval = total18+total28;
        finaltotal.setText(finaltotalval.toString());

        String return_val_in_english =   EnglishNumberToWords.convert(finaltotalval);

        amountwords.setText(return_val_in_english);



    }








    public void addrow(){

        tl =(TableLayout)findViewById(R.id.TableLayout01);
        tr=new TableRow(SecondAct.this);
        counter++;


        EditText tv= new EditText(SecondAct.this);
        tv.setText(""+counter);
        tv.setBackgroundResource(R.drawable.roundededittext);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);





        final AutoCompleteTextView pert= new AutoCompleteTextView(SecondAct.this);
        pert.setHint("Perticulars");
        pert.setBackgroundResource(R.drawable.roundededittext);
        pert.setGravity(Gravity.CENTER_HORIZONTAL);
        pert.setId(R.id.edit_text_auto);
        pert.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);




        String[] namesArr = new String[cdata.size()];
        for (int i = 0; i < cdata.size(); i++) {
            namesArr[i] = cdata.get(i).getName();
            Log.d("Error",cdata.get(i).getName());

        }



        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (SecondAct.this, R.layout.autocomplete_dropdown, namesArr);
        //Getting the instance of AutoCompleteTextView

        pert.setThreshold(1);//will start working from first character
        pert.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        pert.setTextColor(Color.BLACK);
        pert.setDropDownHeight(200);
        pert.setSingleLine(true);



        final EditText qty= new EditText(SecondAct.this);
        qty.setHint("Qty");
        qty.setBackgroundResource(R.drawable.roundededittext);
        qty.setGravity(Gravity.CENTER_HORIZONTAL);
        qty.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        qty.setTextColor(Color.BLACK);
        qty.setSingleLine(true);
        qty.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);





        final EditText basic= new EditText(SecondAct.this);
        basic.setHint("Basic");
        basic.setBackgroundResource(R.drawable.roundededittext);
        basic.setGravity(Gravity.CENTER_HORIZONTAL);
        basic.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        basic.setTextColor(Color.BLACK);
        basic.setSingleLine(true);
        basic.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);




        final EditText gst= new EditText(SecondAct.this);
        gst.setHint("GST");
        gst.setBackgroundResource(R.drawable.roundededittext);
        gst.setGravity(Gravity.CENTER_HORIZONTAL);
        gst.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        gst.setTextColor(Color.BLACK);
        gst.setSingleLine(true);
        gst.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);






        final EditText disc= new EditText(SecondAct.this);
        disc.setHint("Dis%");
        disc.setBackgroundResource(R.drawable.roundededittext);
        disc.setGravity(Gravity.CENTER_HORIZONTAL);
        disc.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        disc.setTextColor(Color.BLACK);
        disc.setSingleLine(true);
        disc.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);






        final EditText tamt= new EditText(SecondAct.this);
        tamt.setHint("Amount");
        tamt.setBackgroundResource(R.drawable.roundededittext);
        tamt.setGravity(Gravity.CENTER_HORIZONTAL);
        tamt.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        tamt.setTextColor(Color.BLACK);
        tamt.setSingleLine(true);
        tamt.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);





        tr.addView(tv);
        tr.addView(pert);
        tr.addView(basic);
        tr.addView(qty);
        tr.addView(disc);
        tr.addView(gst);
        tr.addView(tamt);


        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);



        tl.addView(tr,tableRowParams);







pert.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
    @Override
    public void onDismiss() {


        int index =0;

        for(ClientData clientData : cdata){

            String pertstr = pert.getText().toString();

            if(clientData.getName().equals(pertstr)){
                index = cdata.indexOf(clientData);


            }

            float basicprice = Float.valueOf(cdata.get(index).getPrice());

            basic.setText(""+basicprice);





        }




    }
});



    basic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {


            if(basic.getText().length()>0) {


                Basicval = Float.valueOf(basic.getText().toString());


                temptotal = Basicval;

                tamt.setText(temptotal+"");



            }






        }
    });


    qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {





            if(qty.getText().length()>0&&basic.getText().length()>0) {


                Basicval = Float.valueOf(basic.getText().toString());

                Qtyval = Integer.parseInt(qty.getText().toString());


                net_amount = (Qtyval * Basicval);

                tamt.setText(net_amount + "");

            }


        }
    });




        disc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


                if(basic.getText().length()>0&&qty.getText().length()>0&&disc.getText().length()>0) {


                    Basicval = Float.valueOf(basic.getText().toString());

                    Qtyval = Integer.parseInt(qty.getText().toString());


                    Discval = Integer.parseInt(disc.getText().toString());



                    net_amount = (Qtyval * Basicval);
                    Discval = 100-Discval;
                    disc_amount =((net_amount * Discval)/100);
                    basic_amount_after_disc = net_amount + disc_amount;


                    tamt.setText(disc_amount+"");



                }




            }
        });


        gst.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


                if(basic.getText().length()>0&&qty.getText().length()>0&&disc.getText().length()>0&&gst.getText().length()>0) {


                    Basicval = Float.valueOf(basic.getText().toString());

                    Qtyval = Integer.parseInt(qty.getText().toString());


                    Discval = Integer.parseInt(disc.getText().toString());

                    gstval = Integer.parseInt(gst.getText().toString());


                    net_amount = (Qtyval * Basicval);
                    Discval = 100-Discval;
                    disc_amount =((net_amount * Discval)/100);
                  //  basic_amount_after_disc = net_amount + disc_amount;


                    total_amount = disc_amount +  (disc_amount * gstval/100);



                    tamt.setText(total_amount+"");


                }





            }
        });









    }


    public void deleterow(){


        if(counter>1) {

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    tl.removeView(tl.getChildAt(counter));
                    counter--;
                }
            }, 100);
        }


    }





    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://projectvtu.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();


                cdata = new ArrayList<>(Arrays.asList(jsonResponse.getClientData()));


                Log.d("Error",cdata.get(0).getName());


            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }






    public static class EnglishNumberToWords {

        private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty",
                " fifty", " sixty", " seventy", " eighty", " ninety" };

        private static final String[] numNames = { "", " one", " two", " three", " four", " five",
                " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen",
                " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen" };

        private static String convertLessThanOneThousand(int number)
        {
            String soFar;

            if (number % 100 < 20)
            {
                soFar = numNames[number % 100];
                number /= 100;
            } else
            {
                soFar = numNames[number % 10];
                number /= 10;

                soFar = tensNames[number % 10] + soFar;
                number /= 10;
            }
            if (number == 0)
                return soFar;
            return numNames[number] + " hundred" + soFar;
        }

        public static String convert(float number)
        {
            // 0 to 999 999 999 999
            if (number == 0)
            {
                return "zero";
            }

            String snumber = Float.toString(number);

            // pad with "0"
            String mask = "000000000000";
            DecimalFormat df = new DecimalFormat(mask);
            snumber = df.format(number);

            // XXXnnnnnnnnn
            int billions = Integer.parseInt(snumber.substring(0, 3));
            // nnnXXXnnnnnn
            int millions = Integer.parseInt(snumber.substring(3, 6));
            // nnnnnnXXXnnn
            int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
            // nnnnnnnnnXXX
            int thousands = Integer.parseInt(snumber.substring(9, 12));

            String tradBillions;
            switch (billions)
            {
                case 0:
                    tradBillions = "";
                    break;
                case 1:
                    tradBillions = convertLessThanOneThousand(billions) + " billion ";
                    break;
                default:
                    tradBillions = convertLessThanOneThousand(billions) + " billion ";
            }
            String result = tradBillions;

            String tradMillions;
            switch (millions)
            {
                case 0:
                    tradMillions = "";
                    break;
                case 1:
                    tradMillions = convertLessThanOneThousand(millions) + " million ";
                    break;
                default:
                    tradMillions = convertLessThanOneThousand(millions) + " million ";
            }
            result = result + tradMillions;

            String tradHundredThousands;
            switch (hundredThousands)
            {
                case 0:
                    tradHundredThousands = "";
                    break;
                case 1:
                    tradHundredThousands = "one thousand ";
                    break;
                default:
                    tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
            }
            result = result + tradHundredThousands;

            String tradThousand;
            tradThousand = convertLessThanOneThousand(thousands);
            result = result + tradThousand;

            // remove extra spaces!
            return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
        }

    }






}
