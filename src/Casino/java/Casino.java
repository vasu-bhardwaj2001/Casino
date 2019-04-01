/**
 * 
 */
/**
 * @author vabha
 *
 */
package Casino.java;
import java.util.*;
public class Casino {
private static int cash;
private static int bet;
private static int AceCounter;
private static ArrayList<Card> hand;
private static int handvalue;
private static String name;
	public static void main(String[] args){
		System.out.println("Hi! What is your name?");
		Scanner scan = new Scanner(System.in);
		name = scan.nextLine();
		System.out.println("Hello, "+name+", lets play some BlackJack!");
		System.out.println("How much cash do you want to start with?");
		Scanner money = new Scanner(System.in);
		cash = money.nextInt();
		System.out.println("You start with cash: "+cash);
		while(cash>0){
			Deck deck = new Deck();
			deck.shuffle();
			AceCounter=0;
			Dealer dealer = new Dealer(deck);
			List<Card> hand = new ArrayList<>();
			hand.add(deck.drawCard());
			hand.add(deck.drawCard());
			System.out.println("How much would you like to bet?");
			bet=Bet(cash);
			System.out.println("Cash:"+(cash-bet));
			System.out.println("Money on the table:"+bet);
			System.out.println("Here is your hand: ");
			System.out.println(hand);
			int handvalue = calcHandValue(hand);
			System.out.println("The dealer is showing: ");
			dealer.showFirstCard();
			if(hasBlackJack(handvalue) && dealer.hasBlackJack()){
				Push();
			}
			else if(hasBlackJack(handvalue)){
				System.out.println("You have BlackJack!");
				System.out.println("You win 2x your money back!");
				cash=cash+bet;
				Win();
			}
			else if(dealer.hasBlackJack()){
				System.out.println("Here is the dealer's hand:");
				dealer.showHand();
				Lose();
			}	
			else{
				if(2*bet<cash){
					System.out.println("Would you like to double down?");
					Scanner doubledown = new Scanner(System.in);
					String doubled = doubledown.nextLine();
					while(!isyesorno(doubled)){
						System.out.println("Please enter yes or no.");
						doubled = doubledown.nextLine();
					}
					if(doubled.equals("yes")){
						System.out.println("You have opted to double down!");
						bet=2*bet;
						System.out.println("Cash:"+(cash-bet));
						System.out.println("Money on the table:"+bet);
					}
				}	
            System.out.println("Would you like to hit or stand?");
            Scanner hitorstand = new Scanner(System.in);
            String hitter = hitorstand.nextLine();
            while(!isHitorStand(hitter)){
                System.out.println("Please enter 'hit' or 'stand'.");
                hitter = hitorstand.nextLine();
            }
            while(hitter.equals("hit")){
                Hit(deck, hand);
                System.out.println("Your hand is now:");
                System.out.println(hand);
                handvalue = calcHandValue(hand);
                if(checkBust(handvalue)){
                    Lose();
                    break;
                }
                if(handvalue<=21 && hand.size()==5){
                    fivecardtrick();
                    break;
                }
                System.out.println("Would you like to hit or stand?");
                hitter = hitorstand.nextLine();
            }
            if(hitter.equals("stand")){
                int dealerhand = dealer.takeTurn(deck);
                System.out.println("");
                System.out.println("Here is the dealer's hand:");
                dealer.showHand();
                if(dealerhand>21){
                    Win();
                }
                else{
                    int you = 21-handvalue;
                    int deal = 21-dealerhand;
                    if(you==deal){
                        Push();
                    }
                    if(you<deal){
                        Win();
                    }
                    if(deal<you){
                        Lose();
                    }
                }
            }
        }
			System.out.println("Would you like to play again?");
			Scanner yesorno = new Scanner(System.in);
			String answer = yesorno.nextLine();
			while(!isyesorno(answer)){
                System.out.println("Please answer yes or no.");
                answer = yesorno.nextLine();
            }
			if(answer.equals("no")){
				break;
			}
		}
		System.out.println("Your cash is: "+cash);
		if(cash==0)
		{
			System.out.println("You ran out of cash!");
		}
		else
		{
			System.out.println("Enjoy your winnings, "+name+"!");
		}
	}

	public static boolean hasBlackJack(int handValue){
		if(handValue==21)
		{
			return true;
		}	
		return false;
	}
	
	public static int calcHandValue(List<Card> hand){
		Card[] aHand = new Card[]{};
		aHand = hand.toArray(aHand);
		int handvalue=0;
		for(int i=0; i<aHand.length; i++){
			handvalue += aHand[i].getValue();
			if(aHand[i].getValue()==11){
				AceCounter++;
			}
			while(AceCounter>0 && handvalue>21){
				handvalue-=10;
				AceCounter--;
			}
		}
		return handvalue;
	}

	public static int Bet(int cash){
		Scanner sc=new Scanner(System.in);
		int bet=sc.nextInt();
		while(bet>cash){
			System.out.println("You cannot bet more cash than you have!");
			System.out.println("How much would you like to bet?");
			bet=sc.nextInt();
		}	
		return bet;
	}

	public static void Win(){
		System.out.println("Congratulations, you win!");
		cash=cash+bet;
		System.out.println("Cash: "+cash);
	}

	public static void Lose(){
		System.out.println("Sorry, you lose!");
		cash=cash-bet;
		System.out.println("Cash: "+cash);
	}

	public static void Push(){
		System.out.println("It's a push!");
		System.out.println("You get your money back.");
		System.out.println("Cash: "+cash);
	}

	public static void Hit(Deck deck, List<Card> hand){
		hand.add(deck.drawCard());
		Card[] aHand = new Card[]{};
		aHand = hand.toArray(aHand);
		handvalue = 0;
		for(int i=0; i<aHand.length; i++){
			handvalue += aHand[i].getValue();
			if(aHand[i].getValue()==11){
				AceCounter++;
			}
			while(AceCounter>0 && handvalue>21){
				handvalue-=10;
				AceCounter--;
			}
		}
	}

	public static boolean isHitorStand(String hitter){
		if(hitter.equals("hit") || hitter.equals("stand")){
			return true;
		}
		return false;
	}

	public static boolean checkBust(int handvalue){
		if(handvalue>21){
			System.out.println("You have busted!");
			return true;
		}
		return false;
	}

	public static boolean isyesorno(String answer){
		if(answer.equals("yes") || answer.equals("no"))
		{
			return true;
		}
		return false;
	}

	public static void fivecardtrick(){
		System.out.println("You have achieved a five card trick!");
		Win();
	}
}