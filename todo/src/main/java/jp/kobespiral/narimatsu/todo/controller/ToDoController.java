package jp.kobespiral.narimatsu.todo.controller;

import java.util.List;

import jp.kobespiral.narimatsu.todo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.narimatsu.todo.dto.MemberForm;
import jp.kobespiral.narimatsu.todo.dto.ToDoForm;
import jp.kobespiral.narimatsu.todo.entity.ToDo;
import jp.kobespiral.narimatsu.todo.service.ToDoService;

@Controller
@RequestMapping("/ToDo")
public class ToDoController {
   @Autowired
   ToDoService tService;
   @Autowired
   MemberService mService; 
   
   MemberForm memberForm; 

   @GetMapping("/login")
   String login(MemberForm form, Model model){
       model.addAttribute("MemberForm", form);
       return "index"; 
   }

   @PostMapping("/login")
   String checkId(@ModelAttribute(name = "MemberForm") MemberForm mForm, @ModelAttribute(name = "ToDoForm") ToDoForm tForm, BindingResult bindingResult, Model model){  
       if (bindingResult.hasErrors()) {
            // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
            return login(mForm, model);
       }   
       mService.getMember(mForm.getMid());
       memberForm = mForm;
       return showToDo(tForm, model);
   }

   String showToDo(ToDoForm tForm, Model model){  
       model.addAttribute("MemberForm", memberForm);
       List<ToDo> ToDoList = tService.getToDoList(memberForm.getMid());
       model.addAttribute("ToDoList", ToDoList);
       List<ToDo> doneList = tService.getDoneList(memberForm.getMid());
       model.addAttribute("DoneList", doneList);
       model.addAttribute("ToDoForm", tForm);
       return "redirect:" +memberForm.getMid()+"/list/";
       //return "list";
   }
   
   @PostMapping("/regist")
   String createToDo(@Validated @ModelAttribute(name = "ToDoForm") ToDoForm tForm, BindingResult bindingResult, Model model) {  
        if (bindingResult.hasErrors()) {
                // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
                return showToDo(tForm, model);
        }   
       tService.createToDo(memberForm.getMid(), tForm);
       return showToDo(tForm, model);
   }

   @GetMapping("/done/{seq}")
   String doneToDo(@ModelAttribute(name = "ToDoForm") ToDoForm tForm, @PathVariable Long seq, Model model) {
       tService.doneToDo(memberForm.getMid(), seq);
       return showToDo(tForm, model);
   }

   @GetMapping("/all")
   String showAllToDo(Model model){
       List<ToDo> allToDoList = tService.getToDoList();
       model.addAttribute("allToDoList", allToDoList);
       List<ToDo> allDoneList = tService.getDoneList();
       model.addAttribute("allDoneList", allDoneList);
       return "alllist";
   }

}