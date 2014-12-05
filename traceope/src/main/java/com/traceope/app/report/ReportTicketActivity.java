package com.traceope.app.report;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.traceope.app.R;
import com.traceope.app.activity.SlideActivity;

import java.net.NetworkInterface;
import java.util.Calendar;

/**
 * Created by ale on 14/11/14.
 */
public class ReportTicketActivity extends Activity {

    private String userName;
    private static final String LOGIN_USER = "LOGIN_USER";
    public static final String SLIDE_CODE_COULEUR = "SLIDE_CODE_COULEUR";

    Button btnSelectDate, btnSelectTime;
    RadioButton btnMenuIncident, btnMenuMaintenance, btnIncident, btnFixe, btnMobile, btnIntervenant, btnSuperviseur, btnAlerte, btnFixeOk, btnFixeNok, btnPublic, btnPrive;

    RadioButton btnFixeMaint, btnMobileMaint, btnPublicMaint, btnPriveMaint;
    CheckBox cbTiers;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    public int year, month, day, hour, minute;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public ReportTicketActivity() {
        //TODO change locale En fr
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket);
    }

    //gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report, menu);

        //affichage du login
        MenuItem itemLogin = menu.findItem(R.id.user);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString(LOGIN_USER);
        itemLogin.setTitle(userName);

        //formulaire
        btnMenuIncident = (RadioButton) findViewById(R.id.rd_menu_incident);
        btnMenuMaintenance = (RadioButton) findViewById(R.id.rd_menu_maintenance);
        btnSelectDate = (Button) findViewById(R.id.buttonSelectDate);
        btnSelectTime = (Button) findViewById(R.id.buttonSelectTime);
        btnIntervenant = (RadioButton) findViewById(R.id.rd_intervenant);
        btnSuperviseur = (RadioButton) findViewById(R.id.rd_superviseur);
        btnFixe = (RadioButton) findViewById(R.id.rd_fixe);
        btnMobile = (RadioButton) findViewById(R.id.rd_mobile);
        btnFixeOk = (RadioButton) findViewById(R.id.rb_fixe_oui);
        btnFixeNok = (RadioButton) findViewById(R.id.rb_fixe_non);
        btnPrive = (RadioButton) findViewById(R.id.rd_prive);
        btnPublic = (RadioButton) findViewById(R.id.rd_public);
        cbTiers = (CheckBox) findViewById(R.id.cb_tiers);

        btnFixeMaint = (RadioButton) findViewById(R.id.rd_fixe_maintenance);
        btnMobileMaint = (RadioButton) findViewById(R.id.rd_mobile_maintenance);
        btnPriveMaint = (RadioButton) findViewById(R.id.rd_prive_maintenance);
        btnPublicMaint = (RadioButton) findViewById(R.id.rd_public_maintenance);


        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);
            }
        });


        //choix des menus incident/maintenance//
        // TODO checked two times
        btnMenuIncident.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.layout_incident).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.layout_incident).setVisibility(View.VISIBLE);
                    findViewById(R.id.layout_maintenance).setVisibility(View.GONE);

                } else {
                    findViewById(R.id.layout_incident).setVisibility(View.GONE);

                }

            }
        });

        //choix des menus incident/maintenance
        btnMenuMaintenance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.layout_maintenance).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.layout_maintenance).setVisibility(View.VISIBLE);
                    findViewById(R.id.layout_incident).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.layout_maintenance).setVisibility(View.GONE);

                }

            }
        });

        btnIntervenant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_intervenant).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_intervenant).setVisibility(View.VISIBLE);
                    ((RadioButton) findViewById(R.id.rd_intervenant)).setChecked(true);

                } else {
                    findViewById(R.id.raw_intervenant).setVisibility(View.GONE);
                    ((RadioButton) findViewById(R.id.rd_intervenant)).setChecked(false);
                }
            }
        });

        btnSuperviseur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_superviseur).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_superviseur).setVisibility(View.VISIBLE);
                    ((RadioButton) findViewById(R.id.rd_superviseur)).setChecked(true);
                } else {
                    findViewById(R.id.raw_superviseur).setVisibility(View.GONE);
                    ((RadioButton) findViewById(R.id.rd_superviseur)).setChecked(false);

                }
            }
        });

        btnFixe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_fixe).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_fixe).setVisibility(View.VISIBLE);

                    findViewById(R.id.raw_mobile).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_fixe).setVisibility(View.GONE);
                    findViewById(R.id.raw_fixe_yes).setVisibility(View.GONE);
                    findViewById(R.id.raw_fixe_no).setVisibility(View.GONE);
                }

            }
        });

        btnMobile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_mobile).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_mobile).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_fixe).setVisibility(View.GONE);
                    findViewById(R.id.raw_fixe_yes).setVisibility(View.GONE);
                    findViewById(R.id.raw_fixe_no).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_mobile).setVisibility(View.GONE);

                }
            }
        });

        btnFixeOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_fixe_yes).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_fixe_yes).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_fixe_no).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_fixe_yes).setVisibility(View.GONE);
                }


            }
        });

        btnFixeNok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_fixe_no).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_fixe_no).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_fixe_yes).setVisibility(View.GONE);

                } else {
                    findViewById(R.id.raw_fixe_no).setVisibility(View.GONE);

                }
            }
        });

        btnPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_public).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_public).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_prive).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_public).setVisibility(View.GONE);
                }

            }
        });

        btnPrive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_prive).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_prive).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_public).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_prive).setVisibility(View.GONE);

                }
            }
        });

        cbTiers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_tiers).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_tiers).setVisibility(View.VISIBLE);
                    ((CheckBox) findViewById(R.id.cb_tiers)).setChecked(true);
                } else {
                    findViewById(R.id.raw_tiers).setVisibility(View.GONE);
                    ((CheckBox) findViewById(R.id.cb_tiers)).setChecked(false);

                }
            }
        });

        btnFixeMaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_fixe_maintenance).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_fixe_maintenance).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_mobile_maintenance).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_fixe_maintenance).setVisibility(View.GONE);
                }

            }
        });

        btnMobileMaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_mobile_maintenance).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_mobile_maintenance).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_fixe_maintenance).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_mobile_maintenance).setVisibility(View.GONE);

                }
            }
        });

        btnPublicMaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_public_maintenance).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_public_maintenance).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_prive_maintenance).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_public_maintenance).setVisibility(View.GONE);
                }

            }
        });

        btnPriveMaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int state = findViewById(R.id.raw_prive_maintenance).getVisibility();
                if (state == View.GONE) {
                    findViewById(R.id.raw_prive_maintenance).setVisibility(View.VISIBLE);
                    findViewById(R.id.raw_public_maintenance).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.raw_prive_maintenance).setVisibility(View.GONE);

                }
            }
        });
        return true;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear;
                    day = dayOfMonth;
                    btnSelectDate.setText("Date : " + day + "-" + month + "-" + year);
                }
            };


    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                    hour = hourOfDay;
                    minute = min;
                    btnSelectTime.setText("Heure :" + hour + "-" + minute);
                }
            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_report_ticket);
        item.setEnabled(false);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_code_couleur) {
            Intent intent = new Intent(getApplicationContext(), SlideActivity.class);
            intent.putExtra("slideType", SLIDE_CODE_COULEUR);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_report_free) {
            Intent intent = new Intent(getApplicationContext(), ReportFreeActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_report_ticket) {
            Intent intent = new Intent(getApplicationContext(), ReportTicketActivity.class);
            intent.putExtra(LOGIN_USER, userName);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
