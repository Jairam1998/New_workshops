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
        Log.d(Constants.LOGTAG,"PARTICIPANTJSON:"+participantJsonString);
        boolean flag = showParticipantDetails(participantJsonString);

        if (flag) {

            String eventsJsonString = getIntent().getStringExtra(Constants.INTENT_EVENT_LIST_NAME);
            Log.d(Constants.LOGTAG, eventsJsonString);
            Log.d(Constants.LOGTAG, "EVENTSJSON:" + eventsJsonString);
            showEventsList(eventsJsonString);
        }

    }

    private boolean showParticipantDetails(String jsonString) {

        Properties participant;

        Log.d(Constants.LOGTAG,"JSONString: " + jsonString);

        try {

            if (Constants.RESPONSE_NULL_VALUE.equals(jsonString)) invalidId();
            else {

                participant = Utils.getJSONObject(new JSONObject(jsonString));

                if (participant.size() > 0) {

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

                    return true;

                } else {

                    invalidId();

                }
            }


        } catch (Exception e) {
            Log.e(Constants.LOGTAG,"Exception",e);
        }

        return false;

    }

    private void invalidId() {

        View parentView = findViewById(R.id.participantDetails);

        TextView nameView = parentView.findViewById(R.id.name);
        nameView.setText("Invalid ID given");
        ((TextView)parentView.findViewById(R.id.college)).setText("");
        ((TextView)parentView.findViewById(R.id.email)).setText("");
    }

    private void showEventsList(String jsonString) {

        Log.d(Constants.LOGTAG,"JSONString: " + jsonString);

        List <EventListItem> eventsList = null;

        try {
            eventsList = getEventsList(jsonString);
        } catch (Exception e) {
            Log.e(Constants.LOGTAG,"Exception",e);
        }

        if (eventsList.size() == 0) {

            findViewById(R.id.no_events_registered).setVisibility(View.VISIBLE);

        } else {

            EventsListAdapter eventsListAdapter = new EventsListAdapter(this, R.layout.workshop_list_item, eventsList);
            ListView workshopsListView = findViewById(R.id.workshopsListView);
            workshopsListView.setAdapter(eventsListAdapter);
        }
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
