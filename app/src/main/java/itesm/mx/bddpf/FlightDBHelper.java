package itesm.mx.bddpf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class that manages the database for events. It can create and upgrade/downgrade the database
 */

public class FlightDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FlightsDB.db";
    private static final int DATABASE_VERSION = 1;

    public FlightDBHelper(Context context) {
        //Create database
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String fkConstraint = "PRAGMA foreign_keys = on";
        db.execSQL(fkConstraint);

        String CREATE_FLIGHTS_TABLE = "CREATE TABLE " +
                DataBaseSchema.FlightTable.TABLE_NAME +
                "(" +
                DataBaseSchema.FlightTable._ID + " INTEGER," + //placeholder
                DataBaseSchema.FlightTable.COLUMN_NAME_FLIGHT_ID + " TEXT PRIMARY KEY," +
                DataBaseSchema.FlightTable.COLUMN_NAME_FLIGHT_TIME + " INTEGER," +
                DataBaseSchema.FlightTable.COLUMN_NAME_FLIGHT_DURATION + " INTEGER," +
                DataBaseSchema.FlightTable.COLUMN_NAME_AIRPORT_ORIGIN + " TEXT," +
                DataBaseSchema.FlightTable.COLUMN_NAME_TERMINAL_ORIGIN + " TEXT," +
                DataBaseSchema.FlightTable.COLUMN_NAME_GATE_ORIGIN + " TEXT," +
                DataBaseSchema.FlightTable.COLUMN_NAME_AIRPORT_DESTINATION + " TEXT," +
                DataBaseSchema.FlightTable.COLUMN_NAME_TERMINAL_DESTINATION + " TEXT," +
                DataBaseSchema.FlightTable.COLUMN_NAME_GATE_DESTINATION + " TEXT," +
                DataBaseSchema.FlightTable.COLUMN_NAME_AIRPLANE + " TEXT, " +

                "FOREIGN KEY (" + DataBaseSchema.FlightTable.COLUMN_NAME_AIRPORT_ORIGIN + ") " +
                "REFERENCES " + DataBaseSchema.AirportTable.TABLE_NAME +
                "(" + DataBaseSchema.AirportTable.COLUMN_NAME_AIRPORT_CODE + ") " +
                " ON UPDATE CASCADE ON DELETE CASCADE, " +

                "FOREIGN KEY (" + DataBaseSchema.FlightTable.COLUMN_NAME_AIRPORT_DESTINATION + ") "+
                "REFERENCES " + DataBaseSchema.AirportTable.TABLE_NAME +
                "(" + DataBaseSchema.AirportTable.COLUMN_NAME_AIRPORT_CODE + ") " +
                " ON UPDATE CASCADE ON DELETE CASCADE, " +

                "FOREIGN KEY (" + DataBaseSchema.Airplane.TABLE_NAME + ") " +
                "REFERENCES " + DataBaseSchema.FlightTable.COLUMN_NAME_AIRPLANE +
                "(" + DataBaseSchema.Airplane.COLUMN_NAME_AIRPLANE_ID + ")" +
                " ON UPDATE CASCADE ON DELETE CASCADE " +

                ")";
        Log.i("FlightDBHelper onCreate", CREATE_FLIGHTS_TABLE);
        db.execSQL(CREATE_FLIGHTS_TABLE);

        String CREATE_AIRPORT_TABLE = "CREATE TABLE " +
                DataBaseSchema.AirportTable.TABLE_NAME +
                "(" +
                DataBaseSchema.AirportTable._ID + " INTEGER," + //placeholder
                DataBaseSchema.AirportTable.COLUMN_NAME_AIRPORT_CODE + " TEXT PRIMARY KEY," +
                DataBaseSchema.AirportTable.COLUMN_NAME_CITY + " TEXT," +
                DataBaseSchema.AirportTable.COLUMN_NAME_COUNTRY + " TEXT," +
                DataBaseSchema.AirportTable.COLUMN_NAME_NAME + " TEXT " +
                ")";
        Log.i("FlightDBHelper onCreate", CREATE_AIRPORT_TABLE);
        db.execSQL(CREATE_AIRPORT_TABLE);

        String CREATE_AIRPLANE_TABLE = "CREATE TABLE " +
                DataBaseSchema.Airplane.TABLE_NAME +
                "(" +
                DataBaseSchema.Airplane._ID + " INTEGER," + //placeholder
                DataBaseSchema.Airplane.COLUMN_NAME_AIRPLANE_ID + " TEXT PRIMARY KEY," +
                DataBaseSchema.Airplane.COLUMN_NAME_MODEL + " TEXT," +
                DataBaseSchema.Airplane.COLUMN_NAME_SEATS_ECONOMY + " INTEGER," +
                DataBaseSchema.Airplane.COLUMN_NAME_SEATS_BUSINESS + " INTEGER," +
                DataBaseSchema.Airplane.COLUMN_NAME_SEATS_FIRST_CLASS + " INTEGER " +
                ")";
        Log.i("FlightDBHelper onCreate", CREATE_AIRPLANE_TABLE);
        db.execSQL(CREATE_AIRPLANE_TABLE);

        String CREATE_TICKET_TABLE = "CREATE TABLE " +
                DataBaseSchema.Ticket.TABLE_NAME +
                "(" +
                DataBaseSchema.Ticket._ID + " INTEGER," +
                DataBaseSchema.Ticket.COLUMN_NAME_TICKET_NUMBER + " TEXT PRIMARY KEY," +
                DataBaseSchema.Ticket.COLUMN_NAME_PRICE + " REAL," +
                DataBaseSchema.Ticket.COLUMN_NAME_SEAT + " TEXT," +
                DataBaseSchema.Ticket.COLUMN_NAME_CLASS + " TEXT " +
                "CHECK (" + DataBaseSchema.Ticket.COLUMN_NAME_CLASS +
                " IN ('Economy', 'Business', 'First')), " +
                DataBaseSchema.Ticket.COLUMN_NAME_FLIGHT + " TEXT," +
                DataBaseSchema.Ticket.COLUMN_NAME_RESERVATION + " TEXT," +
                DataBaseSchema.Ticket.COLUMN_NAME_EXTRA_SERVICES + " TEXT," +
                DataBaseSchema.Ticket.COLUMN_NAME_PASSENGER + " TEXT, " +

                "FOREIGN KEY (" + DataBaseSchema.Ticket.COLUMN_NAME_RESERVATION+ ") " +
                "REFERENCES " + DataBaseSchema.Reservation.TABLE_NAME +
                "(" + DataBaseSchema.Reservation.COLUMN_NAME_RESERVATION_CODE + ") " +
                " ON UPDATE CASCADE ON DELETE CASCADE, " +

                "FOREIGN KEY (" + DataBaseSchema.Ticket.COLUMN_NAME_FLIGHT + ") " +
                "REFERENCES " + DataBaseSchema.FlightTable.TABLE_NAME +
                "(" + DataBaseSchema.FlightTable.COLUMN_NAME_FLIGHT_ID + ") " +
                " ON UPDATE CASCADE ON DELETE CASCADE, " +

                "FOREIGN KEY (" + DataBaseSchema.Ticket.COLUMN_NAME_PASSENGER + ") " +
                "REFERENCES " + DataBaseSchema.Passenger.TABLE_NAME +
                "(" + DataBaseSchema.Passenger.COLUMN_NAME_PASSENGER_ID + ")" +
                " ON UPDATE CASCADE ON DELETE CASCADE " +
                ")";
        Log.i("FlightDBHelper onCreate", CREATE_TICKET_TABLE);
        db.execSQL(CREATE_TICKET_TABLE);

        String CREATE_PASSENGERS_TABLE = "CREATE TABLE " +
                DataBaseSchema.Passenger.TABLE_NAME +
                "(" +
                DataBaseSchema.Passenger._ID + " INTEGER," +
                DataBaseSchema.Passenger.COLUMN_NAME_PASSENGER_ID + " TEXT PRIMARY KEY," +
                DataBaseSchema.Passenger.COLUMN_NAME_PASSENGER_TYPE + " TEXT " +
                "CHECK (" + DataBaseSchema.Passenger.COLUMN_NAME_PASSENGER_TYPE +
                " IN ('Adult', 'Baby', 'Child')), " +
                DataBaseSchema.Passenger.COLUMN_NAME_NUMBER_CELL + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_NUMBER_FIXED + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_FIRST_NAME + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_LAST_NAME + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_EMAIL + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_STREET_ADDRESS + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_POSTAL_CODE + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_CITY + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_STATE + " TEXT," +
                DataBaseSchema.Passenger.COLUMN_NAME_COUNTRY + " TEXT " +
                ")";
        Log.i("FlightDBHelper onCreate", CREATE_PASSENGERS_TABLE);
        db.execSQL(CREATE_PASSENGERS_TABLE);

        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " +
                DataBaseSchema.Reservation.TABLE_NAME +
                "(" +
                DataBaseSchema.Reservation.COLUMN_NAME_RESERVATION_CODE + " TEXT PRIMARY KEY," +
                DataBaseSchema.Reservation.COLUMN_NAME_PAYMENT_INFORMATION + " TEXT " +
                "CHECK (" + DataBaseSchema.Reservation.COLUMN_NAME_PAYMENT_INFORMATION +
                " IN ('Cash', 'CreditCard', 'DebitCard', 'Invoice')), " +
                DataBaseSchema.Reservation.COLUMN_NAME_PASSENGER + " TEXT, " +

                "FOREIGN KEY (" + DataBaseSchema.Reservation.COLUMN_NAME_PASSENGER + ") " +
                "REFERENCES " + DataBaseSchema.Passenger.TABLE_NAME +
                "(" + DataBaseSchema.Passenger.COLUMN_NAME_PASSENGER_ID + ")" +
                " ON UPDATE CASCADE ON DELETE CASCADE " +
                ")";
        Log.i("FlightDBHelper onCreate", CREATE_RESERVATIONS_TABLE);
        db.execSQL(CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("PRAGMA foreign_keys = off");
        String DELETE_FLIGHTS_TABLE = "DROP TABLE IF EXISTS  " +
                DataBaseSchema.FlightTable.TABLE_NAME;
        db.execSQL(DELETE_FLIGHTS_TABLE);
        String DELETE_RESERVATIONS_TABLE = "DROP TABLE IF EXISTS  " +
                DataBaseSchema.Reservation.TABLE_NAME;
        db.execSQL(DELETE_RESERVATIONS_TABLE);
        String DELETE_PASSENGERS_TABLE = "DROP TABLE IF EXISTS  " +
                DataBaseSchema.Passenger.TABLE_NAME;
        db.execSQL(DELETE_PASSENGERS_TABLE);
        String DELETE_AIRPLANES_TABLE = "DROP TABLE IF EXISTS  " +
                DataBaseSchema.Airplane.TABLE_NAME;
        db.execSQL(DELETE_AIRPLANES_TABLE);
        String DELETE_AIRPORTS_TABLE = "DROP TABLE IF EXISTS  " +
                DataBaseSchema.AirportTable.TABLE_NAME;
        db.execSQL(DELETE_AIRPORTS_TABLE);
        String DELETE_TICKETS_TABLE = "DROP TABLE IF EXISTS  " +
                DataBaseSchema.Ticket.TABLE_NAME;
        db.execSQL(DELETE_TICKETS_TABLE);
        onCreate(db);
    }

    public  void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //actualize database version
    }
}
