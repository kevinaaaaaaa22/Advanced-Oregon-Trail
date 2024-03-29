//An object class used to store the player's inventory

class Supplies{

    //_______________________________________________
    //Declaring the instance variables

    private int food;
    private int rifle;
    private int bullets;
    private int gunpowder;
    private int oxens;
    private int axels;
    private int tongues;
    private int wheels;

    //_______________________________________________
    //Constructor

    public Supplies(){
        this.food = 0;
        this.rifle = 0;
        this.bullets = 0;
        this.gunpowder = 0;
        this.oxens = 0;
        this.axels = 0;
        this.tongues = 0;
        this.wheels = 0;
    }

    //_______________________________________________
    //Accessors

    public String getSuppliesSummary(){
        String returnStr = "Food: " + this.food + " lbs\n" + "Weapons: " + this.rifle + " hunting rifles\n"
                + "Ammunitions: " + this.bullets + " bullets\n"
                + "Gunpowder: " + this.gunpowder + " lbs\n"
                + "Oxens: " + this.oxens + " oxens\n"
                + "Axels: " + this.axels + " wagon axels\n"
                + "Tongues: " + this.tongues + " wagon tongues\n"
                + "Wheels: " + this.wheels + " wagon wheels\n";
        return returnStr;
    }

    public int getFood(){
        return this.food;
    }

    public int getWeapons(){
        return this.rifle;
    }

    public int getAmmunition(){
        return this.bullets;
    }

    public int getGunpowder(){
        return this.gunpowder;
    }

    public int getAxels(){
        return this.axels;
    }

    public int getTongues(){
        return this.tongues;
    }

    public int getWheels(){
        return this.wheels;
    }

    //_______________________________________________
    //Mutators

    public void addFood(int amount){
        this.food += amount;
    }

    public void resetFood(){
        this.food = 0;
    }

    public void addGunpowder(int amount){
        this.gunpowder += amount;
    }

    public void addBullets(int amount){
        this.bullets += amount;
    }

    public void addAxels(int amount){
        this.axels += amount;
    }

    public void addTongues(int amount){
        this.tongues += amount;
    }

    public void addWheels(int amount){
        this.wheels += amount;
    }

    //A helper method used to add supplies to a player's inventory.  Returns true if the supplies are successfully added, and false otherwise
    public boolean addSupplies(String key, int amount){
        if (key.equals("f")){
            this.food += amount;
            return true;
        } else if (key.equals("r")){
            this.rifle += amount;
            return true;
        } else if (key.equals("b")){
            this.bullets += amount;
            return true;
        } else if (key.equals("g")){
            this.gunpowder += amount;
            return true;
        } else if (key.equals("o")){
            this.oxens += amount;
            return true;
        } else if (key.equals("a")){
            this.axels += amount;
            return true;
        } else if (key.equals("t")){
            this.tongues += amount;
            return true;
        } else if (key.equals("w")){
            this.wheels += amount;
            return true;
        }
        return false;
    }

}





