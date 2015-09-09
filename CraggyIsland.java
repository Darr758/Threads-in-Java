/**
* This class defines and implements the two anonymous classes for Ted and MrsDoyle.
* It also contains the main which starts the two threads for Ted and MrsDoyle.
*
* @author Darragh King (ID 113372871)
*/

public class CraggyIsland{
	private boolean condition;
	private static CraggyIsland lock = new CraggyIsland();

	/**
	*	This anonymous class implements the Runnable interface.
	*	Implements run() from runnable
	*/
	private static class Ted implements Runnable{
		/**
		*	Prints out the various steps that Ted goes through.
		*	Synchronizes on the same object as MrsDoyle so their respective threads
		*	are synchronized.
		*
		* 	Notfies "MrsDoyle" when he is ready for tea and then updates condition
		*	calls "wait()" to wait for MrsDoyle class to notify() him that
		*	tea is ready.
		*
		*/	
		public void run(){
			try{
				System.out.println("Saying mass");
				System.out.println("Sitting down");
			}finally{
				synchronized(lock){
					lock.condition = true;

					lock.notify( );
					try{
						while(lock.condition){
							lock.wait();
						}
					}catch(Exception e){
						System.out.println("Something went wrong");
					}
				}
				System.out.println("Having tea");
			}
		}	
	}	

	/**
	*	This anonymous class implements the Runnable interface.
	*	Implements run() from runnable
	*/
	private static class MrsDoyle implements Runnable{
		/**
		*	Prints out the various steps that MrsDoyle goes through.
		*	Synchronizes on the same object as Ted so their respective threads
		*	are synchronized.
		*
		*	Wait() for Ted to notify() her when he wants tea.
		*	Once Ted calls notify() this class will update the condition statement
		*	and notify Ted class.
		*
		*/	
		public void run(){
			synchronized(lock){
				try{
					while(!lock.condition){
						lock.wait();
					}
				}catch(Exception e){
					System.out.println("FECK OFF CUP!");
				}
				System.out.println("Serving Tea");
				lock.condition = false;
				lock.notify();
			}

		}
	}

	/**
	*	Creates a thread for MrsDoyle and Ted and starts them in any order.
	*
	*	@param args The given command line arguments 
	*/
	public static void main(String[] args){
		Thread mrsDoyle = new Thread(new MrsDoyle());
		Thread ted = new Thread(new Ted());
		mrsDoyle.start();
		ted.start();	

	}

}
