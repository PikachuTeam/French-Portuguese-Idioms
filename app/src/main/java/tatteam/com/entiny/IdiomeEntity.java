package tatteam.com.entiny;

import java.util.List;

/**
 * Created by hoaba on 9/7/2015.
 */
public class IdiomeEntity {
    public int ID;
    public String Letter;
    public String Phrase;
    public String Description;
    public String Definition;
    public String Example;
    public List<String> listSynonym;
    public List<String> listAntonym;

    public int isFavorite = 0;
    public int isRecent = 0;
    public boolean isHeader = false;
    public boolean isInData = false;
    public float index;

}
