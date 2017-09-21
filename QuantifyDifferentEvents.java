/* Java program to quantify different events associated with concerned projects separately as pre-Fork events and post-Fork events.
Author: Ajiinkkya Bhaalerao
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.*;

public class QuantifyDifferentEvents {

	
	public static void getEventCounts(String path) throws FileNotFoundException,IOException, ParseException{

		File dir = new File(path);
		File[] dirListing = dir.listFiles();

		String line = null;
		int _IssuesEvent_preFork = 0, _MilestoneEvent_preFork = 0, _PullRequestEvent_preFork = 0, _PushEvent_preFork = 0, _ReleaseEvent_preFork = 0;
		int _IssuesEvent_postFork = 0, _MilestoneEvent_postFork = 0, _PullRequestEvent_postFork = 0, _PushEvent_postFork = 0, _ReleaseEvent_postFork = 0;
		int _IssuesEvent_xt = 0, _MilestoneEvent_xt = 0, _PullRequestEvent_xt = 0, _PushEvent_xt = 0, _ReleaseEvent_xt = 0;

		FileWriter fw_err_reading = new FileWriter("Failed_files.csv",true); //A list of JSON files which could not be processed
		BufferedWriter bw_err_reading = new BufferedWriter(fw_err_reading);

		String repoName = null;
		String event = null;

		int failed_files_counter = 0;
		int line_counter = 0;

		JSONObject obj = null;
		JSONObject repository = null;

		String created_at="";

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date releaseDate = dateFormat.parse("2014-12-27");
		Date d = null;

		if(dirListing != null){

			for(File file: dirListing){
				if(file.isFile()){
					//process only Github archive files while are in JSON format
					if(file.toString().endsWith(".json")){
						line_counter = 0;
						FileReader fr = new FileReader(file); //
						BufferedReader br = new BufferedReader(fr);
						while((line = br.readLine())!= null){
							try{
								line_counter++;
								obj = new JSONObject(line);
								JSONObject repo = obj.optJSONObject("repo");
								if(repo != null){
									repoName = repo.getString("name");
									event = obj.optString("type");//
									created_at = obj.optString("created_at");//Date of that event creation
									d = dateFormat.parse(created_at);

									if(repoName.equals("bitcoin/bitcoin")){
										if(event != null){
											if(event.equalsIgnoreCase("IssuesEvent")){
												if(d.before(releaseDate))
													_IssuesEvent_preFork++;
												else
													_IssuesEvent_postFork++;
											}
											if(event.equalsIgnoreCase("MilestoneEvent")){
												if(d.before(releaseDate))
													_MilestoneEvent_preFork++;
												else
													_MilestoneEvent_postFork++;
											}
											if(event.equalsIgnoreCase("PullRequestEvent")){
												if(d.before(releaseDate))
													_PullRequestEvent_preFork++;
												else
													_PullRequestEvent_postFork++;
											}
											if(event.equalsIgnoreCase("PushEvent")){
												if(d.before(releaseDate))
													_PushEvent_preFork++;
												else
													_PushEvent_postFork++;
											}
											if(event.equalsIgnoreCase("ReleaseEvent")){
												System.out.println("Bitcoin Release: "+file);
												if(d.before(releaseDate))
													_ReleaseEvent_preFork++;
												else
													_ReleaseEvent_postFork++;
											}
										}//
									}

									//Bitcoin-XT
									else if(repoName.equals("bitcoinxt/bitcoinxt")){
										if(event != null){

											if(event.equalsIgnoreCase("IssuesEvent"))
												_IssuesEvent_xt++;
											if(event.equalsIgnoreCase("MilestoneEvent"))
												_MilestoneEvent_xt++;
											if(event.equalsIgnoreCase("PullRequestEvent"))
												_PullRequestEvent_xt++;
											if(event.equalsIgnoreCase("PushEvent"))
												_PushEvent_xt++;
											if(event.equalsIgnoreCase("ReleaseEvent")){
												System.out.println("BitcoinXT Release: "+file);
												_ReleaseEvent_xt++;
											}
										}//
									}
								}
								else{
									repository = obj.optJSONObject("repository");
									if(repository != null){
										repoName = repository.getString("name");
										event = obj.optString("type");//
										created_at = obj.optString("created_at");//Date of that event creation
										d = dateFormat.parse(created_at);

										if(repoName.equals("bitcoin")){
											if(event != null){
												if(event.equalsIgnoreCase("IssuesEvent")){
													if(d.before(releaseDate))
														_IssuesEvent_preFork++;
													else
														_IssuesEvent_postFork++;
												}
												if(event.equalsIgnoreCase("MilestoneEvent")){
													if(d.before(releaseDate))
														_MilestoneEvent_preFork++;
													else
														_MilestoneEvent_postFork++;
												}
												if(event.equalsIgnoreCase("PullRequestEvent")){
													if(d.before(releaseDate))
														_PullRequestEvent_preFork++;
													else
														_PullRequestEvent_postFork++;
												}
												if(event.equalsIgnoreCase("PushEvent")){
													if(d.before(releaseDate))
														_PushEvent_preFork++;
													else
														_PushEvent_postFork++;
												}
												if(event.equalsIgnoreCase("ReleaseEvent")){
													System.out.println("Bitcoin Release: "+file);
													if(d.before(releaseDate))
														_ReleaseEvent_preFork++;
													else
														_ReleaseEvent_postFork++;
												}
											}//
										}

										//Bitcoin-XT
										else if(repoName.equals("bitcoinxt")){

											if(event != null){
												if(event.equalsIgnoreCase("IssuesEvent"))
													_IssuesEvent_xt++;
												if(event.equalsIgnoreCase("MilestoneEvent"))
													_MilestoneEvent_xt++;
												if(event.equalsIgnoreCase("PullRequestEvent"))
													_PullRequestEvent_xt++;
												if(event.equalsIgnoreCase("PushEvent"))
													_PushEvent_xt++;
												if(event.equalsIgnoreCase("ReleaseEvent")){
													//System.out.println("BitcoinXT Release: "+file);
													_ReleaseEvent_xt++;
												}
											}//
										}
									}
								}
							}catch(JSONException je){
								//System.out.println("Line: "+line_counter);
								//System.out.println(je);
								bw_err_reading.write(file+" : "+je+" on line "+line_counter);
								bw_err_reading.newLine();
								bw_err_reading.flush();	
								failed_files_counter++;
							}
						}//end-while
						br.close();
					}
				}
			}
			bw_err_reading.close();
		}
		else{
			System.out.println("Directory empty");
			bw_err_reading.close();
			return;
		}

		System.out.println("failed_files_counter: "+failed_files_counter);

		FileWriter fw = new FileWriter("Bitcoin_DifferentEventsQuantificationResults.csv",true);
		BufferedWriter bw = new BufferedWriter(fw);

		FileWriter fw_xt = new FileWriter("BitcoinXT_DifferentEventsQuantificationResults.csv",true);
		BufferedWriter bw_xt = new BufferedWriter(fw_xt);

		try{
			bw.write("IssuesEvent Pre-Fork,"+ _IssuesEvent_preFork);
			bw.newLine();
			bw.flush();
			bw.write("MilestoneEvent Pre-Fork,"+ _MilestoneEvent_preFork);
			bw.newLine();
			bw.flush();
			bw.write("PullRequestEvent Pre-Fork,"+ _PullRequestEvent_preFork);
			bw.newLine();
			bw.flush();
			bw.write("PushEvent Pre-Fork,"+ _PushEvent_preFork);
			bw.newLine();
			bw.flush();
			bw.write("ReleaseEvent Pre-Fork,"+ _ReleaseEvent_preFork);
			bw.newLine();
			bw.flush();

			//Post-Fork Details
			bw.write("IssuesEvent Post-Fork,"+ _IssuesEvent_postFork);
			bw.newLine();
			bw.flush();
			bw.write("MilestoneEvent Post-Fork,"+ _MilestoneEvent_postFork);
			bw.newLine();
			bw.flush();
			bw.write("PullRequestEvent Post-Fork,"+ _PullRequestEvent_postFork);
			bw.newLine();
			bw.flush();
			bw.write("PushEvent Post-Fork,"+ _PushEvent_postFork);
			bw.newLine();
			bw.flush();
			bw.write("ReleaseEvent Post-Fork,"+ _ReleaseEvent_postFork);
			bw.newLine();
			bw.flush();

			//BitcoinXT
			bw_xt.write("IssuesEvent,"+ _IssuesEvent_xt);
			bw_xt.newLine();
			bw_xt.flush();
			bw_xt.write("MilestoneEvent,"+ _MilestoneEvent_xt);
			bw_xt.newLine();
			bw_xt.flush();
			bw_xt.write("PullRequestEvent,"+ _PullRequestEvent_xt);
			bw_xt.newLine();
			bw_xt.flush();
			bw_xt.write("PushEvent,"+ _PushEvent_xt);
			bw_xt.newLine();
			bw_xt.flush();
			bw_xt.write("ReleaseEvent,"+ _ReleaseEvent_xt);
			bw_xt.newLine();
			bw_xt.flush();


		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally{
			if(bw != null)
				bw.close();
			if(fw != null)
				fw.close();

			if(bw_xt != null)
				bw_xt.close();
			if(fw_xt != null)
				fw_xt.close();
		}

	}

	public static void main(String[] args) throws IOException, ParseException {

		getEventCounts("/path/to/input/Github/archive/json/files");

	}
}
