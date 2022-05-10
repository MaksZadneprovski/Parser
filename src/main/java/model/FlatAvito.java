package model;

import db.LiksDAO;
import lombok.Data;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class FlatAvito {
    public   static Map<String, String> link;

    static {
        try {
            link = LiksDAO.getLinks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long price;
    private int priceDollar;
    private int dollar;
    private int pricePerMeter;
    private int numberOfRooms;
    private double square;
    private int floors;
    private String city;
    private String address;
    private String href;
    private long date;

    public FlatAvito(String moneyString, String titleString ,String city,String address, String href) {

        this.address = address;
        this.href = "https://www.avito.ru/"+href;
        parseMoneyString(moneyString);
        parseTitleString(titleString);
        this.dollar = Constans.dollar;
        this.priceDollar = (int) (this.price/this.dollar);
        calculateEmptyFields();
        this.city = city;
        this.date = System.currentTimeMillis();
    }

    public void calculateEmptyFields(){
        if (this.getPricePerMeter() == -1){
            if (this.getPriceDollar() != -1 && this.getSquare() != -1) {
                this.setPricePerMeter((int) (this.getPriceDollar() / this.getSquare()));
            }
        }
    }

    public void parseMoneyString(String s){
        try {
            s = s.replaceAll("\\s","");
            Pattern pattern = Pattern.compile("\\d+₽");
            /////////////////////////////////
            Matcher matcher = pattern.matcher(s);
            this.price = Long.parseLong(cutToPattern(s,matcher));
            this.pricePerMeter = Integer.parseInt(cutToPattern(s,matcher));
        }catch (Exception e){
            System.out.println(s);
        }

    }
    public static void printSortedAndLimitList(List<FlatAvito> flatAvitoList, int count){
        flatAvitoList.stream().distinct().sorted(Comparator.comparing(f->f.getPricePerMeter())).limit(count).forEach(System.out::println);
    }

    public static long calculateAverage(List<FlatAvito> flatAvitoList, String s , int numberOfRooms){
        double d = 0;
        int i = 0;
        if (numberOfRooms > 0){
            flatAvitoList = getFilterNumberOfRoomsList(flatAvitoList, numberOfRooms);
        }
        for (FlatAvito f :flatAvitoList) {
            if (s.equals("price")){
                d+=f.getPrice();
            }
            if (s.equals("pricePerMetr")){
                d+=f.getPricePerMeter();
            }
            i++;
        }
        return Math.round(d/i);
    }

    private static List<FlatAvito> getFilterNumberOfRoomsList(List<FlatAvito> flatAvitoList, int numberOfRooms){
        return flatAvitoList.stream()
                .filter(f->f.getNumberOfRooms() == numberOfRooms)
                .collect(Collectors.toList());
    }

    public static long calculateMedian(List<FlatAvito> flatAvitoList,String s, int numberOfRooms){
        if (numberOfRooms > 0){
            flatAvitoList = getFilterNumberOfRoomsList(flatAvitoList, numberOfRooms);
        }
        List<Long> integers = new ArrayList<>();
        if (s.equals("price")){
            flatAvitoList = flatAvitoList.stream().sorted(Comparator.comparing(e->e.getPrice())).collect(Collectors.toList());
            flatAvitoList.forEach(e-> integers.add(e.getPrice()));
        }
        if (s.equals("pricePerMetr")){
            flatAvitoList = flatAvitoList.stream().sorted(Comparator.comparing(e->e.getPricePerMeter())).collect(Collectors.toList());
            flatAvitoList.forEach(e-> integers.add((long) e.getPricePerMeter()));
        }
        Long median;
        if (integers.size() % 2 == 1) median = integers.get((integers.size() + 1)/2);
        else median = (integers.get(integers.size()/2) + integers.get(integers.size()/2 +1)) / 2;
        return Math.round(median);
    }

    public void parseTitleString(String s){
        s = s.replaceAll("\\s","");
        Pattern pattern1 = Pattern.compile("\\d+-");
        Pattern pattern2 = Pattern.compile("\\d+,*\\d+м");
        Pattern pattern3 = Pattern.compile("\\d+/");
        Matcher matcher = pattern1.matcher(s);
        Matcher matcher2 = pattern2.matcher(s);
        Matcher matcher3 = pattern3.matcher(s);
        this.numberOfRooms = Integer.parseInt(cutToPattern(s, matcher));
        this.square = Double.parseDouble(cutToPattern(s,matcher2).replace(",","."));
        this.floors = Integer.parseInt( cutToPattern(s,matcher3));
    }

    public String cutToPattern(String s, Matcher matcher){
        if(matcher.find()) {
            return s.substring(matcher.start(), matcher.end()-1);
        }else return "-1";
    }

    @Override
    public String toString() {
        return "Цена = " + price +
                "   Цена м² = " + pricePerMeter +
                "   Адрес = " + address +
                "   Кол-во комнат = " + numberOfRooms +
                "   Площадь = " + square+
                "   Этаж = " + floors +"\n"+
                "   Дата = " + date+"\n"+
                "   Ссылка = " + href+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAvito flatAvito = (FlatAvito) o;
        return price == flatAvito.price && pricePerMeter == flatAvito.pricePerMeter && numberOfRooms == flatAvito.numberOfRooms && Double.compare(flatAvito.square, square) == 0 && floors == flatAvito.floors && address.equals(flatAvito.address) && href.equals(flatAvito.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, pricePerMeter, numberOfRooms, square, floors, address, href);
    }
}
