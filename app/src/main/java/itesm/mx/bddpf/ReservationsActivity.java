package itesm.mx.bddpf;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReservationsActivity extends AppCompatActivity {
    private FlightOperations dao;
    private AutoCompleteTextView actv_Passenger;
    private AutoCompleteTextView actv_Payment;
    private AutoCompleteTextView actv_Name;
    private ArrayList<Reservation> reservations;
    private ReservationAdapter reservationAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        dao = new FlightOperations(this);
        dao.open();

        listView = (ListView) findViewById(R.id.listview);
        reservations = dao.getAllReservations();
        setReservationsList();

        ArrayAdapter<String> adapterPassenger = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, dao.getUniquePassengers());
        actv_Passenger = (AutoCompleteTextView) findViewById(R.id.edit_passenger);
        actv_Passenger.setThreshold(0);
        actv_Passenger.setAdapter(adapterPassenger);
        actv_Passenger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchReservations();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        actv_Passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv_Passenger.showDropDown();
            }
        });

        ArrayAdapter<String> adapterPayment = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, dao.getUniquePayments());
        actv_Payment = (AutoCompleteTextView) findViewById(R.id.edit_payment);
        actv_Payment.setThreshold(0);
        actv_Payment.setAdapter(adapterPayment);
        actv_Payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchReservations();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        actv_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv_Payment.showDropDown();
            }
        });

        ArrayAdapter<String> adapterPassengerNames = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, dao.getUniquePassengerNames());
        actv_Name = (AutoCompleteTextView) findViewById(R.id.edit_name);
        actv_Name.setThreshold(0);
        actv_Name.setAdapter(adapterPassengerNames);
        actv_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                reservations = dao.getAllReservationsWithPassengerName(actv_Name.getText().toString());
                reservationAdapter = new ReservationAdapter(getApplicationContext(), reservations);
                setReservationsList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        actv_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv_Name.showDropDown();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addReservation = new Intent(getApplicationContext(), AddNewReservationActivity.class);
                startActivity(addReservation);
            }
        });
    }

    public void searchReservations(){
        String passengerSelected = actv_Passenger.getText().toString().toUpperCase();
        String paymentSelected = actv_Payment.getText().toString().toUpperCase();
        if (passengerSelected.length() != 0 && paymentSelected.length() != 0) {
            reservationsWithPassAndPay(passengerSelected, paymentSelected);
        } else if (passengerSelected.length() == 0 && paymentSelected.length() != 0) {
            reservationsWithPayment(paymentSelected);
        } else if (passengerSelected.length() != 0 && paymentSelected.length() == 0) {
            reservationsWithPassenger(passengerSelected);
        } else {
            reservations = dao.getAllReservations();
            setReservationsList();
        }
    }

    public void reservationsWithPayment(String paymentSelected) {
        reservations = dao.getAllReservationsWithPayment(paymentSelected);
        reservationAdapter = new ReservationAdapter(getApplicationContext(), reservations);
        setReservationsList();
    }

    public void reservationsWithPassenger(String passengerSelected) {
        reservations = dao.getAllReservationsWithPassenger(passengerSelected);
        reservationAdapter = new ReservationAdapter(getApplicationContext(), reservations);
        setReservationsList();
    }

    public void reservationsWithPassAndPay(String passengerSelected, String paymentSelected) {
        reservations = dao.getAllReservationsWithPassAndPay(passengerSelected, paymentSelected);
        reservationAdapter = new ReservationAdapter(getApplicationContext(), reservations);
        setReservationsList();
    }

    public void setReservationsList() {
        reservationAdapter = new ReservationAdapter(this, reservations);
        listView.setAdapter(reservationAdapter);
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        dao.open();
        super.onResume();
    }
}
