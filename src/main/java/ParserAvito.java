import db.DAOFlatDb;
import db.DAOStatisticsDb;
import db.PostgreConnection;
import model.Dollar;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParserAvito {

    public static void main(String[] args) throws SQLException {
        System.setProperty("webdriver.chrome.driver","selenium\\chromedriver.exe");

        for (int i0 = 0; i0 < FlatAvito.link.size(); i0++) {
            WebDriver driver = new ChromeDriver();
            String city = (String) FlatAvito.link.keySet().toArray()[i0];
            driver.get(FlatAvito.link.get(city));
            List<FlatAvito> flatAvitoList = new ArrayList<>();
            int n = 0;
            int prevNumberPage = 0;
            int numberPage = 0;
            for (int i = 0; n < 5; i++) {
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
                        driver.quit();
                        ex.printStackTrace();
                    }
                }

                DAOFlatDb flatDb = new DAOFlatDb();
                try {
                    PostgreConnection.getFlatAvitoConnection().setAutoCommit(false);
                    flatAvitoList.forEach(e-> flatDb.insertFlatAvito(e));
                    PostgreConnection.getFlatAvitoConnection().commit();
                } catch (Exception e) {
                    PostgreConnection.getFlatAvitoConnection().rollback();
                    e.printStackTrace();
                }
            }
            try {
                DAOStatisticsDb daoStatisticsDb = new DAOStatisticsDb();
                PostgreConnection.getFlatAvitoConnection().setAutoCommit(false);
                daoStatisticsDb.insertStatisticsFlatAvito(new StatisticsFlatAvito(
                        Dollar.dollar,
                        FlatAvito.calculateAveragePricePerMeter(flatAvitoList, "pricePerMetr"),
                        FlatAvito.calculateMedian(flatAvitoList ,Comparator.comparing(e->e.getPrice()), "pricePerMetr"),
                        FlatAvito.calculateAveragePricePerMeter(flatAvitoList.stream()
                                        .filter(f->f.getFloors() == 1)
                                        .collect(Collectors.toList()),
                                "price"),
                        FlatAvito.calculateMedian(flatAvitoList.stream()
                                        .filter(f->f.getFloors() == 1)
                                        .collect(Collectors.toList()),
                                Comparator.comparing(e->e.getPrice()), "price"),
                        FlatAvito.calculateAveragePricePerMeter(flatAvitoList.stream()
                                        .filter(f->f.getFloors() == 2)
                                        .collect(Collectors.toList()),
                                "price"),
                        FlatAvito.calculateMedian(flatAvitoList.stream()
                                        .filter(f->f.getFloors() == 2)
                                        .collect(Collectors.toList()),
                                Comparator.comparing(e->e.getPrice()), "price"),
                        FlatAvito.calculateAveragePricePerMeter(flatAvitoList.stream().
                                        filter(f->f.getFloors() == 3)
                                        .collect(Collectors.toList()),
                                "price"),
                        FlatAvito.calculateMedian(flatAvitoList.stream()
                                        .filter(f->f.getFloors() == 3)
                                        .collect(Collectors.toList()),
                                Comparator.comparing(e->e.getPrice()), "price"),
                        city
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
