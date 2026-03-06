package sims.needs;

public class Needs {
    // private final String name;
    private int energy;
    private int social;
    private int hygiene;
    private int bladder;
    private int hunger;
    private int fun;

    public Needs(int energy, int social, int hygiene, int bladder, int hunger, int fun){
        // CONSTRUCTOR
        // this.name = name;
        this.energy = Math.max(0, Math.min(100, energy)); // combines this.energy = energy and clamps the value between 0 and 100
        this.social = Math.max(0, Math.min(100, social)); // combines this.social = social and clamps the value between 0 and 100
        this.hygiene = Math.max(0, Math.min(100, hygiene)); // combines this.hygiene = hygiene and clamps the value between 0 and 100
        this.bladder = Math.max(0, Math.min(100, bladder)); // combines this.bladder = bladder and clamps the value between 0 and 100
        this.hunger = Math.max(0, Math.min(100, hunger)); // combines this.hunger = hunger and clamps the value between 0 and 100
        this.fun = Math.max(0, Math.min(100, fun)); // combines this.fun = fun and clamps the value between 0 and 100
    }

    // clamping is done in the constructor and in the setter (defensive programming)
    // protects the initial state and protects future states

    // public String getName(){
    //    return name;
    //}


    public int getEnergy(){
        return energy;
    }
    public void setEnergy(int energy){
        this.energy = Math.max(0, Math.min(100, energy)); // protects the object from invalid states outside of initialization (encapsulation)
    }


    public int getSocial(){
        return social;
    }
    public void setSocial(int social){
        this.social = Math.max(0, Math.min(100, social)); // protects the object from invalid states outside of initialization (encapsulation)
    }


    public int getHygiene(){
        return hygiene;
    }
    public void setHygiene(int hygiene){
        this.hygiene = Math.max(0, Math.min(100, hygiene)); // protects the object from invalid states outside of initialization (encapsulation)
    }


    public int getBladder(){
        return bladder;
    }
    public void setBladder(int bladder){
        this.bladder = Math.max(0, Math.min(100, bladder)); // protects the object from invalid states outside of initialization (encapsulation)
    }


    public int getHunger(){
        return hunger;
    }
    public void setHunger(int hunger){
        this.hunger = Math.max(0, Math.min(100, hunger)); // protects the object from invalid states outside of initialization (encapsulation)
    }


    public int getFun(){
        return fun;
    }
    public void setFun(int fun){
        this.fun = Math.max(0, Math.min(100, fun)); // protects the object from invalid states outside of initialization (encapsulation)
    }


    public void decay(){
        this.setEnergy(energy - 9); // energy decreases over time
        this.setSocial(social - 7); // social decreases over time
        this.setHygiene(hygiene - 10); // hygiene decreases over time
        this.setBladder(bladder + 8); // bladder increases over time
        this.setHunger(hunger - 12); // hunger decreases over time
        this.setFun(fun - 8); // fun decreases over time
    }
    // can implement some method like
    // updateNeeds() { needs.decay(); } if yall want

    // eg. sim1.updateNeeds();
    //     sim2.updateNeeds();
}
