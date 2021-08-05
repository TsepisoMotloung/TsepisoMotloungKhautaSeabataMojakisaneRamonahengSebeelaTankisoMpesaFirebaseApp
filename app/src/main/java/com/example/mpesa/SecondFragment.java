package com.example.mpesa;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public
class SecondFragment extends Fragment {


    DatabaseReference users;
    EditText          u_name, u_surname, id_num, u_phone, u_pin;
    Button reg;


    public
    SecondFragment ( ) {
        // Required empty public constructor
    }


    public static
    SecondFragment newInstance ( String param1 , String param2 ) {
        SecondFragment fragment = new SecondFragment ( );
        return fragment;
    }

    @Override
    public
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public
    View onCreateView (
            LayoutInflater inflater , ViewGroup container ,
            Bundle savedInstanceState
                      ) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_second , container , false );
    }

    @Override
    public
    void onViewCreated ( @NonNull @NotNull View view , @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );
        users = FirebaseDatabase.getInstance ( ).getReference ( ).child ( "user" );



        u_name    = view.findViewById ( R.id.name );
        u_surname = view.findViewById ( R.id.surname );
        id_num    = view.findViewById ( R.id.idnumber );
        u_phone   = view.findViewById ( R.id.Phone );
        u_pin     = view.findViewById ( R.id.Pin );

        reg = view.findViewById ( R.id.registerbtn );

        reg.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                String user_name      = u_name.getText ( ).toString ( );
                String user_surname   = u_surname.getText ( ).toString ( );
                String user_phone     = u_phone.getText ( ).toString ( );
                String user_id_number = id_num.getText ( ).toString ( );
                String user_pin       = u_pin.getText ( ).toString ( );
                String user_balance   = "100";


                Users user = new Users ( user_name , user_surname , user_phone , user_id_number , user_pin , user_balance );

                if ( user_name.isEmpty ( ) | user_surname.isEmpty ( ) | user_phone.isEmpty ( ) | user_id_number.isEmpty ( ) | user_pin.isEmpty ( ) ) {
                    Toast toast = Toast.makeText (

                            getActivity ( ).getApplicationContext ( ) , "Fill in all spaces" , Toast.LENGTH_LONG
                                                 );

                    toast.show ( );
                }

                else {


                    users.orderByChild ( "user_phone" ).equalTo ( String.valueOf ( user_phone ) ).addValueEventListener ( new ValueEventListener ( ) {
                        @Override
                        public
                        void onDataChange ( DataSnapshot dataSnapshot ) {
                            if ( dataSnapshot.exists ( ) ) {

                                u_name.setText ( "" );
                                u_surname.setText ( "" );
                                u_pin.setText ( "" );
                                u_phone.setText ( "" );
                                id_num.setText ( "" );


                            }
                            else {

                                users.child ( user_phone ).setValue ( user );

                                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ) , Oparations.class );

                                intent.putExtra ( "userPhone" , user_phone );
                                intent.putExtra ( "userName" , user_name );
                                intent.putExtra ( "userSurname" , user_surname );
                                intent.putExtra ( "userBalance" , user_balance );

                                /*startActivity ( intent );*/

                                Toast toast = Toast.makeText (
                                        //getActivity(),"Custom Toast From Fragment",Toast.LENGTH_LONG
                                        getActivity ( ).getApplicationContext ( ) , "Successfully registered." , Toast.LENGTH_LONG
                                                             );
                                //toast.setGravity( Gravity.START, 0, 0);
                                toast.show ( );

                        }
                    }

                        @Override
                        public
                        void onCancelled ( @NonNull @NotNull DatabaseError error ) {

                        }
                    });
                }
            }
        });
    }
}
