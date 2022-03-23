import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    static String currentDate = "2022-03";
    static String userName = "705649809";
    static String password = "lukaspansarvagn2008";
    static WebDriver driver = webDriver();
    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
     getLinks();
    }
    public static void downloadFiles() throws InterruptedException, IOException {
        ArrayList<String> rows = getRows();
        login();
        for(int i = 0; i<rows.size();i++){
            Actions actions = new Actions(driver);
            String row[] = rows.get(i).split("zz");
            String date = row[1];
            String link = row[0];
            if(!date.contentEquals(currentDate)){
                System.out.println("New driver, current date is " + date + ", previous " + currentDate);
                currentDate = date;
                driver.close();
                driver = newWebDriver();
                login();
            }
            else{
                System.out.println("Not skipping, same date");
            }
            driver.get(link);
            Thread.sleep(1000);
        }
    }
    public static void login() throws InterruptedException {
        driver.get("chrome://settings/clearBrowserData");
        Thread.sleep(5000);
        driver.get("https://customer.easypark.net/history/sv");
        driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys(userName);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password + Keys.ENTER);
        Thread.sleep(2000);
    }
    public static ArrayList getRows() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\ellio\\IdeaProjects\\Kvitton\\files\\Elias.txt"));
        ArrayList<String> list = new ArrayList<String>();
        String row;
        while ((row = csvReader.readLine()) != null) {
            list.add(row);
        }
        return list;
    }
    public static void getLinks() throws InterruptedException, IOException {
        Actions actions = new Actions(driver);
        login();
        driver.get("https://customer.easypark.net/history/sv");
        Thread.sleep(1000);
        while(true){
            try {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
                WebElement element = driver.findElement(By.className("expandAll"));
                actions.moveToElement(element).click().build().perform();
                Thread.sleep(50);
            }catch (Exception e){
                System.out.println("No more pages found");
                break;
            }
        }
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
        List<WebElement> elements = driver.findElements(By.className("parking"));
        ArrayList<String> downloadLinks = new ArrayList<String>();
        for(int i = 0; i<elements.size();i++){
            try {
                WebElement parkTime = elements.get(i).findElements(By.className("parkTime")).get(0);
                String date = returnYearAndMonth(parkTime.getText());
                String dateDaily = returnYearAndMonthAndDate(parkTime.getText());
                JavascriptExecutor Scrool = (JavascriptExecutor) driver;
                elements.get(i).findElement(By.className("parkarea")).click();
                Scrool.executeScript("window.scrollBy(0,85)", "");
                WebElement links = elements.get(i).findElement(By.xpath("//*[contains(@id,'receiptLink')]"));
                downloadLinks.add(links.getAttribute("href") + "zz" + date);
                System.out.println(links.getAttribute("href") + "zz" + date + " | " + dateDaily);
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("Skipping last");
            }
            Thread.sleep(50);
        }
        for(int i = 0; i< downloadLinks.size();i++){
            PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\ellio\\IdeaProjects\\Kvitton\\files\\Elias.txt", true));
            pw.println(downloadLinks.get(i));
            pw.close();
        }
    }
    public static String returnYearAndMonth(String s){
        String time[] = s.split("-");
        String yearAndMonth = time[0] + "-" + time[1];
        return yearAndMonth;
    }
    public static String returnYearAndMonthAndDate(String s){
        String time[] = s.split("-");
        String yearAndMonth = time[0] + "-" + time[1] + "-" + time[2];
        return yearAndMonth;
    }
    public static WebDriver webDriver(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ellio\\IdeaProjects\\Kvitton\\chromedriver.exe");
        File file = new File("C:\\Users\\ellio\\IdeaProjects\\Kvitton\\files\\pdf\\" + currentDate);
        boolean dirCreated = file.mkdir();
        String downloadFilepath = "C:\\Users\\ellio\\IdeaProjects\\Kvitton\\files\\pdf\\" + currentDate;
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("download.default_directory", downloadFilepath);
        p.put("profile.default_content_settings.popups", 0);
        // adding capabilities to browser
        ChromeOptions o = new ChromeOptions();
        o.setExperimentalOption("prefs", p);
        o.addArguments("disable-popup-blocking");
        ChromeDriver driver = new ChromeDriver(o);
        return driver;
    }
    public static WebDriver newWebDriver(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ellio\\IdeaProjects\\Kvitton\\chromedriver.exe");
        File file = new File("C:\\Users\\ellio\\IdeaProjects\\Kvitton\\files\\pdf\\" + currentDate);
        boolean dirCreated = file.mkdir();
        String downloadFilepath = "C:\\Users\\ellio\\IdeaProjects\\Kvitton\\files\\pdf\\" + currentDate;
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("download.default_directory", downloadFilepath);
        p.put("profile.default_content_settings.popups", 0);
        // adding capabilities to browser
        ChromeOptions o = new ChromeOptions();
        o.setExperimentalOption("prefs", p);
        o.addArguments("disable-popup-blocking");
        ChromeDriver driver = new ChromeDriver(o);
        return driver;
    }
}
