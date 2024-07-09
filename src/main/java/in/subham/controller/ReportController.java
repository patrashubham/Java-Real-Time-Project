package in.subham.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.subham.entity.CitizenPlan;
import in.subham.request.SearchRequest;
import in.subham.request.SearchRequest;
import in.subham.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	
	
	@GetMapping("/pdf")
	public void exposePdf(HttpServletResponse response) throws Exception {
		
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment;filename=plans.pdf");
		 service.exportPdf(response);
	
	}
	
	
	@GetMapping("/excel")
	public void exposeExcel(HttpServletResponse response) throws Exception {
		
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=plans.xls");
		service.exportExcel(response);

	}
	
	
	@PostMapping("/search1")
	public String handleSearch(@ModelAttribute("search") SearchRequest request, Model model) {
		
		System.out.println(request);
		
		List<CitizenPlan> plans = service.search(request);
		model.addAttribute("plans", plans);
		//model.addAttribute("search",search);
		init(model);
		
		return "index";
	}
	
	
	
	
	/** This Method Is Used to LOad tHe index page
	 * 
	 * @param model
	 * @return String
	 */
	
	@GetMapping("/")
	public String indexPage(Model model) {
		
		//SearchRequest searchObj=new SearchRequest();
		model.addAttribute("search",new  SearchRequest());
	    
	    init(model);
	   
	 
		
		return "index";
	}





	private void init(Model model) {
		//model.addAttribute("search",new  SearchRequest());
		model.addAttribute("names", service.getplanName());
	    model.addAttribute("status", service.getplanStatuses());
	}
	
}
