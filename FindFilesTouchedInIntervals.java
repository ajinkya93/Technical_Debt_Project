/* Java program to identify files modified during various clusters.
Author: Ajiinkkya Bhaalerao
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;

//Run 1: 24 to 66
//Run 2: 74 to 90
//Run 3: 8 to 16
//Run 4: 66 to 73
//Run 5: 66 to 69
	
public class FindFilesTouchedInIntervals {

	public static void main(String[] args) throws FileNotFoundException,IOException{
		
		CSVReader reader = new CSVReader(new FileReader("\\path\\to\\input\\csv\\file\\consisting\\of\\cluster\\data"));
		String [] nextLine;
		int count = 1;

		//FileWriter
		FileWriter fw = new FileWriter(new File("\\path\\to\\output\\csv\\file\\consisting\\of\\results"));
		
		while((nextLine = reader.readNext()) != null){
			boolean flag = false;
			int work_done = 0;
			if(!(count == 1)){
				for(int i = 66 ; i <= 69 ; i++){
					if(Integer.parseInt(nextLine[i]) > 0){
						if(!flag)
							flag = true;
					}
					work_done += Integer.parseInt(nextLine[i]);
				}
				if(flag){
					fw.write(nextLine[1]+","+work_done);
					fw.write(System.lineSeparator());
				}
			}
			count++;
		}
		reader.close();
		fw.close();
		System.out.println("Total lines read: "+count);
	}
}
