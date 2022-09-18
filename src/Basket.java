import java.io.*;

public class Basket {
    protected String[] prod;
    protected int[] price;
    protected static int[] cart = new int[4];
    protected int totalPrice;

    public Basket(String[] products, int[] prices) {
        this.prod = products;
        this.price = prices;

    }

    public static Basket LoadFromTxtFile() throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader("basket.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] inputArray = line.split(" ");
                int numberProd = Integer.parseInt(inputArray[0]);
                int amountProd = Integer.parseInt(inputArray[1]);
                cart[numberProd] = amountProd;
            }
        } catch (IOException e) {

        }
        return null;
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum - 1] += amount;
        totalPrice += this.cart[productNum] * amount;
        try {
            saveTxt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void printCart() {
        System.out.println("В корзине:");
        for (int i = 0; i < prod.length; i++) {
            if (cart[i] != 0) {
                int totalPrice = cart[i] * price[i];
                this.totalPrice += totalPrice;
                System.out.println(prod[i] + " " + cart[i] + " шт." + price[i] + " руб./шт." +
                        price[i] * cart[i] + " руб. в сумме" + "\n");

            }

        }
        System.out.println("Итого: " + totalPrice + " " + "рублей");
    }

    public void saveTxt() throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("basket.txt"))) {
            for (int i = 0; i < cart.length; i++) {
                if (cart[i] != 0) {
                    bw.write(i + " " + String.valueOf(cart[i]) + "\n");

                }
            }
        } catch (IOException e) {
        }

    }
}

