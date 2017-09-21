/* Java program to quantify work done on different files in terms of number of commits that modified them.
Author: Ajiinkkya Bhaalerao
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;
public class FindTotalWorkDoneOnFiles {

	
	public static void main(String[] args) throws FileNotFoundException,IOException{
		 
		String [] nextLine = null;
		int count = 1;
		int total_work_done = 0;
		CSVReader reader = null;
		boolean flag = false;
		
		//FileWriter
				FileWriter fw = new FileWriter(new File("\\path\\to\\output\\csv\\file\\consisting\\of\\results"));
		
		//Read lines from the file
				FileReader fr = new FileReader("\\path\\to\\input\\csv\\file\\consisting\\of\\a\\list\\of\\files\\for\\which\\total\\work\\done\\is\\to\\be\\calculated");
				BufferedReader br = new BufferedReader(fr);
			    String line;
			    
			    while((line = br.readLine()) != null){
					/*For each of the input file (for which the total work done is to be calculated), open a handle to the csv file 
					as the input files can be in any order.
					*/
			    	reader = new CSVReader(new FileReader("\\path\\to\\csv\\file\\consisting\\of\\results\\of\\HC\\and\\work\\done\\in\\various\\three\\month\\intervals"));
			    	while((nextLine = reader.readNext()) != null){
			    		if(line.equals(nextLine[1])){
			    			if(!flag)
			    				flag = true;
			    			for(int i = 2 ; i <= 90 ; i++){
			    				total_work_done += Integer.parseInt(nextLine[i]);
			    			}
			    			fw.write(nextLine[1]+","+total_work_done); //Write the total work done to the output csv file
							fw.write(System.lineSeparator()); //Terminate that line
							flag = false;
			    			break;
			    		}
			    	}
			    	reader.close();
					count++;
				}
			    br.close();
				fw.close();
				System.out.println("Total lines read: "+(count-1)); //Display a count of total input files processed
				
				
				
	}

}
