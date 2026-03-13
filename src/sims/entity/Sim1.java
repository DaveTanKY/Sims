package sims.entity;

import sims.needs.Needs;

public class Sim1 extends SimProfile{

    // CHILD CLASS
    public Sim1(){
        super("Darius", new Chef(), new Needs(70,60,50,25,60,30));
        // internal states can be modified via setters to how much yall want
    }

}
