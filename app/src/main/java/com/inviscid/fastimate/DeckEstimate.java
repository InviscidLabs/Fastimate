package com.inviscid.fastimate;


//package com.inviscid.fastimate;

/**
 * Created Bryant Donato on 5/26/2015.
 */

//Desk estimation variables
public class DeckEstimate {

    private double deckWidth;
    private double deckLength; //Might need a length against the house variable  See below
    private double deckHouseLedgerLength; // Length against the house.  Defaults to above?
    private double deckHeight;
    private double deckArea;
    private double postLengths;
    private int bagsOfConcrete; //3 per post
    private double houseLedgerBoardLength, rimBoardLength; //2x8's?
    private LedgerFastener ledgerFastener = new LedgerFastener();  //For every 2 foot of board next to house
    public DeckBeam deckBeam;
    private int numberOfDeckBoards;
    private double deckPerimeter;
    private int numberOfPosts; //Every 6 feet.  Is this perimeter?  6x6's always?
    private JoistEstimate joist;
    private DeckBoardFastener dbFastener;

    public double getDeckHouseLedgerLength() {
        return deckHouseLedgerLength;
    }

    public void setDeckHouseLedgerLength(double deckHouseLedgerLength) {
        this.deckHouseLedgerLength = deckHouseLedgerLength;
    }

    public double getDeckArea() {
        return deckArea;
    }

    public void setDeckArea(double deckArea) {
        this.deckArea = deckArea;
    }

    public double getDeckPerimeter() {
        return deckPerimeter;
    }

    public void setDeckPerimeter(double deckPerimeter) {
        this.deckPerimeter = deckPerimeter;
    }



    public double getPostLengths() {
        return postLengths;
    }

    public void setPostLengths(double postLengths) {
        this.postLengths = postLengths;
    }



    //{Get; Set}


    public double getHouseLedgerBoardLength() {
        return houseLedgerBoardLength;
    }

    public double getRimBoardLength() {
        return rimBoardLength;
    }

    public int getBagsOfConcrete() {
        return bagsOfConcrete;
    }

    public int getNumberOfDeckBoards() {
        return numberOfDeckBoards;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setBagsOfConcrete(int bagsOfConcrete) {
        this.bagsOfConcrete = bagsOfConcrete;
    }

    public void setHouseLedgerBoardLength(double houseLedgerBoardLength) {
        this.houseLedgerBoardLength = houseLedgerBoardLength;
    }

    public void setLedgerFastener(LedgerFastener ledgerFastener) {
        this.ledgerFastener = ledgerFastener;
    }

    public void setNumberOfDeckBoards(int numberOfDeckBoards) {
        this.numberOfDeckBoards = numberOfDeckBoards;
    }

    public void setRimBoardLength(double rimBoardLength) {
        this.rimBoardLength = rimBoardLength;
    }

    public LedgerFastener getLedgerFastener() {
        return ledgerFastener;
    }


    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public double getDeckHeight() {
        return deckHeight;
    }

    public double getDeckWidth() {
        return deckWidth;
    }

    public double getDeckLength() {
        return deckLength;
    }

    public void setDeckHeight(double deckHeight) {
        this.deckHeight = deckHeight;
    }

    public void setDeckWidth(double deckWidth) {
        this.deckWidth = deckWidth;
    }


    public void setDeckLength(double deckLength) {
        this.deckLength = deckLength;
    }

    //Base constructor
    public DeckEstimate(){

        deckHeight = 0;
        deckLength = 0;
        deckWidth = 0;
        deckHouseLedgerLength = 0;

        deckArea = 0;
        deckPerimeter = 0;
    }

    //Info Constructor
    public DeckEstimate(double length, double width, double height){
        deckHeight = height;
        deckLength = length;
        deckWidth = width;
        deckHouseLedgerLength = length;

        deckArea = deckLength * deckWidth;
        deckPerimeter = 2*deckLength + 2*deckWidth;
    }

    public DeckEstimate(double length, double ledger, double width, double height){
        deckHeight = height;
        deckLength = length;
        deckWidth = width;
        deckHouseLedgerLength = ledger;

        deckArea = deckLength * deckWidth;
        deckPerimeter = 2*deckLength + 2*deckWidth;
    }

    private void calculatePosts(){

        //number of 6x6's
        postLengths = deckHeight+3;
        numberOfPosts = (int)(deckPerimeter)/6 + 1; //Every 6ft is a post,
        bagsOfConcrete = numberOfPosts *3; //3 bags per
    }

    private void calculateLedger(){

        ledgerFastener.setNumberOfFasteners((int) deckHouseLedgerLength/2 );  //Once fastener per 2 feet
        ledgerFastener.setLagScrews(2*ledgerFastener.getNumberOfFasteners());  // 2 screws, washers, nuts per fastener.  continues below
        ledgerFastener.setNuts(ledgerFastener.getLagScrews());
        ledgerFastener.setWashers(ledgerFastener.getLagScrews());
        this.setRimBoardLength(this.getDeckHouseLedgerLength());
    }

    public void calculateDeckBeams() {  //2x8's
        //2 posts the length of the deck
        deckBeam = new DeckBeam(deckLength);
        deckBeam.setLength(this.deckLength);
        deckBeam.setNumberOfBeams(1);
        while(deckBeam.getLength() > 16){
            deckBeam.setLength(deckBeam.getLength()/2);
            deckBeam.setNumberOfBeams(deckBeam.getNumberOfBeams()*2);
        }
        DeckBeamFastener deckbf = new DeckBeamFastener();
        deckbf.setNumberOfFasteners(numberOfPosts * 2);
        deckbf.setCarriageBolts(2 * deckbf.getNumberOfFasteners());
        deckbf.setNuts(2 * deckbf.getNumberOfFasteners());
        deckbf.setWashers(2*deckbf.getNumberOfFasteners());

        deckBeam.setDeckBeamFastener(deckbf);

    }
    //Added by JCR 5/28/2015
    public void calculateJoistComponents(){
        joist = new JoistEstimate();
        //Equation: 75% of Deck Length +1, rounded up
        joist.setNumberOfJoists((int) Math.ceil((deckLength * .75) + 1));
        //Simply = number of Joists
        joist.setNumberOfJoistHangers(joist.getNumberOfJoists()); //Possible Multiplication by 2?
        //1 for basic decks
        joist.setBoxesOfHangerNails(1);
    }

    public void calculateDeckBoards(){
        //=Width of Deck IN INCHES/ 5.5
        //Important Change: Choose the largest of length and width to determine estimate
        setNumberOfDeckBoards((int) Math.ceil((Math.max(deckWidth,deckLength)*12)/5.5)); //Convert to inches here
        //If deck width and height are both longer than ~15, multiply final amount by 1.5
        if(deckWidth > 15 && deckLength > 15){
            setNumberOfDeckBoards((int)(numberOfDeckBoards* 1.5) );
        }
        dbFastener = new DeckBoardFastener();
        //Formula = 15lb box for every 100ft^2 of deck+10%, round up
        dbFastener.setBoxesOfGalvanizedDeckScrews((int) Math.ceil(1.1*(deckLength*deckWidth/100)));
        dbFastener.setBoxesOf8dGalvanizedSpiralNails((int) Math.ceil(1.1*(deckLength*deckWidth/100)));
    }

    public void printTestEstimate(){ //Debugging method
        if(deckLength <=1 || deckWidth <= 1 ){

            System.out.println("Debug: Missing height and width");

        }else{



            this.calculatePosts();
            this.calculateLedger();
            this.calculateDeckBeams();
            //JCR 5/28/2015
            this.calculateDeckBoards();
            this.calculateJoistComponents();


            System.out.println("Here is the DeckEstimation Test Results:");
            System.out.println("Deck Dimensions:  Length: " + this.deckLength + " Width: " + this.deckHeight + " Height: " + this.deckHeight) ;
            System.out.println("Deck Calculations: Area: " + this.deckArea + "  Perimiter: "+ this.deckPerimeter );
            System.out.println("House Ledger Length: " + this.deckHouseLedgerLength);
            System.out.println("House Ledger Fasteners: " + this.ledgerFastener.getNumberOfFasteners());
            System.out.println("Ledger Fastener Screws and related: Screws, Nuts and Washers: " + this.ledgerFastener.getLagScrews());
            System.out.println("Number of Posts (Not including House Ledger length: " + this.numberOfPosts + " at Length " + (this.deckHeight + 3) +"'");
            System.out.println("Number of bags of concrete: " + this.bagsOfConcrete);
            System.out.println("Number of Deck Beams: " + this.deckBeam.getNumberOfBeams() + " At length " + this.deckBeam.getLength()+"'");
            System.out.println("Number of Deck Beam Fasteners: "  + this.deckBeam.getDeckBeamFastener().getNumberOfFasteners() );
            System.out.println("Deck Beam Screws and related: Carriage Bolts, Washers, Nuts: "  + this.deckBeam.getDeckBeamFastener().getNuts());

            System.out.println("Number- of Joists: " +this.joist.getNumberOfJoists());
            System.out.println("Number of Joist Hangers: " + this.joist.getNumberOfJoistHangers());
            System.out.println("Number of Hanger Nails:" + this.joist.getBoxesOfHangerNails());
            System.out.println("Rim Board Length: " + this.getRimBoardLength());
            System.out.println("Number of Deck Boards: " + this.getNumberOfDeckBoards());
            System.out.println("Number of Spiral Nail Boxes: " + dbFastener.getBoxesOf8dGalvanizedSpiralNails());
            System.out.println("Number of Gavanized Deck Screw Boxes: " + dbFastener.getBoxesOfGalvanizedDeckScrews());

        }
    }

    public String getDeckEstimate(){
        StringBuilder sb = new StringBuilder();


        if(deckLength <=1 || deckWidth <= 1 ){

            System.out.println("Debug: Missing height and width");

        }else{



            this.calculatePosts();
            this.calculateLedger();
            this.calculateDeckBeams();
            //JCR 5/28/2015
            this.calculateDeckBoards();
            this.calculateJoistComponents();


            sb.append("Here is the DeckEstimation Test Results: \n");
            sb.append("Deck Dimensions:  Length: ").append(this.deckLength).append(" Width: ").append(this.deckWidth).append(" Height: ").append(this.deckHeight).append("\n");
            sb.append("Deck Calculations: Area: ").append(this.deckArea).append("  Perimiter: ").append(this.deckPerimeter);sb.append("\n");
            sb.append("House Ledger Length: ").append(this.deckHouseLedgerLength);sb.append("\n");
            sb.append("House Ledger Fasteners: ").append(this.ledgerFastener.getNumberOfFasteners());sb.append("\n");
            sb.append("Ledger Fastener Screws and related: Screws, Nuts and Washers: ").append(this.ledgerFastener.getLagScrews());sb.append("\n");
            sb.append("Number of Posts (Not including House Ledger length: ").append(this.numberOfPosts).append(" at Length ").append((this.deckHeight + 3)).append("'");sb.append("\n");
            sb.append("Number of bags of concrete: ").append(this.bagsOfConcrete);sb.append("\n");
            sb.append("Number of Deck Beams: ").append(this.deckBeam.getNumberOfBeams()).append(" At length ").append(this.deckBeam.getLength()).append( "'");sb.append("\n");
            sb.append("Number of Deck Beam Fasteners: " ).append(this.deckBeam.getDeckBeamFastener().getNumberOfFasteners());sb.append("\n");
            sb.append("Deck Beam Screws and related: Carriage Bolts, Washers, Nuts: " ).append(this.deckBeam.getDeckBeamFastener().getNuts());sb.append("\n");

            sb.append("Number of Joists: ").append(this.joist.getNumberOfJoists());sb.append("\n");
            sb.append("Number of Joist Hangers: ").append(this.joist.getNumberOfJoistHangers());sb.append("\n");
            sb.append("Number of Hanger Nails:").append(this.joist.getBoxesOfHangerNails());sb.append("\n");
            sb.append("Rim Board Length: ").append(this.getRimBoardLength());sb.append("\n");
            sb.append("Number of Deck Boards: ").append(this.getNumberOfDeckBoards());sb.append("\n");
            sb.append("Number of Spiral Nail Boxes: ").append(dbFastener.getBoxesOf8dGalvanizedSpiralNails());sb.append("\n");

        }
        System.out.println(sb);
        return sb.toString();
    }

}
class LedgerFastener  {

    private int numberOfFasteners;
    private int lagScrews;
    private int washers;
    private int nuts;

    //{get; set;}


    public int getLagScrews() {
        return lagScrews;
    }

    public int getNumberOfFasteners() {
        return numberOfFasteners;
    }

    public int getNuts() {
        return nuts;
    }

    public int getWashers() {
        return washers;
    }


    public void setLagScrews(int lagScrews) {
        this.lagScrews = lagScrews;
    }

    public void setNumberOfFasteners(int numberOfFasteners) {
        this.numberOfFasteners = numberOfFasteners;
    }

    public void setNuts(int nuts) {
        this.nuts = nuts;
    }

    public void setWashers(int washers) {
        this.washers = washers;
    }

    //Constructor

    public LedgerFastener(){

    }

}

class DeckBeam extends DeckEstimate{
    //attributes
    private int numberOfBeams;
    private double length;
    public DeckBeamFastener deckBeamFastener;

    //{get;set;}
    public DeckBeamFastener getDeckBeamFastener(){

        return deckBeamFastener;
    }

    public void setDeckBeamFastener(DeckBeamFastener dbf){

        deckBeamFastener = dbf;
    }
    public double getLength() {
        return length;
    }

    public int getNumberOfBeams() {
        return numberOfBeams;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setNumberOfBeams(int numberOfBeams) {
        this.numberOfBeams = numberOfBeams;
    }
    //Constructors
    public DeckBeam(){

    }
    public DeckBeam(double len){
        length = len;
    }

}

class DeckBeamFastener extends DeckEstimate{

    //Attributes
    private int numberOfFasteners,carriageBolts,washers,nuts;

    //Getters and Setters


    public int getCarriageBolts() {
        return carriageBolts;
    }

    public void setWashers(int washers) {
        this.washers = washers;
    }

    public void setNuts(int nuts) {
        this.nuts = nuts;
    }

    public int getNumberOfFasteners() {
        return numberOfFasteners;
    }

    public int getNuts() {
        return nuts;
    }

    public int getWashers() {
        return washers;
    }

    public void setCarriageBolts(int carriageBolts) {
        this.carriageBolts = carriageBolts;
    }

    public void setNumberOfFasteners(int numberOfFasteners) {
        this.numberOfFasteners = numberOfFasteners;
    }


    //Constructors
    public DeckBeamFastener(){}

}

class JoistEstimate extends DeckEstimate {


    //Attributes
    private int numberOfJoists, numberOfJoistHangers;
    private int boxesOfHangerNails;

    //Getters and Setters


    public int getBoxesOfHangerNails() {
        return boxesOfHangerNails;
    }

    public int getNumberOfJoistHangers() {
        return numberOfJoistHangers;
    }

    public int getNumberOfJoists() {
        return numberOfJoists;
    }

    public void setBoxesOfHangerNails(int boxesOfHangerNails) {
        this.boxesOfHangerNails = boxesOfHangerNails;
    }

    public void setNumberOfJoistHangers(int numberOfJoistHangers) {
        this.numberOfJoistHangers = numberOfJoistHangers;
    }

    public void setNumberOfJoists(int numberOfJoists) {
        this.numberOfJoists = numberOfJoists;
    }

    //Constructors
    public JoistEstimate() {
    }


}

class DeckBoardFastener{


    public int getBoxesOfGalvanizedDeckScrews() {
        return boxesOfGalvanizedDeckScrews;
    }

    public void setBoxesOfGalvanizedDeckScrews(int boxesOfGalvanizedDeckScrews) {
        this.boxesOfGalvanizedDeckScrews = boxesOfGalvanizedDeckScrews;
    }

    public int getBoxesOf8dGalvanizedSpiralNails() {
        return boxesOf8dGalvanizedSpiralNails;
    }

    public void setBoxesOf8dGalvanizedSpiralNails(int boxesOf8dGalvanizedSpiralNails) {
        this.boxesOf8dGalvanizedSpiralNails = boxesOf8dGalvanizedSpiralNails;
    }

    private int boxesOfGalvanizedDeckScrews, boxesOf8dGalvanizedSpiralNails; //5lbs per


    //Constructor
    public DeckBoardFastener(){}
}

//Handrails

class Handrail{

    private int lengthOfHandrail, lengthOfBoardsSelected, numberOfPieces;
    private int topAndBottomRail = 2*lengthOfHandrail;
    private int topCap = lengthOfHandrail;
    private int blausterOrSpindle = lengthOfHandrail*3+1;

    public int getLengthOfHandrail() {
        return lengthOfHandrail;
    }

    public void setLengthOfHandrail(int lengthOfHandrail) {
        this.lengthOfHandrail = lengthOfHandrail;
    }

    public int getLengthOfBoardsSelected() {
        return lengthOfBoardsSelected;
    }

    public void setLengthOfBoardsSelected(int lengthOfBoardsSelected) {
        this.lengthOfBoardsSelected = lengthOfBoardsSelected;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    public int getTopAndBottomRail() {
        return topAndBottomRail;
    }

    public void setTopAndBottomRail(int topAndBottomRail) {
        this.topAndBottomRail = topAndBottomRail;
    }

    public int getTopCap() {
        return topCap;
    }

    public void setTopCap(int topCap) {
        this.topCap = topCap;
    }

    public int getBlausterOrSpindle() {
        return blausterOrSpindle;
    }

    public void setBlausterOrSpindle(int blausterOrSpindle) {
        this.blausterOrSpindle = blausterOrSpindle;
    }


    //constructors
    public Handrail(){}
}

class Stairs{

    //attriubutes
    private int stringers; //If stairs are 3' or widers
    private double stairHeight, stairWidth, staitLength;
    private int numberOfTreads;//Number of actual steps
    private double treadLinearFeet = numberOfTreads * stairWidth * 2;

    private int stairPosts; //Include Bolts, Washers, Nuts
    private int bagsOfConcrete;

    private Handrail stairHandrail = new Handrail();
}