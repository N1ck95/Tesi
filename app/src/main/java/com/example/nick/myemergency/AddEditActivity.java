package com.example.nick.myemergency;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddEditActivity extends Activity
        implements OnKeyListener {

    private TextView textViewInfo;
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText CFEditText;
    private EditText anniEditText;
    private EditText telephoneEditText;
    private EditText contact1EditText;
    private EditText contact2EditText;
    private RadioGroup radioSendMessages;

    private MyEmergencyDB db;
    private boolean editMode;
    private boolean firstTime;
    private Information information;

    // define messages constants
    private final int MESSAGES_NONE = 0;
    private final int MESSAGES_NORMAL = 1;
    private final int MESSAGES_WHATSAPP = 2;

    // set up preferences
    private SharedPreferences prefs;
    private Boolean rememberPhoneNumber = true;
    private int messages_type = MESSAGES_NONE;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        // get references to widgets
        textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        CFEditText = (EditText) findViewById(R.id.CFEditText);
        anniEditText = (EditText) findViewById(R.id.anniEditText);
        telephoneEditText = (EditText) findViewById(R.id.telephoneEditText);
        radioSendMessages = (RadioGroup) findViewById(R.id.radioSendMessages);
        contact1EditText = (EditText) findViewById(R.id.contact1EditText);
        contact2EditText = (EditText) findViewById(R.id.contact2EditText);

        // set listeners
        nameEditText.setOnKeyListener(this);
        surnameEditText.setOnKeyListener(this);
        CFEditText.setOnKeyListener(this);
        anniEditText.setOnKeyListener(this);
        telephoneEditText.setOnKeyListener(this);

        nameEditText.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (nameEditText.getText().toString().length() <= 0) {
                    nameEditText.setError("Questo campo non può essere vuoto");
                } else {
                    nameEditText.setError(null);
                }
            }
        });

        surnameEditText.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (surnameEditText.getText().toString().length() <= 0) {
                    surnameEditText.setError("Questo campo non può essere vuoto");
                } else {
                   surnameEditText.setError(null);
                }
            }
        });

        CFEditText.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                String pattern = "^[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$";
                Pattern regEx = Pattern.compile(pattern);
                Matcher m = regEx.matcher(CFEditText.getText().toString());
                if (CFEditText.getText().toString().length() <= 0) {
                    CFEditText.setError("Questo campo non può essere vuoto");
                } else if (!m.matches()) {
                    CFEditText.setError("CF non rispettato");
                } else {
                    CFEditText.setError(null);
                }
            }
        });

        anniEditText.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                String pattern = "((((0[1-9]|1[0-9]|2[0-8])(\\/)(0[1-9]|1[0-2]))|((2[9]|3[0])(\\/)(0[469]|11))|((3[1])(\\/)(0[13578]|1[02])))(\\/)(19([0-9][0-9])|20(0[0-9]|1[0-5])))|((29)(\\/)(02)(\\/)(19(0[048]|1[26]|2[048]|3[26]|4[048]|5[26]|6[048]|7[26]|8[048]|9[26])|20(0[048]|1[26])))";
                Pattern regEx = Pattern.compile(pattern);
                Matcher m = regEx.matcher(anniEditText.getText().toString());
                if (anniEditText.getText().toString().length() <= 0) {
                    anniEditText.setError("Questo campo non può essere vuoto");
                } else if (!m.matches()) {
                    anniEditText.setError("Formato da rispettare gg/mm/aaaa");
                } else {
                    anniEditText.setError(null);
                }
            }
        });

        telephoneEditText.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (telephoneEditText.getText().toString().length() <= 0) {
                    telephoneEditText.setError("Questo campo non può essere vuoto");
                } else if (telephoneEditText.getText().toString().length() >10) {
                    telephoneEditText.setError("formato numero telefonico non rispettato");
                } else {
                    telephoneEditText.setError(null);
                }
            }
        });

        // get the database object
        db = new MyEmergencyDB(this);

        // get edit mode from intent
        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode", false);
        firstTime = intent.getBooleanExtra("first", false);
        if (!editMode) {
            if (firstTime) {
                textViewInfo.setText("Inserisci i tuoi dati per poterli avere a disposizione nel momento del bisogno");
            } else {
                textViewInfo.setText("Inserisci i dati di qualcuno che potrebbe avere bisogno");
            }
        } else {
            textViewInfo.setText("Aggiornamento dei dati");
        }

        rememberPhoneNumber = prefs.getBoolean("pref_remember_phone_number", true);
        messages_type = Integer.parseInt(prefs.getString("pref_messages", "0"));
        if (!firstTime) {
            if (!rememberPhoneNumber) {
                telephoneEditText.setClickable(true);
                telephoneEditText.setCursorVisible(true);
                telephoneEditText.setFocusable(true);
                telephoneEditText.setFocusableInTouchMode(true);
            } else {
                telephoneEditText.setText(db.getInformation(1).getTelephone());
            }

        } else {
            telephoneEditText.setClickable(true);
            telephoneEditText.setCursorVisible(true);
            telephoneEditText.setFocusable(true);
            telephoneEditText.setFocusableInTouchMode(true);
        }
        ((RadioButton)radioSendMessages.getChildAt(messages_type)).setChecked(true);
        for (int i = 0; i < radioSendMessages.getChildCount(); i++) {
            radioSendMessages.getChildAt(i).setEnabled(false);
        }

        if (messages_type == MESSAGES_NONE) {
            contact1EditText.setText(null);
            contact2EditText.setText(null);
            contact1EditText.setVisibility(View.INVISIBLE);
            contact2EditText.setVisibility(View.INVISIBLE);
        } else if (messages_type == MESSAGES_WHATSAPP) {
            contact1EditText.setVisibility(View.VISIBLE);
            contact2EditText.setText(null);
            contact2EditText.setVisibility(View.INVISIBLE);
        } else {
            contact1EditText.setVisibility(View.VISIBLE);
            contact2EditText.setVisibility(View.VISIBLE);
            contact1EditText.setText(null);
            contact2EditText.setText(null);
        }

        // if editing
        if (editMode) {
            // get information
            long informationId = intent.getLongExtra("informationId", -1);
            information = db.getInformation(informationId);

            // update UI with task
            nameEditText.setText(information.getName());
            surnameEditText.setText(information.getSurname());
            CFEditText.setText(information.getCodiceFiscale());
            anniEditText.setText(information.getDate_of_birth());
            telephoneEditText.setClickable(true);
            telephoneEditText.setCursorVisible(true);
            telephoneEditText.setFocusable(true);
            telephoneEditText.setFocusableInTouchMode(true);
            telephoneEditText.setText(information.getTelephone());
            contact1EditText.setText(information.getContact1());
            contact2EditText.setText(information.getContact2());
        }
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get preferences
        rememberPhoneNumber = prefs.getBoolean("pref_remember_phone_number", true);
        messages_type = Integer.parseInt(prefs.getString("pref_message", "0"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSave:
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String CF = CFEditText.getText().toString();
                String date_of_birth = anniEditText.getText().toString();
                String telephone = telephoneEditText.getText().toString();

                // if no informations, exit method

                if (name.equals("")) {
                    nameEditText.setError("Questo campo non può essere vuoto");
                }
                if (surname.equals("")) {
                    surnameEditText.setError("Questo campo non può essere vuoto");
                }
                if (CF.equals("")) {
                    CFEditText.setError("Questo campo non può essere vuoto");
                }
                if (date_of_birth.equals("")) {
                    anniEditText.setError("Questo campo non può essere vuoto");
                }
                if (telephone.equals("")) {
                    telephoneEditText.setError("Questo campo non può essere vuoto");
                }
                if ((nameEditText.getError() == null && nameEditText.getText().length() != 0) &&
                        (surnameEditText.getError() == null && surnameEditText.getText().length() != 0) &&
                        (CFEditText.getError() == null && CFEditText.getText().length() != 0) &&
                        (anniEditText.getError() == null && anniEditText.getText().length() != 0) &&
                        (telephoneEditText.getError() == null && telephoneEditText.getText().length() != 0)) {
                        saveToDB();
                        if (!firstTime) {
                            this.finish();
                        } else {
                            Intent intent = new Intent(this, FistLaunch.class);
                            startActivity(intent);
                            finish();
                        }
                }
                break;
            case R.id.menuCancel:
                    this.finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveToDB() {
        // get data from widgets
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String CF = CFEditText.getText().toString();
        String date_of_birth = anniEditText.getText().toString();
        String telephone = telephoneEditText.getText().toString();
        String contact1 = contact1EditText.getText().toString();
        String contact2 = contact2EditText.getText().toString();

        // if add mode, create new information
        if (!editMode) {
            information = new Information();
        }

        // put data in information
        information.setName(name);
        information.setSurname(surname);
        information.setCodiceFiscale(CF);
        information.setDate_of_birth(date_of_birth);
        information.setTelephone(telephone);
        information.setContact1(contact1);
        information.setContact2(contact2);


        // update or insert task
        if (editMode) {
            db.updateInformation(information);
        }
        else {
            db.insertInformation(information);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            // hide the soft Keyboard
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;
    }



    @Override
    public void onBackPressed() {
    }
}
