package com.example.mpesa;

public
class Users {
    String user_name;
    String user_surname;
    String user_phone;
    String user_id_number;
    String user_pin;
    String user_balance;

    public
    Users ( ) {
    }

    public
    Users ( String user_name, String user_surname, String user_phone, String user_id_number, String user_pin, String user_balance ) {
        this.user_name      = user_name;
        this.user_surname   = user_surname;
        this.user_phone     = user_phone;
        this.user_id_number = user_id_number;
        this.user_pin       = user_pin;
        this.user_balance   = user_balance;
    }

    public
    String getUser_name ( ) {
        return user_name;
    }

    public
    void setUser_name ( String user_name ) {
        this.user_name = user_name;
    }

    public
    String getUser_surname ( ) {
        return user_surname;
    }

    public
    void setUser_surname ( String user_surname ) {
        this.user_surname = user_surname;
    }

    public
    String getUser_phone ( ) {
        return user_phone;
    }

    public
    void setUser_phone ( String user_phone ) {
        this.user_phone = user_phone;
    }

    public
    String getUser_id_number ( ) {
        return user_id_number;
    }

    public
    void setUser_id_number ( String user_id_number ) {
        this.user_id_number = user_id_number;
    }

    public
    String getUser_pin ( ) {
        return user_pin;
    }

    public
    void setUser_pin ( String user_pin ) {
        this.user_pin = user_pin;
    }

    public
    String getUser_balance ( ) {
        return user_balance;
    }

    public
    void setUser_balance ( String user_balance ) {
        this.user_balance = user_balance;
    }
}
