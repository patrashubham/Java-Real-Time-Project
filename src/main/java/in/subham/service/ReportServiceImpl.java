package in.subham.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.subham.entity.CitizenPlan;
import in.subham.repo.CitizenPlanRepository;
import in.subham.request.SearchRequest;
import in.subham.util.EmailUtil;
import in.subham.util.ExcelGenerator;
import in.subham.util.PdfGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private CitizenPlanRepository planRepo;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public List<String> getplanName() {
//		List<String> planNames = planRepo.getPlanNames();
//		return planNames;
		
		return planRepo.getPlanNames();
	}

	@Override
	public List<String> getplanStatuses() {

		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		CitizenPlan entity=new CitizenPlan();
		//BeanUtils.copyProperties(request, entity);  //Directly i should not Copy Like this becoz bug will be happen
	
		if(null!=request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}
			
			if(null!=request.getPlanStatus()&& !"".equals(request.getPlanStatus())){
				entity.setPlanStatus(request.getPlanStatus());
			
			}
		if(null!=request.getGender()&& !"".equals(request.getGender())){
			entity.setGender(request.getGender());
		}
		
		
		if(null!=request.getStartDate()&& !"".equals(request.getStartDate())) {
			String startDate = request.getStartDate();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			  
			//convert String to Local Date
			 LocalDate localDate = LocalDate.parse(startDate, formatter);
			entity.setPlanStartDate(localDate);
		}
		
		
		if(null!=request.getEndDate()&& !"".equals(request.getEndDate())) {
			String endDate = request.getEndDate();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			  
			//convert String to Local Date
			 LocalDate localDate = LocalDate.parse(endDate, formatter);
			entity.setPlanEndDate(localDate);
		}
		
		
		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {

		List<CitizenPlan> plans = planRepo.findAll();
		
		File f=new File("plans.xls");
		
		excelGenerator.generator(response, plans,f);
		
		String subject="Text mail subject";
		String body="<h1>Text mail body</h1>";
		String to="psubhu69@gmail.com";
		
		emailUtil.sendMail(subject, body, to,f);
 
		f.delete();
		
		
		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		
		List<CitizenPlan> plans = planRepo.findAll();
		
		File f=new File("plans.pdf");
		
		pdfGenerator.generate(response, plans,f);
		
		String subject="Text mail subject";
		String body="<h1>Text mail body</h1>";
		String to="psubhu69@gmail.com";
		
		emailUtil.sendMail(subject, body, to,f);
 
		f.delete();
		

		return true;
	}

}
