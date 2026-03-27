package Game;

import java.util.Scanner;

public class Game {
    private Scanner sc = new Scanner(System.in);
    private int choice;
    private int tries = 1;
    private int triesLimit;
    private final int NUM_MIN = 1;
    private boolean cheat = false;

    public Game() {
        game();
    }

    public void game() {
        System.out.println("1 - Modos de Jogo");
        System.out.println("2 - Configuração");
        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> gameModes();
            case 2 -> gameConfiguations();
            default -> game();
        }
    }

    public void gameModes() {
        System.out.println("1 - Padrão (1 - 100)");
        System.out.println("2 - Médio (1 - 1000)");
        System.out.println("3 - Difícil? (1 - 10000)");
        System.out.println("4 - Personalizado");
        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> gameMode(NUM_MIN, 100);
            case 2 -> gameMode(NUM_MIN, 1000);
            case 3 -> gameMode(NUM_MIN, 10000);
            case 4 -> personalizedMode();
            default -> gameModes();
        }
    }

    public void gameConfiguations() {
        System.out.println("1 - Cheat");
        System.out.println("2 - Limite de tentativas");

        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> toggleCheat();
            case 2 -> triesLimit();
            default -> game();
        }

    }

    public void gameMode(int NUM_MIN, int numMax) {
        int range = numMax - NUM_MIN;
        int randomNum = (int) (Math.random() * range) + NUM_MIN;
        int answer;
        String writingTryOrTries;

        System.out.println("De " + NUM_MIN + " a " + numMax);
        System.out.println("Numero gerado, qual seu chute?");
        if (cheat) {
            System.out.println("Colinha: " + randomNum);
        }

        do {
            answer = Integer.parseInt(sc.nextLine());
            if (answer == randomNum) {
                writingTryOrTries = (tries > 1) ? "Tentativas" : "Tentativa";

                System.out.println("Acertou!");
                System.out.println("Com: " + tries + " " + writingTryOrTries);
            } else if (answer < randomNum) {
                System.out.println("O número é maior que: " + answer);
            } else {
                System.out.println("O número é menor que: " + answer);
            }

            if (triesLimit == tries) {
                System.out.println("Limite atingido!");
                game();
            }

            tries++;
        } while (answer != randomNum);
    }

    public void personalizedMode() {
        int personalizedMaxValue;

        System.out.println("O valor mínimo por padrão é 0");
        System.out.println("Qual será o valor máximo?");
        personalizedMaxValue = Integer.parseInt(sc.nextLine());
        gameMode(NUM_MIN, personalizedMaxValue);
    }

    public void toggleCheat() {
        cheat = !cheat;
        System.out.println("Cheat: " + cheat);
        game();
    }

    public void triesLimit() {
        System.out.println("Quantidade de tentativas:");
        triesLimit = Integer.parseInt(sc.nextLine());
    }

}
