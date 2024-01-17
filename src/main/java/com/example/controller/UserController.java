package com.example.controller;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/report/pdf")
	public ResponseEntity<Object> exportFileToPDF(HttpServletResponse response) throws IOException{
		byte[] exportPDF = service.generateReportPDF();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=UserReport.pdf");
		
		OutputStream output = response.getOutputStream();
		output.write(exportPDF);
		
		output.close();
		output.flush();
		
		return new ResponseEntity<Object>("Report successfully downloaded", HttpStatus.OK);
	}
	
	@GetMapping("/report/html")
	public ResponseEntity<Object> exportFileToHTML(HttpServletResponse response) throws IOException {
		service.generateReportHTML();
		
		response.setContentType("text/html");
		response.setHeader("Content-Disposition", "attachment; filename=UserReport.html");
		
		
		return new ResponseEntity<Object>("HTML report successfully generated", HttpStatus.OK);
		
	}
	
	@GetMapping("/report/csv")
	public ResponseEntity<Object> exportFileToCSV() throws IOException {
		
		service.generateReportCSV();
		
		return new ResponseEntity<Object>("CSV report generated", HttpStatus.OK);
	}
}
