package jp.kobespiral.narimatsu.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.narimatsu.todo.dto.ToDoForm;
import jp.kobespiral.narimatsu.todo.entity.ToDo;
import jp.kobespiral.narimatsu.todo.exception.ToDoAppException;
import jp.kobespiral.narimatsu.todo.repository.ToDoRepository;

@Service
public class ToDoService {
   @Autowired
   ToDoRepository tRepo;
   /**
    * ToDoを作成する (C)
    * @param form
    * @return
    */
   public ToDo createToDo(String mid, ToDoForm form) {
       ToDo t = form.toEntity();
       t.setMid(mid);
       return tRepo.save(t);
   }
   /**
    * ToDoを取得する (R)
    * @param mid
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_TODO_EXISTS, seq + ": No such ToDo exists"));
       return t;
   }
   /**
    * midのToDoリストを取得する (R)
    * @return
    */
   public List<ToDo> getToDoList(String mid) {
       return tRepo.findByDoneAndMid(false, mid);
   }
   /**
    * midのDoneリストを取得する (R)
    * @return
    */
    public List<ToDo> getDoneList(String mid) {
        return tRepo.findByDoneAndMid(true, mid);
    }
    /**
    * 全員のToDoリストを取得する (R)
    * @return
    */
   public List<ToDo> getToDoList() {
       return tRepo.findByDone(false);
    }
    /**
    * 全員のDoneリストを取得する (R)
    * @return
    */
   public List<ToDo> getDoneList() {
       return tRepo.findByDone(true);
    }

    public ToDo doneToDo(String mid, Long seq) {
        ToDo t = getToDo(seq);
        Date doneAt = new Date();
        t.setDoneAt(doneAt);
        t.setDone(true);
        return tRepo.save(t);
    }
   
    public void deleteToDo(String mid, Long seq) {
        ToDo t = getToDo(seq);
        tRepo.delete(t);
    }

    public ToDo updateToDo(String mid, Long seq, ToDoForm form) {
        ToDo t = getToDo(seq);
        t.setTitle(form.getTitle());
        return tRepo.save(t);
    }
}