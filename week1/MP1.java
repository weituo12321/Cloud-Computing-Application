import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
    List<String> strLists = Arrays.asList(stopWordsArray);
    HashMap<String, Integer> mp= new HashMap<String, Integer>();
    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public void divideSentence(String s){
        StringTokenizer st = new StringTokenizer(s, delimiters);
        //List<String> strLists = Arrays.asList(stopWordsArray);
        while(st.hasMoreTokens())
        {
            //st.nextToken();
            //System.out.print(st.nextToken()+"     ");
            String tmp = st.nextToken().toLowerCase().trim();
            if(tmp== null || tmp.length() == 0) break;
            //System.out.println(tmp);
            if(!strLists.contains(tmp))
            {
                if(mp.containsKey(tmp))
                {
                    Integer val = mp.get(tmp);
                    val++;
                    mp.put(tmp,val);
                }
                else
                {
                    mp.put(tmp,1);
                }
            }
        }
        //System.out.println();
        // String[] strlist = new String[3];
        // return strlist;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
        //TODO
        Integer[] index = getIndexes();
        List<Integer> idxlist = Arrays.asList(index);
        Collections.sort(idxlist);
        FileReader a = new FileReader(this.inputFileName);
        BufferedReader b = new BufferedReader(a);
        int countLineNum = 0;

            //System.out.println(i);
        String oneLine;
        while((oneLine = b.readLine())!= null)
        {
            countLineNum++;
            if(idxlist.contains(countLineNum))
            {
                divideSentence(oneLine);
                //System.out.print(countLineNum);
                //System.out.println(oneLine);
            }
        }
        TreeMap<Integer, String> sortedMap = new TreeMap<Integer, String>(Collections.reverseOrder());
        for (Map.Entry entry : mp.entrySet()) {
            sortedMap.put((Integer) entry.getValue(), (String)entry.getKey());
        }
        int count = 0;
        Iterator it = sortedMap.entrySet().iterator();
        String[] result = new String[20];
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            result[count] = pair.getValue().toString();
            System.out.println(result[count]+" " +pair.getKey());
            count++;
            if(count==20)break;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
