package com.example.test2.service.impl;

import com.example.test2.dto.index.QuenstionnaireIndexDto;
import com.example.test2.dto.mini.QuestionnaireMiniDto;
import com.example.test2.exception.ResourceNotFoundException;
import com.example.test2.model.Questionnaire;
import com.example.test2.repository.QuestionnaireRepository;
import com.example.test2.service.QuestionService;
import com.example.test2.service.QuestionnaireService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private QuestionService questionService;
    private Long mainQuestionnaireId;


    @Override
    public Questionnaire createQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }

    @Override
    public QuenstionnaireIndexDto toDTO(Questionnaire questionnaire) {
        QuenstionnaireIndexDto dto = new QuenstionnaireIndexDto();
        BeanUtils.copyProperties(questionnaire, dto);
        dto.setName(questionnaire.getName());
        dto.setId(questionnaire.getId());
        dto.setGrading(questionnaire.getGrading());
        return dto;
    }
    @Override
    public QuestionnaireMiniDto toMiniDto(Questionnaire questionnaire) {
        QuestionnaireMiniDto dto = new QuestionnaireMiniDto();
        BeanUtils.copyProperties(questionnaire, dto);
        dto.setId(questionnaire.getId());
        dto.setName(questionnaire.getName());
        return dto;
    }
    @Override
    public List<QuenstionnaireIndexDto> allQuestionnaires() {
        List<QuenstionnaireIndexDto> questionnaires = new ArrayList<>();
        questionnaireRepository.findAll().stream().forEach(questionnaire -> questionnaires.add(toDTO(questionnaire)));
        return questionnaires;
    }

    @Override
    public List<QuestionnaireMiniDto> allQuestionnairesMini() {
        List<QuestionnaireMiniDto> questionnaires = new ArrayList<>();
        questionnaireRepository.findAll().stream().forEach(questionnaire -> questionnaires.add(toMiniDto(questionnaire)));
        return questionnaires;
    }

    @Override
    public Questionnaire getQuestionnaireById(long id) {
        return questionnaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("questionnaire", "Id", id));
    }

    @Override
    public Questionnaire updateQuestionnaire(long id) {
        return questionnaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("questionnaire", "Id", id));
    }



    @Override
    public Long getMainQuestionnaireId() {
        return mainQuestionnaireId;
    }

    @Override
    public void setMainQuestionnaireId(Long id) {
        Questionnaire questionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid questionnaire ID: " + id));
        mainQuestionnaireId = questionnaire.getId();
    }



}
