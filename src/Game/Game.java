package Game;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Scanner sc = new Scanner(System.in);
    private int choice;
    private int answer;
    private int tries = 1;
    private int triesLimit = 5;
    private boolean cheat = false;
    private boolean isTriesLimitActive = false;

    public Game() {
        gameStart();
    }

    public void gameStart() {
        boolean gameStartMenu = true;
        while (gameStartMenu) {
            System.out.println("--Início--\n1 - Modos de Jogo\n2 - Configuração");
            choiceInt();

            switch (choice) {
                case 0 -> gameStartMenu = false;
                case 1 -> {
                    gameModeSelect();
                    return;
                }
                case 2 -> {
                    gameConfigurations();
                    return;
                }
                default -> System.out.println("Inválido");
            }
        }
    }

    public void gameModeSelect() {
        boolean gameModeMenu = true;
        this.tries = 1;

        while (gameModeMenu) {
            System.out.println("--Modos de Jogo--\n1 - Padrão (1 - 100)\n2 - Médio (1 - 1000)\n3 - Difícil? (1 - 10000)\n4 - Personalizado\n0 - Voltar");
            choiceInt();

            gameModeMenu = switch (choice) {
                case 0 -> {
                    gameStart();
                    yield false;
                }
                case 1 -> {
                    gameMode(1, 100);
                    yield false;
                }
                case 2 -> {
                    gameMode(1, 1000);
                    yield false;
                }
                case 3 -> {
                    gameMode(1, 10000);
                    yield false;
                }
                case 4 -> {
                    personalizedMode();
                    yield false;
                }
                default -> true;
            };
        }
    }

    public void gameMode(int numMin, int numMax) {
        int randomNum = generateNumber(numMin, numMax);
        List<Integer> saveAnswers = new ArrayList<>();

        System.out.println("De " + numMin + " a " + numMax + "\nNumero gerado, qual seu chute?");
        System.out.println((cheat) ? "colinha: " + randomNum : "");

        Instant startTime = Instant.now();
        do {
            answerInt(numMax);

            if (!saveAnswers.contains(answer)) {
                saveAnswers.add(answer);
            } else {
                System.out.println("Já utilizou este número");
                tries -= 1;
            }

            // Está feio
            if (answer == randomNum) {
                Instant endTime = Instant.now();
                long time = Duration.between(startTime, endTime).getSeconds();
                String writingTryOrTries = (tries > 1) ? "Tentativas" : "Tentativa";
                System.out.println("Acertou!");
                System.out.println("Com: " + tries + " " + writingTryOrTries);
                System.out.println("Tempo: " + time + " segundos");
            } else {
                incorrectAnswer(answer, randomNum, saveAnswers);
            }
            tries++;
        } while (answer != randomNum && tryLimitExpression(randomNum));
    }

    public void personalizedMode() {
        boolean running = true;
        do {
            int personalizedMaxValue;
            System.out.println("--Modo Personalizado--\nO valor mínimo por padrão é 1\nQual será o valor máximo?");

            try {
                personalizedMaxValue = Integer.parseInt(sc.nextLine());
                gameMode(1, personalizedMaxValue);
                running = false;
            } catch (NumberFormatException e) {
                System.out.println("Insira um valor válido");
            }
        } while (running);
    }

    public void gameConfigurations() {
        boolean gameConfigurationsMenu = true;
        while (gameConfigurationsMenu) {
            System.out.println("--Configurações--\n1 - Cheat\n2 - Limite de tentativas\n0 - Voltar");
            choiceInt();

            gameConfigurationsMenu = switch (choice) {
                case 0 -> {
                    gameStart();
                    yield false;
                }
                case 1 -> {
                    toggleCheat();
                    yield false;
                }
                case 2 -> {
                    triesLimitConfiguration();
                    yield false;
                }
                default -> true;
            };
        }
    }

    public void triesLimitConfiguration() {
        boolean triesLimitConfigurationMenu = true;
        while (triesLimitConfigurationMenu) {
            System.out.println("--Tentativas Configuração--");
            System.out.println("Quantidade de tentativas: " + ((isTriesLimitActive) ? "Ativo" : "Desativado") + " | Quantidade: " + triesLimit);
            System.out.println("1 - Ativar/Desativar\n2 - Escolher quantidade (Padrão 5)\n0 - Voltar");
            choiceInt();

            triesLimitConfigurationMenu = switch (choice) {
                case 0 -> {
                    gameConfigurations();
                    yield false;
                }
                case 1 -> {
                    isTriesLimitActive = !isTriesLimitActive;
                    yield true;
                }
                case 2 -> {
                    System.out.println("Digite a quantidade");
                    triesLimit = Integer.parseInt(sc.nextLine());
                    yield true;
                }
                default -> {
                    System.out.println("Escolha uma opção válida.");
                    yield true;
                }
            };
        }
    }

    public boolean tryLimitExpression(int randomNum) {
        if (isTriesLimitActive) {
            if (tries == triesLimit) {
                System.out.println("Acabou as tentativas!\nO número era: " + randomNum);
                return false;
            }
        }
        return true;
    }

    public void toggleCheat() {
        cheat = !cheat;
        System.out.println("Cheat: " + cheat);
        gameStart();
    }

    public void choiceInt() {
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido, por favor insira um número.");
            choice = -1;
        }
    }

    public void answerInt(int numMax) {
        try {
            do {
                answer = Integer.parseInt(sc.nextLine());
            } while (validAnswer(numMax));
        } catch (Exception e) {
            System.out.println("Formato inválido, por favor insira um número inteiro.");
        }
    }

    public boolean validAnswer(int numMax) {
        if (answer < 1 || answer > numMax) {
            System.out.println("Número excedente, maior que o máximo ou menor que o mínimo\nDigite um número válido novamente");
            return true;
        } else {
            return false;
        }
    }

    public void incorrectAnswer(int answer, int randomNum, List<Integer> saveAnswers) {
        System.out.println((answer > randomNum) ? "O número é menor que: " + answer : "O número é maior que: " + answer);
        System.out.println("Tentativa de número: " + tries + "\nNúmeros utilizados: " + saveAnswers);
    }

    public int generateNumber(int numMin, int numMax) {
        int range = (numMax - numMin) + 1;
        return (int) (Math.random() * range) + numMin;
    }
}