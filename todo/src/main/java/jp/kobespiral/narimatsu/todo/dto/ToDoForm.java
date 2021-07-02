package jp.kobespiral.narimatsu.todo.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.kobespiral.narimatsu.todo.entity.ToDo;
import lombok.Data;

@Data
public class ToDoForm {
    @NotBlank
    @Size(min = 1, max = 64)
    String title; //タイトル．最大64文字

    public ToDo toEntity() {
        ToDo m = new ToDo();
        m.setTitle(title);
        m.setDone(false);
        Date date = new Date();
        m.setCreatedAt(date);
        return m;
    }
}