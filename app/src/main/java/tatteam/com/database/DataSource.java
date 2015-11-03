package tatteam.com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.entiny.IdiomeEntity;
import tatteam.com.entiny.LetterEntity;
import tatteam.com.entiny.TopicEntity;


/**
 * Created by ThanhNH on 2/1/2015.
 */
public class DataSource {

    private static DataSource instance;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private DataSource() {
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    //import db from assets if need and open connection
    public void createDatabaseIfNeed() {
        this.openConnection();
    }

    private void openConnection() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            AssetDatabaseOpenHelper assetDatabaseOpenHelper = new AssetDatabaseOpenHelper(context);
            sqLiteDatabase = assetDatabaseOpenHelper.openDatabase();
        }
    }

    private void closeConnection() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }


    public List<LetterEntity> getLetters() {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Letters", null);
        List<LetterEntity> listLetter = new ArrayList<LetterEntity>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LetterEntity letterEntity = new LetterEntity();
            letterEntity.id = cursor.getInt(0);
            letterEntity.letter = cursor.getString(1);
            listLetter.add(letterEntity);
            cursor.moveToNext();
        }
        cursor.close();
        return listLetter;
    }

    public List<LetterEntity> getLettersTopic() {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT distinct(substr(Concept,1,1)) FROM Concepts", null);
        List<LetterEntity> listLetter = new ArrayList<LetterEntity>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LetterEntity letterEntity = new LetterEntity();

            letterEntity.letter = cursor.getString(0);
            listLetter.add(letterEntity);
            cursor.moveToNext();
        }
        cursor.close();
        return listLetter;
    }

    public List<IdiomeEntity> getListIdiomByPharse(List<String> phrases) {
        List<IdiomeEntity> list = new ArrayList<>();
        if (phrases == null) return list;
        String string = "(";
        if (phrases.size() > 1) {
            for (int i = 0; i < phrases.size() - 1; i++)
                string = string + "\"" + phrases.get(i) + "\"" + ",";
            string = string + "\"" + phrases.get(phrases.size() - 1) + "\"" + ")";
        } else string = string + "\"" + phrases.get(0) + "\"" + ")";


        String query = "SELECT * FROM Phrases where Phrase IN " + string;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                IdiomeEntity idiome = new IdiomeEntity();


                idiome.Letter = cursor.getString(2);
                idiome.Phrase = cursor.getString(3);
                idiome.Description = cursor.getString(4);
                idiome.Definition = cursor.getString(5);
                idiome.Example = cursor.getString(6);
                idiome.listSynonym = splitList(cursor.getString(7));
                idiome.listAntonym = splitList(cursor.getString(8));
                idiome.isFavorite = cursor.getInt(9);
                idiome.isRecent = cursor.getInt(10);
                idiome.isInData = true;

                list.add(idiome);
                cursor.moveToNext();

            }
            cursor.close();

        }
        return list;

    }

    public IdiomeEntity getIdiomByPharse(String pharse) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Phrases where Phrase = ? limit 1", new String[]{pharse});
        IdiomeEntity idiome = new IdiomeEntity();
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            idiome.Letter = cursor.getString(2);
            idiome.Phrase = cursor.getString(3);
            idiome.Description = cursor.getString(4);
            idiome.Definition = cursor.getString(5);
            idiome.Example = cursor.getString(6);
            idiome.listSynonym = splitList(cursor.getString(7));
            idiome.listAntonym = splitList(cursor.getString(8));
            idiome.isFavorite = cursor.getInt(9);
            idiome.isRecent = cursor.getInt(10);
            idiome.isInData = true;
            cursor.close();
        }

        return idiome;
    }

    public List<IdiomeEntity> getListFavorite() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Phrases where IsFavorite = 1 order by Phrase", null);
        List<IdiomeEntity> list = new ArrayList<>();
        if (cursor.getCount() == 0) return list;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            IdiomeEntity idiome = new IdiomeEntity();
            idiome.Letter = cursor.getString(2);
            idiome.Phrase = cursor.getString(3);
            idiome.Description = cursor.getString(4);
            idiome.Definition = cursor.getString(5);
            idiome.Example = cursor.getString(6);
            idiome.listSynonym = splitList(cursor.getString(7));
            idiome.listAntonym = splitList(cursor.getString(8));
            idiome.isFavorite = cursor.getInt(9);
            idiome.isRecent = cursor.getInt(10);
            idiome.isInData = true;

            list.add(idiome);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<IdiomeEntity> getIdiomsByLetter(String letter) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Phrases where Phrases.Letter =?", new String[]{letter});
        List<IdiomeEntity> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            IdiomeEntity idiome = new IdiomeEntity();

            idiome.Letter = cursor.getString(2);
            idiome.Phrase = cursor.getString(3);
            idiome.Description = cursor.getString(4);
            idiome.Definition = cursor.getString(5);
            idiome.Example = cursor.getString(6);
            idiome.listSynonym = splitList(cursor.getString(7));
            idiome.listAntonym = splitList(cursor.getString(8));
            idiome.isFavorite = cursor.getInt(9);
            idiome.isRecent = cursor.getInt(10);
            idiome.isInData = true;


            list.add(idiome);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<IdiomeEntity> getIdiomsRecent(int count) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Phrases where IsRecent > 0 order by IsRecent desc limit ?", new String[]{"" + count});
        List<IdiomeEntity> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst())
            while (!cursor.isAfterLast()) {
                IdiomeEntity idiome = new IdiomeEntity();
                idiome.Letter = cursor.getString(2);
                idiome.Phrase = cursor.getString(3);
                idiome.Description = cursor.getString(4);
                idiome.Definition = cursor.getString(5);
                idiome.Example = cursor.getString(6);
                idiome.listSynonym = splitList(cursor.getString(7));
                idiome.listAntonym = splitList(cursor.getString(8));
                idiome.isFavorite = cursor.getInt(9);
                idiome.isRecent = cursor.getInt(10);
                idiome.isInData = true;

                list.add(idiome);
                cursor.moveToNext();
            }
        cursor.close();

        return list;
    }

    public List<TopicEntity> getTopicsByLetter(String letter) {
        String s = letter + "%";

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Concepts where Concept LIKE ? ", new String[]{s});


        List<TopicEntity> list = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TopicEntity topic = new TopicEntity();

            topic.ID = cursor.getInt(0);
            topic.Concept = cursor.getString(1);
            topic.listPhrase = splitList(cursor.getString(2));

            list.add(topic);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public TopicEntity getTopicsByConcept(String concept) {


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Concepts where Concept = ? limit 1 ", new String[]{concept});


        cursor.moveToFirst();

        TopicEntity topic = new TopicEntity();

        topic.ID = cursor.getInt(0);
        topic.Concept = cursor.getString(1);
        topic.listPhrase = splitList(cursor.getString(2));


        cursor.close();
        return topic;
    }

    public void destroy() {
        closeConnection();
        instance = null;
    }


    public List<String> splitList(String s) {
        String[] splitlist = new String[]{};
        List<String> list = new ArrayList<>();
        if (s != null) {
            splitlist = s.split(";");
        }
        for (int i = 0; i < splitlist.length; i++) {
            list.add(splitlist[i]);
        }
        return list;

    }


    public void changeFavorite(String phrase) {
        IdiomeEntity entity = getIdiomByPharse(phrase);
        int favorite = entity.isFavorite;
        if (favorite == 1) {
            Cursor cursor = sqLiteDatabase.rawQuery("UPDATE Phrases SET isFavorite= 0 WHERE Phrase =?", new String[]{phrase});
            cursor.moveToFirst();
            cursor.close();
        } else {
            Cursor cursor = sqLiteDatabase.rawQuery("UPDATE Phrases SET isFavorite= 1 WHERE Phrase =?", new String[]{phrase});
            cursor.moveToFirst();
            cursor.close();
        }


        ;
    }

    public void updateRecent(String phrase) {

        int newRecent = getMaxRecent() + 1;
        String value = newRecent + "";
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE Phrases SET isRecent= ? WHERE Phrase =?", new String[]{value, phrase});
        cursor.moveToFirst();
        cursor.close();

    }

    public int getMaxRecent() {
        int max;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Phrases where IsRecent > 0 order by IsRecent desc limit 1", null);
        if (cursor.getCount() == 0) return 0;
        cursor.moveToFirst();

        max = cursor.getInt(10);

        cursor.close();
        return max;

    }


}
