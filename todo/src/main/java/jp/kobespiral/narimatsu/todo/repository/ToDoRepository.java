package jp.kobespiral.narimatsu.todo.repository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import jp.kobespiral.narimatsu.todo.entity.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long>{
    List<ToDo> findAll();
    List<ToDo> findByDone(boolean done);
    List<ToDo> findByMid(String mid);
    List<ToDo> findByDoneAndMid(boolean done,String mid);
}
