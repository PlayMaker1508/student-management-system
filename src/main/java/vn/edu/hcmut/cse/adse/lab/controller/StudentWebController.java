package vn.edu.hcmut.cse.adse.lab.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Luu y: su dung @Controller, KHONG dung → @RestController
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import vn.edu.hcmut.cse.adse.lab.service.StudentService;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import java.util.List;
@Controller
@RequestMapping("/students")
public class StudentWebController {
    @Autowired
    private StudentService service;
// Route: GET http://localhost:8080/students
@GetMapping
public String getAllStudents(@RequestParam(required = false) String keyword, Model model) {
List<Student> students;
if (keyword != null && !keyword.isEmpty()) {
// Can viet them ham searchByName trong Service/Repository
students = service.searchByName(keyword);
} else {
students = service.getAll();
}
model.addAttribute("dsSinhVien", students);
return "students";
}

// Detail view
@GetMapping("/{id}")
public String getStudentDetail(@PathVariable String id, Model model) {
    Student student = service.getById(id);
    if (student == null) {
        return "redirect:/students";
    }
    model.addAttribute("student", student);
    return "student-detail";
}

// Show add form
@GetMapping("/add")
public String showAddForm(Model model) {
    model.addAttribute("student", new Student());
    model.addAttribute("isEdit", false);
    return "student-form";
}

// Show edit form
@GetMapping("/{id}/edit")
public String showEditForm(@PathVariable String id, Model model) {
    Student student = service.getById(id);
    if (student == null) {
        return "redirect:/students";
    }
    model.addAttribute("student", student);
    model.addAttribute("isEdit", true);
    return "student-form";
}

// Handle add
@PostMapping
public String createStudent(@Valid @ModelAttribute Student student, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ: " + result.getAllErrors().get(0).getDefaultMessage());
        return "redirect:/students/add";
    }
    if (service.existsById(student.getId())) {
        redirectAttributes.addFlashAttribute("error", "ID sinh viên đã tồn tại!");
        return "redirect:/students/add";
    }
    try {
        service.save(student);
        redirectAttributes.addFlashAttribute("message", "Thêm sinh viên thành công!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm sinh viên: " + e.getMessage());
    }
    return "redirect:/students";
}

// Handle edit
@PostMapping("/{id}")
public String updateStudent(@PathVariable String id, @Valid @ModelAttribute Student student, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ: " + result.getAllErrors().get(0).getDefaultMessage());
        return "redirect:/students/" + id + "/edit";
    }
    student.setId(id);
    try {
        service.update(student);
        redirectAttributes.addFlashAttribute("message", "Cập nhật sinh viên thành công!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật sinh viên: " + e.getMessage());
    }
    return "redirect:/students";
}

// Handle delete
@PostMapping("/{id}/delete")
public String deleteStudent(@PathVariable String id, RedirectAttributes redirectAttributes) {
    try {
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa sinh viên thành công!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sinh viên: " + e.getMessage());
    }
    return "redirect:/students";
}
}
