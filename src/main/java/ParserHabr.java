import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class ParserHabr {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.setProperty("webdriver.chrome.driver","selenium\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://habr.com/ru/hub/java/top/yearly/");

        for (int i = 2; i < 10; i++) {
            Document document = Jsoup.parse(driver.getPageSource());
            Elements elements = document.getElementsByClass( "tm-article-snippet__title-link");
            elements.forEach(e-> System.out.println(e.text()));
            //WebElement element = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/main/div/div/div/div[1]/div/div[3]/div/div[2]/div/div[2]/a["+i+"]"));
            WebElement element1 = driver.findElement(By.className("tm-pagination__page-group"));
            element1 = element1.findElement(By.linkText(String.valueOf(i)));
            element1.click();
            Thread.sleep(2000);
            System.out.println("Page "+ i);
            System.out.println("************************");
        }

//        Парсинг с помощью JSOUP
//        Document document = Jsoup.connect("https://habr.com/ru/hub/java/top/yearly/").get();
//        Elements elements = document.getElementsByClass( "tm-article-snippet__title-link");
//        elements.forEach(e-> System.out.println(e.text()));



    }
}
