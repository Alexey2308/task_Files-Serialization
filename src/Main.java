import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};//массив названия продуктов
        int[] prices = {100, 200, 300};//массив для хранения цен продуктов
        Basket products1 = new Basket(products, prices);//создаём объект корзины
        String textFile = "basket.txt";

        if (Files.exists(Path.of(String.valueOf(textFile)))) { //проверяем есть ли сохранённая в файле корзина
            System.out.println("Корзина восстановлена, т.к есть файл.");

            try {
                products1.LoadFromTxtFile();

            } catch (Exception e) {

            }
        }

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " руб/шт");
        }
        System.out.println("Начинаем вводить данные.");
        System.out.println("По окончании ввода наберите end");
        while (true) {
            System.out.println("Введите номер продукта и его количество через пробел");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                products1.printCart();
                break;
            } else {
                String[] in = input.split(" ");//разделяем ввод по пробелу
                int productNumber = Integer.parseInt(in[0]);//парсим до пробела
                int productCount = Integer.parseInt(in[1]);//парсим после пробела
                products1.addToCart(productNumber, productCount);//добавляем в корзину

            }
        }
    }
}



