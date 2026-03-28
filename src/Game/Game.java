package Game;

import java.util.Scanner;

public class Game {
    private Scanner sc = new Scanner(System.in);
    private final int NUM_MIN = 1;
    private int choice;
    private int tries = 0;
    private int triesLimit = 5;
    private boolean cheat = false;
    private boolean isTriesLimitActive = false;
    private int randomNum;

    public Game() {
        gameStart();
    }

    public void gameStart() {
        System.out.println("--Início--");
        System.out.println("1 - Modos de Jogo");
        System.out.println("2 - Configuração");
        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> gameModes();
            case 2 -> gameConfigurations();
            default -> gameStart();
        }
    }

    public void gameModes() {
        this.tries = 0;
        System.out.println("--Modos de Jogo--");
        System.out.println("1 - Padrão (1 - 100)");
        System.out.println("2 - Médio (1 - 1000)");
        System.out.println("3 - Difícil? (1 - 10000)");
        System.out.println("4 - Personalizado");
        System.out.println("0 - Voltar");
        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 0 -> gameStart();
            case 1 -> gameMode(NUM_MIN, 100);
            case 2 -> gameMode(NUM_MIN, 1000);
            case 3 -> gameMode(NUM_MIN, 10000);
            case 4 -> personalizedMode();
            default -> gameModes();
        }
    }

    public void gameConfigurations() {
        System.out.println("--Configurações--");
        System.out.println("1 - Cheat");
        System.out.println("2 - Limite de tentativas");
        System.out.println("0 - Voltar");
        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> toggleCheat();
            case 2 -> triesLimit();
            default -> gameStart();
        }
    }

    public void gameMode(int NUM_MIN, int numMax) {
        int range = numMax - NUM_MIN;
        randomNum = (int) (Math.random() * range) + NUM_MIN;
        int answer;
        String writingTryOrTries;

        System.out.println("De " + NUM_MIN + " a " + numMax);
        System.out.println("Numero gerado, qual seu chute?");
        System.out.println((cheat) ? "colinha: " + randomNum : "");

        do {
            tries++;
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
        } while (answer != randomNum && tryLimitCondition());
    }

    public void personalizedMode() {
        int personalizedMaxValue;
        System.out.println("--Modo Personalizado--");
        System.out.println("O valor mínimo por padrão é 1");
        System.out.println("Qual será o valor máximo?");
        personalizedMaxValue = Integer.parseInt(sc.nextLine());
        gameMode(NUM_MIN, personalizedMaxValue);
    }

    public void toggleCheat() {
        cheat = !cheat;
        System.out.println("Cheat: " + cheat);
        gameStart();
    }

    public void triesLimit() {
        System.out.println("--Tentativas Configuração--");
        System.out.println("Quantidade de tentativas: " + isTriesLimitActive + " " + triesLimit);
        System.out.println("1 - Ativar/Desativar");
        System.out.println("2 - Escolher quantidade (Padrão 5)");
        System.out.println("0 - Voltar");
        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 0 -> gameConfigurations();
            case 1 -> {
                isTriesLimitActive = !isTriesLimitActive;
                triesLimit();
            }
            case 2 -> {
                System.out.println("Digite a quantidade");
                triesLimit = Integer.parseInt(sc.nextLine());
                triesLimit();
            }
            default -> {
                System.out.println("Escolha uma oopção válida.");
                triesLimit();
            }
        }
    }

    public boolean tryLimitCondition() {
        if (isTriesLimitActive) {
            if (tries == triesLimit) {
                System.out.println("Acabou as tentativas!");
                System.out.println("O númeor era: " + randomNum);
                return false;
            }
        }
        return true;
    }
}