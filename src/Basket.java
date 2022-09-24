import java.io.*;

public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String[] prod;
    protected int[] price;
    protected int[] cart;

    Basket(String[] products, int[] prices) {
        this.prod = products;
        this.price = prices;
        this.cart = new int[products.length];
    }

    public static Basket loadFromTxtFile(String textFile) throws Exception {

        try (FileInputStream inputStream = new FileInputStream(textFile);
             ObjectInputStream objectStream = new ObjectInputStream(inputStream)) {
            Basket backup = (Basket) objectStream.readObject();
            return backup;
        }
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum - 1] += amount;
    }

    public void printCart() {
        int totalPrice = 0;
        System.out.println("В корзине:");
        for (int i = 0; i < prod.length; i++) {
            if (cart[i] != 0) {
                totalPrice += cart[i] * price[i];
                System.out.println(prod[i] + " " + cart[i] + " шт." + price[i] + " руб./шт." +
                        cart[i] * price[i] + " руб. в сумме" + "\n");
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
