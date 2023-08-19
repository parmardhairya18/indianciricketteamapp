package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    EditText name, email, contact, dateOfBirth;
    Button editProfile,submit;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Calendar calendar;

    RadioButton male,female;
    RadioGroup gender;

    Spinner spinner;
    //String[] cityArray = {"Ahmedabad","Vadodara","Surat","Rajkot"};
    ArrayList<String> arrayList;

    SQLiteDatabase db;

    String sGender,sCity;

    SharedPreferences sp;

    boolean isSelect = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        contact = findViewById(R.id.profile_contact);
        dateOfBirth = findViewById(R.id.profile_date_of_birth);

        spinner = findViewById(R.id.profile_spinner);

        arrayList = new ArrayList<>();
        arrayList.add("Ahmedabad");
        arrayList.add("Anand");
        arrayList.add("Vadodara");
        arrayList.add("Test");
        arrayList.add("Surt");
        arrayList.add("Rajkot");

        arrayList.remove(3);
        arrayList.set(3,"Surat");

        arrayList.add(0,"Gandhinagar");

        ArrayAdapter adapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String selectedCity = cityArray[i];
                sCity = arrayList.get(i);
                new CommonMethod(ProfileActivity.this,sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        male = findViewById(R.id.profile_male);
        female = findViewById(R.id.profile_female);

        gender = findViewById(R.id.profile_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                new CommonMethod(ProfileActivity.this,radioButton.getText().toString());
                sGender = radioButton.getText().toString();
            }
        });

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelect) {
                    //new DatePickerDialog(ProfileActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

                    DatePickerDialog pickerDialog = new DatePickerDialog(ProfileActivity.this, dateClick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    //pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    pickerDialog.show();
                }
            }
        });

        editProfile = findViewById(R.id.profile_button);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelect = true;
                setData(isSelect);
            }
        });

        submit = findViewById(R.id.profile_submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Name Required");
                } else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (dateOfBirth.getText().toString().trim().equals("")) {
                    new CommonMethod(ProfileActivity.this, "Please Select Date Of Birth");
                }
                else if(gender.getCheckedRadioButtonId() == -1){
                    new CommonMethod(ProfileActivity.this,"Please Select Gender");
                }
                else {
                    /*String selectQuery = "SELECT * FROM RECORD WHERE EMAIL='" + email.getText().toString() + "' OR CONTACT='" + contact.getText().toString() + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        new CommonMethod(ProfileActivity.this,"Email Id/Contact No. Already Regsitered");
                    } else {
                        String insertQuery = "INSERT INTO RECORD VALUES ('" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "','" + password.getText().toString() + "','" + dateOfBirth.getText().toString() + "','" + sGender + "','" + sCity + "')";
                        db.execSQL(insertQuery);
                        new CommonMethod(ProfileActivity.this, "Signup Successfully");
                        onBackPressed();
                    }*/
                    String updateQuery = "UPDATE RECORD SET NAME='"+name.getText().toString()+"',EMAIL='"+email.getText().toString()+"',DOB='"+dateOfBirth.getText().toString()+"',GENDER='"+sGender+"',CITY='"+sCity+"' WHERE CONTACT='"+contact.getText().toString()+"'";
                    db.execSQL(updateQuery);
                    new CommonMethod(ProfileActivity.this,"Profile Update Successfully");

                    sp.edit().putString(ConstantData.NAME,name.getText().toString()).commit();
                    sp.edit().putString(ConstantData.EMAIL,email.getText().toString()).commit();
                    sp.edit().putString(ConstantData.DOB,dateOfBirth.getText().toString()).commit();
                    sp.edit().putString(ConstantData.GENDER,sGender).commit();
                    sp.edit().putString(ConstantData.CITY,sCity).commit();

                    isSelect = false;
                    setData(isSelect);

                }
            }
        });

        setData(isSelect);

    }

    private void setData(boolean isSelect) {
        name.setEnabled(isSelect);
        email.setEnabled(isSelect);
        contact.setEnabled(false);
        male.setEnabled(isSelect);
        female.setEnabled(isSelect);
        gender.setEnabled(isSelect);
        spinner.setEnabled(isSelect);

        if(isSelect){
            editProfile.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        }
        else{
            editProfile.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }

        name.setText(sp.getString(ConstantData.NAME,""));
        email.setText(sp.getString(ConstantData.EMAIL,""));
        contact.setText(sp.getString(ConstantData.CONTACT,""));
        dateOfBirth.setText(sp.getString(ConstantData.DOB,""));

        sGender = sp.getString(ConstantData.GENDER,"");
        if(sGender.equalsIgnoreCase(getResources().getString(R.string.male))){
            male.setChecked(true);
        }
        else if(sGender.equalsIgnoreCase(getResources().getString(R.string.female))){
            female.setChecked(true);
        }
        else{

        }

        sCity = sp.getString(ConstantData.CITY,"");
        int iCityPosition = 0;
        for(int i=0;i<arrayList.size();i++){
            if(sCity.equalsIgnoreCase(arrayList.get(i))){
                iCityPosition = i;
                break;
            }
        }
        spinner.setSelection(iCityPosition);
    }
}