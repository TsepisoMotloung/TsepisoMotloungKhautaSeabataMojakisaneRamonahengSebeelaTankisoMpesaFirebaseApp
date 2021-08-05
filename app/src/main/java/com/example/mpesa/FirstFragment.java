package com.example.mpesa;

import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {


    DatabaseReference users;
    EditText          phoneNumber, pin;
    Button login;
    public FirstFragment() {
        // Required empty public constructor
    }


    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_first, container, false);
    }

    @Override
    public
    void onViewCreated ( @NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view, savedInstanceState );

        phoneNumber = view.findViewById ( R.id.Phone );
        pin = view.findViewById ( R.id.Pin );
        login = view.findViewById ( R.id.loginbtn );

        users = FirebaseDatabase.getInstance ().getReference ().child("user");


        login.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {

                String user_phone = phoneNumber.getText ().toString ();
                String user_pin = pin.getText ().toString ();



                users.orderByChild("user_phone").equalTo( String.valueOf ( user_phone ) ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public
                    void onDataChange ( DataSnapshot dataSnapshot ) {
                        if ( dataSnapshot.exists ( ) ) {

                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                String checkPin = issue.child("user_pin").getValue().toString();
                                String name = issue.child("user_name").getValue().toString ();
                                String surname = issue.child("user_surname").getValue().toString ();
                                String balance = issue.child("user_balance").getValue().toString ();


                                if(checkPin.equalsIgnoreCase ( user_pin )) {
                                    Toast.makeText ( getActivity ( ).getApplicationContext ( ) , "Login successful" , Toast.LENGTH_SHORT ).show ( );

                                    Intent intent = new Intent ( getActivity ().getApplicationContext (), Oparations.class );
                                    intent.putExtra ( "userPhone", user_phone );
                                    intent.putExtra ( "userName", name );
                                    intent.putExtra ( "userSurname", surname );
                                    intent.putExtra ( "userBalance", balance );

                                    startActivity ( intent );

                                }else{
                                    pin.setText ( "" );
                                    phoneNumber.setText ( "" );
                                    Toast.makeText ( getActivity ( ).getApplicationContext ( ) , "Wrong password" , Toast.LENGTH_SHORT ).show ( );

                                }
                            }


                        }
                        else{

                            Toast toast = Toast.makeText(

                                    getActivity().getApplicationContext(), "USER DOES NOT EXIST", Toast.LENGTH_LONG
                            );

                            toast.show();

                        }

                    }

                    @Override
                    public
                    void onCancelled ( @NonNull @NotNull DatabaseError error ) {

                    }
                });
            }
        } );
    }
}



