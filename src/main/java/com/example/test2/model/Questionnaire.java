package com.example.test2.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "questionnaire")
@Validated
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @NotEmpty
    String name;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "questionnaire_questions",
            joinColumns = { @JoinColumn(name = "questionnaire_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") })
    private List<Question> questionList = new ArrayList<>();

    public Questionnaire() {
    }

    public Questionnaire( String name, Date date) {

        this.name = name;
        this.date = date;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
    public void addQuestion(Question question) {
        this.questionList.add(question);
        question.getQuestionnaires().add(this);
    }
    public void remove (long questionId){
        Question question = this.questionList.stream().filter(t -> t.getId() == questionId).findFirst().orElse(null);
        if (question != null){
            this.questionList.remove(question);
            question.getQuestionnaires().remove(this);
        }
    }

}
