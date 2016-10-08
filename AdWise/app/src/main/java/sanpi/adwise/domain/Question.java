package sanpi.adwise.domain;

import java.util.Date;

import sanpi.adwise.domain.Choice;

/**
 * Created by Teyfik on 28.09.2016.
 */

public class Question {

    private int id;
    private String question_title;
    private int subcategory_id;
    private Date creation_time;
    private Date end_time;
    private Choice choice_1;
    private Choice choice_2;
    private Choice choice_3;
    private Choice choice_4;

    public Question(int id, String question_title, int subcategory_id, Date creation_time, Date end_time,
                    Choice choice_1, Choice choice_2, Choice choice_3, Choice choice_4){
        this.id = id;
        this.question_title = question_title;
        this.subcategory_id = subcategory_id;
        this.creation_time = creation_time;
        this.end_time = end_time;
        this.choice_1 = choice_1;
        this.choice_2 = choice_2;
        this.choice_3 = choice_3;
        this.choice_4 = choice_4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public Choice getChoice_1() {
        return choice_1;
    }

    public void setChoice_1(Choice choice_1) {
        this.choice_1 = choice_1;
    }

    public Choice getChoice_4() {
        return choice_4;
    }

    public void setChoice_4(Choice choice_4) {
        this.choice_4 = choice_4;
    }

    public Choice getChoice_3() {
        return choice_3;
    }

    public void setChoice_3(Choice choice_3) {
        this.choice_3 = choice_3;
    }

    public Choice getChoice_2() {
        return choice_2;
    }

    public void setChoice_2(Choice choice_2) {
        this.choice_2 = choice_2;
    }
}
