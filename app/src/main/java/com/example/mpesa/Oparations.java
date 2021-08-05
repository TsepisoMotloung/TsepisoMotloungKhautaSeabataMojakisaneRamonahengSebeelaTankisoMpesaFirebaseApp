package com.example.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public
class Oparations extends AppCompatActivity implements ValueEventListener {


    Button send;

    EditText recPhone, reference, amount;

    TextView balance;
    TextView name;
    TextView surname, myNumber;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance ();
    DatabaseReference users = FirebaseDatabase.getInstance ().getReference ().child("user");

    //String number;
    String newMyBal;
    String phoneNumber, user_balance;
    double newRecBal;

    int checkUserPhone, checkUserBalance;


    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_oparations );

        Intent receiveIntent = getIntent ( );
        phoneNumber = receiveIntent.getStringExtra("userPhone");
        String user_name = receiveIntent.getStringExtra("userName");
        String user_surname = receiveIntent.getStringExtra("userSurname");
        user_balance = receiveIntent.getStringExtra("userBalance");



        //receiver
        recPhone = (EditText)findViewById ( R.id.recNumber );
        reference = (EditText)findViewById ( R.id.reference );
        amount = (EditText )findViewById ( R.id.amount );


        //my credenctials
        balance  = (TextView)findViewById ( R.id.balance );
        name  = (TextView)findViewById ( R.id.name );
        surname  = (TextView)findViewById ( R.id.surname );
        myNumber  = (TextView)findViewById ( R.id.usernumber );
        send = (Button)findViewById ( R.id.send );


        myNumber.setText ( phoneNumber );
        name.setText ( user_name );
        surname.setText ( user_surname );
        balance.setText ( user_balance );


        send.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {

                String receiverPhone = recPhone.getText ().toString ();
                String moneyToSend = amount.getText ().toString ();

                double money = Double.parseDouble(moneyToSend);
                double bal = Double.parseDouble ( user_balance );

                if(bal >= money){
                    Toast.makeText ( Oparations.this , "sent" , Toast.LENGTH_SHORT ).show ( );

                    int exist = checkRecieverPhone();

                    if ( exist == 0 ){
                        Toast.makeText ( Oparations.this , "does not exist" , Toast.LENGTH_SHORT ).show ( );

                    }

                    else if(exist == 1){
                        Toast.makeText ( Oparations.this , " exist" , Toast.LENGTH_SHORT ).show ( );
                        sendMoney (phoneNumber, receiverPhone);
                    }

                }else {
                    Toast.makeText ( Oparations.this , "not enough money "+user_balance , Toast.LENGTH_SHORT ).show ( );
                }


            }

        } );

    }



    @Override
    public
    void onDataChange ( @NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot ) {

    }

    @Override
    public
    void onCancelled ( @NonNull @org.jetbrains.annotations.NotNull DatabaseError error ) {

    }

    @Override
    protected
    void onStart ( ) {
        super.onStart ( );
        //childReference.addValueEventListener ( this );
    }












    private
    int checkRecieverPhone ( ) {

        String rec_phone = recPhone.getText ().toString ();

        users.orderByChild("user_phone").equalTo( String.valueOf ( rec_phone ) ).addValueEventListener( new ValueEventListener ( ) {
            @Override
            public
            void onDataChange ( @NonNull @NotNull DataSnapshot snapshot ) {

                if ( snapshot.exists () ){
                    checkUserPhone = 1;

                    Toast.makeText ( Oparations.this , "Phone number exists" , Toast.LENGTH_SHORT ).show ( );

                }else{
                    checkUserPhone = 0;
                    Toast.makeText ( Oparations.this , "The phone number does not exist" , Toast.LENGTH_SHORT ).show ( );
                }

            }



            @Override
            public
            void onCancelled ( @NonNull @NotNull DatabaseError error ) {

            }
        });
    return checkUserPhone;
    }

    private
    void sendMoney ( String phoneNumber, String receiverPhone) {
        //

        String moneyToSend = amount.getText ().toString ();
        double bal = Double.parseDouble ( user_balance );
        double money = Double.parseDouble(moneyToSend);
        double resBal,recResBal;



        HashMap User = new HashMap (  );

        double recBal = receiverBalance ( receiverPhone );

        recResBal  = recBal + money;

        //Toast.makeText ( this , (int) newRecBal , Toast.LENGTH_SHORT ).show ( );

        User.put("user_balance", recResBal);
        users.child ( receiverPhone ).updateChildren(User);

        //String rec_phone = recPhone.getText ().toString ();






        resBal  = bal - money;

        User.put("user_balance", resBal);



        users.child ( phoneNumber ).updateChildren(User).addOnCompleteListener ( new OnCompleteListener ( ) {
            @Override
            public
            void onComplete ( @NonNull @NotNull Task task ) {
                if(task.isSuccessful ()){
                    Toast.makeText ( Oparations.this , "successful update" , Toast.LENGTH_SHORT ).show ( );
                }else{
                    Toast.makeText ( Oparations.this , "unsuccessful update" , Toast.LENGTH_SHORT ).show ( );
                }
            }
        });


        //users.orderByChild("user_phone").equalTo( String.valueOf ( rec_phone ) ).addValueEventListener( new ValueEventListener ( ) {
    }

    private
    double receiverBalance ( String receiverPhone ) {

        users.orderByChild("user_phone").equalTo( receiverPhone ).addValueEventListener( new ValueEventListener() {

            @Override
            public
            void onDataChange ( @NonNull @NotNull DataSnapshot snapshot ) {

                if ( snapshot.exists ( ) ) {

                    for (DataSnapshot issue : snapshot.getChildren () ) {

                        String recBalance = issue.child("user_balance").getValue().toString ();
                        Double newRecBal = Double.parseDouble ( recBalance );


                    }








                    /*users.child ( receiverPhone ).updateChildren(User).addOnCompleteListener ( new OnCompleteListener ( ) {
                        @Override
                        public
                        void onComplete ( @NonNull @NotNull Task task ) {

                            if(task.isSuccessful ()){

                                Toast.makeText ( Oparations.this , "successful receiver balance update" , Toast.LENGTH_SHORT ).show ( );

                            }else{

                                Toast.makeText ( Oparations.this , "unsuccessful receiver balance update" , Toast.LENGTH_SHORT ).show ( );

                            }
                        }

                    });*/

                }else{
                    //checkUserBalance = 0;
                }

            }

            @Override
            public
            void onCancelled ( @NonNull @NotNull DatabaseError error ) {

            };
        });
        return newRecBal;
    }

}



//userRef.child('mike').update({'dateOfBirth': moment(value.dateOfBirth).toDate().getTime()})