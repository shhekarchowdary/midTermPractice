package com.arr.midtermpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mQualification, mCourse;
    ImageView mBuilding;
    RadioGroup mRadioGroup;
    RadioButton mDaysScholar,mHostler;
    CheckBox mStudyHours,mFood,mAC;
    SeekBar mNoOfStudents;
    EditText mName;
    Switch mDiscount;
    Button mEstimateFee;

    ArrayList<Course> mCourses = new ArrayList<>();
    ArrayList<String> mQualifications = new ArrayList<>();
    ArrayList<String> mCourseNames = new ArrayList<>();
    public static double basicFee,extraFee1,extraFee2,totalFee;
    public static int noOfStudents,discountRate;
    public static Course mCourseSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQualification = findViewById(R.id.spinner1);
        mCourse = findViewById(R.id.spinner2);
        mBuilding = findViewById(R.id.imageView);
        mRadioGroup = findViewById(R.id.radioGroup);
        mDaysScholar = findViewById(R.id.radioButton1);
        mHostler = findViewById(R.id.radioButton2);
        mStudyHours = findViewById(R.id.checkBox1);
        mFood = findViewById(R.id.checkBox2);
        mAC = findViewById(R.id.checkBox3);
        mNoOfStudents = findViewById(R.id.seekBar);
        mName = findViewById(R.id.etxtName);
        mDiscount = findViewById(R.id.switch1);
        mEstimateFee = findViewById(R.id.button);

        fillData();

        for(Course course:mCourses)
            mQualifications.add(course.getLevel());

        mQualifications = new ArrayList<>(new LinkedHashSet<>(mQualifications));
        ArrayAdapter qualificationAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,mQualifications);
        mQualification.setAdapter(qualificationAdapter);

        mQualification.setOnItemSelectedListener(this);
        mCourse.setOnItemSelectedListener(this);

        mDaysScholar.setOnClickListener(new RadioButtonEvents());
        mDaysScholar.setOnClickListener(new RadioButtonEvents());

        mStudyHours.setOnCheckedChangeListener(new CheckBoxEvents());
        mFood.setOnCheckedChangeListener(new CheckBoxEvents());
        mAC.setOnCheckedChangeListener(new CheckBoxEvents());

        mNoOfStudents.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                noOfStudents = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(mDiscount.isChecked()){
            discountRate = 5;
        }else
            discountRate = 0;

        mEstimateFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getBaseContext(),MainActivity2.class);
                startActivity(i);
                totalFee = (basicFee + extraFee1 + extraFee2)*noOfStudents;
                totalFee -= totalFee*(discountRate%100);
            }
        });
    }

    public void fillData(){
        mCourses.add(new Course("School","6th",1000.0));
        mCourses.add(new Course("School","7th",2000.0));
        mCourses.add(new Course("School","8th",3000.0));
        mCourses.add(new Course("School","9th",4000.0));
        mCourses.add(new Course("School","10th",5000.0));
        mCourses.add(new Course("College","ECE",10000.0));
        mCourses.add(new Course("College","EEE",9000.0));
        mCourses.add(new Course("College","CSE",15000.0));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinner1:
                String levelSelected = mQualifications.get(i).toLowerCase();
                mCourseNames.clear();
                for(Course course:mCourses)
                    if(course.getLevel().equalsIgnoreCase(levelSelected))
                        mCourseNames.add(course.getName());

                ArrayAdapter courseAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,mCourseNames);
                mCourse.setAdapter(courseAdapter);
                int res = getResources().getIdentifier(levelSelected,"drawable",getPackageName());
                mBuilding.setImageResource(res);
            break;
            case R.id.spinner2:
                String courseSelected = mCourseNames.get(i);
                for(Course course:mCourses)
                    if(course.getName().equalsIgnoreCase(courseSelected)){
                        mCourseSelected = course;
                        basicFee = course.getBasicFee();
                        break;
                    }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + adapterView.getId());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private class RadioButtonEvents implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            double fee =0;
            switch (view.getId()){
                case R.id.radioButton1:
                    fee += 500;
                break;
                case R.id.radioButton2:
                    fee += 1000;
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
            extraFee1 = fee;
        }
    }

    private class CheckBoxEvents implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            double fee = 0;
            switch (compoundButton.getId()){
                case R.id.checkBox1:
                    if(compoundButton.isChecked())
                        fee += 100;
                    else
                        fee -= 100;
                break;
                case R.id.checkBox2: case R.id.checkBox3:
                    if(compoundButton.isChecked())
                        fee += 500;
                    else
                        fee -= 500;
                 break;
                default:
                    throw new IllegalStateException("Unexpected value: " + compoundButton.getId());
            }
            extraFee2 = fee;
        }
    }
}