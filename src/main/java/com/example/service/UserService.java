package com.example.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.model.UserInfo;
import com.example.repository.UserInfoRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

@Service
public class UserService{

	@Autowired
	private UserInfoRepository repo;
	
	Logger log = LoggerFactory.getLogger(UserService.class);
	
	public Collection<UserInfo> getUsers(){

		return repo.findAll();
	}
	

	public byte[] generateReportPDF() {
		
		byte[] exportPDF = null;
		
		try {
			File file = ResourceUtils.getFile("classpath:UserReport.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			
			Collection<UserInfo> listUsers = getUsers();
			JRBeanCollectionDataSource jrbeanCollections = new JRBeanCollectionDataSource(listUsers);
			
			Map<String, Object> parameter = new HashedMap();
			parameter.put("Location", "Abuja");
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, jrbeanCollections);
			
			exportPDF = JasperExportManager.exportReportToPdf(jasperPrint);
			
			
		}catch(FileNotFoundException | JRException e) {
			System.err.println("Error occurred " + e.getMessage());
		}
		
		return exportPDF;
	}
	
    public void generateReportHTML() {
		
    	
		
		try {
			File file = ResourceUtils.getFile("classpath:UserReport.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			
			Collection<UserInfo> listUsers = getUsers();
			JRBeanCollectionDataSource jrbeanCollections = new JRBeanCollectionDataSource(listUsers);
			
			Map<String, Object> parameter = new HashedMap();
			parameter.put("Location", "Abuja");
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, jrbeanCollections);
			
			JasperExportManager.exportReportToHtmlFile(jasperPrint, "C:/Users/User/JaspersoftWorkspace/MyReports/UserReport.html");
			
			
		}catch(FileNotFoundException | JRException e) {
			System.err.println("Error occurred " + e.getMessage());
		}
    }
    
		public void generateReportCSV() {	    	
			
			try {
				File file = ResourceUtils.getFile("classpath:UserReport.jrxml");
				JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
				
				Collection<UserInfo> listUsers = getUsers();
				JRBeanCollectionDataSource jrbeanCollections = new JRBeanCollectionDataSource(listUsers);
				
				Map<String, Object> parameter = new HashedMap();
				parameter.put("Location", "Abuja");
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, jrbeanCollections);
				
				JRCsvExporter csvExporter = new JRCsvExporter();
				csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				csvExporter.setExporterOutput(new SimpleWriterExporterOutput("C:/Users/User/JaspersoftWorkspace/MyReports/UserReport.csv"));
				
				csvExporter.exportReport();
				
			}catch(FileNotFoundException | JRException e) {
				System.err.println("Error occurred " + e.getMessage());
			}
		
	}
    
    
}
