package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class EventsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops_view);

        String participantJsonString = getIntent().getStringExtra(Constants.INTENT_PARTICIPANT_DETAILS_NAME);
        Log.d(Constants.LOGTAG,participantJsonString);
        showParticipantDetails(participantJsonString);

        String workshopsJsonString = getIntent().getStringExtra(Constants.INTENT_WORKSHOP_LIST_NAME);
        Log.d(Constants.LOGTAG,workshopsJsonString);
        showEventsList(workshopsJsonString);

    }

    private void showParticipantDetails(String jsonString) {

        Properties participant = null;

        Log.d(Constants.LOGTAG,"JSONString: " + jsonString);

        try {

            participant = Utils.getJSONObject(new JSONObject(jsonString));

            View parentView = findViewById(R.id.participantDetails);

            String name = participant.get(Constants.DB_PARTICIPANT_NAME_NAME).toString();
            TextView nameView = parentView.findViewById(R.id.name);
            nameView.setText(name);

            String college = participant.get(Constants.DB_PARTICIPANT_COLLEGE_NAME).toString();
            TextView collegeView = parentView.findViewById(R.id.college);
            collegeView.setText(college);

            String email = participant.get(Constants.DB_PARTICIPANT_EMAIL_NAME).toString();
            TextView emailView = parentView.findViewById(R.id.email);
            emailView.setText(email);


        } catch (Exception e) {
            Log.e("CarteBlanche","Exception",e);
        }

    }

    private void showEventsList(String jsonString) {

        Log.d(Constants.LOGTAG,"JSONString: " + jsonString);

        List <EventListItem> workshopsList = null;

        try {
            workshopsList = getEventsList(jsonString);
        } catch (Exception e) {
            Log.e("CarteBlanche","Exception",e);
        }

        EventsListAdapter workshopsListAdapter = new EventsListAdapter(this,R.layout.workshop_list_item,workshopsList);
        ListView workshopsListView = findViewById(R.id.workshopsListView);
        workshopsListView.setAdapter(workshopsListAdapter);
    }

    private List<EventListItem> getEventsList(String jsonString) throws Exception {

        List<Properties> events = Utils.getJSONObjects(jsonString);
        List <EventListItem> eventsList = new LinkedList<>();

        for (Properties event : events) {

            String name = (String)event.get(Constants.DB_EVENT_NAME_NAME);
            String orderID = (String)event.get(Constants.DB_ORDER_ID_NAME);
            String date = (String)event.get(Constants.DB_EVENT_DATE_NAME);

            EventListItem model = new EventListItem(name,orderID,date);
            eventsList.add(model);
        }

        return eventsList;

    }
}
