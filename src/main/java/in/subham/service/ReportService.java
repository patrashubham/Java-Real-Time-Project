package in.subham.service;

import java.util.List;

import in.subham.entity.CitizenPlan;
import in.subham.request.SearchRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

	public List<String> getplanName();
	
	public List<String> getplanStatuses();
	
	public  List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel(HttpServletResponse response) throws Exception;
	
	public boolean exportPdf(HttpServletResponse response) throws Exception;
}
