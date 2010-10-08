package org.openmeetings.utils.stringhandlers;

import java.util.Iterator;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import org.openmeetings.app.remote.red5.EmoticonsManager;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;

public class ChatString {
	
	private static final Logger log = Red5LoggerFactory.getLogger(ChatString.class, ScopeApplicationAdapter.webAppRootKey);
	
	private static ChatString instance = null;
	
	private ChatString() {}
	
	public static synchronized ChatString getInstance(){
		if (instance == null){
			instance = new ChatString();
		}
		return instance;
	}
	
	//Testing stuff
//	LinkedList<String> emot = new LinkedList<String>();
//	emot.add("smily");
//	emot.add("\\:\\)");
//	emot.add("y");
//	log.error("CHECK EMOT: "+ emot.get(0));
//	
//	list = this.splitStr(list,emot);
//	
//	
//	emot = new LinkedList<String>();
//	emot.add("sad");
//	emot.add("\\:\\(");
//	emot.add("y");
//	log.error("CHECK EMOT: "+ emot.get(0));	
//	
//	list = this.splitStr(list,emot);	
	
	
	public LinkedList<String[]> parseChatString(String message) {
		try {
			LinkedList<String[]> list = new LinkedList<String[]>();
			
			//log.debug("this.link(message) "+this.link(message));
			
			String[] messageStr = {"text",message};
			list.add(messageStr);
			
			for (Iterator<LinkedList<String>> iter = EmoticonsManager.getEmotfilesList().iterator();iter.hasNext();){
				LinkedList<String> emot = iter.next();
				
				//log.error("CHECK EMOT: "+ emot.get(0) + emot.get(1) + emot.size());
				list = this.splitStr(list,emot.get(0), emot.get(1), emot.get(emot.size()-2));
				
				if (emot.size()>4) {
					//log.error("CHECK EMOT ASIAN: "+ emot.get(0) + emot.get(2) + emot.size());
					list = this.splitStr(list,emot.get(0), emot.get(2), emot.get(emot.size()-2));
				}
			}			
			
//			log.debug("#########  ");
//			for (Iterator<String[]> iter = list.iterator();iter.hasNext();){
//				String[] stringArray = iter.next();
//				//stringArray[1] = this.link(stringArray[1]);
//				log.debug(stringArray[0]+"||"+stringArray[1]);
//			}			
			
			return list;
			
		} catch (Exception err) {
			log.error("[parseChatString]",err);
		}
		return null;
	}
	

	
	private LinkedList<String[]> splitStr (LinkedList<String[]> list, String image, String regexp, String spaces){
		
		LinkedList<String[]> newList = new LinkedList<String[]>();
		
		for (Iterator<String[]> iter = list.iterator();iter.hasNext();){
			
			String[] messageObj = iter.next();
			String messageTye = messageObj[0];
			
			if (messageTye.equals("text")){
				String messageStr = messageObj[1];
				
				String[] newStr = messageStr.split(regexp);
				
				for (int k=0;k<newStr.length;k++) {
					String[] textA = {"text",newStr[k]};
					newList.add(textA);
					if (k+1!=newStr.length){
						String[] imageA = {"image",image, spaces, regexp.replace("\\", "") };
						newList.add(imageA);
					}
				}
			} else {

				newList.add(messageObj);
			}

		}
			
		return newList;
	}
	
	public void replaceAllRegExp(){
		try {
			LinkedList<LinkedList<String>> emotfilesList = EmoticonsManager.getEmotfilesList();
			LinkedList<LinkedList<String>> emotfilesListNew = new LinkedList<LinkedList<String>>();
			for (Iterator<LinkedList<String>> iter = emotfilesList.iterator();iter.hasNext();){
				LinkedList<String> emot = iter.next();
				
				//log.error("FILE: "+emot.get(0));
				String westernMeaning = this.checkforRegex(emot.get(1));
				emot.set(1, westernMeaning);
				//log.error("westernMeaning "+westernMeaning);
				if (emot.size()>2){
					String asianMeaning = this.checkforRegex(emot.get(2));
					emot.set(2, asianMeaning);
					//log.error("westernMeaning "+asianMeaning);
				}
				emotfilesListNew.add(emot);
			}
			EmoticonsManager.setEmotfilesList(emotfilesListNew);
		} catch (Exception err) {
			log.error("[replaceAllRegExp]",err);
		}
	}
	
	  /**
	  * Replace characters having special meaning in regular expressions
	  *
	  */
	  private String checkforRegex(String aRegexFragment){
	    final StringBuilder result = new StringBuilder();

	    final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
	    char character =  iterator.current();
	    while (character != CharacterIterator.DONE ){
	      /*
	      * All literals need to have backslashes doubled.
	      */
	      if (character == '.') {
	        result.append("\\.");
	      }
	      else if (character == '\\') {
	        result.append("\\\\");
	      }
	      else if (character == '?') {
	        result.append("\\?");
	      }
	      else if (character == '*') {
	        result.append("\\*");
	      }
	      else if (character == '+') {
	        result.append("\\+");
	      }
	      else if (character == '&') {
	        result.append("\\&");
	      }
	      else if (character == ':') {
	        result.append("\\:");
	      }
	      else if (character == '{') {
	        result.append("\\{");
	      }
	      else if (character == '}') {
	        result.append("\\}");
	      }
	      else if (character == '[') {
	        result.append("\\[");
	      }
	      else if (character == ']') {
	        result.append("\\]");
	      }
	      else if (character == '(') {
	        result.append("\\(");
	      }
	      else if (character == ')') {
	        result.append("\\)");
	      }
	      else if (character == '^') {
	        result.append("\\^");
	      }
	      else if (character == '$') {
	        result.append("\\$");
	      }
	      else if (character == '|') {
	        result.append("\\|");
	      }
	      else {
	        //the char is not a special one
	        //add it to the result as is
	        result.append(character);
	      }
	      character = iterator.next();
	    }
	    return result.toString();
	  }	
	  
	  public String link(String input) {
			try {
				
				String tReturn = "";
				
				String parts[] = input.split(" ");
				
				for (int t=0;t<parts.length;t++) {
					
					String text = parts[t];
		
					//System.out.println("Part 1 "+text);
					
					Matcher matcher = Pattern.compile("(^|[ \t\r\n])((ftp|http|https|gopher|mailto|news|nntp|telnet|wais|file|prospero|aim|webcal):(([A-Za-z0-9$_.+!*(),;/?:@&~=-])|%[A-Fa-f0-9]{2}){2,}(#([a-zA-Z0-9][a-zA-Z0-9$_.+!*(),;/?:@&~=%-]*))?([A-Za-z0-9$_+!*();/?:~-]))").matcher(text);

					if (matcher.find()) {
						text = matcher.replaceFirst("<u><FONT color=\"#0000CC\"><a href='" + text + "'>" + text
								+ "</a></FONT></u>");

					}
					
					//System.out.println("Part 2 "+text);
					
					if (t != 0) {
						tReturn += " ";
					}
					
					tReturn += text;
				
				}
				
				return tReturn;
				
			} catch (Exception e) {
				log.error("[link]",e);
			}
			return "";
		}
}
