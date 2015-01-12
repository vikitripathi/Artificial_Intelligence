/**
 * 
 */
package minmax;//practise to use lower case name for package
//If a class wants to use another class in the same package, 
//the package name does not need to be used. Classes in the same package find each other without any special syntax.

//if the class is in other package then use import
//java.lang package is the default package for java, no need to import
/**
 * @author Abhishek Dutt
 *
 */
import java.util.*;
//Every class inherits methods from the Object class.
public class TicTacToe {
	//all classes should start with a capital case
	
	public static int checkGameOver(String state,String user){
		int complete=0;//local variables need to initialised before first use
		//can have a boolean complete
		int flag=0;
		int score=0;
		
		if(state.charAt(0)==state.charAt(4)&&state.charAt(0)==state.charAt(8)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
			
		}
		else if(state.charAt(0)==state.charAt(3) && state.charAt(0)==state.charAt(6)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
			
		}
		else if(state.charAt(1)==state.charAt(4) && state.charAt(1)==state.charAt(7)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
		}
		else if(state.charAt(2)==state.charAt(5) && state.charAt(2)==state.charAt(8)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
		}
		else if(state.charAt(0)==state.charAt(1) && state.charAt(0)==state.charAt(2)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
		}
		else if(state.charAt(3)==state.charAt(4) && state.charAt(3)==state.charAt(5)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
		}
		else if(state.charAt(6)==state.charAt(7) && state.charAt(6)==state.charAt(8)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
		}
		else if(state.charAt(2)==state.charAt(4) && state.charAt(2)==state.charAt(6)){
			//can also use Character.toString('c') or even concatenation
			complete=1;
		}
		else if(state.indexOf('_')==-1){
			//string.indexOf(char c) if not match returns -1 
			complete=1;
			flag=1;
		}
		
		if(complete==1){
			if(user.equals("O") && flag==0){
				score=-10;
				
				return score;
			}
			else if(user.equals("X") && flag==0){
				score=10;
				return score;
			}
			else if(flag==1){
				return score;//0
			}
		}
		return -1;
	}
	
	public static ArrayList<String> generateStatesList(String state,String user){
		ArrayList<String> stateLists = new ArrayList<String>();
		
		int i=-1;
		while(true){
			//always check local variable definition
			String newState="";
			i=state.indexOf('_',i+1);//first occurrence of '_' from i+1
			if(i== -1){
				break;
			}
			else if(i==0){
				newState=user+state.substring(i+1,9);//inclusive,exclusive
			}
			else if(i==8){
				newState=state.substring(0, i)+user;
			}
			else{
				newState=state.substring(0, i)+user+state.substring(i+1,9);//inclusive , exclusive
			}
			
			stateLists.add(newState);
			
		}
		//System.out.println(stateLists);
		return stateLists;
	}
	
	public static int minmax(HashMap<Integer,Integer> minmaxValue,String user){
		//hashmap doesn't handle primitives , just objects
		//Java will automatically autobox the int primitive values to Integer objects.
		Collection<Integer> values=minmaxValue.values();//to get all the values
		if(user.equals("X")){
			return Collections.max(values);//using collection api
			//get key 
		}
		if(user.equals("O")){
			return Collections.min(values);
		}
		return -1;
	}

	public static String notUser(String currentUser){
		String[] users={"X","O"}; 
		if (users[0].equals(currentUser))
			return "O";
		else return "X";
	}

	public static int getKey(int n,HashMap<Integer,Integer> newStateScores){
		Integer value=n;
		Integer key=0;
		for(Map.Entry entry: newStateScores.entrySet()){
            if(value.equals(entry.getValue())){
                key = (Integer) entry.getKey();
                return key; //breaking because its one to one map, assume
            }
        }
		return -1;

	}
	
	public static String controlFunction(String currentState,String currentUser){
			
		ArrayList<String> newStates=generateStatesList(currentState,currentUser);
		HashMap<Integer,Integer> newStateScores=new HashMap<Integer,Integer>();//even an arraylist would have done the job here!!
		int flag=-1;
		for(String newState:newStates){
			//the above is just shorthand for the use of Iterator	
			/*
			 Iterator<T> iter = foo.iterator();
			 while(iter.hasNext()){
    		 T item = iter.next();
    		 // body
			 }
			*/
			flag++;
			if(checkGameOver(newState, currentUser)!= -1){
				// != -1 here implies its the leaf node
				newStateScores.put(flag,checkGameOver(newState, currentUser));
				//System.out.println(checkGameOver(newState, currentUser));
			}
			else{
				//get the other alternate user but not change the currentUser value of this level!!!
				String s=controlFunction(newState, notUser(currentUser));//Recursion
				String[] str=s.split("=");//beware of the fact that negative no have - sign so change to =
				newStateScores.put(flag,Integer.parseInt(str[0]));
			}
		}
		//System.out.println(minmax(newStateScores, currentUser)+"="+newStates.get(getKey(minmax(newStateScores, currentUser), newStateScores)));
		return minmax(newStateScores, currentUser)+"="+newStates.get(getKey(minmax(newStateScores, currentUser), newStateScores));//return key also
		
	}
	
	public static void nextStep(String currentState,String currentUser){
		String stateValue=controlFunction(currentState, currentUser);//get score and state separated by "-"
		System.out.println(stateValue);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		//A Scanner breaks its input into tokens using a delimiter pattern, which by default matches whitespace
		System.out.println("enter current state of tic tac toe board");
		String state = sc.nextLine().trim();//to get string from console
		//given a state of tictactoe game , find the next best step
		//System.out.println("the string is of length=>"+state.length());
		long startTime = System.currentTimeMillis();
		nextStep(state, "X");
		//System.out.println(checkGameOver(state, "O"));
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

}
