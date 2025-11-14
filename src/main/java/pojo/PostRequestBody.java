package pojo;

import java.util.*;

public class PostRequestBody {
	
	private String name;
	private String job;
	
	private List<String> languages = new ArrayList<>();
	
	private List<CityRequest> cityRequests;
	
	
	public List<CityRequest> getCityRequests() {
		return cityRequests;
	}
	public void setCityRequests(List<CityRequest> cityRequests) {
		this.cityRequests = cityRequests;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
	

}
