package sims.world;

public class LivingRoom extends Location{
    public LivingRoom(){
        super("LivingRoom");
    }

    @Override
    public void showOptions(){
        System.out.println("1. Browse the internet on your computer");
        System.out.println("2. Watch TV");
        System.out.println("3. Socialise with a friend over the phone");
        System.out.println("4. Go to the kitchen");
        System.out.println("5. Go to the washroom");
        System.out.println("6. Go to the bedroom");
    }
}
