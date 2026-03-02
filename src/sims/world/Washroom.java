package sims.world;

public class Washroom extends Location{
    public Washroom(){
        super("Washroom");
    }

    @Override
    public void showOptions(){
        System.out.println("1. Take a steamy bath");
        System.out.println("2. Take a rinse");
        System.out.println("3. Use the toilet");
        System.out.println("4. Go to the kitchen");
        System.out.println("5. Go to the living room");
        System.out.println("6. Go to the bedroom");
        System.out.println("7. Go to work");
    }
}
