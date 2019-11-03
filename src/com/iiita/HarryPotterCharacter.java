package com.iiita;

import jess.JessException;
import jess.RU;
import jess.Value;
import jess.ValueVector;

import java.io.Serializable;

public class HarryPotterCharacter implements Serializable {
    String name;
    String gender;
    ValueVector job;
    String house;
    String wand;
    String patronus;
    String species;
    String bloodStatus;
    String hairColor;
    String eyeColor;
    ValueVector loyalty;
    ValueVector skills;

    public HarryPotterCharacter(String name, String gender, String job, String house, String wand, String patronus, String species, String bloodStatus, String hairColor, String eyeColor, String loyalty, String skills) throws JessException {
        this.name = name;
        this.gender = gender;
        this.job = new ValueVector();
        String[] l = job.split("[|]");
        for (String s : l) this.job.add(new Value(s.trim(), RU.STRING));
        this.house = house;
        this.wand = wand;
        this.patronus = patronus;
        this.species = species;
        this.bloodStatus = bloodStatus;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.loyalty = new ValueVector();
        l = loyalty.split("[|]");
        for (String s : l) {
            this.loyalty.add(new Value(s.trim(), RU.STRING));
        }
        this.skills = new ValueVector();
        l = skills.split("[|]");
        for (String s : l) {
            this.skills.add(new Value(s.trim(), RU.STRING));
        }
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public ValueVector getJob() {
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

    public ValueVector getloyalty() {
        return loyalty;
    }

    public ValueVector getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return "(character (name \"" + name + "\") (gender \"" + gender + "\") (job \"" + job + "\") (house \"" + house + "\") (wand \"" + wand + "\") (species \"" + species + "\") (blood_status \"" + bloodStatus + "\") (hair_color \"" + hairColor + "\") (eye_color \"" + eyeColor +"\") (slot \"" + loyalty + "\") (slot \"" + skills + "\"))";
    }
}
