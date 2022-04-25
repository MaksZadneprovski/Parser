import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> firstArrayList = new ArrayList<>();
        firstArrayList.add("1");
        firstArrayList.add("2");
        firstArrayList.add("3");

        ArrayList<String> secondArrayList = new ArrayList<>();
        secondArrayList.add("0");
        secondArrayList.addAll(firstArrayList);
        System.out.println(secondArrayList);
    }
}
