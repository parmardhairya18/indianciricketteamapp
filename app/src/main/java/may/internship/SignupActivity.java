package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

public class SignupActivity extends AppCompatActivity {

    EditText name, email, contact, password, confirmPassword, dateOfBirth;
    Button signup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Calendar calendar;

    //RadioButton male,female;
    RadioGroup gender;

    Spinner spinner;
    ArrayList<String> arrayList;

    SQLiteDatabase db;

    String sGender,sCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_password);
        dateOfBirth = findViewById(R.id.signup_date_of_birth);

        spinner = findViewById(R.id.signup_spinner);

        arrayList = new ArrayList<>();
        arrayList.add("Ahmedabad");
        arrayList.add("Anand");
        arrayList.add("Vadodara");
        arrayList.add("Test");
        arrayList.add("Surat");
        arrayList.add("Rajkot");

        arrayList.remove(3);
        arrayList.set(3,"Surat");

        arrayList.add(0,"Gandhinagar");

        ArrayAdapter adapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String selectedCity = cityArray[i];
                sCity = arrayList.get(i);
                new CommonMethod(SignupActivity.this,sCity);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*male = findViewById(R.id.signup_male);
        female = findViewById(R.id.signup_female);*/

        gender = findViewById(R.id.signup_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                new CommonMethod(SignupActivity.this,radioButton.getText().toString());
                sGender = radioButton.getText().toString();
            }
        });
        /*male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SignupActivity.this,male.getText().toString());
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SignupActivity.this,female.getText().toString());
            }
        });*/

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
                //new DatePickerDialog(SignupActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

                DatePickerDialog pickerDialog = new DatePickerDialog(SignupActivity.this, dateClick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                pickerDialog.show();

            }
        });

        /*name.setText("Test");
        email.setText("test@gmail.com");
        contact.setText("9090901234");
        password.setText("123123");
        confirmPassword.setText("123123");
        dateOfBirth.setText("23-05-2023");*/

        signup = findViewById(R.id.signup_button);

        signup.setOnClickListener(new View.OnClickListener() {
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
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().length() < 6) {
                    password.setError("Min.6 Character Password Required");
                } else if (confirmPassword.getText().toString().trim().equals("")) {
                    confirmPassword.setError("Confirm Password Required");
                } else if (confirmPassword.getText().toString().length() < 6) {
                    confirmPassword.setError("Min.6 Character Confirm Password Required");
                } else if (!confirmPassword.getText().toString().matches(password.getText().toString())) {
                    confirmPassword.setError("Confirm Password Does Not Match");
                } else if (dateOfBirth.getText().toString().trim().equals("")) {
                    //dateOfBirth.setError("Please Select Date Of Birth");
                    new CommonMethod(SignupActivity.this, "Please Select Date Of Birth");
                }
                /*else if(male.isChecked() == false && female.isChecked() == false){
                    new CommonMethod(SignupActivity.this,"Please Select Gender");
                }*/
                else if(gender.getCheckedRadioButtonId() == -1){
                    new CommonMethod(SignupActivity.this,"Please Select Gender");
                }
                else {
                    String selectQuery = "SELECT * FROM RECORD WHERE EMAIL='" + email.getText().toString() + "' OR CONTACT='" + contact.getText().toString() + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        new CommonMethod(SignupActivity.this,"Email Id/Contact No. Already Regsitered");
                    } else {
                        String insertQuery = "INSERT INTO RECORD VALUES ('" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "','" + password.getText().toString() + "','" + dateOfBirth.getText().toString() + "','" + sGender + "','" + sCity + "')";
                        db.execSQL(insertQuery);
                        new CommonMethod(SignupActivity.this, "Signup Successfully");
                        onBackPressed();
                    }
                }
            }
        });

    }
}