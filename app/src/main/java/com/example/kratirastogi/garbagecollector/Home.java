package com.example.kratirastogi.garbagecollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class Home extends AppCompatActivity {
TextView name,vn,status,housecover;
String newdate,state;
double lat,lng;
Button show,replace;
int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name=findViewById(R.id.name);
        vn=findViewById(R.id.vn);
        status=findViewById(R.id.status);
        show=findViewById(R.id.show);
        housecover=findViewById(R.id.housecover);
        replace=findViewById(R.id.replace);
        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       // currentdate.add(java.util.Calendar.DAY_OF_MONTH,1);
        newdate=sdf.format(currentdate.getTime());

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Collector").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               state= dataSnapshot.child("State").getValue(String.class);
                 Data1 d= dataSnapshot.getValue(Data1.class);
                 name.setText(d.getName());
                 vn.setText(d.getNumplate());
                 status.setText(d.getStatus());
               lat=  d.getLat();
               lng=d.getLng();

               String s=  d.getDate().getD1().getDate();
                String s1=  d.getDate().getD2().getDate();
                String s2=  d.getDate().getD3().getDate();
               if(s.equals(newdate))
               {

                  databaseReference.child("date").child("d1").child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          n= (int) dataSnapshot.getChildrenCount();

                          housecover.setText(String.valueOf(n));
                          show.setVisibility(View.VISIBLE);
                          if(n<1)
                          {
                              replace.setVisibility(View.VISIBLE);
                          }
                          show.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Intent i=new Intent(Home.this,CustomerListView.class);
                                  startActivity(i);
                              }
                          });

                          replace.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                //  databaseReference.child("date").child("d1").removeValue();
                                  if(state.equals("1"))
                                  {   Toast.makeText(Home.this, ""+state, Toast.LENGTH_SHORT).show();
                                      final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                      final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                      currentdate.add(java.util.Calendar.DAY_OF_MONTH,3);
                                      final String  newdate1=sdf.format(currentdate.getTime());
                                      databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                  }
                                  else
                                      if(state.equals("3"))
                                      {
                                          final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                          final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                          currentdate.add(java.util.Calendar.DAY_OF_MONTH,9);
                                          final String  newdate1=sdf.format(currentdate.getTime());
                                          databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                      }
                                      else
                                          if(state.equals("5"))
                                          {
                                              final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                              final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                              currentdate.add(java.util.Calendar.DAY_OF_MONTH,15);
                                              final String  newdate1=sdf.format(currentdate.getTime());
                                              databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                          }
                                          else
                                              if(state.equals("7"))
                                              {
                                                  final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                                  final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                                  currentdate.add(java.util.Calendar.DAY_OF_MONTH,21);
                                                  final String  newdate1=sdf.format(currentdate.getTime());
                                                  databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                              }

                              }
                          });
                         databaseReference.child("status").setValue("Busy");


                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });


                   Toast.makeText(Home.this, ""+n, Toast.LENGTH_SHORT).show();
               }

                else if(s1.equals(newdate))
                {   final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                    final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    currentdate.add(java.util.Calendar.DAY_OF_MONTH,9);
                    final String  newdate1=sdf.format(currentdate.getTime());

                    databaseReference.child("date").child("d2").child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            n= (int) dataSnapshot.getChildrenCount();

                            housecover.setText(String.valueOf(n));
                            Toast.makeText(Home.this, ""+n, Toast.LENGTH_SHORT).show();
                            show.setVisibility(View.VISIBLE);
                            if(n<1)
                            {
                                replace.setVisibility(View.VISIBLE);
                            }
                            show.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i=new Intent(Home.this,CustomerListView.class);
                                    startActivity(i);
                                }
                            });
                            replace.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    databaseReference.child("date").child("d1").removeValue();
                                    if(state.equals("1"))
                                    {   final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,3);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }
                                    else
                                    if(state.equals("3"))
                                    {
                                        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,9);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }
                                    else
                                    if(state.equals("5"))
                                    {
                                        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,15);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }
                                    else
                                    if(state.equals("7"))
                                    {
                                        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,21);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }

                                }
                            });
                            databaseReference.child("status").setValue("Busy");


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    Toast.makeText(Home.this, ""+n, Toast.LENGTH_SHORT).show();
                }
             else   if(s2.equals(newdate))
                {


                    databaseReference.child("date").child("d3").child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            n= (int) dataSnapshot.getChildrenCount();

                            housecover.setText(String.valueOf(n));
                            Toast.makeText(Home.this, ""+n, Toast.LENGTH_SHORT).show();
                            show.setVisibility(View.VISIBLE);
                            if(n<1)
                            {
                                replace.setVisibility(View.VISIBLE);
                            }
                            show.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i=new Intent(Home.this,CustomerListView.class);
                                    startActivity(i);
                                }
                            });
                            replace.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    databaseReference.child("date").child("d1").removeValue();
                                    if(state.equals("1"))
                                    {   final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,3);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }
                                    else
                                    if(state.equals("3"))
                                    {
                                        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,9);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }
                                    else
                                    if(state.equals("5"))
                                    {
                                        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,15);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }
                                    else
                                    if(state.equals("7"))
                                    {
                                        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
                                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                        currentdate.add(java.util.Calendar.DAY_OF_MONTH,21);
                                        final String  newdate1=sdf.format(currentdate.getTime());
                                        databaseReference.child("date").child("d1").setValue(new Date1(newdate1,lat,lng));
                                    }

                                }
                            });
                            databaseReference.child("status").setValue("Busy");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    Toast.makeText(Home.this, ""+n, Toast.LENGTH_SHORT).show();
                }
                else
               {
                   databaseReference.child("status").setValue("Inactive");
               }
        String j1=  d.getDate().getD1().getDate();

                String j2=  d.getDate().getD2().getDate();

                String j3= d.getDate().getD2().getDate();
                if(j1.equals(newdate))
                {
                    show.setVisibility(View.VISIBLE);
                }
                else
                    if(j2.equals(newdate))
                    {
                        show.setVisibility(View.VISIBLE);
                    }
                    else
                        if(j3.equals(newdate))
                        {
                            show.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            show.setVisibility(View.GONE);
                        }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
