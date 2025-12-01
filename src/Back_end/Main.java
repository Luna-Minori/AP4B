package Back_end;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // Declare scanner once here

		// Proceed with the rest of your game logic
		Board board = new Board();
		int nb_player = getnumberPlayers(scanner);
		for (int i = 0; i < nb_player; i++) {
			board.addPlayer(new Player(getname(scanner), true));
		}
        /*
		int nb_bot = getnumberBot(scanner);
		for (int i = 0; i < nb_bot; i++) {
			board.addPlayer(new Player("Bot" + i, false));
		}*/
		
		// Continue with the game
		board.new_turn();
        System.out.println("New Turn Started");
		boolean lowest = true;
        System.out.println("1");
		while (!board.stateOfGame()) {
            System.out.println("2");
			for (int i = 0; i < board.getPlayers().size(); i++) {
                System.out.println("3");
				if (board.stateOfGame()) {
					break;
				}
                System.out.println("4");
                board.getPlayers().get(i).affHand();
					if(board.getPlayers().get(i).getHuman() == true) {
						boolean endturn = false;
                        int nBCard = 0;
                        boolean endask = false;
                        int index = 0;
						do {
							try {
								switch (getaction(scanner)) {
                                    case 1:
                                        board.getPlayers().get(i).addAskedCard(board.getPlayers().get(i).showCard(getindex(board.getPlayers().get(i).getHand().size(), scanner)), board.getPlayers().get(i));
                                        board.getPlayers().get(i).cardPlayble(board.getPlayers().get(i).getAskedCards().getLast().getCard().getIntValue());
                                        break;
                                    case 2: // joue une carte
                                        endask = false;
                                        do {
                                            try { // demande le joueur
                                                System.out.print("Choisissez un joueur (1 à " + board.getPlayers().size() + ") : ");
                                                int choice = scanner.nextInt();
                                                index = choice - 1;

                                                if (index < 0 || index >= board.getPlayers().size()) {
                                                    System.out.println("Index invalide.");
                                                } else {
                                                    Player p = board.getPlayers().get(index);
                                                    do {
                                                        try { // demande la carte la plus haute ou basse
                                                            switch (getaction(scanner)) {
                                                                case 1:
                                                                    lowest = true;
                                                                    endask = true;
                                                                    break;
                                                                case 2:
                                                                    lowest = false;
                                                                    endask = true;
                                                                    break;
                                                            }
                                                        } catch (IllegalArgumentException e) {
                                                            System.out.println(e.getMessage());
                                                        }
                                                    } while (!endask);
                                                }
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } while (!endask);
                                        board.getPlayers().get(i).addAskedCard(board.getPlayers().get(i).askCard(lowest, board.getPlayers().get(index)), board.getPlayers().get(index));
                                        break;
                                    case 3:
                                        endask = false;
                                        do {
                                            try {
                                                System.out.print("Choisissez une carte (1 à " + board.getMiddleDeck().size() + ") : ");
                                                int choice = scanner.nextInt();
                                                index = choice - 1;

                                                if (index < 0 || index >= board.getPlayers().size()) {
                                                    System.out.println("Index invalide.");
                                                }
                                                else{
                                                    endask = true;
                                                }
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } while (!endask);
                                        board.getPlayers().get(i).addAskedCard(board.getMiddleDeck().get(index), board);
                                    default:
                                        break;
                                }
                                nBCard += 1;
                                if(!(nBCard < 3 && sameValues(board.getPlayers().get(i).getAskedCards()))){
                                    endturn = true;
                                }
							} catch (IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
						} while (!endturn);

                        if(nBCard == 3 && sameValues(board.getPlayers().get(i).getAskedCards())){
                            board.getPlayers().get(i).updatePointGame();
                        }
					}
					else {
                        /*
						if(board.getPlayers().get(i).getHandValue() <= 5) {
							board.getPlayers().get(i).setHandDown(true);
						}
						else {
							for(int j = 0; j < board.getPlayers().get(i).getHand().size(); j++) {
								if(board.getPlayers().get(i).getCard(j).getValue() >= board.getBin().getValue() || (board.getBin().getValue() == 7 && board.getPlayers().get(i).getCard(j).getValue() == 1)) {
									board.cardPlayble(j, i);
									board.getPlayers().get(i).affhand();
									break;
								}
							}
							if(board.getPlayers().get(i).getHandValue() <= 5) {
								board.getPlayers().get(i).setHandDown(true);
							}
							else {
								try {
									board.getPlayers().get(i).drawCard(board.getDeck().draw());
								} catch (IllegalArgumentException e) {
									board.getPlayers().get(i).setHandDown(true);
								}
							}*/
				}
			}
		}
		scanner.close(); // Close scanner at the end
	}


	private static String getname(Scanner scanner) {
		String name;
		do {
			System.out.println("Veuillez saisir votre nom: ");
			name = scanner.nextLine();
		} while (name.isEmpty());
		return name;
	}

	private static int getnumberPlayers(Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre de joueur entre 2 et 6: ");
			number = scanner.nextInt();
		} while (!(number >= 2 && number <= 6));
		return number;
	}
	
	private static int getnumberBot(Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre de Bot entre 1 et 2 : ");
			number = scanner.nextInt();
		} while (!(number >= 1 && number <= 2));
		return number;
	}

	private static int getaction(Scanner scanner) {
		int number;
		do {
            System.out.println("Choisissez une actions  : ");
            System.out.println("1 Montrer une de vos carte  : ");
            System.out.println("2 demander une autre carte  : ");
            System.out.println("3 centre  : ");
			number = scanner.nextInt();
		} while (!(number >= 1 && number <= 3));
		return number;
	}

	private static int getindex(int taille, Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre entre 1 et " + taille);
			number = scanner.nextInt();
		} while (!(number >= 1 && number <= taille));
		return number - 1;
	}

    private static boolean sameValues(ArrayList<PlayerCardRequest> cards) {
        if (cards.isEmpty()) {
            return true; // An empty list can be considered to have the same values
        }
        int firstValue = cards.get(0).getCard().getIntValue();
        for (PlayerCardRequest card : cards) {
            if (card.getCard().getIntValue() != firstValue) {
                return false;
            }
        }
        return true;
    }
}

