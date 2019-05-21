package com.example.kratirastogi.garbagecollector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.security.AccessController.getContext;


public class CustomerListView extends AppCompatActivity {
ListView listview;
DatabaseReference databaseReference,databaseref,databaseref1,databaseref2;
String newdate;

LocationManager lm;
double lat1,lng1;
DatabaseReference ref;
    List<ListViewItemDTO> ret ;
    LatLng a;
    String name,add,identity,pay,sta;
    String num,plate,nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list_view);
        listview=findViewById(R.id.listview);
        ret=new ArrayList<ListViewItemDTO>();



        ref= FirebaseDatabase.getInstance().getReference("Collector");
        final java.util.Calendar currentdate= java.util.Calendar.getInstance();
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        newdate=sdf.format(currentdate.getTime());
        Toast.makeText(this, ""+newdate, Toast.LENGTH_SHORT).show();
         databaseReference= FirebaseDatabase.getInstance().getReference("Collector").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("date");
      databaseref=FirebaseDatabase.getInstance().getReference("Collector").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("date").child("d1").child("user");
        databaseref1=FirebaseDatabase.getInstance().getReference("Collector").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("date").child("d2").child("user");
        databaseref2=FirebaseDatabase.getInstance().getReference("Collector").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("date").child("d3").child("user");
       // final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();
      //  Toast.makeText(this, ""+initItemList.size(), Toast.LENGTH_SHORT).show();


         ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
             nam=dataSnapshot.child("name").getValue(String.class);
                 num=dataSnapshot.child("number").getValue(String.class);
                 plate=dataSnapshot.child("numplate").getValue(String.class);

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date d=dataSnapshot.getValue(Date.class);
                String d1=  d.getD1().getDate();
                String d2=  d.getD2().getDate();
                String d3=  d.getD3().getDate();
                Toast.makeText(CustomerListView.this, "date"+d2, Toast.LENGTH_SHORT).show();
                if(d1.equals(newdate))
                {

                    databaseref
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                        Data d = ds.getValue(Data.class);
                                        ListViewItemDTO dto = new ListViewItemDTO(d.getName(), d.getAdd(), d.getPaymentStatus(),d.getId(),d.getState());

                                        ret.add(dto);
                                        Toast.makeText(CustomerListView.this, "" + d.getName(), Toast.LENGTH_SHORT).show();

                                    }

                                    final ListViewBaseAdapter listViewDataAdapter = new ListViewBaseAdapter(CustomerListView.this, ret);

                                    listViewDataAdapter.notifyDataSetChanged();

                                    listview.setAdapter(listViewDataAdapter);
                                    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                                            final ListViewItemDTO ob = (ListViewItemDTO) parent.getItemAtPosition(position);
                                            final String s= ob.getAddress();

                                            final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerListView.this);
                                            builder.setMessage("Select");
                                            builder.setPositiveButton("View Map", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(CustomerListView.this, MapsActivity.class);
                                                    i.putExtra("add", s);

                                                    startActivity(i);
                                                }
                                            });
                                   builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           int size = ret.size();

                                           for (int i = 0; i < size; i++) {
                                               final ListViewItemDTO ob = (ListViewItemDTO) ret.get(i);
                                        final String s=   ob.getId();
                                        String state=ob.getState();

                                        if(state.equals("1")) {

                                            final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            currentdate.add(Calendar.DAY_OF_MONTH, 1);
                                            final String newdate1 = sdf.format(currentdate.getTime());
                                            final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                            refer.removeValue();
                                            databaseref.child(s).removeValue();
                                            DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                            rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Data d = dataSnapshot.getValue(Data.class);
                                                    name = d.getName();

                                                    add = d.getAdd();
                                                    pay = d.getPaymentStatus();
                                                    identity = d.getId();
                                                    sta = d.getState();
                                                    databaseref1.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                    refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                           // databaseref1.setValue(s);
                                            Toast.makeText(CustomerListView.this, ""+name+add, Toast.LENGTH_SHORT).show();

                                        }

                                           if(state.equals("3")) {
                                               final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                               final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                               currentdate.add(Calendar.DAY_OF_MONTH, 3);
                                               final String newdate1 = sdf.format(currentdate.getTime());
                                               final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                               refer.removeValue();
                                               databaseref.child(s).removeValue();
                                               DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                               rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       Data d = dataSnapshot.getValue(Data.class);
                                                       name = d.getName();

                                                       add = d.getAdd();
                                                       pay = d.getPaymentStatus();
                                                       identity = d.getId();
                                                       sta = d.getState();
                                                       databaseref1.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                       refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                   }

                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) {

                                                   }
                                               });



                                               refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                           }
                                           if(state.equals("5")) {
                                               final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                               final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                               currentdate.add(Calendar.DAY_OF_MONTH, 5);
                                               final String newdate1 = sdf.format(currentdate.getTime());
                                               final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                               refer.removeValue();
                                               databaseref.child(s).removeValue();
                                               DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                               rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       Data d = dataSnapshot.getValue(Data.class);
                                                       name = d.getName();

                                                       add = d.getAdd();
                                                       pay = d.getPaymentStatus();
                                                       identity = d.getId();
                                                       sta = d.getState();
                                                       databaseref1.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                       refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                   }

                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) {

                                                   }
                                               });



                                               refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                           }
                                           if(state.equals("7")) {
                                               final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                               final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                               currentdate.add(Calendar.DAY_OF_MONTH, 7);
                                               final String newdate1 = sdf.format(currentdate.getTime());
                                               final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                               refer.removeValue();
                                               databaseref.child(s).removeValue();
                                               DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                               rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       Data d = dataSnapshot.getValue(Data.class);
                                                       name = d.getName();

                                                       add = d.getAdd();
                                                       pay = d.getPaymentStatus();
                                                       identity = d.getId();
                                                       sta = d.getState();
                                                       databaseref1.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                       refer.setValue(new CollectorData1(nam, plate, num, newdate1));
                                                   }

                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) {

                                                   }
                                               });


                                               refer.setValue(new CollectorData1(nam, plate, num, newdate1));

                                           }
                                               ret.remove(i);
                                               i--;
                                               size = ret.size();

                                           }
                                           listViewDataAdapter.notifyDataSetChanged();

                                       }

                                   });
                                            AlertDialog b=builder.create();
                                            b.show();

                                            return false;

                                        }

                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                }
               else if(d2.equals(newdate))
                {    Toast.makeText(CustomerListView.this, "hello", Toast.LENGTH_SHORT).show();
                   // Toast.makeText(CustomerListView.this, ""+newdate, Toast.LENGTH_SHORT).show();
                    databaseref1
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                        Data d = ds.getValue(Data.class);
                                        ListViewItemDTO dto = new ListViewItemDTO(d.getName(), d.getAdd(), d.getPaymentStatus(), d.getId(), d.getState());

                                        ret.add(dto);
                                        Toast.makeText(CustomerListView.this, "" + d.getName(), Toast.LENGTH_SHORT).show();

                                    }

                                    final ListViewBaseAdapter listViewDataAdapter = new ListViewBaseAdapter(CustomerListView.this, ret);

                                    listViewDataAdapter.notifyDataSetChanged();

                                    listview.setAdapter(listViewDataAdapter);
                                    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                                            final ListViewItemDTO ob = (ListViewItemDTO) parent.getItemAtPosition(position);
                                            final String s = ob.getAddress();
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerListView.this);
                                            builder.setMessage("Select");
                                            builder.setPositiveButton("View Map", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(CustomerListView.this, MapsActivity.class);
                                                    i.putExtra("add", s);

                                                    startActivity(i);
                                                }
                                            });
                                            builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    int size = ret.size();

                                                    for (int i = 0; i < size; i++) {
                                                        final ListViewItemDTO ob = (ListViewItemDTO) ret.get(i);
                                                    final String s=   ob.getId();
                                                    String state=ob.getState();

                                                    if(state.equals("1")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 1);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref1.child(s).removeValue();


                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref2.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                    }

                                                    if(state.equals("3")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 3);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref1.child(s).removeValue();


                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref2.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                    }
                                                    if(state.equals("5")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 5);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref1.child(s).removeValue();


                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref2.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                    }
                                                    if(state.equals("7")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 7);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref1.child(s).removeValue();


                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref2.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam, plate, num, newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                        refer.setValue(new CollectorData1(nam, plate, num, newdate1));


                                                    }
                                                        ret.remove(i);
                                                        i--;
                                                        size = ret.size();


                                                    }

                                                    listViewDataAdapter.notifyDataSetChanged();


                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();

                                            return false;
                                        }
                                    });

                                }



                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                }
               else if(d3.equals(newdate))
                {

                    databaseref2
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds:dataSnapshot.getChildren())
                                    {

                                        Data d=   ds.getValue(Data.class);
                                        ListViewItemDTO dto = new ListViewItemDTO(d.getName(),d.getAdd(),d.getPaymentStatus(),d.getId(),d.getState());

                                        ret.add(dto);
                                        Toast.makeText(CustomerListView.this, ""+d.getName(), Toast.LENGTH_SHORT).show();

                                    }

                                    final ListViewBaseAdapter listViewDataAdapter = new ListViewBaseAdapter(CustomerListView.this, ret);

                                    listViewDataAdapter.notifyDataSetChanged();

                                    listview.setAdapter(listViewDataAdapter);
                                    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                            final ListViewItemDTO ob = (ListViewItemDTO) parent.getItemAtPosition(position);
                                            final String s = ob.getAddress();
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerListView.this);
                                            builder.setMessage("Select");
                                            builder.setPositiveButton("View Map", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(CustomerListView.this, MapsActivity.class);
                                                    i.putExtra("add", s);

                                                    startActivity(i);
                                                }
                                            });
                                            builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final String s=   ob.getId();
                                                    String state=ob.getState();
                                                    if(state.equals("1")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 1);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref2.child(s).removeValue();
                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                    }

                                                    if(state.equals("3")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 3);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref2.child(s).removeValue();
                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                    }
                                                    if(state.equals("5")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 5);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref2.child(s).removeValue();
                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                    }
                                                    if(state.equals("7")) {
                                                        final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        currentdate.add(Calendar.DAY_OF_MONTH, 7);
                                                        final String newdate1 = sdf.format(currentdate.getTime());
                                                        final DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users").child(s).child("collector");
                                                        refer.removeValue();
                                                        databaseref2.child(s).removeValue();


                                                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Users").child(s);
                                                        rr.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Data d = dataSnapshot.getValue(Data.class);
                                                                name = d.getName();

                                                                add = d.getAdd();
                                                                pay = d.getPaymentStatus();
                                                                identity = d.getId();
                                                                sta = d.getState();
                                                                databaseref.child(s).setValue(new Data(name, add, pay, identity, sta));
                                                                refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                        refer.setValue(new CollectorData1(nam,plate,num,newdate1));
                                                        ret.remove(position);
                                                        listViewDataAdapter.notifyDataSetChanged();

                                                    }

                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();
                                            return false;
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }






}


