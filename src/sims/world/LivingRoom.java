package sims.world;

import java.util.Scanner;
import sims.entity.*;
import sims.actions.*;

public class LivingRoom extends Location{
    public LivingRoom(){
        super("LivingRoom");
    }

    @Override
    public void showOptions(){
        System.out.println("1. View Needs");
        System.out.println("2. Browse the internet on your computer");
        System.out.println("3. Watch TV");
        System.out.println("4. Socialise with a friend over the phone");
        System.out.println("5. Go to the kitchen");
        System.out.println("6. Go to the washroom");
        System.out.println("7. Go to the bedroom");
    }
}
