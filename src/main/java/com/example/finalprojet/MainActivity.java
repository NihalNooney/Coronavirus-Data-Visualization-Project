package com.example.finalprojet;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Bar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
        EditText EnterStatename;
        TextView display, display2;
        Button searchButton;
        String url;
        Animation rotateAnimation;
        ImageView imageView;
        AnyChartView anyChartView;
        ArrayList<cityData>temporary = new ArrayList<>();
       // String[] highestStates={"Wisconsin", "Connecticut","North Dakota"};
        //int[] numbers={85600, 54360,111150};
        String j,k;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.DisplayAllocations);
        EnterStatename = findViewById(R.id.EnterState);
        searchButton = findViewById(R.id.SearchButton);
        display2=findViewById(R.id.textView3);
        anyChartView=findViewById(R.id.any_chart_view);
        String name = EnterStatename.getText().toString();

        imageView=(ImageView)findViewById(R.id.imageView);
        rotateAnimation();

        //setupPieChart();


        final String[] temp = {""};
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    if (name!=null)
                    {
                        url = "https://data.cdc.gov/resource/saz5-9hgg.json";
                        display.setText("");
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Enter State", Toast.LENGTH_SHORT).show();
                    }
                    getWeather task = new getWeather();
                    temp[0] = task.execute(url).get();
                } catch (ExecutionException e)
                {
                    e.printStackTrace();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if(temp[0] == null)
                {
                    display.setText("Cannot Find Data");
                }



            }

        });
    }



    private void rotateAnimation() {
    rotateAnimation= AnimationUtils.loadAnimation(this,R.anim.rotate);
    imageView.startAnimation(rotateAnimation);
    }

    class getWeather extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL (urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while((line = reader.readLine())!=null)
                {
                    result.append(line).append("\n");
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            EnterStatename = findViewById(R.id.EnterState);
            String name = EnterStatename.getText().toString();

            try {
                JSONArray jsonArray = new JSONArray(result);
                 j = "";
                 k="";
                // highestStates = new String[]{jsonArray.getJSONObject(32).getString("_1st_dose_allocations"), jsonArray.getJSONObject(28).getString("_1st_dose_allocations"), jsonArray.getJSONObject(18).getString("_1st_dose_allocations")};
                for(int i = 0; i<50; i++)
                {
                    try {
                        j = jsonArray.getJSONObject(i).getString("_1st_dose_allocations");
                        k=jsonArray.getJSONObject(i).getString("_2nd_dose_allocations");
                        if(jsonArray.getJSONObject(i).getString("jurisdiction").equalsIgnoreCase(name)){
                            display.setText(j+" COVID 1st dose vaccine allocations");
                            display2.setText(k+ " COVID 2nd does vaccine allocations");
                            Log.d("TAG", j);
                            temporary.add((new cityData(name, Integer.parseInt(j))));
                        }


                    }catch (JSONException jsonException)
                    {
                        jsonException.printStackTrace();
                    }

                }
            }
            catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            setupPieChart();
        };


    }


    public class cityData {
        public String city;
        public int num;

        public cityData(String city, int num){
            this.city= city;
            this.num = num;
        }
    }

    public void setupPieChart(){
        Log.d("TAG1", temporary.get(0).city + "");
        Cartesian pie =AnyChart.bar();
        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i = 0; i<temporary.size();i++){
            dataEntries.add(new ValueDataEntry(temporary.get(i).city, temporary.get(i).num));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }




}




































/*

*/








