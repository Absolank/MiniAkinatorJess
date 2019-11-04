package com.iiita.models;

public enum Attribute
{
    NAME("name", 0),
    GENDER("gender", 1),
    JOB("job", 2),
    HOUSE("house", 3),
    WAND("wand", 4),
    PATRONUS("patronus", 5),
    SPECIES("species", 6),
    BLOOD_STATUS("blood_status", 7),
    HAIR_COLOR("hair_color", 8),
    EYE_COLOR("eye_color", 9);

    private String name;
    private Integer i;
    private String question;
    Attribute(String name, Integer i) {
        this.name = name;
        this.i = i;
        StringBuilder sb =  new StringBuilder("Is the character's");
        String[] temp = name.split("_");
        for(String s : temp) {
            sb.append(" ").append(s);
        }
        sb.append(": ");
        this.question = sb.toString();
    }
    @Override
    public String toString() {
        return name;
    }
    public Integer getIndex() {
        return i;
    }
    public String getQuestion(){
        return question;
    }
}
