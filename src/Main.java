//Main class

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;

class Main {

    //__________________________________
    //Instance variables

    //Booleans and main game
    public static boolean idSet;
    public static OregonTrail game;

    //Arraylists to store the player's purchase requests
    public static ArrayList<String> ids;
    public static ArrayList<String> amounts;

    //Frames used by the main game
    public static JFrame infoFrame;
    public static JFrame classSelectionFrame;
    public static JFrame shopFrame;
    public static JFrame transitionFrame;
    public static JFrame gameFrame;
    public static JFrame gameOverFrame;
    public static JFrame suppliesFrame;

    //Frames used by events
    public static JFrame fortFrame;

    //Scanner and dimensions
    private static final Scanner SCAN = new Scanner(System.in);
    public static final int WIDTH = 500;
    public static final int HEIGHT = 650;

    //Images
    public static JLabel trail;
    public static JLabel fort;
    public static JLabel Kinney;
    public static JLabel Shulman;

    public static void main(String[] args) throws IOException {

        //________________________________________________
        //Initializing the frames
        infoFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Intro");
        classSelectionFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Class Selection");
        shopFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Shop");
        transitionFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Transition");
        gameFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Game");
        gameOverFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Game Over");
        suppliesFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Supplies Summary");

        //________________________________________________
        //Initializing the images
        trail = SwingHelpers.image(new File("assets/Trail.png"));
        fort = SwingHelpers.image(new File("assets/Fort.png"));
        Kinney = SwingHelpers.image(new File("assets/Kinney.png"));
        Shulman = SwingHelpers.image(new File("assets/Shulman.png"));

        //________________________________________________
        //Setting up the info page
        JPanel infoPanel = new JPanel();
        infoPanel.add(SwingHelpers.image(new File("assets/Logo.png")));
        infoPanel.add(SwingHelpers.textArea(OregonTrail.getIntroduction()));
        infoPanel.add(SwingHelpers.frameSwitch("Continue", infoFrame, classSelectionFrame));
        infoFrame.add(infoPanel);
        infoFrame.setVisible(true);

        //________________________________________________
        //Setting up the classSelectionFrame + Selecting the class of the player and starting the game
        game = new OregonTrail();
        JPanel classSelectionPanel = new JPanel();

        classSelectionPanel.add(SwingHelpers.textArea(OregonTrail.getClassDirections()));

        /*
        //Doesn't work
        classSelectionPanel.add(SwingHelpers.classSelectionButton("Become the farmer!", classSelectionFrame, shopFrame, "farmer", game));
        classSelectionPanel.add(SwingHelpers.classSelectionButton("Become the carpenter!", classSelectionFrame, shopFrame, "carpenter", game));
        classSelectionPanel.add(SwingHelpers.classSelectionButton("Become the banker!", classSelectionFrame, shopFrame, "banker", game));
        classSelectionPanel.add(SwingHelpers.classSelectionButton("Become Eric Shulman!", classSelectionFrame, shopFrame, "shulman", game));
        classSelectionPanel.add(SwingHelpers.classSelectionButton("Become Bryan Kinney!", classSelectionFrame, shopFrame, "kinney", game));
         */

        //____________________________________________
        //Continue the game as a farmer
        JButton csb1 = new JButton("Become the farmer!");
        class csl1 implements ActionListener {
            public void actionPerformed(ActionEvent e){
                classSelectionFrame.dispose();
                shopFrame.setVisible(true);

                //____________________________________________________
                //Creating the shop frame and buying itmes for the farmer class

                game.changeClass("farmer");

                JPanel shopPanel = new JPanel();
                shopPanel.add(SwingHelpers.textArea("You have selected " + game.getClassSelection() + "."));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopDirections(game.getClassSelection())));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopContent()));

                //A textfield where the player enters the items they wanted to buy.  The items are stored in arraylists
                JTextArea shopInstructions = SwingHelpers.textArea(OregonTrail.purchaseDirections1());
                shopPanel.add(shopInstructions);
                idSet = false;
                ids = new ArrayList<String>();
                amounts = new ArrayList<String>();
                JTextField buy = new JTextField(3);
                buy.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (!idSet) {
                            ids.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections2());
                            idSet = true;
                        }
                        else {
                            amounts.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections1());
                            idSet = false;
                        }
                    }
                });
                shopPanel.add(buy);

                JButton shopSwitch = new JButton("Exit Shop");
                class shopListener implements ActionListener {
                    public void actionPerformed(ActionEvent e){
                        //The items are added to the player's inventory.  Invalid inputs will be skipped.  Ends when the player is out of money
                        addInventory();

                        shopFrame.dispose();
                        transitionFrame.setVisible(true);

                        //________________________________________________
                        //Transition into main game
                        JPanel transitionPanel = new JPanel();
                        transitionPanel.add(SwingHelpers.textArea("The following is a summary of your supplies:\n" + game.getInventory().getSuppliesSummary()));
                        transitionPanel.add(SwingHelpers.textArea(OregonTrail.mainDirections()));

                        JButton transitionButton = new JButton("Start your journey!");
                        class transitionListener implements ActionListener {
                            public void actionPerformed(ActionEvent e){
                                transitionFrame.dispose();

                                //________________________________________________
                                //Main game
                                mainGame();
                            }
                        }
                        transitionButton.addActionListener(new transitionListener());
                        transitionButton.setPreferredSize(new Dimension(300, 40));

                        transitionPanel.add(transitionButton);
                        transitionFrame.add(transitionPanel);
                    }
                }
                shopSwitch.addActionListener(new shopListener());
                shopSwitch.setPreferredSize(new Dimension(200, 40));
                shopPanel.add(shopSwitch);

                shopFrame.add(shopPanel);

            }
        }
        csb1.addActionListener(new csl1());
        csb1.setPreferredSize(new Dimension(400, 40));
        classSelectionPanel.add(csb1);

        //____________________________________________
        //Continue the game as a carpenter
        JButton csb2 = new JButton("Become the carpenter!");
        class csl2 implements ActionListener {
            public void actionPerformed(ActionEvent e){
                classSelectionFrame.setVisible(false);
                shopFrame.setVisible(true);

                //____________________________________________________
                //Creating the shop frame and buying itmes for the carpenter class

                game.changeClass("carpenter");

                JPanel shopPanel = new JPanel();
                shopPanel.add(SwingHelpers.textArea("You have selected " + game.getClassSelection() + "."));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopDirections(game.getClassSelection())));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopContent()));

                //A textfield where the player enters the items they wanted to buy.  The items are stored in arraylists
                JTextArea shopInstructions = SwingHelpers.textArea(OregonTrail.purchaseDirections1());
                shopPanel.add(shopInstructions);
                idSet = false;
                ids = new ArrayList<String>();
                amounts = new ArrayList<String>();
                JTextField buy = new JTextField(3);
                buy.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (!idSet) {
                            ids.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections2());
                            idSet = true;
                        }
                        else {
                            amounts.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections1());
                            idSet = false;
                        }
                    }
                });
                shopPanel.add(buy);

                JButton shopSwitch = new JButton("Exit Shop");
                class shopListener implements ActionListener {
                    public void actionPerformed(ActionEvent e){
                        //The items are added to the player's inventory.  Invalid inputs will be skipped.  Ends when the player is out of money
                        addInventory();

                        shopFrame.dispose();
                        transitionFrame.setVisible(true);

                        //________________________________________________
                        //Transition into main game
                        JPanel transitionPanel = new JPanel();
                        transitionPanel.add(SwingHelpers.textArea("The following is a summary of your supplies:\n" + game.getInventory().getSuppliesSummary()));
                        transitionPanel.add(SwingHelpers.textArea(OregonTrail.mainDirections()));

                        JButton transitionButton = new JButton("Start your journey!");
                        class transitionListener implements ActionListener {
                            public void actionPerformed(ActionEvent e){
                                transitionFrame.dispose();

                                //________________________________________________
                                //Main game
                                mainGame();
                            }
                        }
                        transitionButton.addActionListener(new transitionListener());
                        transitionButton.setPreferredSize(new Dimension(300, 40));

                        transitionPanel.add(transitionButton);
                        transitionFrame.add(transitionPanel);
                    }
                }
                shopSwitch.addActionListener(new shopListener());
                shopSwitch.setPreferredSize(new Dimension(200, 40));
                shopPanel.add(shopSwitch);

                shopFrame.add(shopPanel);
            }
        }
        csb2.addActionListener(new csl2());
        csb2.setPreferredSize(new Dimension(400, 40));
        classSelectionPanel.add(csb2);

        //____________________________________________
        //Continue the game as a banker
        JButton csb3 = new JButton("Become the banker!");
        class csl3 implements ActionListener {
            public void actionPerformed(ActionEvent e){
                classSelectionFrame.setVisible(false);
                shopFrame.setVisible(true);

                //____________________________________________________
                //Creating the shop frame and buying itmes for the banker class

                game.changeClass("banker");

                JPanel shopPanel = new JPanel();
                shopPanel.add(SwingHelpers.textArea("You have selected " + game.getClassSelection() + "."));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopDirections(game.getClassSelection())));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopContent()));

                //A textfield where the player enters the items they wanted to buy.  The items are stored in arraylists
                JTextArea shopInstructions = SwingHelpers.textArea(OregonTrail.purchaseDirections1());
                shopPanel.add(shopInstructions);
                idSet = false;
                ids = new ArrayList<String>();
                amounts = new ArrayList<String>();
                JTextField buy = new JTextField(3);
                buy.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (!idSet) {
                            ids.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections2());
                            idSet = true;
                        }
                        else {
                            amounts.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections1());
                            idSet = false;
                        }
                    }
                });
                shopPanel.add(buy);

                JButton shopSwitch = new JButton("Exit Shop");
                class shopListener implements ActionListener {
                    public void actionPerformed(ActionEvent e){
                        //The items are added to the player's inventory.  Invalid inputs will be skipped.  Ends when the player is out of money
                        addInventory();

                        shopFrame.dispose();
                        transitionFrame.setVisible(true);

                        //________________________________________________
                        //Transition into main game
                        JPanel transitionPanel = new JPanel();
                        transitionPanel.add(SwingHelpers.textArea("The following is a summary of your supplies:\n" + game.getInventory().getSuppliesSummary()));
                        transitionPanel.add(SwingHelpers.textArea(OregonTrail.mainDirections()));

                        JButton transitionButton = new JButton("Start your journey!");
                        class transitionListener implements ActionListener {
                            public void actionPerformed(ActionEvent e){
                                transitionFrame.dispose();

                                //________________________________________________
                                //Main game
                                mainGame();
                            }
                        }
                        transitionButton.addActionListener(new transitionListener());
                        transitionButton.setPreferredSize(new Dimension(300, 40));

                        transitionPanel.add(transitionButton);
                        transitionFrame.add(transitionPanel);
                    }
                }
                shopSwitch.addActionListener(new shopListener());
                shopSwitch.setPreferredSize(new Dimension(200, 40));
                shopPanel.add(shopSwitch);

                shopFrame.add(shopPanel);

            }
        }
        csb3.addActionListener(new csl3());
        csb3.setPreferredSize(new Dimension(400, 40));
        classSelectionPanel.add(csb3);

        //____________________________________________
        //Continue the game as Eric Shulman
        classSelectionPanel.add(Shulman);
        JButton csb4 = new JButton("Become Eric Shulman!");
        class csl4 implements ActionListener {
            public void actionPerformed(ActionEvent e){
                classSelectionFrame.setVisible(false);
                shopFrame.setVisible(true);

                //____________________________________________________
                //Creating the shop frame and buying itmes for the banker class

                game.changeClass("Shulman");

                JPanel shopPanel = new JPanel();
                shopPanel.add(SwingHelpers.textArea("You have selected " + game.getClassSelection() + "."));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopDirections(game.getClassSelection())));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopContent()));

                //A textfield where the player enters the items they wanted to buy.  The items are stored in arraylists
                JTextArea shopInstructions = SwingHelpers.textArea(OregonTrail.purchaseDirections1());
                shopPanel.add(shopInstructions);
                idSet = false;
                ids = new ArrayList<String>();
                amounts = new ArrayList<String>();
                JTextField buy = new JTextField(3);
                buy.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (!idSet) {
                            ids.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections2());
                            idSet = true;
                        }
                        else {
                            amounts.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections1());
                            idSet = false;
                        }
                    }
                });
                shopPanel.add(buy);

                JButton shopSwitch = new JButton("Exit Shop");
                class shopListener implements ActionListener {
                    public void actionPerformed(ActionEvent e){
                        //The items are added to the player's inventory.  Invalid inputs will be skipped.  Ends when the player is out of money
                        addInventory();

                        shopFrame.dispose();
                        transitionFrame.setVisible(true);

                        //________________________________________________
                        //Transition into main game
                        JPanel transitionPanel = new JPanel();
                        transitionPanel.add(SwingHelpers.textArea("The following is a summary of your supplies:\n" + game.getInventory().getSuppliesSummary()));
                        transitionPanel.add(SwingHelpers.textArea(OregonTrail.mainDirections()));

                        JButton transitionButton = new JButton("Start your journey!");
                        class transitionListener implements ActionListener {
                            public void actionPerformed(ActionEvent e){
                                transitionFrame.dispose();

                                //________________________________________________
                                //Main game
                                mainGame();
                            }
                        }
                        transitionButton.addActionListener(new transitionListener());
                        transitionButton.setPreferredSize(new Dimension(300, 40));

                        transitionPanel.add(transitionButton);
                        transitionFrame.add(transitionPanel);
                    }
                }
                shopSwitch.addActionListener(new shopListener());
                shopSwitch.setPreferredSize(new Dimension(200, 40));
                shopPanel.add(shopSwitch);

                shopFrame.add(shopPanel);

            }
        }
        csb4.addActionListener(new csl4());
        csb4.setPreferredSize(new Dimension(250, 140));
        classSelectionPanel.add(csb4);

        //____________________________________________
        //Continue the game as Bryan Kinney
        classSelectionPanel.add(Kinney);
        JButton csb5 = new JButton("Become Bryan Kinney!");
        class csl5 implements ActionListener {
            public void actionPerformed(ActionEvent e){
                classSelectionFrame.setVisible(false);
                shopFrame.setVisible(true);

                //____________________________________________________
                //Creating the shop frame and buying itmes for the banker class

                game.changeClass("Kinney");

                JPanel shopPanel = new JPanel();
                shopPanel.add(SwingHelpers.textArea("You have selected " + game.getClassSelection() + "."));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopDirections(game.getClassSelection())));
                shopPanel.add(SwingHelpers.textArea(OregonTrail.getShopContent()));

                //A textfield where the player enters the items they wanted to buy.  The items are stored in arraylists
                JTextArea shopInstructions = SwingHelpers.textArea(OregonTrail.purchaseDirections1());
                shopPanel.add(shopInstructions);
                idSet = false;
                ids = new ArrayList<String>();
                amounts = new ArrayList<String>();
                JTextField buy = new JTextField(3);
                buy.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (!idSet) {
                            ids.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections2());
                            idSet = true;
                        }
                        else {
                            amounts.add(buy.getText());
                            buy.setText("");
                            shopInstructions.setText(OregonTrail.purchaseDirections1());
                            idSet = false;
                        }
                    }
                });
                shopPanel.add(buy);

                JButton shopSwitch = new JButton("Exit Shop");
                class shopListener implements ActionListener {
                    public void actionPerformed(ActionEvent e){
                        //The items are added to the player's inventory.  Invalid inputs will be skipped.  Ends when the player is out of money
                        addInventory();

                        shopFrame.dispose();
                        transitionFrame.setVisible(true);

                        //________________________________________________
                        //Transition into main game
                        JPanel transitionPanel = new JPanel();
                        transitionPanel.add(SwingHelpers.textArea("The following is a summary of your supplies:\n" + game.getInventory().getSuppliesSummary()));
                        transitionPanel.add(SwingHelpers.textArea(OregonTrail.mainDirections()));

                        JButton transitionButton = new JButton("Start your journey!");
                        class transitionListener implements ActionListener {
                            public void actionPerformed(ActionEvent e){
                                transitionFrame.dispose();

                                //________________________________________________
                                //Main game
                                mainGame();
                            }
                        }
                        transitionButton.addActionListener(new transitionListener());
                        transitionButton.setPreferredSize(new Dimension(300, 40));

                        transitionPanel.add(transitionButton);
                        transitionFrame.add(transitionPanel);
                    }
                }
                shopSwitch.addActionListener(new shopListener());
                shopSwitch.setPreferredSize(new Dimension(200, 40));
                shopPanel.add(shopSwitch);

                shopFrame.add(shopPanel);

            }
        }
        csb5.addActionListener(new csl5());
        csb5.setPreferredSize(new Dimension(250, 140));
        classSelectionPanel.add(csb5);

        classSelectionFrame.add(classSelectionPanel);

    }

    //_________________________________________________
    //Utility

    //Checks if string is number
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //The items stored in the arraylists are added to the player's inventory.  Invalid inputs will be skipped.  Ends when the player is out of money.
    public static void addInventory() {
        for (int i = 0; i < ids.size(); i++){
            if (!isNumeric(ids.get(i)) || !isNumeric(amounts.get(i))) continue;
            int currentId = Integer.parseInt(ids.get(i));
            int currentAmount = Integer.parseInt(amounts.get(i));
            if (1<=currentId && currentId<=14){
                if (0<=currentAmount){
                    String returnedMessage = game.buySupplies(currentId, currentAmount);
                    if (returnedMessage.equals("noMoney")) break;
                }
            }
        }
    }

    //_________________________________________________
    //Main game
    public static void mainGame() {
        boolean gameLoop = true;

        gameFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Game");
        JPanel gamePanel = new JPanel();

        //Everyday during the game, the game's progress methods are called.  If an event occurs, the event's method is called.
        String rationMessage = game.progressRation();
        if (!rationMessage.equals("")) {
            gamePanel.add(SwingHelpers.textArea(rationMessage));
        }

        String event = game.progressMain();
        gamePanel.add(SwingHelpers.textArea(game.getSummary() + "\n"));

        if (event.equals("Over")) {
            gameLoop = false;
            JPanel gameOverPanel = new JPanel();
            gameOverPanel.add(SwingHelpers.textArea("You died.\n" + game.getSummary()));
            gameOverFrame.add(gameOverPanel);
            gameFrame.setVisible(false);
            gameFrame.dispose();
            gameOverFrame.setVisible(true);
        } else if (event.equals("none")) {
            gamePanel.add(trail);
            gamePanel.add(SwingHelpers.textArea(GameEvents.normalDayDirections()));
        } else if (event.equals("fortEncounter")) {
            fortEncounterDriver(gamePanel);
        } else if (event.equals("bearEncounter")) {
            bearEncounterDriver(gamePanel);
        } else if (event.equals("wolfEncounter")) {
            wolfEncounterDriver(gamePanel);
        } else if (event.equals("fire")) {
            fire(gamePanel);
        } else if (event.equals("brokenPart")) {
            brokenPart(gamePanel);
        }

        //Getting the player's action for the day
        if (gameLoop) {

            //Check supplies
            JButton suppliesButton = new JButton("Display Your Supplies");
            class suppliesListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    JPanel suppliesPanel = new JPanel();
                    suppliesPanel.add(SwingHelpers.textArea(game.getInventory().getSuppliesSummary()));
                    suppliesPanel.add(SwingHelpers.frameSwitch("Return", suppliesFrame, gameFrame));
                    suppliesFrame.add(suppliesPanel);
                    gameFrame.setVisible(false);
                    suppliesFrame.setVisible(true);
                }
            }
            suppliesButton.addActionListener(new suppliesListener());
            suppliesButton.setPreferredSize(new Dimension(400, 40));
            gamePanel.add(suppliesButton);

            //Increase your rations by 5
            JButton increaseRation = new JButton("Increase Ration by 5 lbs/day");
            class increaseRationListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    game.changeRation(game.getRation() + 5);
                }
            }
            increaseRation.addActionListener(new increaseRationListener());
            increaseRation.setPreferredSize(new Dimension(200, 40));
            gamePanel.add(increaseRation);

            //Decrease your rations by 5
            JButton decreaseRation = new JButton("Decrease Ration by 5 lbs/day");
            class decreaseRationListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    game.changeRation(game.getRation() - 5);
                }
            }
            decreaseRation.addActionListener(new decreaseRationListener());
            decreaseRation.setPreferredSize(new Dimension(200, 40));
            gamePanel.add(decreaseRation);

            //Continue to next day
            JButton continueButton = new JButton("Continue to Next Day");
            class continueListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    gameFrame.dispose();
                    mainGame();
                }
            }
            continueButton.addActionListener(new continueListener());
            continueButton.setPreferredSize(new Dimension(400, 40));
            gamePanel.add(continueButton);
        }

        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
    }

    //_________________________________________________
    //GameEvents methods that require inputs and outputs

    //Driver for the main fortEncounter method.  Asks the player if they want to enter the fort or not
    public static void fortEncounterDriver(JPanel gamePanel) {
        JButton fortButton = new JButton("Enter the Fort");
        class fortListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                fortEncounterMain(gamePanel, fortButton);
            }
        }
        fortButton.addActionListener(new fortListener());
        fortButton.setPreferredSize(new Dimension(400, 40));

        gamePanel.add(SwingHelpers.textArea(GameEvents.fortEncounterDirections()));
        gamePanel.add(fortButton);
    }

    //Values used for fortEncounterMain
    public static int item1 = 0;
    public static int item2 = 0;
    public static int item3 = 0;
    //Simuates a fort where the player could purchase additional supplies, however, they have limited options.  Similar to the initial shop method
    public static void fortEncounterMain(JPanel gamePanel, JButton fortButton){
        fortFrame = SwingHelpers.getFrame("Advanced Oregon Trail - Fort Encounter");
        JPanel fortPanel = new JPanel();

        fortPanel.add(fort);
        fortPanel.add(SwingHelpers.textArea("You have entered the fort."));

        int num = (int) (Math.random()*100+1);
        if (num<=25){
            fortPanel.add(SwingHelpers.textArea("There are three items for sell at the fort.\n1. 5 x 5lb Crackers for $1.10 each\n2. 1 x Hunting Rifle for $20.00 each\n3. 1 x Wagon Axel for $8.25 each"));
            item1 = 1;
            item2 = 8;
            item3 = 12;
        } else if (num<=50){
            fortPanel.add(SwingHelpers.textArea("There are three items for sell at the fort.\n1. 5 x 10lb Flour for $0.36 each\n2. 1 x Box of 20 Bullets for $1.96 per box\n3. 1 x 3 Oxen Team for $225 each"));
            item1 = 3;
            item2 = 9;
            item3 = 11;
        } else if (num<=75){
            fortPanel.add(SwingHelpers.textArea("There are three items for sell at the fort.\n1. 5 x 20lb Flour for $0.42 each\n2. 1 x 20lb of Gunpowder for $6.05\n3. 1 x Wagon Tongue for $9.35 each"));
            item1 = 4;
            item2 = 10;
            item3 = 13;
        } else {
            fortPanel.add(SwingHelpers.textArea("There are three items for sell at the fort.\n1. 5 x 10lb Candy for $8.80 each\n2. 1 x Hunting Rifle for $20.00 each\n3. 1 x Wagon Wheel for $12.00 each"));
            item1 = 7;
            item2 = 8;
            item3 = 14;
        }

        JButton buy1 = new JButton("Buy Item 1");
        class buy1L implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                game.buySupplies(item1, 5);
                gamePanel.add(SwingHelpers.textArea("You have already entered the fort."));
                gamePanel.remove(fortButton);
                fortFrame.dispose();
                gameFrame.setVisible(true);
            }
        }
        buy1.addActionListener(new buy1L());
        buy1.setPreferredSize(new Dimension(100, 40));

        JButton buy2 = new JButton("Buy Item 2");
        class buy2L implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                game.buySupplies(item2, 1);
                gamePanel.add(SwingHelpers.textArea("You have already entered the fort."));
                gamePanel.remove(fortButton);
                fortFrame.dispose();
                gameFrame.setVisible(true);
            }
        }
        buy2.addActionListener(new buy2L());
        buy2.setPreferredSize(new Dimension(100, 40));

        JButton buy3 = new JButton("Buy Item 3");
        class buy3L implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                game.buySupplies(item3, 1);
                gamePanel.add(SwingHelpers.textArea("You have already entered the fort."));
                gamePanel.remove(fortButton);
                fortFrame.dispose();
                gameFrame.setVisible(true);
            }
        }
        buy3.addActionListener(new buy3L());
        buy3.setPreferredSize(new Dimension(100, 40));

        JButton exit = new JButton("Leave the Fort");
        class exitL implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                fortFrame.dispose();
                gameFrame.setVisible(true);
            }
        }
        exit.addActionListener(new exitL());
        exit.setPreferredSize(new Dimension(100, 40));

        fortPanel.add(buy1);
        fortPanel.add(buy2);
        fortPanel.add(buy3);
        fortPanel.add(exit);
        fortFrame.add(fortPanel);
        gameFrame.setVisible(false);
        fortFrame.setVisible(true);
    }

    //Driver for the bear encounter class.  Asks the player if they want to attack the bear or not
    public static void bearEncounterDriver(JPanel gamePanel){
        JButton bearButton = new JButton("Attack the Bear");
        class bearListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                bearEncounterMain(gamePanel, bearButton);
            }
        }
        bearButton.addActionListener(new bearListener());
        bearButton.setPreferredSize(new Dimension(400, 40));

        gamePanel.add(SwingHelpers.textArea("You have encountered a bear.  Do you want to attack it for food?"));
        gamePanel.add(bearButton);
    }

    //Bear ecounter class.  The player has a 50% chance of surprising the bear and killing it.  If the player did not surprised the bear, they would need to kill it with their rifle using 3 bullets and 3 pounds of gunpowder.  If they do not have the weapons required, they have a 20% chance of killing it anyways.  If they failed to kill the bear, they will lose hitpoints.  They get between 150 and 200 pounds of food from killing the bear.
    public static void bearEncounterMain(JPanel gamePanel, JButton bearButton){
        int num = (int) (Math.random()*100+1);
        if (num<=50){
            int amount = (int) (Math.random()*51) + 150;
            gamePanel.add(SwingHelpers.textArea("You surprised the bear and successfully killed it without much trouble.  You have gained " + amount + " pounds of food.\n"));
            gamePanel.remove(bearButton);
            game.addFood(amount);
            game.addScore(19);
        } else {
            gamePanel.add(SwingHelpers.textArea("The bear noticed you! You are now under attack from the bear!!!"));
            Supplies inventory = game.getInventory();
            if (inventory.getWeapons()>0 && inventory.getAmmunition()>=3 && inventory.getGunpowder()>=3){
                int amount = (int) (Math.random()*51) + 150;
                gamePanel.add(SwingHelpers.textArea("You killed the bear with your hunting rifle.  You used 3 bullets and 3 pounds of gunpowder in the process.  You have gained " + amount + " pounds of food.\n"));
                gamePanel.remove(bearButton);
                game.getInventory().addBullets(-3);
                game.getInventory().addGunpowder(-3);
                game.addFood(amount);
                game.addScore(19);
            } else {
                int num2 = (int) (Math.random()*100+1);
                if (num2<=20){
                    int amount = (int) (Math.random()*51) + 150;
                    gamePanel.add(SwingHelpers.textArea("You did not have enough rifle, gunpowder, or bullets to fire at the bear.  However, you were able to kill it.  You have gained " + amount + " pounds of food.\n"));
                    gamePanel.remove(bearButton);
                    game.addFood(amount);
                    game.addScore(19);
                } else {
                    int amount = (int) (Math.random()*11) + 5;
                    gamePanel.add(SwingHelpers.textArea("You did not have enough rifle, gunpowder, or bullets to fire at the bear.  The bear attacked you and left.  You lost some health from the attack.\n"));
                    gamePanel.remove(bearButton);
                    game.changeHealth(-amount);
                    game.addScore(4);
                }
            }
        }
    }

    //Driver for the wolf encounter class.  Asks the player if they want to attack the wolf or not
    public static void wolfEncounterDriver(JPanel gamePanel){
        JButton wolfButton = new JButton("Attack the Wolf");
        class wolfListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                wolfEncounterMain(gamePanel, wolfButton);
            }
        }
        wolfButton.addActionListener(new wolfListener());
        wolfButton.setPreferredSize(new Dimension(400, 40));

        gamePanel.add(SwingHelpers.textArea("You have encountered a wolf.  Do you want to attack it for food?"));
        gamePanel.add(wolfButton);
    }

    //Wolf ecounter class.  The player has a 50% chance of surprising the wolf and killing it.  If the player did not surprised the wolf, they would need to kill it with their rifle using 2 bullets and 2 pounds of gunpowder.  If they do not have the weapons required, they have a 40% chance of killing it anyways.  If they failed to kill the wolf, they will lose hitpoints.  They gain between 50 and 150 pounds of food from killing the wolf.
    public static void wolfEncounterMain(JPanel gamePanel, JButton wolfButton){
        int num = (int) (Math.random()*100+1);
        if (num<=50){
            int amount = (int) (Math.random()*101) + 50;
            gamePanel.add(SwingHelpers.textArea("You surprised the wolf and successfully killed it without much trouble.  You have gained " + amount + " pounds of food.\n"));
            gamePanel.remove(wolfButton);
            game.addFood(amount);
            game.addScore(14);
        } else {
            gamePanel.add(SwingHelpers.textArea("The wolf noticed you! You are now under attack from the wolf!!!\n"));
            Supplies inventory = game.getInventory();
            if (inventory.getWeapons()>0 && inventory.getAmmunition()>=2 && inventory.getGunpowder()>=2){
                int amount = (int) (Math.random()*101) + 50;
                gamePanel.add(SwingHelpers.textArea("You killed the wolf with your hunting rifle.  You used 2 bullets and 2 pounds of gunpowder in the process.  You have gained " + amount + " pounds of food.\n"));
                gamePanel.remove(wolfButton);
                game.getInventory().addBullets(-2);
                game.getInventory().addGunpowder(-2);
                game.addFood(amount);
                game.addScore(14);
            } else {
                int num2 = (int) (Math.random()*100+1);
                if (num2<=40){
                    int amount = (int) (Math.random()*101) + 50;
                    gamePanel.add(SwingHelpers.textArea("You did not have enough rifle, gunpowder, or bullets to fire at the wolf.  However, you were able to kill it.  You have gained " + amount + " pounds of food.\n"));
                    gamePanel.remove(wolfButton);
                    game.addFood(amount);
                    game.addScore(14);
                } else {
                    int amount = (int) (Math.random()*8) + 3;
                    gamePanel.add(SwingHelpers.textArea("You did not have enough rifle, gunpowder, or bullets to fire at the wolf.  The wolf attacked you and left.  You lost some health from the attack.\n"));
                    gamePanel.remove(wolfButton);
                    game.changeHealth(-amount);
                    game.addScore(2);
                }
            }
        }
    }

    //Simulates a fire event in which some of the player's food is lost
    public static void fire(JPanel gamePanel){
        int amount = 0;
        if (game.getInventory().getFood() <= 400){
            amount = (int) (Math.random()*game.getInventory().getFood());
        } else {
            amount = (int) (Math.random()*201) + 200;
        }
        game.addFood(-amount);
        gamePanel.add(SwingHelpers.textArea("There was a fire on your wagon!!!  You have lost " + amount + " pounds of food from the fire.  You have " + game.getFood() + " pounds of food left.\n"));
        game.addScore(3);
    }

    //Simulates an event in which the player's wagon breaks down and they have to repair it.  If the player does not have the part required, their speed drops to 20 kilometers a day.  This will be reset to 35 the next time they successfully repaired their wagon.
    public static void brokenPart(JPanel gamePanel){
        int num = (int) (Math.random()*99) + 1;
        String part = "";
        if (num<=33){
            part = "a";
        } else if (num<=66){
            part = "t";
        } else {
            part = "w";
        }
        if (part.equals("a")){
            if (game.getInventory().getAxels() > 0){
                gamePanel.add(SwingHelpers.textArea("Your wagon's axel is broken.  Luckily, you have wagon axels left in your inventory and is able to repair your wagon.\n"));
                game.addScore(11);
                game.getInventory().addAxels(-1);
                game.changeSpeed(35);
            } else {
                gamePanel.add(SwingHelpers.textArea("Your wagon's axel is broken and you have no axels in your inventory to fix it.  You continued your journey with a makeshift axel and your speed has been reduced to 20 kilometers per day.\n"));
                game.changeSpeed(20);
            }
        } else if (part.equals("t")){
            if (game.getInventory().getTongues() > 0){
                gamePanel.add(SwingHelpers.textArea("Your wagon's tongue is broken.  Luckily, you have wagon tongues left in your inventory and is able to repair your wagon.\n"));
                game.addScore(11);
                game.getInventory().addTongues(-1);
                game.changeSpeed(35);
            } else {
                gamePanel.add(SwingHelpers.textArea("Your wagon's tongue is broken and you have no tongues in your inventory to fix it.  You continued your journey with a makeshift tongue and your speed has been reduced to 20 kilometers per day.\n"));
                game.changeSpeed(20);
            }
        } else {
            if (game.getInventory().getWheels() > 0){
                gamePanel.add(SwingHelpers.textArea("Your wagon's wheel is broken.  Luckily, you have wagon wheels left in your inventory and is able to repair your wagon.\n"));
                game.addScore(11);
                game.getInventory().addWheels(-1);
                game.changeSpeed(35);
            } else {
                gamePanel.add(SwingHelpers.textArea("Your wagon's wheel is broken and you have no wheels in your inventory to fix it.  You continued your journey with a makeshift wheel and your speed has been reduced to 20 kilometers per day.\n"));
                game.changeSpeed(20);
            }
        }
    }

}













