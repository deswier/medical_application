package com.myapplication.model;

import android.graphics.Bitmap;
import androidx.compose.runtime.Composable;
import com.myapplication.exception.DataException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Calendar;

@Getter
@Setter
@Composable
public class Profile {
    public BigInteger user_id;
    public Bitmap photo;
    public FullName name;
    public Calendar dateOfBirth;
    public char gender;

    public Profile(BigInteger user_id, FullName name, Calendar dateOfBirth, char gender) throws DataException {
        this.user_id = user_id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photo = null;
        //TODO get photo;
        setGender(gender);
    }

    public Profile() throws DataException {
        emptyProfile();
    }

    private void emptyProfile() throws DataException {
        photo = null;
        name = new FullName();
        dateOfBirth = null;
    }

    public void clearProfile() throws DataException {
        emptyProfile();
    }

    public Boolean isEmptyProfile() throws DataException {
        return photo == null && name.equals(new FullName()) && dateOfBirth == null;
    }

    public Profile(FullName name, Calendar dateOfBirth, char gender, Bitmap photo) throws DataException {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photo = photo;
        if (isGender(gender)) this.gender = gender;
        else throw new DataException("Incorrect gender " + gender + ". Gender must be 'M' for male or 'F' for female");
    }

    public void setGender(char gender) throws DataException {
        if (isGender(gender)) this.gender = gender;
        else throw new DataException("Incorrect gender " + gender + ". Gender must be 'M' for male or 'F' for female");
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    private boolean isGender(char gender) {
        return gender == 'M' || gender == 'F';
    }

    public String getGenderToString() {
        if (gender == 'M') return "Мужской";
        else return "Женский";
    }
}