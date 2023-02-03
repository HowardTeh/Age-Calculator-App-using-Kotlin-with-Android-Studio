package com.example.agecalculatorapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    /*
    - declare global textview components to be used in different functions
    - apply null safe concepts to each component
    */
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMins : TextView? = null
    private var tvAgeInDays : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assign the declared components with the designed components in xml file
        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        tvAgeInMins = findViewById<TextView>(R.id.tvAgeInMin)
        tvAgeInDays = findViewById<TextView>(R.id.tvAgeInDays)

        // if date picker button is clicked, it will call the clickDatePickerBtn()
        btnDatePicker.setOnClickListener() {
            clickDatePickerBtn()
        }
    }

    private fun clickDatePickerBtn() {
        // declare calendar components and information
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // create date picker dialog
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                Toast.makeText(
                    this@MainActivity,
                    "Selected Year: $selectedYear, Selected Month: ${selectedMonth + 1}, Selected Day: $selectedDay",
                    Toast.LENGTH_SHORT
                ).show()


                val selectedDate =  "$selectedDay/${selectedMonth + 1}/$selectedYear" // set the date display string format
                tvSelectedDate?.text = selectedDate // assign the selectedDate to the textview (? is needed because it is nullable)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) // create a date format
                val theDate = sdf.parse(selectedDate) // parse the selectedDate from string to an actual date format

                //  ?. means if theDate is not null, then it will perform the following code statements
                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000 // count selected date in minutes
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) // count current date in minutes

                    // if currentDate is not null, then execute the following statements
                    currentDate?.let{
                        // convert currentDate to minutes and use it to find the difference with selectedDate in minutes
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val ageInDays = differenceInMinutes / 1440
                        tvAgeInMins?.text = differenceInMinutes.toString()
                        tvAgeInDays?.text = ageInDays.toString()
                    }
                }
            },
            year, month, day
        ).show()
    }
}