package Game;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Game {
    private final Scanner sc = new Scanner(System.in);
    private int choice;
    private int tries = 0;
    private int triesLimit = 5;
    private boolean cheat = false;
    private boolean isTriesLimitActive = false;

    public Game() {
        gameStart();
    }

    public void gameStart() {
        boolean gameStartMenu = true;
        while (gameStartMenu) {
            System.out.println("--Início--");
            System.out.println("1 - Modos de Jogo");
            System.out.println("2 - Configuração");
            checkScanner();

            gameStartMenu = switch (choice) {
                case 1 -> {
                    gameModeSelect();
                    yield false;
                }
                case 2 -> {
                    gameConfigurations();
                    yield false;
                }
                default -> true;
            };
        }
    }

    public void gameModeSelect() {
        boolean gameModeMenu = true;
        this.tries = 0;

        while (gameModeMenu) {
            System.out.println("--Modos de Jogo--");
            System.out.println("1 - Padrão (1 - 100)");
            System.out.println("2 - Médio (1 - 1000)");
            System.out.println("3 - Difícil? (1 - 10000)");
            System.out.println("4 - Personalizado");
            System.out.println("0 - Voltar");
            checkScanner();

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
        int answer;
        int range = numMax - numMin;
        int randomNum = (int) (Math.random() * range) + numMin;

        System.out.println("De " + numMin + " a " + numMax);
        System.out.println("Numero gerado, qual seu chute?");
        System.out.println((cheat) ? "colinha: " + randomNum : "");

        Instant startTime = Instant.now();
        do {
            answer = Integer.parseInt(sc.nextLine());
            tries++;
            if (answer == randomNum) {
                Instant endTime = Instant.now();
                long time = Duration.between(startTime, endTime).getSeconds();
                String writingTryOrTries = (tries > 1) ? "Tentativas" : "Tentativa";
                System.out.println("Acertou!");
                System.out.println("Com: " + tries + " " + writingTryOrTries);
                System.out.println("Tempo: " + time + " segundos");
                gameStart();
            } else if (answer < randomNum) {
                System.out.println("O número é maior que: " + answer);
            } else {
                System.out.println("O número é menor que: " + answer);
            }
        } while (answer != randomNum && tryLimitExpression(randomNum));
    }

    public void personalizedMode() {
        int personalizedMaxValue;
        System.out.println("--Modo Personalizado--");
        System.out.println("O valor mínimo por padrão é 1");
        System.out.println("Qual será o valor máximo?");
        personalizedMaxValue = Integer.parseInt(sc.nextLine());
        gameMode(1, personalizedMaxValue);
    }

    public void gameConfigurations() {
        boolean gameConfigurationsMenu = true;
        while (gameConfigurationsMenu) {
            System.out.println("--Configurações--");
            System.out.println("1 - Cheat");
            System.out.println("2 - Limite de tentativas");
//          System.out.println("3 - Limite de tentativas por tempo");
            System.out.println("0 - Voltar");
            checkScanner();

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

    public void toggleCheat() {
        cheat = !cheat;
        System.out.println("Cheat: " + cheat);
        gameStart();
    }

    public void triesLimitConfiguration() {
        boolean triesLimitConfigurationMenu = true;
        while (triesLimitConfigurationMenu) {
            System.out.println("--Tentativas Configuração--");
            System.out.println("Quantidade de tentativas: " + ((isTriesLimitActive) ? "Ativo" : "Desativado") + " | Quantidade: " + triesLimit);
            System.out.println("1 - Ativar/Desativar");
            System.out.println("2 - Escolher quantidade (Padrão 5)");
            System.out.println("0 - Voltar");
            checkScanner();

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
                System.out.println("Acabou as tentativas!");
                System.out.println("O número era: " + randomNum);
                return false;
            }
        }
        return true;
    }

    public void checkScanner() {
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido, por favor insira um número.");
            choice = -1;
        }
    }
}