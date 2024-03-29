//A helper class for selecting daily events

class GameEvents{

    //Returns a randomely selected event
    static String getEvent(OregonTrail game){
        int num = (int) (Math.random()*100+1);

        if (game.getDay()>45){
            if (num >= 70){
                return "none";
            } else if (num >= 65) {
                return "fortEncounter";
            } else if (num >= 55) {
                return "bearEncounter";
            } else if (num >= 45) {
                return "wolfEncounter";
            } else if (num >= 20) {
                return "fire";
            } else if (num >= 0) {
                return "brokenPart";
            }
        } else if (game.getDay()>30){
            if (num >= 70){
                return "none";
            } else if (num >= 60) {
                return "fortEncounter";
            } else if (num >= 50) {
                return "bearEncounter";
            } else if (num >= 40) {
                return "wolfEncounter";
            } else if (num >= 20) {
                return "fire";
            } else if (num >= 0) {
                return "brokenPart";
            }
        } else {
            if (num >= 60){
                return "none";
            } else if (num >= 40) {
                return "fortEncounter";
            } else if (num >= 30) {
                return "bearEncounter";
            } else if (num >= 20) {
                return "wolfEncounter";
            } else if (num >= 10) {
                return "fire";
            } else if (num >= 0) {
                return "brokenPart";
            }
        }

        return "none";
    }

    //_______________________________________________
    //Directions

    static String fortEncounterDirections(){
        return "You passed by a fort! Do you want to enter it?";
    }

    static String normalDayDirections(){
        return "This is a normal day.  Nothing significant happened during your journey.\n";
    }

    static String unIdentifiedInput(){
        return "Unidentified input!  Make sure you entered 'Yes' or 'No'.\n";
    }

}



