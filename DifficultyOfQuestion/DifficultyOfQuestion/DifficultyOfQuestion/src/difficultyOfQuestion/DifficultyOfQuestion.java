package difficultyOfQuestion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Answer {
	String typeOfQuestion,difficulty;
	double rightAnsPercentage,wrongAnsPercentage,avgTimeTaken;
	String languageUsed;
	int maximumMarks;
	
	Answer(String difficulty,String languageUsed, double rightAnsPercentage,double wrongAnsPercentage)
	{
		this.difficulty = difficulty;
		this.languageUsed = languageUsed;
		this.rightAnsPercentage = rightAnsPercentage;
		this.wrongAnsPercentage = wrongAnsPercentage;
	}
}

public class DifficultyOfQuestion {

	public static void main(String args[])
	{
		Answer answer = null;
		Scanner fileName = new Scanner(System.in);
		System.out.println("Enter file name with path : ");
		String getFile = fileName.nextLine();
		try {
		File file = new File(getFile);
		fileName.close();
		Scanner input = new Scanner(file);
		ArrayList<String> files = new ArrayList<>();
		while(input.hasNextLine())
		{
			files.add(input.nextLine());
		}
		try {
			
		int i=0;
		
		String questionType = files.get(i++);
		
		if(questionType.equalsIgnoreCase("MCQ") || questionType.equalsIgnoreCase("Fillups") || questionType.equalsIgnoreCase("Match") || questionType.equalsIgnoreCase("Programming")) {
			
			int times_AnsChanged=0,times_Compiled=0,students_AnsweredPartiallyRight=0;
			String langUsed="";
			
			int studentsAttended = Integer.parseInt(files.get(i++));
			int timeTaken = Integer.parseInt(files.get(i++));
			if(questionType.equalsIgnoreCase("MCQ"))
				times_AnsChanged = Integer.parseInt(files.get(i++));
			if(questionType.equalsIgnoreCase("Programming")) {
				times_Compiled = Integer.parseInt(files.get(i++));
				langUsed = files.get(i++);
			}
			int hintsUsed = Integer.parseInt(files.get(i++));
			String feedback = files.get(i++);
			int students_AnsweredRight = Integer.parseInt(files.get(i++));
			int students_AnsweredWrong = Integer.parseInt(files.get(i++));
			if(questionType.equalsIgnoreCase("Match") || questionType.equalsIgnoreCase("Programming"))
				students_AnsweredPartiallyRight = Integer.parseInt(files.get(i++));
			int maximumMarks = Integer.parseInt(files.get(i++));
			
			if(questionType.equalsIgnoreCase("MCQ")) {
			
				MCQClass object1 = new MCQClass();
				object1.setValues(studentsAttended,times_AnsChanged,students_AnsweredRight,students_AnsweredWrong,feedback,hintsUsed);
				answer = object1.findDifficulty();
			
			}
		
			else if(questionType.equalsIgnoreCase("Fillups")) {
			
				FillUpsClass object2 = new FillUpsClass();
				object2.setValues(studentsAttended,students_AnsweredRight,students_AnsweredWrong,feedback,hintsUsed);
				answer = object2.findDifficulty();
			
			}
		
			else if(questionType.equalsIgnoreCase("Match")) {
			
				MatchClass object3 = new MatchClass();
				object3.setValues(studentsAttended,students_AnsweredRight,students_AnsweredWrong,students_AnsweredPartiallyRight,feedback,hintsUsed);
				answer = object3.findDifficulty();
			
			}
		
			else if(questionType.equalsIgnoreCase("Programming")) {
		
				ProgramClass object4 = new ProgramClass();
				object4.setValues(studentsAttended, langUsed, times_Compiled, students_AnsweredRight, students_AnsweredWrong, students_AnsweredPartiallyRight, feedback,hintsUsed);
				answer = object4.findDifficulty();
			
			}
			answer.maximumMarks = maximumMarks;
			answer.typeOfQuestion = questionType;
			answer.avgTimeTaken = (double)timeTaken / (double)studentsAttended;
			System.out.println("Question Type : "+answer.typeOfQuestion);
			System.out.println("Difficulty level of the question : "+answer.difficulty);
			System.out.println("Average time taken by the student : "+answer.avgTimeTaken);
			System.out.println("Percentage of students answered the entire question Correctly : "+answer.rightAnsPercentage+"%");
			System.out.println("Percentage of students answered the entire question wrongly : "+answer.wrongAnsPercentage+"%");
			System.out.println("Maximum marks : "+answer.maximumMarks);
			if(answer.typeOfQuestion.equals("Programming"))
				System.out.println("Programming language used Mostly : "+answer.languageUsed);
		}
		else
		{
			System.out.println("Sorry, invalid Question Type");
		}
		}
		catch(Exception e) {
			System.out.println("Sorry, invalid file contents");
		}
		input.close();
			
		}
		catch(Exception e) {
			System.out.println("Sorry, input file is not found");
		}
	}
}


class ProgramClass {
	
	String langUsed="";
	int timesCompiled,studentsAttended,students_AnsweredRight,hintsUsed,students_AnsweredWrong,students_AnsweredPartiallyRight;
	String feedback="";
	void setValues(int studentsAttended,String langUsed,int timesCompiled,int students_AnsweredRight,int students_AnsweredWrong,int students_AnsweredPartiallyRight,String feedback,int hintsUsed)
	{
		this.studentsAttended = studentsAttended;
		this.langUsed = langUsed;
		this.timesCompiled = timesCompiled;
		this.students_AnsweredRight = students_AnsweredRight;
		this.students_AnsweredWrong = students_AnsweredWrong;
		this.students_AnsweredPartiallyRight = students_AnsweredPartiallyRight;
		this.hintsUsed = hintsUsed;
	}
	
	Answer findDifficulty() 
	{
		String difficultyFromFB[] = feedback.split(" ");
		int students_ChosenHard=0,students_ChosenEasy=0,students_ChosenMedium=0;
		HashMap<String,Integer> langMap = new HashMap<>();
		
		String programmingLang[] = langUsed.split(" ");
		for(int i=0; i<programmingLang.length; i++)
		{
			String temp = programmingLang[i];
			if(langMap.containsKey(temp))
				langMap.put(temp, langMap.get(temp)+1);
			else
				langMap.put(temp, 1);
		}
		
		int max = 0;
		String mostlyUsedLanguage="";
		for(Map.Entry i:langMap.entrySet())
		{
			if((int)i.getValue() > max)
			{
				max = (int)i.getValue();
				mostlyUsedLanguage = (String)i.getKey();
			}
		}
		
		for(int i=0; i<difficultyFromFB.length; i++)
		{
			if(difficultyFromFB[i].equals("EASY"))
				students_ChosenEasy++;
			else if(difficultyFromFB[i].equals("MEDIUM"))
				students_ChosenMedium++;
			else if(difficultyFromFB[i].equals("HARD"))
				students_ChosenHard++;
		}
				
		double rightAnswerScore = (double)students_AnsweredRight/(double)studentsAttended;
		double wrongAnswerScore = (double)students_AnsweredWrong/(double)studentsAttended;
		double partiallyRightScore = (double)students_AnsweredPartiallyRight/(double)(2*studentsAttended);
		double difficultyIndex = rightAnswerScore + partiallyRightScore;
		
		if(students_ChosenMedium < students_ChosenEasy || students_ChosenMedium < students_ChosenHard) {
			int overallFeedback = students_ChosenEasy - students_ChosenHard;
			if(overallFeedback > 0)
				difficultyIndex += 0.1;
			else if(overallFeedback < 0)
				difficultyIndex -= 0.1;
		}
		
		if(hintsUsed + timesCompiled > (4*studentsAttended))
			difficultyIndex -= 0.1;
		else if(hintsUsed + timesCompiled < (studentsAttended))
			difficultyIndex += 0.1;
		
		String levelOfDifficulty="";
		if(difficultyIndex < 0.4)
			levelOfDifficulty = "HARD";
		else if(difficultyIndex >= 0.4 && difficultyIndex <=0.6)
			levelOfDifficulty = "MEDIUM";
		else
			levelOfDifficulty = "EASY";
	
		return new Answer(levelOfDifficulty, mostlyUsedLanguage, rightAnswerScore*100, wrongAnswerScore*100);
	}
}


class MCQClass {
	
	int studentsAttended,hintsUsed,times_AnsChanged,students_AnsweredRight,students_AnsweredWrong;
	String feedback;
	
	void setValues(int studentsAttended, int times_AnsChanged, int students_AnsweredRight, int students_AnsweredWrong,String feedback,int hintsUsed)
	{
		this.studentsAttended = studentsAttended;
		this.times_AnsChanged = times_AnsChanged;
		this.students_AnsweredRight = students_AnsweredRight;
		this.students_AnsweredWrong = students_AnsweredWrong;
		this.feedback = feedback;
		this.hintsUsed = hintsUsed;
	}
	
	Answer findDifficulty() {
		
		String difficultyFromFB[] = feedback.split(" ");
		int students_ChosenHard=0,students_ChosenEasy=0,students_ChosenMedium=0;
		
		for(int i=0; i<difficultyFromFB.length; i++)
		{
			if(difficultyFromFB[i].equals("EASY"))
				students_ChosenEasy++;
			else if(difficultyFromFB[i].equals("MEDIUM"))
				students_ChosenMedium++;
			else if(difficultyFromFB[i].equals("HARD"))
				students_ChosenHard++;
		}
		
		double difficultyIndex;
		double rightAnswerScore = (double)students_AnsweredRight/(double)studentsAttended;
		double wrongAnswerScore = (double)students_AnsweredWrong/(double)studentsAttended;
		
		difficultyIndex = rightAnswerScore;
		
		if(students_ChosenMedium < students_ChosenEasy || students_ChosenMedium < students_ChosenHard) {
			int overallFeedback = students_ChosenEasy - students_ChosenHard;
			if(overallFeedback > 0)
				difficultyIndex += 0.1;
			else if(overallFeedback < 0)
				difficultyIndex -= 0.1;
		}

		if(hintsUsed + times_AnsChanged > (4*studentsAttended))
			difficultyIndex -= 0.1;
		else if(hintsUsed + times_AnsChanged < (studentsAttended))
			difficultyIndex += 0.1;
		
		String levelOfDifficulty="";
		if(difficultyIndex < 0.4)
			levelOfDifficulty = "HARD";
		else if(difficultyIndex >= 0.4 && difficultyIndex <=0.6)
			levelOfDifficulty = "MEDIUM";
		else
			levelOfDifficulty = "EASY";
	
		return new Answer(levelOfDifficulty, "", rightAnswerScore*100, wrongAnswerScore*100);
	}
}

class FillUpsClass {
	
	int studentsAttended,hintsUsed,students_AnsweredRight,students_AnsweredWrong;
	String feedback;
	
	void setValues(int studentsAttended, int students_AnsweredRight, int students_AnsweredWrong,String feedback,int hintsUsed)
	{
		this.studentsAttended = studentsAttended;
		this.students_AnsweredRight = students_AnsweredRight;
		this.students_AnsweredWrong = students_AnsweredWrong;
		this.feedback = feedback;
		this.hintsUsed = hintsUsed;
	}
	
	Answer findDifficulty() {
		
		String difficultyFromFB[] = feedback.split(" ");
		int students_ChosenHard=0,students_ChosenEasy=0,students_ChosenMedium=0;
		
		for(int i=0; i<difficultyFromFB.length; i++)
		{
			if(difficultyFromFB[i].equals("EASY"))
				students_ChosenEasy++;
			else if(difficultyFromFB[i].equals("MEDIUM"))
				students_ChosenMedium++;
			else if(difficultyFromFB[i].equals("HARD"))
				students_ChosenHard++;
		}
		
		double difficultyIndex;
		double rightAnswerScore = (double)students_AnsweredRight/(double)studentsAttended;
		double wrongAnswerScore = (double)students_AnsweredWrong/(double)studentsAttended;
		
		difficultyIndex = rightAnswerScore ;
		
		if(students_ChosenMedium < students_ChosenEasy || students_ChosenMedium < students_ChosenHard) {
			int overallFeedback = students_ChosenEasy - students_ChosenHard;
			if(overallFeedback > 0)
				difficultyIndex += 0.1;
			else if(overallFeedback < 0)
				difficultyIndex -= 0.1;
		}

		if(hintsUsed > (2*studentsAttended))
			difficultyIndex -= 0.1;
		else if(hintsUsed < (studentsAttended/2))
			difficultyIndex += 0.1;
		
		String levelOfDifficulty="";
		if(difficultyIndex < 0.4)
			levelOfDifficulty = "HARD";
		else if(difficultyIndex >= 0.4 && difficultyIndex <=0.6)
			levelOfDifficulty = "MEDIUM";
		else
			levelOfDifficulty = "EASY";
	
		return new Answer(levelOfDifficulty, "", rightAnswerScore*100, wrongAnswerScore*100);

	}
}

class MatchClass {
	
	int studentsAttended,hintsUsed,students_AnsweredRight,students_AnsweredWrong,students_AnsweredPartiallyRight;
	String feedback;
	
	void setValues(int studentsAttended, int students_AnsweredRight, int students_AnsweredWrong,int students_AnsweredPartiallyRight,String feedback,int hintsUsed)
	{
		this.studentsAttended = studentsAttended;
		this.students_AnsweredRight = students_AnsweredRight;
		this.students_AnsweredWrong = students_AnsweredWrong;
		this.students_AnsweredPartiallyRight = students_AnsweredPartiallyRight;
		this.feedback = feedback;
		this.hintsUsed = hintsUsed;
	}
	
	Answer findDifficulty() {
		
		String difficultyFromFB[] = feedback.split(" ");
		int students_ChosenHard=0,students_ChosenEasy=0,students_ChosenMedium=0;
		
		for(int i=0; i<difficultyFromFB.length; i++)
		{
			if(difficultyFromFB[i].equals("EASY"))
				students_ChosenEasy++;
			else if(difficultyFromFB[i].equals("MEDIUM"))
				students_ChosenMedium++;
			else if(difficultyFromFB[i].equals("HARD"))
				students_ChosenHard++;
		}
		
		double difficultyIndex;
		double rightAnswerScore = (double)students_AnsweredRight/(double)studentsAttended;
		double wrongAnswerScore = (double)students_AnsweredWrong/(double)studentsAttended;
		double partialAnswerScore = (double)students_AnsweredPartiallyRight/(double)(2*studentsAttended);
		difficultyIndex = rightAnswerScore + partialAnswerScore;
		
		if(students_ChosenMedium < students_ChosenEasy || students_ChosenMedium < students_ChosenHard) {
			int overallFeedback = students_ChosenEasy - students_ChosenHard;
			if(overallFeedback > 0)
				difficultyIndex += 0.1;
			else if(overallFeedback < 0)
				difficultyIndex -= 0.1;
		}
		
		if(hintsUsed > (2*studentsAttended))
			difficultyIndex -= 0.1;
		else if(hintsUsed < (studentsAttended/2))
			difficultyIndex += 0.1;
		
		String levelOfDifficulty="";
		if(difficultyIndex < 0.4)
			levelOfDifficulty = "HARD";
		else if(difficultyIndex >= 0.4 && difficultyIndex <=0.6)
			levelOfDifficulty = "MEDIUM";
		else
			levelOfDifficulty = "EASY";
	
		return new Answer(levelOfDifficulty, "", rightAnswerScore*100, wrongAnswerScore*100);

	}
}
