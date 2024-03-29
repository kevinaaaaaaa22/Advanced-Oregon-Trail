//An object class operating the main game

class OregonTrail{

    //_______________________________________________
    //Declaring the instance fields

    private String classSelection;
    private double money;
    private double scoreMultiplier;
    private int score;
    private int day;
    private Supplies inventory;

    private int ration;
    private int health;
    private int distanceTravelled;
    private int speed;
    private int lastMilestone;

    //_______________________________________________
    //Game directions:

    static String getIntroduction(){
        return "Welcome to Advanced Oregon Trail!  You are about to embark on the journey from Independence to Oregon.  However, in this version of the game, the jourey will never end and will progressively get harder!  Your goal is to survive as many days as possible and receive as high a score as possible.  Your decisions will impact how the game progresses, and your score will be determined by your actions.  Now, to begin, select who you want to start out as.  You can choose to be:\nThe Farmer\nThe Carpenter\nThe Banker\nEric Shulman\nBryan Kinney\nFarmers start out with the lowest amount of money and bankers start out with the highest amount of money.  However, farmers receive higher scores for similar actions compared to bankers.";
    }

    static String getClassDirections(){
        return "Please select your class.  Some classes start out with more resources, however, these classes will also be awarded less points for their actions and achievements. ";
    }

    static String getShopDirections(String classSelection){
        if (classSelection.equals("farmer")){
            return "As a farmer, you have saved $500 to purchase supplies.  You can use them to purchase supplies now, or during the trip when you pass by forts.  $335 will automatically be deducted to purchase 1x connestoga wagon and 1x 3 oxen team, which are required to start the journey.  You are recommended to stock up on supplies right now as there is a long way ahead!";
        } else if (classSelection.equals("carpenter")){
            return "As a carpenter, you have saved $600 to purchase supplies.  You can use them to purchase supplies now, or during the trip when you pass by forts.  $335 will automatically be deducted to purchase 1x connestoga wagon and 1x 3 oxen team, which are required to start the journey.  You are recommended to stock up on supplies right now as there is a long way ahead!";
        } else if (classSelection.equals("banker")) {
            return "As a banker, you have saved $730 to purchase supplies.  You can use them to purchase supplies now, or during the trip when you pass by forts.  $335 will automatically be deducted to purchase 1x connestoga wagon and 1x 3 oxen team, which are required to start the journey.  You are recommended to stock up on supplies right now as there is a long way ahead!";
        } else if (classSelection.equals("Shulman")) {
            return "As Eric Shulman, you have saved $" + Integer.MAX_VALUE/2 + " to purchase supplies.  You can use them to purchase supplies now, or during the trip when you pass by forts.  $335 will automatically be deducted to purchase 1x connestoga wagon and 1x 3 oxen team, which are required to start the journey.  You are recommended to stock up on supplies right now as there is a long way ahead!";
        }
        return "As Bryan Kinney, you have saved $" + Integer.MAX_VALUE + " to purchase supplies.  You can use them to purchase supplies now, or during the trip when you pass by forts.  $335 will automatically be deducted to purchase 1x connestoga wagon and 1x 3 oxen team, which are required to start the journey.  You are recommended to stock up on supplies right now as there is a long way ahead!";
    }

    static String getShopContent(){
        String shopContents = "The following merchandise are available for purchase at the Independence Wagon Shop\n" +
                "Food: \n" +
                "1. 5lb Crackers,     $1.10\n" +
                "2. 10lb Dried Fruit, $0.33\n" +
                "3. 10lb Flour,       $0.36\n" +
                "4. 20lb Potato,      $0.42\n" +
                "5. 10lb Bacon,       $0.50\n" +
                "6. 10lb Butter,      $2.70\n" +
                "7. 10lb Candy,       $8.80\n" +
                "Weapons: \n" +
                "8. Hunting Rifle,    $20.00\n" +
                "9. 20xBullets,       $1.96\n" +
                "10. 20lb Gunpowder,  $6.05\n" +
                "Tools: \n" +
                "11. 3 Oxen Team,     $225.00\n" +
                "12. Wagon Axel,      $8.25\n" +
                "13. Wagon Tongues,   $9.35\n" +
                "14. Wagon Wheel,     $12.00\n";
        return shopContents;
    }

    static String purchaseDirections1(){
        return "Please enter the id (the number on the left) of the item you want to purchase.";
    }

    static String purchaseDirections2(){
        return "Please enter the quantity you want to purchase.";
    }

    static String mainDirections(){
        return "Your journey is about to start! Here are some information about the journey.  Everyday, your wagontrain will consume a certain amount of food and travel a certain distance.  Your health will also change depending on many factors.  You are allowed to check your supplies or change your ration (the amout of food consumed each day).  If your ration is lower than 50, you will begin to lose health.  If your ration is above 65, you will begin to gain health.  On some days, events might happen.  Events could be anything from passing by a fort where you can purchase additional supplies, to encoutering a bear which you can kill for extra food.  Fighting a bear without the proper equipment may not always end well though...\n";
    }

    //_______________________________________________
    //Money processor
    static double moneyProcess(double num){
        return ((int)(num*100)) / 100.0;
    }

    //_______________________________________________
    //Constructor
    public OregonTrail(){
        classSelection = "farmer";
        this.money = 500.0-335;
        this.scoreMultiplier = 1.5;

        this.score = 0;
        this.day = 0;
        this.distanceTravelled = 0;
        this.speed = 35;
        this.lastMilestone = 100;
        this.inventory = new Supplies();
        this.ration = 50;
        this.health = 100;
        this.inventory.addSupplies("o", 3);
    }

    //_______________________________________________
    //Mutators

    public void changeRation(int newR){
        this.ration = newR;
    }

    public void addScore(int num){
        this.score += (int) (num*this.scoreMultiplier) + 1;
    }

    public void changeHealth(int num){
        this.health += num;
    }

    public void addFood(int num){
        this.inventory.addFood(num);
    }

    public void changeSpeed(int num){
        this.speed = num;
    }

    public void changeClass(String c) {
        this.classSelection = c;
        if (classSelection.equals("farmer")){
            this.money = 500.0-335;
            this.scoreMultiplier = 1.5;
        } else if (classSelection.equals("carpenter")){
            this.money = 600.0-335;
            this.scoreMultiplier = 1.1;
        } else if (classSelection.equals("banker")){
            this.money = 730.0-335;
            this.scoreMultiplier = 0.75;
        } else if (classSelection.equals("Shulman")){
            this.money = Integer.MAX_VALUE/2-335;
            this.scoreMultiplier = 0.50;
        } else {
            this.money = Integer.MAX_VALUE-335;
            this.scoreMultiplier = 10.00;
        }
    }

    //_______________________________________________
    //Accessors

    public String getClassSelection(){
        return this.classSelection;
    }

    public Supplies getInventory(){
        return this.inventory;
    }

    public int getRation(){
        return this.ration;
    }

    public int getScore(){
        return this.score;
    }

    public int getDay(){
        return this.day;
    }

    public double getMoney(){
        return this.money;
    }

    public int getFood(){
        return this.inventory.getFood();
    }

    public String getSummary(){
        String returnStr = "Game Summary- \n" +
                "Current day: " + this.day + "\n" +
                "Current score: " + this.score + "\n" +
                "Distance travelled: " + this.distanceTravelled + "km\n" +
                "Money left: $" + this.money + "\n" +
                "Food left: " + this.getFood() + " lbs\n" +
                "Health: " + this.getHealth() + "\n" +
                "Ration per day: " + this.ration + "lbs";
        return returnStr;
    }

    //_______________________________________________
    //Game methods

    //A helper method used to buy supplies.  If the player does not have money left or encounters some other problems, the method returns the error.  When there are no problems with the purchase, the method deducts money and add the purchased supplies to the player's inventory
    public String buySupplies(int id, int quantity){
        String type = "";
        int amount = 0;
        double price = 0;
        if (id==1){
            type = "f";
            amount = quantity*5;
            price = quantity*1.1;
        } else if (id==2){
            type = "f";
            amount = quantity*10;
            price = quantity*0.33;
        } else if (id==3){
            type = "f";
            amount = quantity*10;
            price = quantity*0.36;
        } else if (id==4){
            type = "f";
            amount = quantity*20;
            price = quantity*0.42;
        } else if (id==5){
            type = "f";
            amount = quantity*10;
            price = quantity*0.50;
        } else if (id==6){
            type = "f";
            amount = quantity*10;
            price = quantity*2.70;
        } else if (id==7){
            type = "f";
            amount = quantity*10;
            price = quantity*8.80;
        } else if (id==8){
            type = "r";
            amount = quantity;
            price = quantity*20;
        } else if (id==9){
            type = "b";
            amount = quantity*20;
            price = quantity*1.96;
        } else if (id==10){
            type = "g";
            amount = quantity*20;
            price = quantity*6.05;
        } else if (id==11){
            type = "o";
            amount = quantity*3;
            price = quantity*225;
        } else if (id==12){
            type = "a";
            amount = quantity;
            price = quantity*8.25;
        } else if (id==13){
            type = "t";
            amount = quantity;
            price = quantity*9.35;
        } else if (id==14){
            type = "w";
            amount = quantity;
            price = quantity*12;
        } else {
            return "invalidID";
        }
        if (price>this.money){
            return "noMoney";
        }
        this.money -= price;
        this.money = moneyProcess(money);
        boolean added = this.inventory.addSupplies(type, amount);
        if (!added){
            return "failedAdd";
        }
        return "Purchased " + quantity + " items for " + price + " dollars.";
    }

    //Deducts the player's daily ration from their food supply.  Sets ration to 0 if the player runs out of food
    public String progressRation(){
        if (this.inventory.getFood() >= this.ration){
            this.inventory.addFood(-this.ration);
        } else {
            this.inventory.resetFood();
            this.ration = 0;
            return "YOU ARE OUT OF FOOD!!! Your ration has been set to 0.\n";
        }
        if (this.ration == 0 && this.inventory.getFood()>0){
            this.ration = 50;
            return "As you have aquired food, your ration has been reset to 50.";
        }
        return "";
    }

    //Daily progression method.  Changes the player's health based on their ration.  Add the daily score bonus to the player's score count.  Return the events that happened during the day.
    public String progressMain(){
        if (this.ration == 0){
            this.health -= 30;
        } else if (this.ration < 10){
            this.health -= 20;
        } else if (this.ration < 35){
            this.health -= 9;
        } else if (this.ration < 50) {
            this.health -= 4;
        } else if (this.ration > 65){
            this.health += (int) (Math.random()*15);
        }
        if (this.health <= 0){
            return "Over";
        }
        this.day += 1;
        this.score += (int) (20*this.scoreMultiplier);
        this.distanceTravelled += this.speed;
        if (this.distanceTravelled - this.lastMilestone >= 0){
            this.score += (int) (25*this.scoreMultiplier);
            this.lastMilestone += 100;
        }
        return GameEvents.getEvent(this);
    }

    //A helper method used to determine how healthy the player is based on their hp left
    public String getHealth(){
        if (this.health > 70){
            return "Healthy";
        } else if (this.health > 50){
            return "Somewhat Healthy";
        } else if (this.health > 30){
            return "Unhealthy";
        } else if (this.health > 0) {
            return "Very Unhealthy";
        }
        return "You Died";
    }

}










