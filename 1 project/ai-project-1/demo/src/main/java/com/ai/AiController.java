package com.ai;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/qna")
public class AiController {

	private final QnaService qnaService;

	public AiController(QnaService qnaService) {
		this.qnaService = qnaService;
	}

	@PostMapping("/ask")
	public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> payload) {
		String question = payload.get("question");
		String answer = qnaService.getAnswer(question);
		return ResponseEntity.ok(answer);
	}
}

