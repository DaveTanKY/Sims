package sims.world;

public class Kitchen extends Location{
    public Kitchen(){
        super("Kitchen");
    }

    @Override
    public void showOptions(){
        System.out.println("1. Eat something");
        System.out.println("2. Go to the living room");
        System.out.println("3. Go to the bedroom");
        System.out.println("4. Go to the washroom");
        System.out.println("5. Go to work");
    }
}
