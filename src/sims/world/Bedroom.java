package sims.world;

public class Bedroom extends Location{
    public Bedroom(){
        super("Bedroom");
    }

    @Override
    public void showOptions(){
        System.out.println("1. Go to sleep");
        System.out.println("2. Go to the kitchen");
        System.out.println("3. Go to the living room");
        System.out.println("4. Go to the washroom");
        System.out.println("5. Go to work");
    }
}
