package com.example.mahirhasan.registrationandlogin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.SquareCellView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class CalendarActivityFragment extends Fragment implements FlexibleCalendarView.OnMonthChangeListener,
        FlexibleCalendarView.OnDateClickListener {

    private FlexibleCalendarView calendarView;
    private TextView someTextView;
    private String category, email;
    private int dd, mm, yy;
    MyDBHandler dbHandler;

    ArrayList<Holiday> data;

  public CalendarActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        email = LoginActivity.getemail;
        dbHandler = new MyDBHandler(getActivity(), null, null, 1);
        data = new ArrayList<Holiday>();
        //getData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (FlexibleCalendarView)view.findViewById(R.id.calendar_view);

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar1_date_cell_view, null);
                }
                if(cellType == BaseCellView.OUTSIDE_MONTH){
                    cellView.setTextColor(getResources().getColor(R.color.date_outside_month_text_color_activity_2));
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (SquareCellView) inflater.inflate(R.layout.calendar1_week_cell_view, null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                char s1 = defaultValue.charAt(0);
                char s2 = defaultValue.charAt(1);
                char s3 = defaultValue.charAt(2);
                String ss = String.valueOf(s1) + String.valueOf(s2) + String.valueOf(s3);
                return ss;
            }


        });


        calendarView.setOnMonthChangeListener(this);
        calendarView.setOnDateClickListener(this);
        calendarView.setShowDatesOutsideMonth(true);

        //getData();
       // final Vector<Holiday> data;
        data = dbHandler.databaseToString(email);
        //System.out.println("here " + data.size());


        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {

            @Override
            public List<CalendarEvent> getEventsForTheDay(int year, int month, int day) {
                List<CalendarEvent> eventColors = new ArrayList<>(2);
                int cnt = 0;
                for(int i=0; i<data.size(); i++){
                    String ss = data.get(i).getDate();
                    yy = Integer.parseInt(ss.substring(0, 4));
                    mm = Integer.parseInt(ss.substring(5, 7));
                    dd = Integer.parseInt(ss.substring(8,10));
                    category = data.get(i).getCategory();
                    //System.out.println(i + " " + category);

                if (year == yy && month == mm-1 && day == dd) {
                    if(cnt<1)eventColors.add(new CalendarEvent(android.R.color.holo_red_dark));
                    cnt++;
                }
            }

            if(eventColors.size()>0)return eventColors;
            return null;
            }
        });

        setupToolBar(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateTitle(calendarView.getSelectedDateItem().getYear(), calendarView.getSelectedDateItem().getMonth());
    }


    public void setupToolBar(View mainView){
        Toolbar toolbar = (Toolbar) mainView.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        //bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowCustomEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        someTextView = new TextView(getActivity());
        someTextView.setTextColor(getActivity().getResources().getColor(R.color.title_text_color_activity_1));
        someTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.isShown()) {
                    calendarView.collapse();
                } else {
                    calendarView.expand();
                }
            }
        });
        bar.setCustomView(someTextView);

        bar.setBackgroundDrawable(new ColorDrawable(getActivity().getResources()
                .getColor(R.color.colorPrimary)));

        //back button color
        //final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        //upArrow.setColorFilter(getResources().getColor(R.color.title_text_color_activity_1), PorterDuff.Mode.SRC_ATOP);
        //bar.setHomeAsUpIndicator(upArrow);
    }

    private void updateTitle(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        someTextView.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                this.getResources().getConfiguration().locale) + " " + year);
    }

    @Override
    public void onMonthChange(int year, int month, int direction) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        updateTitle(year,month);
    }

    @Override
    public void onDateClick(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        String ss1 = "";
        String tst = "";
        String name = "";
        for(int i=0; i<data.size(); i++)
        {
            String ss = data.get(i).getDate();
            yy = Integer.parseInt(ss.substring(0, 4));
            mm = Integer.parseInt(ss.substring(5, 7));
            dd = Integer.parseInt(ss.substring(8,10));
            category = data.get(i).getCategory();

            if(year == yy && month == mm-1 && dd == day) {
                ss1 += category;
                ss1 += ": ";
                ss1 += data.get(i).getName();
                ss1 += "\n";
                //name = data.get(i).getName();
            }
        }

        if(ss1.equals("")) tst = cal.getTime().toString();
        else  tst = cal.getTime().toString()+ "\n"+ name + "\nCategory : " + ss1;
        ((ShowmycalendarActivity)getActivity()).setText(tst);
        //Toast.makeText(getActivity(),tst , Toast.LENGTH_LONG).show();
    }


}
