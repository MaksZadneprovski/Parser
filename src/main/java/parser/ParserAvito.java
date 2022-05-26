package parser;

import com.sun.tools.javac.Main;
import db.FlatDAO;
import db.LiksDAO;
import db.StatisticsFlatAvitoDAO;
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
import org.openqa.selenium.chrome.ChromeOptions;
import telegram.BotTG;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.stream.Collectors;

public class ParserAvito {
    private static final Logger log = Logger.getLogger(ParserAvito.class.getName());

    public static void parse() throws SQLException, IOException {
        Handler handler = new FileHandler("C:\\Users\\user\\Desktop\\log.txt");
        log.addHandler(handler);
        handler.setFormatter(new SimpleFormatter());
//        System.setProperty("webdriver.chrome.driver","selenium/chromedriver");
        System.setProperty("webdriver.chrome.driver","D:\\Java\\Projects\\Parser\\selenium\\chromedriver.exe");
        StatisticsFlatAvitoDAO statisticsFlatAvitoDAO = new StatisticsFlatAvitoDAO();
        log.info("Start Parser");
        String city;

        for (int i0 = 0; i0 < FlatAvito.link.size(); i0++) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920x1080");
            options.setPageLoadTimeout(Duration.ofMinutes(20));

            log.info("Parsing "+FlatAvito.link.keySet().toArray()[i0]);

            WebDriver driver = new ChromeDriver(options);
            city = (String) FlatAvito.link.keySet().toArray()[i0];
            driver.get(FlatAvito.link.get(city));
            List<FlatAvito> fullFlatAvitoList = new ArrayList<>();
            int numberPage = 0;
            int maxNumberPage = 100;


            while (numberPage < maxNumberPage) {
                List<FlatAvito> flatAvitoList = new ArrayList<>();
                // Чтобы не терялось соединение с БД
                LiksDAO.getLinks().size();

                Document document = Jsoup.parse(driver.getPageSource());
                Elements elements = document.getElementsByClass( "iva-item-body-KLUuy");

                try {TimeUnit.SECONDS.sleep(4);}
                catch (InterruptedException e) {e.printStackTrace();}

                String finalCity = city;
                elements.forEach(e-> flatAvitoList.add(new FlatAvito(
                        e.getElementsByAttributeValue("class","iva-item-priceStep-uq2CQ").text(),
                        e.getElementsByAttributeValue("class","iva-item-titleStep-pdebR").text(),
                        finalCity,
                        e.getElementsByAttributeValue("class","geo-root-zPwRk iva-item-geo-_Owyg").text(),
                        e.select("div.iva-item-titleStep-pdebR > a").attr("href")
                )));
                try {
                    WebElement webNumberPage = driver.findElement(By.className("pagination-item_active-NcJX6"));
                    List<WebElement> webElementsList = driver.findElements(By.className("pagination-item-JJq_j"));
                    maxNumberPage = Integer.parseInt(webElementsList.get(webElementsList.size()-2).getText());
                    numberPage = Integer.parseInt(webNumberPage.getText());

                    WebElement webElement = webElementsList.get(webElementsList.size()-1);
                    webElement.click();
                }catch (Exception e){
                    e.printStackTrace();
                }
                fullFlatAvitoList.addAll(flatAvitoList);
                log.info("Finished "+(i0+1)+" из "+ FlatAvito.link.size() +" "+city.toUpperCase()+" page №" + numberPage);

            }

                try {
                    FlatDAO flatDb = new FlatDAO();
                    fullFlatAvitoList = fullFlatAvitoList.stream().distinct().collect(Collectors.toList());
                    PostgreConnection.getFlatAvitoConnection().setAutoCommit(false);
                    for (FlatAvito flatAvito : fullFlatAvitoList) {
                        flatDb.insert(flatAvito);
                    }

                    statisticsFlatAvitoDAO.insert(new StatisticsFlatAvito(
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
                            System.currentTimeMillis(),
                            fullFlatAvitoList.size()
                    ));
                    PostgreConnection.getFlatAvitoConnection().commit();
                }catch (Exception e) {
                    PostgreConnection.getFlatAvitoConnection().rollback();
                    e.printStackTrace();
                }
            BotTG.botTG.sendMessage(city+" - read "+numberPage+" pages");
            driver.quit();
        }
        PostgreConnection.getFlatAvitoConnection().close();
    }
}
