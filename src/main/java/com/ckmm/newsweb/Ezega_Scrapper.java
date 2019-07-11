package com.ckmm.newsweb;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ckmm.domain.News;

@Controller
@RequestMapping(value = "/ezega")
public class Ezega_Scrapper {
private List<News> newsList;
	
	@GetMapping
	public String Request(Model model) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException{
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		 WebDriver driver = new ChromeDriver(); 	
		 
		 newsList = new ArrayList<News>();
		 
		//Ethiopian Reporter Page Scrapper
        String appUrl = "https://www.ezega.com/News";
        driver.get(appUrl);
        String title = driver.getTitle();
        List<WebElement> elements=driver.findElements(By.className("item"));
        ArrayList<String> newsLinks=new ArrayList<String>();
        for(WebElement element:elements){
            newsLinks.add(element.findElement(By.className("post-title")).findElement(By.tagName("a")).getAttribute("href"));
        }
        System.out.println(newsLinks.toString());
        for(String newsLink:newsLinks){

            System.out.println(newsLink);
            driver.navigate().to(newsLink);

            String newsTile=driver.findElement(By.className("post-title")).getText();
            System.out.println("news Title: " + newsTile);
            String newsCatagory=driver.findElement(By.className("post-categories")).getText();
            System.out.println("News Catagory: "+newsCatagory);
            String newsDetail="";
            for(WebElement paragraph:driver.findElements(By.tagName("p"))){
                newsDetail+=paragraph.getText();
            }
            System.out.println("Detail: "+ newsDetail);
            String newsDate=driver.findElement(By.className("post-created")).getText();
            System.out.println("Date Created On: "+newsDate);
            System.out.println(newsLinks.toString());
            newsList.add(new News(newsTile,newsCatagory,newsDetail));
        }
        model.addAttribute("newsList", newsList);
		return "ezega";
	}

}
