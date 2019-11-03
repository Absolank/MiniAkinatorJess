package com.iiita;

import jess.JessException;
import jess.RU;
import jess.Value;
import jess.ValueVector;

import java.io.Serializable;

public class HarryPotterCharacter implements Serializable {
    String name;
    String gender;
    String job;
    String house;
    String wand;
    String patronus;
    String species;
    String bloodStatus;
    String hairColor;
    String eyeColor;

    public HarryPotterCharacter(String name, String gender, String job, String house, String wand, String patronus, String species, String bloodStatus, String hairColor, String eyeColor) throws JessException {
        this.name = name;
        this.gender = gender;
        this.job = job;
        this.house = house;
        this.wand = wand;
        this.patronus = patronus;
        this.species = species;
        this.bloodStatus = bloodStatus;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getJob() {
        return job;
    }

    public String getHouse() {
        return house;
    }

    public String getWand() {
        return wand;
    }

    public String getPatronus() {
        return patronus;
    }

    public String getSpecies() {
        return species;
    }

    public String getBloodStatus() {
        return bloodStatus;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    @Override
    public String toString() {
        return "(character (name \"" + name + "\") (gender \"" + gender + "\") (job \"" + job + "\") (house \"" + house + "\") (wand \"" + wand + "\") (species \"" + species + "\") (blood_status \"" + bloodStatus + "\") (hair_color \"" + hairColor + "\") (eye_color \"" + eyeColor +"\"))";
    }
}
