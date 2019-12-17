package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class WorkshopsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops_view);

        String participantJsonString = getIntent().getStringExtra(Constants.INTENT_PARTICIPANT_DETAILS_NAME);
        Log.d(Constants.LOGTAG,participantJsonString);
        showParticipantDetails(participantJsonString);

        String workshopsJsonString = getIntent().getStringExtra(Constants.INTENT_WORKSHOP_LIST_NAME);
        Log.d(Constants.LOGTAG,workshopsJsonString);
        showWorkshopDetails(workshopsJsonString);

    }

    private void showParticipantDetails(String jsonString) {

        Properties participant = null;

        Log.d(Constants.LOGTAG,"JSONString: " + jsonString);

        try {

            participant = Utils.getJSONObject(new JSONObject(jsonString));

            View parentView = findViewById(R.id.participantDetails);

            String name = participant.get(Constants.DB_PARTICIPANT_NAME_KEY).toString();
            TextView nameView = parentView.findViewById(R.id.name);
            nameView.setText(name);

            String college = participant.get(Constants.DB_PARTICIPANT_COLLEGE_KEY).toString();
            TextView collegeView = parentView.findViewById(R.id.college);
            collegeView.setText(college);

            String email = participant.get(Constants.DB_PARTICIPANT_EMAIL_KEY).toString();
            TextView emailView = parentView.findViewById(R.id.email);
            emailView.setText(email);


        } catch (Exception e) {
            Log.e("CarteBlanche","Exception",e);
        }

    }

    private void showWorkshopDetails(String jsonString) {

        Log.d(Constants.LOGTAG,"JSONString: " + jsonString);

        List <WorkshopListItem> workshopsList = null;

        try {
            workshopsList = getWorkshopsList(jsonString);
        } catch (Exception e) {
            Log.e("CarteBlanche","Exception",e);
        }

        WorkshopsListAdapter workshopsListAdapter = new WorkshopsListAdapter(this,R.layout.workshop_list_item,workshopsList);
        ListView workshopsListView = findViewById(R.id.workshopsListView);
        workshopsListView.setAdapter(workshopsListAdapter);
    }

    private List<WorkshopListItem> getWorkshopsList(String jsonString) throws Exception {

        List<Properties> workshops = Utils.getJSONObjects(jsonString);
        List <WorkshopListItem> workshopsList = new LinkedList<>();

        for (Properties workshop : workshops) {

            String name = (String)workshop.get("workshopName");
            String orderID = (String)workshop.get("orderID");
            String status = (String)workshop.get("status");
            WorkshopListItem model = new WorkshopListItem(name,orderID,status);
            workshopsList.add(model);
        }

        return workshopsList;

    }
}
