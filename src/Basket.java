import java.io.*;

public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    protected static String[] prod;
    protected static int[] price;
    protected int[] cart;
    protected int totalPrice;

    Basket(String[] products, int[] prices) {
        this.prod = products;
        this.price = prices;
        this.cart = new int[products.length];
    }

    public static Basket LoadFromTxtFile(String textFile) throws Exception {

        try (FileInputStream inputStream = new FileInputStream(textFile);
             ObjectInputStream objectStream = new ObjectInputStream(inputStream)) {
            Basket backup = (Basket) objectStream.readObject();
            return backup;
        }

    }


    public void addToCart(int productNum, int amount) {
        cart[productNum - 1] += amount;
        totalPrice += price[productNum] * amount;
    }


    public void printCart() {
        System.out.println("В корзине:");
        for (int i = 0; i < prod.length; i++) {
            if (cart[i] != 0) {
                int totalPrice = cart[i] * price[i];
               this.totalPrice += totalPrice;
                System.out.println(prod[i] + " " + cart[i] + " шт." + price[i] + " руб./шт." +
                        totalPrice + " руб. в сумме" + "\n");

            }

        }
        System.out.println("Итого: " + totalPrice + " " + "рублей");
    }

    public static void saveBin(Basket saveBin) throws Exception {

        try (FileOutputStream outputStream = new FileOutputStream("basket.bin");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

            objectOutputStream.writeObject(saveBin);
        } catch (Exception ex) {

        }

    }
}
