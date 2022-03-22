import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;

public class Main {
    static WebDriver driver = webDriver();
    public static void main(String[] args) throws InterruptedException {
        Actions actions = new Actions(driver);
        driver.get("https://customer.easypark.net/history/sv");
        driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys("707381831");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Elky9901" + Keys.ENTER);
        Thread.sleep(2000);
        driver.get("https://customer.easypark.net/history/sv");
        Thread.sleep(1000);
        /*while(true){
            try {
                WebElement element = driver.findElement(By.className("expandAll"));
                actions.moveToElement(element).click().build().perform();
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("No more pages found");
                break;
            }
        }*/
        List<WebElement> elements = driver.findElements(By.className("parking"));
        for(int i = 0; i<elements.size();i++){
            List<WebElement> parkTime = elements.get(i).findElements(By.className("parkTime"));
            for(int c = 0; c<parkTime.size();c++){
                System.out.println(parkTime.get(c).getText());
            }
            actions.moveToElement(elements.get(i)).click().build().perform();
            WebElement links = elements.get(i).findElement(By.xpath("//*[contains(@id,'receiptLink')]"));
            returnYearAndMonth(elements.get(i).getText());
            //actions.moveToElement(links).click().build().perform();
            Thread.sleep(2000);
        }
    }
    public static String returnYearAndMonth(String s){
        String splitted[] = s.split("\n");
        String yearAndDate[]
        return "";
    }
    public static WebDriver webDriver(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Grebyn\\IdeaProjects\\kvitton\\chromedriver.exe");
        String downloadFilepath = "C:\\Users\\Grebyn\\IdeaProjects\\kvitton\\Filer\\Elliot";
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("download.default_directory", downloadFilepath);
        // adding capabilities to browser
        ChromeOptions o = new ChromeOptions();
        o.setExperimentalOption("prefs", p);
        ChromeDriver driver = new ChromeDriver(o);
        return driver;
    }
}
