package parser;

import db.FlatDAO;
import db.StatisticsDAO;
import db.PostgreConnection;
import model.Constans;
import model.FlatAvito;
import model.StatisticsFlatAvito;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParserAvito {

    public static void parse() throws SQLException {
        System.setProperty("webdriver.chrome.driver","selenium\\chromedriver.exe");
        FlatDAO flatDb = new FlatDAO();
        StatisticsDAO statisticsDAO = new StatisticsDAO();

        for (int i0 = 0; i0 < FlatAvito.link.size(); i0++) {
            WebDriver driver = new ChromeDriver();
            String city = (String) FlatAvito.link.keySet().toArray()[i0];
            driver.get(FlatAvito.link.get(city));
            List<FlatAvito> fullFlatAvitoList = new ArrayList<>();
            int n = 0;
            int prevNumberPage = 0;
            int numberPage = 0;
            for (int i = 0; n < 10; i++) {
//            for (int i = 0; i < 2; i++) {
                List<FlatAvito> flatAvitoList = new ArrayList<>();
                Document document = Jsoup.parse(driver.getPageSource());
                Elements elements = document.getElementsByClass( "iva-item-body-KLUuy");
                elements.forEach(e-> {
                    flatAvitoList.add(new FlatAvito(
                            e.getElementsByAttributeValue("class","iva-item-priceStep-uq2CQ").text(),
                            e.getElementsByAttributeValue("class","iva-item-titleStep-pdebR").text(),
                            city,
                            e.getElementsByAttributeValue("class","geo-root-zPwRk iva-item-geo-_Owyg").text(),
                            e.select("div.iva-item-titleStep-pdebR > a").attr("href")
                    ));
                });
                try {
                    WebElement webElement2 = driver.findElement(By.className("pagination-item_active-NcJX6"));
                    System.out.print(webElement2.getText()+" ");
                    numberPage = Integer.parseInt(webElement2.getText());
                    if (prevNumberPage == numberPage ) n++;
                    else {
                        n = 0;
                        prevNumberPage = numberPage;
                    }
                    WebElement webElement = driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div[3]/div[3]/div[6]/div[1]/span[11]"));
                    webElement.click();
                }catch (Exception e){
                    try {
                        WebElement webElement = driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div[3]/div[3]/div[6]/div[1]/span[9]"));
                        webElement.click();
                    }catch (Exception ex) {
                        System.out.println("continue");
                        continue;
                    }
                }

                try {
                    PostgreConnection.getFlatAvitoConnection().setAutoCommit(false);

                    List<FlatAvito> finalFlatAvitoList = new ArrayList<>();
                    finalFlatAvitoList.addAll(flatAvitoList);
                    finalFlatAvitoList = finalFlatAvitoList.stream().distinct().collect(Collectors.toList());
                    fullFlatAvitoList.addAll(flatAvitoList);
                    finalFlatAvitoList.forEach(flatDb::insert);
                    flatAvitoList.clear();
                    finalFlatAvitoList.clear();
                    PostgreConnection.getFlatAvitoConnection().commit();
                } catch (Exception e) {
                    PostgreConnection.getFlatAvitoConnection().rollback();
                    e.printStackTrace();
                }
            }
            try {
                fullFlatAvitoList = fullFlatAvitoList.stream().distinct().collect(Collectors.toList());
                PostgreConnection.getFlatAvitoConnection().setAutoCommit(false);
                statisticsDAO.insert(new StatisticsFlatAvito(
                        Constans.dollar,
                        FlatAvito.calculateAverage(fullFlatAvitoList, "pricePerMetr",0),
                        FlatAvito.calculateMedian(fullFlatAvitoList, "pricePerMetr",0),
                        FlatAvito.calculateAverage(fullFlatAvitoList, "price",0),
                        FlatAvito.calculateMedian(fullFlatAvitoList, "price",0),
                        FlatAvito.calculateAverage(fullFlatAvitoList, "price",1),
                        FlatAvito.calculateMedian(fullFlatAvitoList, "price",1),
                        FlatAvito.calculateAverage(fullFlatAvitoList, "price",2),
                        FlatAvito.calculateMedian(fullFlatAvitoList, "price",2),
                        FlatAvito.calculateAverage(fullFlatAvitoList, "price",3),
                        FlatAvito.calculateMedian(fullFlatAvitoList, "price",3),
                        city,
                        System.currentTimeMillis()
                ));
                PostgreConnection.getFlatAvitoConnection().commit();
            }catch (Exception e) {
                PostgreConnection.getFlatAvitoConnection().rollback();
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}