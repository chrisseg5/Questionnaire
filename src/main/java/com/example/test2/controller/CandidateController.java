package com.example.test2.controller;

import com.example.test2.exception.ResourceNotFoundException;
import com.example.test2.model.Answer;
import com.example.test2.model.Candidate;
import com.example.test2.model.Questionnaire;
import com.example.test2.repository.CandidateRepository;
import com.example.test2.repository.QuestionnaireRepository;
import com.example.test2.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @PostMapping("/newCandidate")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate, @RequestParam Long questionnaireId) {
        // Set the questionnaire id for the candidate
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(questionnaireId);
        candidate.setQuestionnaire(questionnaire);

        // Save the candidate to the database
        Candidate savedCandidate = candidateRepository.save(candidate);

        return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }


    @PostMapping("/new/candidate/{id}")
    public ResponseEntity<Candidate> createCandidate(@PathVariable("id") long id, @RequestBody Candidate candidateRequest) {
        Candidate candidate = questionnaireRepository.findById(id).map(t -> {
            candidateRequest.setQuestionnaire(t);
            return candidateRepository.save(candidateRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("question", "Id", id));

        return new ResponseEntity<>(candidate, HttpStatus.CREATED);
    }

}
