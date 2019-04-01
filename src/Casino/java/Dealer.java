package Casino.java;

import java.util.ArrayList;
import java.util.Arrays;

	@SuppressWarnings("unused")
	class Dealer {
		ArrayList<Card> hand;
		private int handvalue=0;
		private Card[] aHand;
		private int AceCounter;
		Dealer(Deck deck){
			hand = new ArrayList<>();
			aHand = new Card[]{};
			int AceCounter=0;
			for(int i=0; i<2; i++){
		        hand.add(deck.drawCard());
    		}
    		aHand = hand.toArray(aHand);
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

	public void showFirstCard(){
	    Card[] firstCard = new Card[]{};
	    firstCard = hand.toArray(firstCard);
	    System.out.println("["+firstCard[0]+"]");
	}

	public void Hit(Deck deck){
	    hand.add(deck.drawCard());
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

	public boolean wantsToHit(){
	    if(handvalue<17){
	        return true;
	    }
    	return false;
	}

	public boolean hasBlackJack(){
	    if(hand.size()==2 && handvalue==21){
	        System.out.println("The dealer has blackjack!");
	        return true;
    	}
    	return false;
	}

	public void showHand(){
	    System.out.println(hand);
	}

	public int getHandValue(){
	    return handvalue;
	}

	public boolean busted(int handvalue){
	    if(handvalue>21){
	        System.out.println("The dealer busted!");
	        return true;
	    }
    	return false;
	}

	public int takeTurn(Deck deck){
	    while(wantsToHit()){
	        System.out.println("The dealer hits");
	        Hit(deck);
	        if(busted(handvalue)){
            break;
        	}
    	}
    	if(handvalue<=21){
	        System.out.print("The dealer stands.");
	    }
    	return handvalue;
	}
}