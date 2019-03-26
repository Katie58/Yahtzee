package yahtzee;

public enum MainMenu implements MenuInterface{

    PLAY{
    	@Override
    	public void execute() {
    		Play.play();
    	}
    },
//    RECORDS{
//    	@Override
//    	public void execute() {
//    		YahtzeeFiles.getRecords();
//    	}
//    },
    EXIT{
    	@Override
    	public void execute() {
    		YahtzeeApp.exit = true;
    	}  	
    }
}