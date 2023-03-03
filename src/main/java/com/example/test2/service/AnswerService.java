package com.example.test2.service;

import com.example.test2.model.Answer;
import com.example.test2.model.Question;
import com.example.test2.repository.AnswerRepository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
public interface AnswerService {

    Answer saveAnswer (Answer answer);

}
