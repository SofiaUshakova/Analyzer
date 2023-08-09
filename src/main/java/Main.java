import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static ArrayBlockingQueue<String> blockingQueue1 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> blockingQueue2 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> blockingQueue3 = new ArrayBlockingQueue<>(100);

    public Main() throws InterruptedException {
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                String text = generateText("abc", 100_000);
                try {
                    blockingQueue1.put(text);
                    blockingQueue2.put(text);
                    blockingQueue3.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }).start();
        Thread maxA = new Thread(() -> {
            System.out.println("Максимальное кол-во символов а: " + count(blockingQueue1, 'a'));
        });
        maxA.start();


        Thread maxB = new Thread(() -> {
            System.out.println("Максимальное кол-во символов b: " + count(blockingQueue2, 'b'));
        });
        maxB.start();

        Thread macC = new Thread(() ->{
            System.out.println("Максимальное кол-во символов c: " + count(blockingQueue3, 'c'));
        });
        macC.start();
    }




    public static int count(ArrayBlockingQueue<String> queue, char letter) {
        int count = 0;
        int max = 0;
        String maxText;
        for (int i = 0; i < 10_000; i++) {

            try {
                maxText = queue.take();
                for (char c : maxText.toCharArray()) {
                    if (c == letter) count++;
                }
                if (count > max) max = count;
                count = 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return max;
    }

        public static String generateText (String letters,int length){
            Random random = new Random();
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < length; i++) {
                text.append(letters.charAt(random.nextInt(letters.length())));
            }
            return text.toString();
        }
    }
