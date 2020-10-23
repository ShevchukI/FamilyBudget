package com.peryite.familybudget.dbhelper;

import android.content.Context;

import com.peryite.familybudget.dbhelper.dao.CategoryDAO;
import com.peryite.familybudget.dbhelper.dao.ItemDAO;
import com.peryite.familybudget.entities.BudgetCategory;

import java.util.ArrayList;
import java.util.List;

public class DBConverter {
    private static DBConverter ourInstance = null;

    private Context context;

    public static DBConverter getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DBConverter(context);
        }
        return ourInstance;
    }


    private CategoryDAO categoryDAO;
    private ItemDAO itemDAO;

    private DBConverter(Context context) {
        this.context = context;

        categoryDAO = BudgetDB.getInstance(context.getApplicationContext()).categoryDAO();
        itemDAO = BudgetDB.getInstance(context.getApplicationContext()).itemDAO();
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public ItemDAO getItemDAO(){
        return itemDAO;
    }


//    public void fillDataBase(List<Chapter> chapters) {
//        for (Chapter chapter : chapters) {
//            insertChapter(chapter);
//        }
//    }

    public void clearDataBase() {
        categoryDAO.deleteAll();
    }

    public List<BudgetCategory> getAllCategories() {
        //List<BudgetCategory> budgetCategories = new ArrayList<>();
       // List<BudgetCategory> chapterEntities = getCategoryDAO().getAllEntity();
//        for (ChapterEntity chapterEntity : chapterEntities) {
//            Chapter chapter = getChapterFromEntity(chapterEntity);
//            chapters.add(chapter);
//        }
        return getCategoryDAO().getAllEntity();
    }

//    private Chapter getChapterFromEntity(ChapterEntity chapterEntity){
//        Chapter chapter = new Chapter();
//        chapter.setId(chapterEntity.getId());
//        chapter.setName(chapterEntity.getName());
//        List<Task> tasks = new ArrayList<>();
//        List<TaskEntity> taskEntities = getTaskDAO().getAllEntityByChapterId(chapter.getId());
//        for (TaskEntity taskEntity : taskEntities) {
//            Task task = getTaskFromEntity(taskEntity);
//            tasks.add(task);
//        }
//        chapter.setTasks(tasks);
//        return chapter;
//    }
//
//    private Task getTaskFromEntity(TaskEntity taskEntity){
//        Task task = new Task();
//        task.setId(taskEntity.getId());
//        task.setName(taskEntity.getName());
//        task.setComplete(taskEntity.isComplete());
//        return task;
//    }
//
//    private long insertChapter(Chapter chapter) {
//        ChapterEntity chapterEntity = new ChapterEntity();
//        chapterEntity.setName(chapter.getName());
//        long id = chapterDAO.insert(chapterEntity);
//        if (chapter.getTasks().isEmpty()) {
//            return id;
//        } else {
//            for (Task task : chapter.getTasks()) {
//                task.setId(insertTask(task, id));
//            }
//            return id;
//        }
//    }
//
//    private long insertTask(Task task, long chapterId) {
//        TaskEntity taskEntity = new TaskEntity();
//        taskEntity.setName(task.getName());
//        taskEntity.setComplete(task.isComplete());
//        taskEntity.setChapterId(chapterId);
//        long id = taskDAO.insert(taskEntity);
//        return id;
//    }
//
//    public int updateTask(Task task){
//        TaskEntity taskEntity = new TaskEntity();
//        taskEntity.setId(task.getId());
//        taskEntity.setName(task.getName());
//        taskEntity.setComplete(task.isComplete());
//        return getTaskDAO().update(taskEntity);
//    }

    public boolean isEmptyDataBase(){
        return getCategoryDAO().getAllEntity().isEmpty();
    }
}
