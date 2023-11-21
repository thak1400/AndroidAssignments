package com.code.wlu.abdulrahman.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.code.wlu.abdulrahman.myapplication.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ImageView weatherImgView;

    private static String ACTIVITY_NAME="WEATHER FORECAST ACTIVITY";
    TextView weatherCurrTempTxtView, weatherMinTempTxtView, weatherMaxTempTxtView;
    ProgressBar weatherProgressBar;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        weatherImgView=findViewById(R.id.weatherImgView);
        weatherProgressBar=findViewById(R.id.weatherProgressBar);
        weatherCurrTempTxtView=findViewById(R.id.weatherCurrTempTxtView);
        weatherMinTempTxtView=findViewById(R.id.weatherMinTempTxtView);
        weatherMaxTempTxtView=findViewById(R.id.weatherMaxTempTxtView);
        city = getIntent().getStringExtra("SelectedCity");
        weatherProgressBar.setVisibility(View.VISIBLE);
        new ForecastQuery().execute();


    }
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemperature,maxTemperature,currentTemperature;
        private Bitmap weatherImage;

        @Override
        protected String doInBackground(String... params) {
            String apiKey = "b50434a5ea4e2daaa663718d5be0e0d2";
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="+city+",ca&appid="+apiKey+"&mode=xml&units=metric";
            try {
                //I am not using string from strings xml file for logs as logs are only for debug time here.
                Log.i(ACTIVITY_NAME,"URL: "+apiUrl);
                URL url = new URL(apiUrl);

                publishProgress(25);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    parseXml(in);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return "Fetched weather successfully";
        }




        private boolean checkImageFileExistence(String iconName) {
            String imageFileName = iconName + ".png";
            File file = getBaseContext().getFileStreamPath(imageFileName);
            boolean flag= file.exists();
            Log.i(ACTIVITY_NAME,"Checking image existence");
            return flag;
        }

        private Bitmap readImageFromStorage(String iconName) {
            String imageFileName = iconName + ".png";
            FileInputStream fis = null;
            try {
                fis = openFileInput(imageFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.i(ACTIVITY_NAME,"Reading image from existence");
            return BitmapFactory.decodeStream(fis);
        }

        private void saveImageToStorage(String iconName, Bitmap image) {

            String imageFileName = iconName + ".png";
            try (FileOutputStream outputStream = openFileOutput(imageFileName, Context.MODE_PRIVATE)) {
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(ACTIVITY_NAME,"Saving image to storage");
        }
        private void parseXml(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("temperature")) {
                            currentTemperature = parser.getAttributeValue(null, "value");
                            minTemperature = parser.getAttributeValue(null, "min");
                            maxTemperature = parser.getAttributeValue(null, "max");
                        } else if (name.equals("weather")) {
                            String iconName = parser.getAttributeValue(null, "icon");
                            publishProgress(50);
                            if (checkImageFileExistence(iconName)) {
                                // Image file exists locally, read it from disk
                                publishProgress(75);
                                weatherImage = readImageFromStorage(iconName);
                                publishProgress(100); // Indicate progress is completed
                            } else {
                                // Image file doesn't exist locally
                                publishProgress(75); // Indicate progress after downloading the weather image
                                weatherImage = downloadWeatherImage(iconName);
                                publishProgress(100); // Indicate progress is completed;
                                saveImageToStorage(iconName, weatherImage);

                            }
                        }
                        break;
                }
                eventType = parser.next();
            }

            publishProgress(100); //If no event is fired
            Log.i(ACTIVITY_NAME,"Parsing");
        }

        private Bitmap downloadWeatherImage(String iconName) {
            // Construct the URL for the weather image
            String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";

            try {
                // Create a URL object from the imageUrl string
                URL url = new URL(imageUrl);
                // Open an HttpURLConnection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                // Get the InputStream from the connection
                InputStream input = connection.getInputStream();

                // Decode the InputStream into a Bitmap using BitmapFactory
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                // Close the InputStream and disconnect the HttpURLConnection
                input.close();
                connection.disconnect();
                Log.i(ACTIVITY_NAME,"Downloading weather image");
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                // In case of an error, return a placeholder image or handle the error as needed
                return BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
            }
        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Update the ProgressBar based on the progress values
            if (values.length > 0) {
                int progress = values[0];
                weatherProgressBar.setVisibility(View.VISIBLE);
                weatherProgressBar.setProgress(progress);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // This method is called on the UI thread after doInBackground finishes
            // You can update UI elements with the fetched weather data here

            // Example: Update TextViews with minTemperature, maxTemperature, and currentTemperature
            // Set ImageView with the weatherImage
            weatherMinTempTxtView.setText("Min Temp: " + getMinTemperature());
            weatherMaxTempTxtView.setText("Max Temp: " + getMaxTemperature());
            weatherCurrTempTxtView.setText("Current Temp: " + getCurrentTemperature());
            weatherImgView.setImageBitmap(getWeatherImage());

            // Set the visibility of the progress bar to invisible
            weatherProgressBar.setVisibility(View.INVISIBLE);
        }

        // Add getter methods for the variables if needed
        public String getMinTemperature() {
            return minTemperature;
        }

        public String getMaxTemperature() {
            return maxTemperature;
        }

        public String getCurrentTemperature() {
            return currentTemperature;
        }

        public Bitmap getWeatherImage() {
            return weatherImage;
        }

    }
}