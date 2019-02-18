package com.example.ribes.anew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphActivity extends AppCompatActivity {

    String subject;
    GraphView graph;
    ProgressDialog progressDialog;

    int utMarks, firstMarks, finalMarks;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph = (GraphView) findViewById(R.id.graph);
        sessionManager = new SessionManager(getApplicationContext());

        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);

        progressDialog = new ProgressDialog(GraphActivity.this);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Loading ...");

        alert.setMessage("Select the subject of which you would like to see the results of");
        alert.setTitle("Select the subject");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final Spinner spinner = new Spinner(this);
        spinner.setPadding(50, 50, 50, 50);
        spinner.setBackgroundColor(getResources().getColor(R.color.iron));
        spinner.setBackground(getDrawable(android.R.drawable.btn_dropdown));

        final String[] subjects = {"OOAD", "Database", "Embedded", "OS", "Minor", "Economics", "AI"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjects);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        layout.addView(spinner);



        layout.setPadding(100, 50, 100, 50);
        alert.setView(layout); // Again this is a set method, not add

        alert.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                subject = spinner.getSelectedItem().toString();

                showDialog();

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.URL_EXAMS, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Exams", "Exams Response: " + response.toString());

                        hideDialog();

                        try {

                            JSONArray jsonArray = new JSONArray(response);



                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String exam_name = object.getString("Exam_Name");
                                String student_id = object.getString("Student_Id");
                                String subject_name = object.getString("Subject_Name");
                                String marks  = object.getString("Marks");

                                String id = sessionManager.getID();
                                if(student_id.equals(id) && subject_name.toUpperCase().equals(subject.toUpperCase()))
                                {
                                    if(exam_name.toUpperCase().equals("UT"))
                                    {
                                        utMarks = Integer.parseInt(marks);
                                    }
                                    else if(exam_name.toUpperCase().equals("FIRST"))
                                    {
                                        firstMarks = Integer.parseInt(marks);
                                    }
                                    else if(exam_name.toUpperCase().equals("FINAL"))
                                    {
                                        finalMarks = Integer.parseInt(marks);
                                    }
                                }



                            }
                            displayGraph();






//                    boolean error = jObj.getBoolean("error");




                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Exams", "Exams Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {


                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, "req_results");

            }
        });

        alert.setCancelable(false);
        alert.show();


    }

    public void displayGraph()
    {
//        Toast.makeText(GraphActivity.this, utMarks+" " + firstMarks + " " + finalMarks + "", Toast.LENGTH_SHORT).show();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, utMarks),
                new DataPoint(1, firstMarks),
                new DataPoint(2, finalMarks)
        });


        GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("Exams");
        gridLabelRenderer.setVerticalAxisTitle("Marks");

        graph.setTitle(subject);
        GridLabelRenderer glr = graph.getGridLabelRenderer();
        glr.setPadding(100); // should allow for 3 digits to fit on screen




// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setDataWidth(1d);
        series.setAnimated(true);
//series.setValuesOnTopSize(50);

        graph.addSeries(series);


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"UT", "First", "Final"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getViewport().setDrawBorder(true);

        graph.getViewport().setMinY(20);
        graph.getViewport().setMaxY((double)(100));


    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}



