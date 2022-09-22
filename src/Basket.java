import java.io.*;

public class Basket {

    protected String[] prod;
    protected int[] price;
    protected int[] cart;


    Basket(String[] products, int[] prices) {
        this.prod = products;
        this.price = prices;
        cart = new int[4];
    }

    public static Basket loadFromTxtFile() throws Exception {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("basket.txt"))) {
            String[] prod = br.readLine().split(" ");
            while ((line = br.readLine()) != null) {
                String[] inputArray = line.split(" ");
                int[] price = new int[inputArray.length];
                for (int i = 0; i < inputArray.length; i++) {
                    price[i] = Integer.parseInt(inputArray[i]);
                }
                Basket basket = new Basket(prod, price);
                String[] inputArrayBasket = br.readLine().split(" ");
                for (int i = 0; i < inputArrayBasket.length; i++) {
                    basket.cart[i] = Integer.parseInt(inputArrayBasket[i]);
                }
                return basket;
            }
        }
        return null;
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum - 1] += amount;
        try {
            saveTxt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printCart() {
        int totalPrice = 0;
        System.out.println("В корзине:");
        for (int i = 0; i < prod.length; i++) {
            if (cart[i] != 0) {
                totalPrice += cart[i] * price[i];
                System.out.println(prod[i] + " " + cart[i] + " шт." + price[i] + " руб./шт." +
                        price[i] * cart[i] + " руб. в сумме" + "\n");
            }
        }
        System.out.println("Итого: " + totalPrice + " " + "рублей");
    }

    public void saveTxt() throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("basket.txt"))) {
            for (String a : prod) {
                bw.write(a + " ");
            }

            bw.write("\n");
            for (int p : price) {
                bw.write(p + " ");
            }
            bw.write("\n");
            for (int c : cart) {
                bw.write(c + " ");
            }
        }
    }
}




