package com.example.official;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Register_ws_events extends AppCompatActivity {

    private String [] jan4WorkshopNames, jan5WorkshopNames;
    private int [] jan4WorkshopIds, jan5WorkshopIds;
    private int [] jan4WorkshopPrices, jan5WorkshopPrices;

    private int selectedJan4WorkshopId, selectedJan5WorkshopId;
    private String selectedJan4WorkshopName, selectedJan5WorkshopName;
    private int selectedJan4WorkshopPrice, selectedJan5WorkshopPrice;
    private boolean ticket;
    private int selectedCount;

    private int organizerId;

    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final int AUTO_COMPLETE_DELAY = 300;

    private Properties curParticipant;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ws_events);

        disableAll();
        disableGo();
        initConstants();
        reset();

        createMessageHandler();
        setupAutocompleteEditText();
        setupTicketCheckbox();

    }

    private void disableAll() {
        findViewById(R.id.btnJan4).setEnabled(false);
        findViewById(R.id.btnJan5).setEnabled(false);
        findViewById(R.id.ticketCheckBox).setEnabled(false);
    }

    private void enableAll() {
        findViewById(R.id.btnJan4).setEnabled(true);
        findViewById(R.id.btnJan5).setEnabled(true);
        findViewById(R.id.ticketCheckBox).setEnabled(true);
    }

    private void disableGo() {
        findViewById(R.id.btnGo).setEnabled(false);
    }

    private void enableGo() {
        findViewById(R.id.btnGo).setEnabled(true);
    }

    private void enableOrDisableGo(int d) {
        selectedCount += d;
        Log.d(Constants.LOGTAG,"BEFORE:" + (selectedCount-d) + " AFTER:"+selectedCount);
        if (selectedCount == 0) disableGo();
        else enableGo();
    }

    private void initConstants() {

        organizerId = getIntent().getIntExtra(Constants.INTENT_ORG_ID_NAME,-1);

        jan4WorkshopNames = getResources().getStringArray(R.array.jan4_workshop_names);
        jan4WorkshopIds = getResources().getIntArray(R.array.jan4_workshop_ids);
        jan4WorkshopPrices = getResources().getIntArray(R.array.jan4_workshop_prices);

        jan5WorkshopNames = getResources().getStringArray(R.array.jan5_workshop_names);
        jan5WorkshopIds = getResources().getIntArray(R.array.jan5_workshop_ids);
        jan5WorkshopPrices = getResources().getIntArray(R.array.jan5_workshop_prices);
    }

    private void reset() {

        ticket = false;
        selectedCount = 0;
        resetJan4Vars();
        resetJan5Vars();

        CheckBox checkBox = findViewById(R.id.ticketCheckBox);
        checkBox.setChecked(false);

        showChosenEvents();
    }


    private void resetJan4Vars() {

        selectedJan4WorkshopPrice = 0;
        selectedJan4WorkshopId = -1;
        selectedJan4WorkshopName = "";
    }

    private void resetJan5Vars() {

        selectedJan5WorkshopId = -1;
        selectedJan5WorkshopName = "";
        selectedJan5WorkshopPrice = 0;
    }


    private void createMessageHandler() {

        handler = new Handler(new Handler.Callback() {

            public boolean handleMessage(Message message) {

                if (message.what == TRIGGER_AUTO_COMPLETE) {

                    PostResponseHandler handler = new PostResponseHandler() {
                        @Override
                        public void handlePostResponse(String response) {

                            try {

                                String emails = Utils.getDataJsonString(getApplicationContext(),response);
                                List<String> emailStrings = new ArrayList<>();

                                if (emails.length() > 0) {

                                    List<Properties> tmp = Utils.getJSONObjects(emails);

                                    for (Properties props : tmp) {
                                        String emailString = (String) props.get(Constants.DB_PARTICIPANT_EMAIL_NAME);
                                        emailStrings.add(emailString);
                                    }

                                }

                                autoSuggestAdapter.setData(emailStrings);
                                autoSuggestAdapter.notifyDataSetChanged();

                            } catch (Exception e) {
                                Log.e(Constants.LOGTAG,"Exception",e);
                            }
                        }
                    };

                    EditText editText = findViewById(R.id.participantEmail);
                    String prefix = editText.getText().toString();

                    if (prefix.length() > 0) {

                        ProgressBar progressBar = findViewById(R.id.pProgressBar);
                        PostRequest request = new PostRequest(handler, progressBar, Constants.SERVICE_AUTOFILL);

                        Properties postParams = new Properties();

                        postParams.put(Constants.REQUEST_PREFIX_NAME, prefix);

                        request.execute(postParams);

                    }

                }
                return false;
            }
        });
    }


    private void setupAutocompleteEditText() {

        autoSuggestAdapter = new AutoSuggestAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line);
        autoSuggestAdapter.setData(new ArrayList<String>());

        final AppCompatAutoCompleteTextView editText = findViewById(R.id.participantEmail);
        editText.setAdapter(autoSuggestAdapter);

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = autoSuggestAdapter.getItem(position);
                editText.setText(selectedItem);

                PostResponseHandler handler = new PostResponseHandler() {
                    @Override
                    public void handlePostResponse(String response) {
                        try {
                            String json = Utils.getDataJsonString(getApplicationContext(),response);
                            Properties obj = Utils.getJSONObject(new JSONObject(json));
                            String participantDetailsJson = (String)obj.get(Constants.RESPONSE_PARTICIPANT_DETAILS_NAME);
                            String eventListJson = (String)obj.get(Constants.RESPONSE_EVENT_LIST_NAME);

                            showParticipantDetails(participantDetailsJson);
                            enableUnregisteredEvents(eventListJson);
                            reset();

                        } catch (Exception e) {
                            Log.e(Constants.LOGTAG,"Exception",e);
                        }
                    }
                };

                ProgressBar progressBar = findViewById(R.id.pProgressBar);
                PostRequest request = new PostRequest(handler,progressBar,Constants.SERVICE_GET_DETAILS);
                Properties postParams = new Properties();
                postParams.put(Constants.REQUEST_ID_NAME,selectedItem);
                request.execute(postParams);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findViewById(R.id.btnGo).setEnabled(false);
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
                curParticipant = null;
                disableAll();
                reset();
                disableGo();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    private void showParticipantDetails(String json) throws Exception {

        curParticipant = Utils.getJSONObject(new JSONObject(json));

        String name = (String)curParticipant.get(Constants.DB_PARTICIPANT_NAME_NAME);
        String college = (String)curParticipant.get(Constants.DB_PARTICIPANT_COLLEGE_NAME);

        TextView textView = findViewById(R.id.participantDetails);
        textView.setText(name + " (" + college + ")");
    }

    private void enableUnregisteredEvents(String json) throws Exception {

        List<Properties> events = Utils.getJSONObjects(json);
        enableAll();

        for (Properties event : events) {

            String dateString = (String)event.get(Constants.DB_EVENT_DATE_NAME);
            int day = Integer.parseInt(dateString.substring(0,2));
            if (Constants.ROLLING_EVENT_DATE_VALUE == day) findViewById(R.id.ticketCheckBox).setEnabled(false);
            else if (day == 4) findViewById(R.id.btnJan4).setEnabled(false);
            else if (day == 5) findViewById(R.id.btnJan5).setEnabled(false);
        }

    }

    private void setupTicketCheckbox() {
        CheckBox checkBox = findViewById(R.id.ticketCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                ticket = checked;
                if (ticket) enableOrDisableGo(1);
                else enableOrDisableGo(-1);
                showChosenEvents();
            }
        });
    }


    public void jan4OnClick(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register_ws_events.this);
        mBuilder.setTitle(R.string.jan4_workshops_title);
        mBuilder.setSingleChoiceItems(jan4WorkshopNames, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                setJan4Vars(position);
                showChosenEvents();
                enableOrDisableGo(1);
            }
        });

        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                resetJan4Vars();
                showChosenEvents();
                enableOrDisableGo(-1);
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    private void setJan4Vars(int position) {

        selectedJan4WorkshopId = jan4WorkshopIds[position];
        selectedJan4WorkshopName = jan4WorkshopNames[position];
        selectedJan4WorkshopPrice = jan4WorkshopPrices[position];
    }

    private void setJan5Vars(int position) {

        selectedJan5WorkshopId = jan5WorkshopIds[position];
        selectedJan5WorkshopName = jan5WorkshopNames[position];
        selectedJan5WorkshopPrice = jan5WorkshopPrices[position];
    }

    private void showChosenEvents() {

        String tmp = "";
        int sum = 0;

        if (selectedJan4WorkshopId != -1) {
            tmp += selectedJan4WorkshopName + " (" + selectedJan4WorkshopPrice + ")\n";
            sum += selectedJan4WorkshopPrice;
        }
        if (selectedJan5WorkshopId != -1) {
            tmp += selectedJan5WorkshopName + " (" + selectedJan5WorkshopPrice + ")\n";
            sum += selectedJan5WorkshopPrice;
        }
        if (ticket) {
            tmp += "Event Ticket (";
            String college = (String)curParticipant.get(Constants.DB_PARTICIPANT_COLLEGE_NAME);
            int price;
            if ("MIT".equals(college)) price = 150;
            else price = 250;
            tmp += price + ")\n";
            sum += price;
        }

        if (tmp.length() > 0) {
            tmp += "Total (" + sum + ")";
        }

        TextView textView = findViewById(R.id.tvItemSelected);
        textView.setText(tmp);
    }

    public void jan5OnClick(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register_ws_events.this);
        mBuilder.setTitle(R.string.jan5_workshops_title);
        mBuilder.setSingleChoiceItems(jan5WorkshopNames, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                setJan5Vars(position);
                showChosenEvents();
                enableOrDisableGo(1);
            }
        });

        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                resetJan5Vars();
                showChosenEvents();
                enableOrDisableGo(-1);
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    @Override //on scan qr activity result
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Properties postParams = getWorkshopIdPostParams();
                String ticketId = data.getStringExtra(Constants.INTENT_QR_CODE_NAME);
                postParams.put(Constants.REQUEST_TICKET_ID_NAME,ticketId);
                sendRequest(postParams);
            }
        }
    }

    public void goOnClick(View view) {

        if (ticket) {

            Intent intent = new Intent(getApplicationContext(),scan_code_actual.class);
            intent.putExtra(Constants.INTENT_MESSAGE_NAME,"Scan new ID-card");
            startActivityForResult(intent,1);

        } else {

            sendRequest(getWorkshopIdPostParams());
        }

    }

    private void sendRequest(Properties postParams) {

        String participantEmail = (String)curParticipant.get(Constants.DB_PARTICIPANT_EMAIL_NAME);

        postParams.put(Constants.REQUEST_PARTICIPANT_EMAIL_NAME,participantEmail);
        postParams.put(Constants.REQUEST_ORG_ID_NAME,organizerId);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        PostRequest request = new PostRequest(getResponseHandler(),progressBar,Constants.SERVICE_PUT_DETAILS);
        request.execute(postParams);
    }

    private PostResponseHandler getResponseHandler() {

        return new PostResponseHandler() {
            @Override
            public void handlePostResponse(String response) {

                try {

                    Properties obj = Utils.getJSONObject(new JSONObject(response));
                    String status = (String)obj.get(Constants.RESPONSE_STATUS_NAME);
                    String message = (String)obj.get(Constants.RESPONSE_MESSAGE_NAME);

                    Toast.makeText(getApplicationContext(),status + "! " + message,Toast.LENGTH_SHORT).show();
                    Log.d(Constants.LOGTAG,"Message:"+message+",Status:"+status);
                    disableAll();
                    disableGo();

                    EditText editText = findViewById(R.id.participantEmail);
                    editText.setText("");

                    TextView textView = findViewById(R.id.participantDetails);
                    textView.setText("");

                    reset();

                } catch (Exception e) {

                    Log.e(Constants.LOGTAG,"Exception",e);
                }

            }
        };
    }

    private Properties getWorkshopIdPostParams() {

        Properties postParams = new Properties();

        if (selectedJan4WorkshopId != -1) {
            postParams.put(Constants.REQUEST_JAN4_WORKSHOP_ID_NAME,selectedJan4WorkshopId);
        }

        if (selectedJan5WorkshopId != -1) {
            postParams.put(Constants.REQUEST_JAN5_WORKSHOP_ID_NAME,selectedJan5WorkshopId);
        }

        return postParams;

    }


}
