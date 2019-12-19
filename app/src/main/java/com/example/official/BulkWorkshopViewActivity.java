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

public class BulkWorkshopViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_workshop_view);

        String workshopDetailsJson = getIntent().getStringExtra(Constants.INTENT_WORKSHOP_DETAILS_NAME);
        showWorkshopDetails(workshopDetailsJson);

        String participantsListJson = getIntent().getStringExtra(Constants.INTENT_PARTICIPANT_LIST_NAME);
        showParticipantsList(participantsListJson);
    }

    private void showWorkshopDetails(String json) {

        try {

            Properties obj = Utils.getJSONObject(new JSONObject(json));

            View workshopView = findViewById(R.id.workshopDetails);

            String name = (String)obj.get(Constants.DB_WORKSHOP_NAME_NAME);
            TextView textView = workshopView.findViewById(R.id.workshopName);
            textView.setText(name);

            String date = (String)obj.get(Constants.DB_WORKSHOP_DATE_NAME);
            textView = workshopView.findViewById(R.id.workshopDate);
            textView.setText(date);


        } catch (Exception e) {
            Log.d(Constants.LOGTAG,"Exception",e);
        }
    }

    private void showParticipantsList(String json) {

        try {

            List<Properties> obj = Utils.getJSONObjects(json);
            List<ParticipantListItem> items = new LinkedList<>();

            for (Properties props : obj) {

                String name = (String)props.get(Constants.DB_PARTICIPANT_NAME_NAME);
                String email = (String)props.get(Constants.DB_PARTICIPANT_EMAIL_NAME);
                String college = (String)props.get(Constants.DB_PARTICIPANT_COLLEGE_NAME);
                String id = (String)props.get(Constants.DB_PARTICIPANT_ID_NAME);

                ParticipantListItem item = new ParticipantListItem(name,email,id,college);
                items.add(item);
            }

            ParticipantListAdapter adapter = new ParticipantListAdapter(getApplicationContext(),R.layout.participant_list_item,items);
            ListView listView = findViewById(R.id.participantsListView);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            Log.d(Constants.LOGTAG,"Exception",e);
        }
    }
}
