package ru.basket;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Boolean load = true;
        String textFile = null;
        String fileForm = null;

        Boolean saveCond = true;
        String textFileToSave = null;
        String fileFormToSave = null;

        Boolean logCond = true;
        String fileNameLog = null;

        if (Files.exists(Path.of(String.valueOf("shop.xml")))) {
            System.out.println("Загружены настройки из shop.xml");
            try {
                File xmlFile = new File("shop.xml");
                DocumentBuilderFactory dfBuild = DocumentBuilderFactory.newInstance();
                DocumentBuilder builderDoc = dfBuild.newDocumentBuilder();
                Document document = builderDoc.parse(xmlFile);
                document.getDocumentElement().normalize();

                NodeList conditions = document.getElementsByTagName("load");
                for (int i = 0; i < conditions.getLength(); i++) {
                    Node node = conditions.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        load = Boolean.valueOf(element.getElementsByTagName("enabled").item(0).getTextContent());
                        textFile = element.getElementsByTagName("fileName").item(0).getTextContent();
                        fileForm = element.getElementsByTagName("format").item(0).getTextContent();
                    }

                }
                NodeList savCond = document.getElementsByTagName("save");
                for (int i = 0; i < savCond.getLength(); i++) {
                    Node node = savCond.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        saveCond = Boolean.valueOf(element.getElementsByTagName("enabled").item(0).getTextContent());
                        textFileToSave = element.getElementsByTagName("fileName").item(0).getTextContent();
                        fileFormToSave = element.getElementsByTagName("format").item(0).getTextContent();

                    }
                }
                NodeList loCond = document.getElementsByTagName("log");
                for (int i = 0; i < loCond.getLength(); i++) {
                    Node node = loCond.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        logCond = Boolean.valueOf(element.getElementsByTagName("enabled").item(0).getTextContent());
                        fileNameLog = element.getElementsByTagName("fileName").item(0).getTextContent();
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};//массив названия продуктов
        int[] prices = {100, 200, 300};//массив для хранения цен продуктов
        ClientLog csvLog = new ClientLog();
        Basket products1 = new Basket(products, prices);//создаём объект корзины
//проверяем нужно ли загружать предыдущее состояние корзины из .json
        if (load && Files.exists(Path.of(String.valueOf(fileForm.equals("json"))))) {
            System.out.println("Корзина восстановлена из .json");
            products1.LoadFromJson(new File(textFile));
        } else {
            if (load) {
                System.out.println("Корзина восстановлена из .txt");
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
                if (saveCond && fileFormToSave.equals("json")) {
                    products1.saveToJson(new File(textFileToSave));
                } else {
                    if (saveCond) {
                        products1.saveTxt(textFileToSave);
                    }
                }
                if (logCond) {
                    csvLog.exportAsCSV(fileNameLog);
                    break;
                }

            } else {
                String[] in = input.split(" ");//разделяем ввод по пробелу
                int productNumber = Integer.parseInt(in[0]);//парсим до пробела
                int productCount = Integer.parseInt(in[1]);//парсим после пробела
                products1.addToCart(productNumber, productCount);//добавляем в корзину
                csvLog.log(productNumber, productCount);//сохраняем в массив лога

            }
        }
    }
}




